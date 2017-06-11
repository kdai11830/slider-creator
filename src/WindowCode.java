import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class WindowCode extends JFrame {

	private JPanel contentPane;
	private JTextField txtSliderCode;
	private JButton btnRestart;
	private File file;
	private JTextField txtTimingCode;
	private JLabel lblSlider;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowCode frame = new WindowCode();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public WindowCode(DrawArea drawArea, File file, double beats) throws IOException {
		
		this.file = file;
		
		GraphicsToSlider gts = new GraphicsToSlider(drawArea, file, beats);
		
		String sliderCode = gts.getSliderCode();
		String timingCode = gts.getTimingCode();
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 672, 446);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblInstr = new JLabel("Copy and paste code below into .osu file");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblInstr, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblInstr, 120, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblInstr, 50, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblInstr, -105, SpringLayout.EAST, contentPane);
		lblInstr.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInstr.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblInstr);
		
		txtSliderCode = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtSliderCode, 199, SpringLayout.SOUTH, lblInstr);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtSliderCode, -69, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtSliderCode, -63, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtSliderCode, 65, SpringLayout.WEST, contentPane);
		txtSliderCode.setFont(new Font("Courier New", Font.PLAIN, 13));
		txtSliderCode.setHorizontalAlignment(SwingConstants.CENTER);
		txtSliderCode.setText(sliderCode);
		contentPane.add(txtSliderCode);
		txtSliderCode.setColumns(10);
		
		btnRestart = new JButton("New Slider");
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnRestartActionPerformed(evt);
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnRestart, -38, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnRestart, 15, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnRestart, -5, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnRestart, 122, SpringLayout.WEST, contentPane);
		contentPane.add(btnRestart);
		
		txtTimingCode = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtTimingCode, 61, SpringLayout.SOUTH, lblInstr);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtTimingCode, 0, SpringLayout.WEST, txtSliderCode);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtTimingCode, -54, SpringLayout.NORTH, txtSliderCode);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtTimingCode, 0, SpringLayout.EAST, txtSliderCode);
		txtTimingCode.setText("<dynamic>");
		txtTimingCode.setHorizontalAlignment(SwingConstants.CENTER);
		txtTimingCode.setFont(new Font("Courier New", Font.PLAIN, 13));
		txtTimingCode.setColumns(10);
		txtTimingCode.setText(timingCode);
		contentPane.add(txtTimingCode);
		
		JLabel lblTiming = new JLabel("Copy into end of [Timings]");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblTiming, -6, SpringLayout.NORTH, txtTimingCode);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblTiming, -264, SpringLayout.EAST, contentPane);
		contentPane.add(lblTiming);
		
		lblSlider = new JLabel("Copy into end of [HitObjects]");
		sl_contentPane.putConstraint(SpringLayout.EAST, lblSlider, -260, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblSlider, -6, SpringLayout.NORTH, txtSliderCode);
		contentPane.add(lblSlider);
	}
	
	private void btnRestartActionPerformed(ActionEvent evt) {
		WindowDraw window = new WindowDraw(file);
		window.setVisible(true);
		this.dispose();
	}
}
