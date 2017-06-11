import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

/**
 * 
 * @author Kevin Dai & Terrance Williams
 *
 */
public class WindowComplete extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	File file;

	/**
	 * Create the frame.
	 */
	public WindowComplete(File file) {
		this.file = file;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 443, 238);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblCompleted = new JLabel("Completed!");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblCompleted, 52, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblCompleted, 113, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblCompleted, -122, SpringLayout.EAST, contentPane);
		lblCompleted.setBackground(Color.WHITE);
		lblCompleted.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblCompleted.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblCompleted);
		
		JButton btnNewSlider = new JButton("New Slider");
		btnNewSlider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewSliderActionPerformed(e);
			}
		});
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblCompleted, -35, SpringLayout.NORTH, btnNewSlider);
		btnNewSlider.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnNewSlider, -38, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnNewSlider, 15, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnNewSlider, -5, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnNewSlider, 129, SpringLayout.WEST, contentPane);
		contentPane.add(btnNewSlider);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCloseActionPerformed(e);
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnClose, 0, SpringLayout.NORTH, btnNewSlider);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnClose, -109, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnClose, 0, SpringLayout.SOUTH, btnNewSlider);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnClose, -10, SpringLayout.EAST, contentPane);
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		contentPane.add(btnClose);
		
	}
	
	// go back to WindowDraw to make new slider
	private void btnNewSliderActionPerformed(ActionEvent e) {
		WindowDraw window = new WindowDraw(file);
		window.setVisible(true);
		this.dispose();
	}
	
	private void btnCloseActionPerformed(ActionEvent e) {
		System.exit(0);
	}
	
}
	

