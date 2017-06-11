import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
/**
 * 
 * @author Kevin Dai & Terrance Williams
 *
 */
// converts drawn graphics to osu code and overwrites file
public class GraphicsToSlider {
	// global variables
	private File osuFile;
	private Point sliderStart, sliderEnd;
	private ArrayList<Point> anchors;
	
	private double beats;
	private int pixelLength;
	
	private double[] lastTimingPoint = new double[8];
	private double[] lastInheritedPoint = new double[8];
	private double lastNoteOffset;
	
	// new line for .osu file
	public final static String nl = System.getProperty("line.separator");
	
	
	public GraphicsToSlider(DrawArea drawArea, File file, double beats) {
		// variable initialization
		this.osuFile = file;
		this.beats = beats;
		this.sliderStart = drawArea.getSliderStart();
		this.sliderEnd = drawArea.getSliderEnd();
		this.anchors = drawArea.getAnchorPoints();		
		this.lastInheritedPoint = getLastInheritedPoint();	
		this.lastNoteOffset = getLastHitObjectOffset();
		this.pixelLength = getPixelLength();
		this.lastTimingPoint = getLastTimingPoint();
	}
	
	
	// converts graphic to osu formatted string
	public String getSliderCode() {
				
		int startX = (int) sliderStart.getX();
		int startY = (int) sliderStart.getY();
		ArrayList<Integer> anchorX = new ArrayList<Integer>();
		ArrayList<Integer> anchorY = new ArrayList<Integer>();
		
		// gets all x and y values of anchors
		for (int i = 0; i < anchors.size(); i++) {
			anchorX.add((int) anchors.get(i).getX());
			anchorY.add((int) anchors.get(i).getY());
		}
		
		// calculates offset based off global variables
		int offset = (int) (lastNoteOffset + Math.round(lastTimingPoint[1]));
				
		// adds all code elements into string
		String middleAnchors = startX + "," + startY + "," + offset + ",2,0,B";
		middleAnchors += "|" + anchorX.get(0) + ":" + anchorY.get(0) + "|" + anchorX.get(1) + ":" + anchorY.get(1);
		for (int coor = 2; coor < anchors.size() - 2; coor++) {
			if (isRed(anchors.get(coor - 2), anchors.get(coor - 1), anchors.get(coor), anchors.get(coor + 1), anchors.get(coor + 2))) {
				middleAnchors = middleAnchors + "|" + anchorX.get(coor) + ":" + anchorY.get(coor) + "|" + anchorX.get(coor) + ":" + anchorY.get(coor);
			} else {
				middleAnchors = middleAnchors + "|" + anchorX.get(coor) + ":" + anchorY.get(coor);
			}
		}
		middleAnchors += ",1," + this.pixelLength;
		
		return middleAnchors;
	}
	
	// formats a timing code string using global variables
	public String getTimingCode() throws FileNotFoundException {
		String timing = "";
				
		// calculates offset
		int offset = (int) (lastNoteOffset + Math.round(lastTimingPoint[1]));
		// calculates slider velocity
		double sv = pixelLength / (getSliderMultiplier() * beats * 100);
		sv = (-100) / sv;
		
		// formats string
		timing += offset + "," + Math.round(sv) + "," + (int) lastTimingPoint[2] + ",2," + (int) lastInheritedPoint[4] + 
				"," + (int) lastInheritedPoint[5] + ","  + (int) lastInheritedPoint[6] + "," + (int) lastInheritedPoint[7];
		
		return timing;
	}
	
