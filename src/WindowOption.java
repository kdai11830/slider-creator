import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * 
 * @author Kevin Dai & Terrance Williams
 *
 */
public class WindowOption extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textBeats;
	private JButton btnCreate;
	private DrawArea drawArea;
	private File file;

	/**
	 * Create the frame.
	 */
	public WindowOption(DrawArea drawArea, File file) {
		this.drawArea = drawArea;
		this.file = file;		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 398, 241);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblBeats = new JLabel("How many beats (white ticks) will slider span?");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblBeats, 29, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblBeats, 20, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblBeats, 55, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblBeats, -24, SpringLayout.EAST, contentPane);
		lblBeats.setHorizontalAlignment(SwingConstants.CENTER);
		lblBeats.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(lblBeats);
		
		textBeats = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textBeats, 27, SpringLayout.SOUTH, lblBeats);
		sl_contentPane.putConstraint(SpringLayout.WEST, textBeats, 118, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, textBeats, -54, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, textBeats, 250, SpringLayout.WEST, contentPane);
		textBeats.setToolTipText("Enter number");
		textBeats.setHorizontalAlignment(SwingConstants.CENTER);
		textBeats.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(textBeats);
		textBeats.setColumns(10);
		
		btnCreate = new JButton("Create");
		btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
					btnCreateActionPerformed(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnCreate, 104, SpringLayout.SOUTH, lblBeats);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnCreate, -116, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnCreate, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnCreate, 0, SpringLayout.EAST, lblBeats);
		contentPane.add(btnCreate);
		
		contentPane.getRootPane().setDefaultButton(btnCreate);
	}
	
	private void btnCreateActionPerformed(ActionEvent evt) throws IOException {
		String text = textBeats.getText();
		if (text == null) {
			// error
			JOptionPane.showMessageDialog(contentPane, "Please enter a positive number.");
		}
		boolean isNumber = true;
		
		// check if it is number
		for (char c : text.toCharArray()) {
			if (!Character.isDigit(c)) {
				isNumber = false;
			} else if (c == '-') {
				isNumber = false;
			}
		}
		
		if (isNumber) {
			// send data to final window
			double beats = Double.parseDouble(text);
			GraphicsToSlider gts = new GraphicsToSlider(drawArea, file, beats);
			if (gts.writeToFile()) {
				WindowComplete window = new WindowComplete(file);
				window.setVisible(true);
				this.dispose();
			// failed to overwrite osu file, return to beginning window
			} else {
				JOptionPane.showMessageDialog(contentPane, "Error writing to file. Please restart.");
				WindowChooser window = new WindowChooser();
				window.setVisible(true);
				this.dispose();
			}
		// error on entering text
		} else {
			JOptionPane.showMessageDialog(contentPane, "Please enter a positive number.");
		}
		
	}
}
