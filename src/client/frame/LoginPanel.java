package client.frame;

import client.Listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginPanel extends JPanel {
	
	private JTextField txtName;
	private JPasswordField txtPw;
	private int profileNum = 0;
	ImageIcon profileImage = getProfileImage();
	JLabel lblProfile;

	public LoginPanel(final AppFrame frame) {
		
		setBackground(Color.ORANGE);
		setLayout(null);
		
		JLabel lblTitle = new JLabel("상담원 문의");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("맑은 고딕", Font.BOLD, 34));
		lblTitle.setBounds(12, 100, 276, 46);
		add(lblTitle);

		txtName = new JTextField();
		txtName.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		txtName.setHorizontalAlignment(SwingConstants.CENTER);
		txtName.setBounds(82, 366, 136, 27);
		add(txtName);
		txtName.setColumns(10);

		txtPw = new JPasswordField();
		txtPw.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		txtPw.setHorizontalAlignment(SwingConstants.CENTER);
		txtPw.setBounds(82, 410, 136, 27);
		add(txtPw);
		txtPw.setColumns(10);
		
		JButton btnLogin = new JButton("login");
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(Color.DARK_GRAY);
		btnLogin.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtName.getText() != null && !txtName.getText().equals("")) {
					new Thread(new Listener(frame)).start();
				}
			}
		});
		btnLogin.setBounds(82, 443, 136, 27);
		add(btnLogin);
		
		lblProfile = new JLabel(profileImage);
		lblProfile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ProfileFrame profileFrame = new ProfileFrame(frame);
				profileFrame.setVisible(true);
			}
		});
		lblProfile.setBounds(70, 175, 160, 160);
		add(lblProfile);
	}
	
	public void changeProfileImage(int index) {
		profileNum = index;
		profileImage = getProfileImage();
		lblProfile.setIcon(profileImage);
	}
	
	private ImageIcon getProfileImage() {
		return new ImageIcon(new ImageIcon(ProfileFrame.PROFILEPATH + "/profile"+profileNum+".png").getImage().getScaledInstance(160, 160, java.awt.Image.SCALE_SMOOTH));
	}

	public String getTxtName() {
		return txtName.getText();
	}

	public void setTxtName(JTextField txtName) {
		this.txtName = txtName;
	}
	
	public int getProfileNum() {
		return profileNum;
	}
}
