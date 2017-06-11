import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

@SuppressWarnings("serial")
public class WindowDraw extends JFrame {

	private JPanel contentPane;
	private JTextField txtIntro;
	private DrawArea drawArea;
	private File file;
	/**
	 * Launch the application.
	 */
	/* public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowDraw frame = new WindowDraw();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} */

	/**
	 * Create the frame.
	 */
	public WindowDraw(File file) {
		this.file = file;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 636, 528);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		setContentPane(contentPane);
		
		JButton btnTransform = new JButton("Transform");
		sl_contentPane.putConstraint(SpringLayout.WEST, btnTransform, -131, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnTransform, -15, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnTransform, -10, SpringLayout.EAST, contentPane);
		btnTransform.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnTransform.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		btnTransform.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTransformActionPerformed(evt);
            }
        });
		contentPane.add(btnTransform, BorderLayout.SOUTH);
		
		txtIntro = new JTextField();
		txtIntro.setEditable(false);
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtIntro, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtIntro, 116, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtIntro, -99, SpringLayout.EAST, contentPane);
		txtIntro.setHorizontalAlignment(SwingConstants.CENTER);
		txtIntro.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtIntro.setToolTipText("Must be single stroke");
		txtIntro.setText("Draw your desired slider shape below");
		contentPane.add(txtIntro, BorderLayout.NORTH);
		txtIntro.setColumns(10);
		
		drawArea = new DrawArea();
		sl_contentPane.putConstraint(SpringLayout.NORTH, drawArea, 6, SpringLayout.SOUTH, txtIntro);
		sl_contentPane.putConstraint(SpringLayout.EAST, drawArea, -45, SpringLayout.EAST, contentPane);
		contentPane.add(drawArea);
		
		JButton btnClear = new JButton("Clear");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnClear, 11, SpringLayout.SOUTH, drawArea);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnClear, -18, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnTransform, 0, SpringLayout.NORTH, btnClear);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnClearActionPerformed(evt);
			}
		});
		sl_contentPane.putConstraint(SpringLayout.WEST, btnClear, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnClear, 113, SpringLayout.WEST, contentPane);
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(btnClear);
		
		contentPane.getRootPane().setDefaultButton(btnTransform);
	}
	
	private void btnTransformActionPerformed(ActionEvent evt) {
		WindowOption window = new WindowOption(drawArea, file);
		window.setVisible(true);
		this.dispose();
	}
	
	private void btnClearActionPerformed(ActionEvent evt) {
		drawArea.clear();
	}
}
