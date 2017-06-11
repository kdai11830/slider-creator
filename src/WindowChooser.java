import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;

/**
 * 
 * @author Kevin Dai & Terrance Williams
 *
 */
public class WindowChooser extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String osuPath;
	private JPanel contentPane;
	JFileChooser fileChooser = new JFileChooser(osuPath);
	File dir;

	/**
	 * Create the frame.
	 */
	public WindowChooser() {
		readFromProperty(System.getProperty("user.dir"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 571, 367);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		FileFilter filter = new FileNameExtensionFilter("OSU File", "osu");
		fileChooser.setApproveButtonText("Select");
		fileChooser.setToolTipText("Select OSU File");
		fileChooser.setFileFilter(filter);
		fileChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					fileChooserActionPerformed(evt);
				} catch (IOException e) {
					Logger.getLogger(WindowChooser.class.getName()).log(Level.SEVERE, null, e);
				}
			}
		});
		
		dir = new File(osuPath);
		fileChooser.setCurrentDirectory(dir);
		
		contentPane.add(fileChooser, BorderLayout.CENTER);
	}
	
	// choose file
	private void fileChooserActionPerformed(ActionEvent evt) throws IOException {
		if (evt.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
			if(fileExists()) {
				// write to property file and send data to new window
                osuPath = fileChooser.getSelectedFile().getPath().replace(fileChooser.getSelectedFile().getName(), "");
                writeToPropertyFile(System.getProperty("user.dir"));
                                 
				WindowDraw window = new WindowDraw(fileChooser.getSelectedFile());
				window.setVisible(true);
				this.dispose();
			} else {
				// error message
                JOptionPane.showMessageDialog(fileChooser, "Please select OSU file.");
            }
        } else if (evt.getActionCommand() .equals(JFileChooser.CANCEL_SELECTION)) {
            System.exit(0);
		}
	}
	
	// check if file exists
	private boolean fileExists() {
        File file = fileChooser.getSelectedFile();
        return file.exists()
                && file.isFile()
                && file.toString().substring(file.toString().indexOf(".")).contains("osu");
    }
	
	// remember directory location on application startup
	private void readFromProperty(String path) {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream(path + "\\config.properties");
			prop.load(input);
			osuPath = prop.getProperty("osuPath");
			input.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// save directory in property
	private void writeToPropertyFile(String path) {
		Properties prop = new Properties();
		OutputStream output = null;
		try {
			FileInputStream input = new FileInputStream(path + "\\config.properties");
			prop.load(input);
			prop.setProperty("osuPath", osuPath);
			input.close();
			// save properties to project root folder
			output = new FileOutputStream(path + "\\config.properties");
			prop.store(output, null);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