	// calculates pixel length using anchor points
	public int getPixelLength() {
		int pixelLength;
		double dPixelLength = 0;
		
		// adds up all distances between middle anchors
		for (int curAnch = 0; curAnch < anchors.size() - 1; curAnch++) {
			double distance = anchors.get(curAnch).distance(anchors.get(curAnch + 1));
			dPixelLength += distance;
		}
		
		// adds distance from first anchor
		try {
			dPixelLength += sliderStart.distance(anchors.get(0));
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		// adds distance to last anchor
		dPixelLength += anchors.get(anchors.size() - 1).distance(sliderEnd);
		
		// double average = dPixelLength / (anchors.size() + 1);
		
		// tries to correct error
		dPixelLength *= 0.915;
		
		// casts to int
		pixelLength = (int) dPixelLength;
		
		return pixelLength;
	}	
	
	// parses osu file to find the last timing line (red lines)
	public double[] getLastTimingPoint() {
		String line;
		String timings[] = new String[8]; 
		double pointElements[] = new double[8];
		boolean isTiming = false;
		
		// reads file
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(osuFile), "UTF-8"))){
			while ((line = reader.readLine()) != null) {
				// identifies timing points section
				if (line.contains("[TimingPoints]")) {
					isTiming = true;
				}
				if (isTiming) {
					// checks if it is timing point
					if (!line.contains("[TimingPoints]") && !line.contains("[Colours]") && !line.contains("[HitObjects]") && line.length() > 0  && !line.split(",")[1].contains("-")) {
						timings = line.split(",");
					// stops once end of section is reached
					} else if (line.contains("[Colours]") || line.contains("[HitObjects]") || line.length() == 0) {
						isTiming = false;
						break;
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// convert strings to doubles
		for (int i = 0; i < timings.length; i++) {
			pointElements[i] = Double.parseDouble(timings[i]);
		}
		
		return pointElements;
	}
	
	// parses osu file to find last inherited timing line (green lines)
	public double[] getLastInheritedPoint() {
		String line;
		String timings[] = new String[8];
		double pointElements[] = new double[8];
		boolean isTiming = false;
		
		// reads file
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(osuFile), "UTF-8"))) {
			while ((line = reader.readLine()) != null) {
				// identifies timing points section
				if (line.contains("[TimingPoints]")) {
					isTiming = true;
				}
				if (isTiming) {
					// checks if it is inherited point
					if (!line.contains("[TimingPoints]") && !line.contains("[Colours]") && !line.contains("[HitObjects]") && line.length() > 0) {
						timings = line.split(",");
					// stops once end of section is reached
					} else if (line.contains("[Colours]") || line.contains("[HitObjects]") || line.length() == 0) {
						isTiming = false;
						break;
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// converts strings to doubles
		for (int i = 0; i < timings.length; i++) {
			pointElements[i] = Double.parseDouble(timings[i]);
		}
		
		return pointElements;
	}
	
	// parses osu file for last note
	public double getLastHitObjectOffset() {
		String line;
		double offset = 0;
		boolean isHitObject = false;
		
		// reads file
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(osuFile), "UTF-8"))) {
			while ((line = reader.readLine()) != null) {
				// identifies hit objects section
				if (line.contains("[HitObjects]")) {
					isHitObject = true;
				}
				// checks if is hit object
				if (isHitObject) {
					if (!line.contains("[HitObjects]") && line.length() > 0) {
						// parse offset into double
						String[] data = line.split(",");
						offset = Double.parseDouble(data[2]);
					} else if (line.length() == 0) {
						isHitObject = false;
						break;
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return offset;
	}
	
	// parses osu file for slider multiplier in file header
	public double getSliderMultiplier() throws FileNotFoundException {
		String line;
		String sm = "";
		
		// reads file
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(osuFile), "UTF-8"))) {
			while ((line = reader.readLine()) != null) {
				if (line.contains("SliderMultiplier:")) {
					sm = line.substring(17);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// convert string to double
		return Double.parseDouble(sm);		
	}
	
	
	// find the angle between a specific point (Point b)
	public double getAngle(Point a, Point b, Point c) {
		double d1 = a.distance(b);
		double d2 = b.distance(c);
		double d3 = c.distance(a);
		
		double preAngle = ((d1 * d1) + (d2 * d2) + (d3 * d3)) / (2 * d1 * d2);
		
		return Math.toDegrees(Math.acos(preAngle));
	}
	
	// check if anchor should be red (angular)
	public boolean isRed (Point a, Point b, Point c, Point d, Point e) {
		if (getAngle(b, c, d) < 120 && getAngle(a, b, c) >= 120 && getAngle(c, d, e) >= 120) {
				return true;
		}
		else {
			return false;
		}
	};	
	
	// overwrites file with added slider and timing point
	public boolean writeToFile() {
		String output = "";
		String line;
		boolean isTiming = false;
		boolean isTimingEnd = false;
		// for error checking in WindowComplete
		boolean completed = false;
		
		// read file to string
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(osuFile), "UTF-8"))) {
			while ((line = reader.readLine()) != null) {
				// start of timing points section
				if (line.contains("[TimingPoints]")) {
					isTiming = true;
				}
				// end of timing section
				if (isTiming && line.length() == 0) {
					isTiming = false;
					isTimingEnd = true;
				} else if (!isTiming) {
					isTimingEnd = false;
				}
				
				// add each line to new string with new line character
				output += line + nl;
				
				// add timing code to end of timing section
				if (isTimingEnd) {
					output = output.substring(0, output.length() - nl.length());
					output += getTimingCode() + nl;
				}
			}
			reader.close();
		
		// if not completed, send error to next window
		} catch (IOException e) {
			completed = false;
		}
		
		// add slider code to the end of string
		output += getSliderCode();
		
		// write new string to file
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(osuFile.getPath()))) {
			writer.write(output);
			completed = true;
			writer.close();
			
		} catch (IOException e) {
			// error
			completed = false;
		}
		
		return completed;
	}
}
