package gui;

import java.awt.*;
import org.jdesktop.swingx.border.DropShadowBorder;

import dao.MemberDao;
import entity.Member;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

public class RegisterFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	private LoginFrame loginFrame;
	private MemberDao dao;

	public RegisterFrame(LoginFrame flogin) {
		dao = new MemberDao();
		loginFrame = flogin;
		loginFrame.setVisible(false);
		setUndecorated(true);
		getContentPane().setLayout(new BorderLayout(0, 0));

		// Header
		JPanel header = new JPanel();
		header.setPreferredSize(new Dimension(1000, 50));
		header.setLayout(new BorderLayout(0, 0));
		JPanel pnExit = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) pnExit.getLayout();
		flowLayout_1.setVgap(10);
		flowLayout_1.setHgap(10);
		pnExit.setPreferredSize(new Dimension(50, 50));
		pnExit.setBackground(new Color(246, 247, 246));
		JLabel lbExit = new JLabel("", JLabel.CENTER);
		lbExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				pnExit.setBackground(new Color(254, 254, 255));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				pnExit.setBackground(new Color(246, 247, 246));
			}
		});
		lbExit.setIcon(new ImageIcon(RegisterFrame.class.getResource("/icon/close.png")));

		pnExit.add(lbExit);
		header.add(pnExit, BorderLayout.EAST);
		header.setBackground(new Color(246, 247, 246));
		getContentPane().add(header, BorderLayout.NORTH);

		// Content
		JPanel content = new JPanel();
		content.setPreferredSize(new Dimension(1000, 400));
		content.setBackground(new Color(246, 247, 246));
		content.setLayout(new BorderLayout(0, 0));

		// panel contain content login
		JPanel container = new JPanel();
		container.setPreferredSize(new Dimension(550, 400));
		container.setBackground(new Color(254, 254, 255));
		container.setLayout(new BorderLayout(0, 0));

		// set border shadow
		DropShadowBorder shadow = new DropShadowBorder();
		shadow.setShadowColor(new Color(246, 247, 246));
		shadow.setShowLeftShadow(true);
		shadow.setShowRightShadow(true);
		shadow.setShowBottomShadow(true);
		shadow.setShowTopShadow(true);

		JPanel container1 = new JPanel();
		container1.setPreferredSize(new Dimension(550, 350));
		container1.setBackground(new Color(254, 254, 255));
		container1.setLayout(new BorderLayout(0, 0));
		JLabel lbTitle = new JLabel("User Register", JLabel.CENTER);
		lbTitle.setFont(new Font("Tahoma", Font.PLAIN, 30));

		// contain component username, password, button login, sign up
		JPanel container2 = new JPanel();
		container2.setPreferredSize(new Dimension(550, 300));
		container2.setBackground(new Color(254, 254, 255));
		container2.setLayout(new BoxLayout(container2, BoxLayout.Y_AXIS));
		container2.add(Box.createVerticalGlue());

		JPanel pnUsername = new JPanel();
		pnUsername.setPreferredSize(new Dimension(550, 30));
		pnUsername.setBackground(new Color(254, 254, 255));
		pnUsername.setLayout(new BoxLayout(pnUsername, BoxLayout.X_AXIS));
		pnUsername.add(Box.createHorizontalGlue());
		JLabel lbUsername = new JLabel("Username:", JLabel.LEFT);
		lbUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JTextField txtusername = new JTextField();
		txtusername.setHorizontalAlignment(SwingConstants.LEFT);
		txtusername.setPreferredSize(new Dimension(200, 30));
		pnUsername.add(Box.createRigidArea(new Dimension(80, 0)));
		pnUsername.add(lbUsername);
		pnUsername.add(Box.createRigidArea(new Dimension(20, 0)));
		pnUsername.add(txtusername);
		pnUsername.add(Box.createRigidArea(new Dimension(80, 0)));

		JPanel pnPassword = new JPanel();
		pnPassword.setPreferredSize(new Dimension(550, 30));
		pnPassword.setBackground(new Color(254, 254, 255));
		pnPassword.setLayout(new BoxLayout(pnPassword, BoxLayout.X_AXIS));
		pnPassword.add(Box.createHorizontalGlue());
		JLabel lbPassword = new JLabel("Password:", JLabel.LEFT);
		lbPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JPasswordField txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPassword.setHorizontalAlignment(SwingConstants.LEFT);
		txtPassword.setPreferredSize(new Dimension(200, 30));
		pnPassword.add(Box.createRigidArea(new Dimension(80, 0)));
		pnPassword.add(lbPassword);
		pnPassword.add(Box.createRigidArea(new Dimension(22, 0)));
		pnPassword.add(txtPassword);
		pnPassword.add(Box.createRigidArea(new Dimension(80, 0)));

		JPanel pnName = new JPanel();
		pnName.setPreferredSize(new Dimension(550, 30));
		pnName.setBackground(new Color(254, 254, 255));
		pnName.setLayout(new BoxLayout(pnName, BoxLayout.X_AXIS));
		pnName.add(Box.createHorizontalGlue());
		JLabel lbName = new JLabel("Full Name:", JLabel.LEFT);
		lbName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JTextField txtName = new JTextField();
		txtName.setHorizontalAlignment(SwingConstants.LEFT);
		txtName.setPreferredSize(new Dimension(200, 30));
		pnName.add(Box.createRigidArea(new Dimension(80, 0)));
		pnName.add(lbName);
		pnName.add(Box.createRigidArea(new Dimension(20, 0)));
		pnName.add(txtName);
		pnName.add(Box.createRigidArea(new Dimension(80, 0)));

		JPanel pnCboxShowPassword = new JPanel();
		pnCboxShowPassword.setPreferredSize(new Dimension(550, 30));
		pnCboxShowPassword.setBackground(new Color(254, 254, 255));
		pnCboxShowPassword.setLayout(new BoxLayout(pnCboxShowPassword, BoxLayout.X_AXIS));
		pnCboxShowPassword.add(Box.createHorizontalGlue());
		JCheckBox cboxShowPass = new JCheckBox("Show password", false);
		cboxShowPass.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// char base = txtPassword.getEchoChar();
				if (e.getStateChange() == ItemEvent.SELECTED) {
					txtPassword.setEchoChar((char) 0);
				} else {
					txtPassword.setEchoChar('●');
				}
			}
		});
		cboxShowPass.setBackground(new Color(254, 254, 255));
		pnCboxShowPassword.add(cboxShowPass);
		pnCboxShowPassword.add(Box.createRigidArea(new Dimension(265, 0)));

		JPanel pnButtonRegister = new JPanel();
		pnButtonRegister.setPreferredSize(new Dimension(550, 30));
		pnButtonRegister.setBackground(new Color(254, 254, 255));
		pnButtonRegister.setLayout(new BoxLayout(pnButtonRegister, BoxLayout.X_AXIS));
		pnButtonRegister.add(Box.createHorizontalGlue());
		JButton btnRegister = new JButton("Sign up");
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRegister.setBackground(new Color(25, 180, 76));
		btnRegister.setForeground(Color.white);
		btnRegister.setPreferredSize(new Dimension(100, 30));

		btnRegister.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnRegisterActionPerformed(evt);
			}

			private void btnRegisterActionPerformed(ActionEvent evt) {
				try {
					String username = txtusername.getText();
					String password = String.valueOf(txtPassword.getPassword());
					String fullname = txtName.getText();

					boolean check = dao.checkDupUserName(username);
					if(check) {
						JOptionPane.showMessageDialog(flogin, "Username is exist", "Warning",
								JOptionPane.INFORMATION_MESSAGE);
					}else {
						dao.add(new Member(username, password, fullname, 2));
						JOptionPane.showMessageDialog(flogin, "Register member sucessfully!", "About",
								JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}

				} catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(flogin, "Please try again!", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		pnButtonRegister.add(btnRegister);
		pnButtonRegister.add(Box.createRigidArea(new Dimension(205, 0)));

		container2.add(Box.createRigidArea(new Dimension(0, 50)));
		container2.add(pnUsername);
		container2.add(Box.createRigidArea(new Dimension(0, 20)));
		container2.add(pnPassword);
		container2.add(Box.createRigidArea(new Dimension(0, 20)));
		container2.add(pnName);
		container2.add(Box.createRigidArea(new Dimension(0, 10)));
		container2.add(pnCboxShowPassword);
		container2.add(Box.createRigidArea(new Dimension(0, 50)));
		container2.add(pnButtonRegister);
		container2.add(Box.createRigidArea(new Dimension(0, 75)));

		container1.add(container2, BorderLayout.CENTER);
		container1.add(lbTitle, BorderLayout.NORTH);
		container1.setBorder(shadow);
		container.add(container1, BorderLayout.CENTER);
		JPanel bottom = new JPanel();
		bottom.setPreferredSize(new Dimension(550, 50));
		bottom.setBackground(new Color(246, 247, 246));
		container.add(bottom, BorderLayout.SOUTH);
		JPanel left = new JPanel();
		left.setPreferredSize(new Dimension(50, 400));
		left.setBackground(new Color(246, 247, 246));
		content.add(left, BorderLayout.WEST);
		content.add(container, BorderLayout.CENTER);
		getContentPane().add(content, BorderLayout.CENTER);

		// panel contain image book
		JPanel hero = new JPanel();
		FlowLayout flowLayout = (FlowLayout) hero.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		hero.setPreferredSize(new Dimension(400, 400));
		hero.setBackground(new Color(246, 247, 246));
		JLabel lbhero = new JLabel("", JLabel.CENTER);

		// fit image to scale base panel
		lbhero.setIcon(new ImageIcon(new ImageIcon(RegisterFrame.class.getResource("/icon/3book.png")).getImage()
				.getScaledInstance(400, 400, Image.SCALE_DEFAULT)));

		hero.add(lbhero);
		content.add(hero, BorderLayout.EAST);
		pack();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.getRootPane().setDefaultButton(btnRegister);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("CLose");
			}

			@Override
			public void windowClosed(WindowEvent e) {
				System.out.println("Closed");
				loginFrame.setVisible(true);
			}
		});
	}

}
