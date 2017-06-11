import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 * 
 * @author Kevin Dai & Terrance Williams
 *
 */
public class DrawArea extends JComponent {
	
	
	private static final long serialVersionUID = 1L;
	protected BufferedImage image;
	private Graphics2D g2;
	private int currentX, currentY, oldX, oldY;
	private Point sliderStart, sliderEnd;
	private ArrayList<Point> anchorPoints = new ArrayList<Point>();
	int width = 512;
	int height = 384;
	
	// JComponent interface for drawing
	public DrawArea() {
		
		setDoubleBuffered(false);
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// gets first anchor point
				oldX = e.getX();
				oldY = e.getY();
				sliderStart = new Point(e.getX(), e.getY());
			}
			public void mouseReleased(MouseEvent e) {
				// gets last anchor point
				sliderEnd = new Point(e.getX(), e.getY());			
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				// add to anchor points arraylist
				currentX = e.getX();
				currentY = e.getY();
				anchorPoints.add(e.getPoint());
				
				// draw line with mouse
				if (g2 != null) {
					g2.drawLine(oldX, oldY, currentX, currentY);
					repaint();
					oldX = currentX;
					oldY = currentY;
				}
			}
		});
		
		setBounds(0, 0, width, height);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// draw on new image graphics
		if (image == null) {
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			g2 = (Graphics2D) image.getGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			clear();
		}
		
		g.drawImage(image, 0, 0, null);
	}
	
	@Override
	public Dimension getPreferredSize() {
		// set max width & height
		return new Dimension(width, height);
	}
	
	// for clear button on GUI, resets anchor points
	public void clear() {
		g2.setPaint(Color.white);
		g2.fillRect(0, 0, getSize().width, getSize().height);
		g2.setColor(Color.black);
		repaint();
		this.anchorPoints.clear();
	}
	
	// set color
	public void black() {
		g2.setPaint(Color.black);
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
	
	public Point getSliderStart() {
		return this.sliderStart;
	}
	
	public Point getSliderEnd() {
		return this.sliderEnd;
	}
	
	public ArrayList<Point> getAnchorPoints() {
		return this.anchorPoints;
	}
	
}
