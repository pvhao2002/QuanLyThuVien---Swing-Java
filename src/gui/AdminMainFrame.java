package gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import dao.*;
import entity.*;

public class AdminMainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private LoginFrame frameLogin;
	private JPanel container;
	private JLabel lbTitleContainer;
	private MemberDao memberDao;
	private BookDao bookDao;
	private LoanDao loanDao;
	private boolean addMember = false;
	private boolean addBook = false;

	public void setOnModelMember(JPanel infoModel) {
		JTextField txtUsername = (JTextField) infoModel.getComponent(3);
		JPasswordField txtPassword = (JPasswordField) infoModel.getComponent(5);
		JTextField txtName = (JTextField) infoModel.getComponent(7);
		JComboBox<String> cboRole = (JComboBox<String>) infoModel.getComponent(9);

		txtUsername.setEnabled(true);
		txtPassword.setEnabled(true);
		txtName.setEnabled(true);
		cboRole.setEnabled(true);
	}

	public void setOffModelMember(JPanel infoModel) {
		JTextField txtUsername = (JTextField) infoModel.getComponent(3);
		JPasswordField txtPassword = (JPasswordField) infoModel.getComponent(5);
		JTextField txtName = (JTextField) infoModel.getComponent(7);
		JComboBox<String> cboRole = (JComboBox<String>) infoModel.getComponent(9);

		txtUsername.setEnabled(false);
		txtPassword.setEnabled(false);
		txtName.setEnabled(false);
		cboRole.setEnabled(false);
	}

	JPanel managementMembers() {
		DefaultTableModel model = new DefaultTableModel();
		JPanel members = new JPanel();
		members.setPreferredSize(new Dimension(980, 684));
		members.setBackground(new Color(238, 240, 168));
		members.setLayout(new BorderLayout(0, 0));

		JPanel infoModel = new JPanel();
		infoModel.setPreferredSize(new Dimension(830, 350));
		infoModel.setBackground(new Color(238, 240, 168));
		infoModel.setBorder(new LineBorder(new Color(139, 141, 94), 2, true)); // set line border
		infoModel.setLayout(new GridBagLayout());
		JLabel lbIdMember = new JLabel("Mã độc giả:", JLabel.CENTER);
		JTextField txtIdMember = new JTextField();
		txtIdMember.setEnabled(false);
		txtIdMember.setFont(new Font("Tahoma", Font.BOLD, 14));
		JLabel lbUsername = new JLabel("Tên đăng nhập:", JLabel.CENTER);
		JTextField txtUsername = new JTextField();
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JLabel lbPassword = new JLabel("Mật khẩu:", JLabel.CENTER);
		JPasswordField txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JLabel lbName = new JLabel("Họ và tên:", JLabel.CENTER);
		JTextField txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JLabel lbRole = new JLabel("Chức vụ:", JLabel.CENTER);
		JComboBox<String> cboRole = new JComboBox<>();
		cboRole.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cboRole.addItem("Quản trị viên");
		cboRole.addItem("Độc giả");
		JCheckBox chboxShowPassword = new JCheckBox("Hiện mật khẩu");
		chboxShowPassword.setBackground(new Color(238, 240, 168));
		chboxShowPassword.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					// show password
					txtPassword.setEchoChar((char) 0);
				} else {
					// hide password
					txtPassword.setEchoChar('●');
				}
			}
		});

		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0;
		c1.gridy = 0;
		c1.gridwidth = 1;
		c1.gridheight = 1;
		c1.fill = GridBagConstraints.BOTH;
		c1.weightx = 1.0;
		c1.weighty = 0.5;
		c1.anchor = GridBagConstraints.CENTER;
		c1.insets = new Insets(30, 5, 5, 5);
		infoModel.add(lbIdMember, c1);

		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx = 2;
		c2.gridy = 0;
		c2.gridwidth = 5;
		c2.gridheight = 1;
		c2.fill = GridBagConstraints.BOTH;
		c2.weightx = 5.0;
		c2.weighty = 0.5;
		c2.anchor = GridBagConstraints.CENTER;
		c2.insets = new Insets(30, 5, 5, 5);
		infoModel.add(txtIdMember, c2);

		GridBagConstraints c3 = new GridBagConstraints();
		c3.gridx = 0;
		c3.gridy = 1;
		c3.gridwidth = 1;
		c3.gridheight = 1;
		c3.fill = GridBagConstraints.BOTH;
		c3.weightx = 1.0;
		c3.weighty = 0.5;
		c3.anchor = GridBagConstraints.CENTER;
		c3.insets = new Insets(5, 5, 5, 5);
		infoModel.add(lbUsername, c3);

		GridBagConstraints c4 = new GridBagConstraints();
		c4.gridx = 2;
		c4.gridy = 1;
		c4.gridwidth = 5;
		c4.gridheight = 1;
		c4.fill = GridBagConstraints.BOTH;
		c4.weightx = 5.0;
		c4.weighty = 0.5;
		c4.anchor = GridBagConstraints.CENTER;
		c4.insets = new Insets(5, 5, 5, 5);
		infoModel.add(txtUsername, c4);

		GridBagConstraints c5 = new GridBagConstraints();
		c5.gridx = 0;
		c5.gridy = 2;
		c5.gridwidth = 1;
		c5.gridheight = 1;
		c5.fill = GridBagConstraints.BOTH;
		c5.weightx = 1.0;
		c5.weighty = 0.5;
		c5.anchor = GridBagConstraints.CENTER;
		c5.insets = new Insets(5, 5, 5, 5);
		infoModel.add(lbPassword, c5);

		GridBagConstraints c6 = new GridBagConstraints();
		c6.gridx = 2;
		c6.gridy = 2;
		c6.gridwidth = 5;
		c6.gridheight = 1;
		c6.fill = GridBagConstraints.BOTH;
		c6.weightx = 5.0;
		c6.weighty = 0.5;
		c6.anchor = GridBagConstraints.CENTER;
		c6.insets = new Insets(5, 5, 5, 5);
		infoModel.add(txtPassword, c6);

		GridBagConstraints c7 = new GridBagConstraints();
		c7.gridx = 0;
		c7.gridy = 3;
		c7.gridwidth = 1;
		c7.gridheight = 1;
		c7.fill = GridBagConstraints.BOTH;
		c7.weightx = 1.0;
		c7.weighty = 0.5;
		c7.anchor = GridBagConstraints.CENTER;
		c7.insets = new Insets(5, 5, 5, 5);
		infoModel.add(lbName, c7);

		GridBagConstraints c8 = new GridBagConstraints();
		c8.gridx = 2;
		c8.gridy = 3;
		c8.gridwidth = 5;
		c8.gridheight = 1;
		c8.fill = GridBagConstraints.BOTH;
		c8.weightx = 5.0;
		c8.weighty = 0.5;
		c8.anchor = GridBagConstraints.CENTER;
		c8.insets = new Insets(5, 5, 5, 5);
		infoModel.add(txtName, c8);

		GridBagConstraints c9 = new GridBagConstraints();
		c9.gridx = 0;
		c9.gridy = 4;
		c9.gridwidth = 1;
		c9.gridheight = 1;
		c9.fill = GridBagConstraints.BOTH;
		c9.weightx = 1.0;
		c9.weighty = 0.5;
		c9.anchor = GridBagConstraints.CENTER;
		c9.insets = new Insets(5, 5, 5, 5);
		infoModel.add(lbRole, c9);

		GridBagConstraints c10 = new GridBagConstraints();
		c10.gridx = 2;
		c10.gridy = 4;
		c10.gridwidth = 5;
		c10.gridheight = 1;
		c10.fill = GridBagConstraints.BOTH;
		c10.weightx = 5.0;
		c10.weighty = 0.5;
		c10.anchor = GridBagConstraints.CENTER;
		c10.insets = new Insets(5, 5, 5, 5);
		infoModel.add(cboRole, c10);

		GridBagConstraints c11 = new GridBagConstraints();
		c11.gridx = 2;
		c11.gridy = 5;
		c11.gridwidth = 3;
		c11.gridheight = 1;
		c11.fill = GridBagConstraints.BOTH;
		c11.weightx = 3.0;
		c11.weighty = 0.5;
		c11.anchor = GridBagConstraints.CENTER;
		c11.insets = new Insets(5, 5, 30, 5);

		infoModel.add(chboxShowPassword, c11);
		setOffModelMember(infoModel);
		JPanel grboxButton = new JPanel();
		grboxButton.setPreferredSize(new Dimension(150, 350));
		grboxButton.setBackground(new Color(238, 240, 168));
		initCRUDComponents(grboxButton);
		// grboxButton.setBorder(new LineBorder(new Color(139, 141, 94), 2, true));
		JButton btnAdd = (JButton) grboxButton.getComponent(0);

		JButton btnEdit = (JButton) grboxButton.getComponent(1);
		btnEdit.setEnabled(false);
		JButton btnSave = (JButton) grboxButton.getComponent(2);
		btnSave.setEnabled(false);
		JButton btnDelete = (JButton) grboxButton.getComponent(3);
		btnDelete.setEnabled(false);
		JButton btnReset = (JButton) grboxButton.getComponent(4);
		btnAdd.addActionListener(e -> {
			addMember = true;
			setOnModelMember(infoModel);
			resetTextModelMember(infoModel);
			txtIdMember.setText("");
			txtUsername.requestFocus();
			btnSave.setEnabled(true);
			btnEdit.setEnabled(false);
			btnDelete.setEnabled(false);
		});
		btnEdit.addActionListener(e -> {
			addMember = false;
			setOnModelMember(infoModel);
			txtUsername.setEnabled(false);
			txtUsername.requestFocus();
			btnSave.setEnabled(true);
			btnAdd.setEnabled(false);
			btnDelete.setEnabled(false);
		});
		btnSave.addActionListener(e -> {
			if (addMember) {
				String username = txtUsername.getText();
				try {
					if (memberDao.checkDupUserName(username)) {
						JOptionPane.showMessageDialog(null, "Tên đăng nhập đã tồn tại", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
						txtUsername.requestFocus();
					} else {
						String password = String.valueOf(txtPassword.getPassword());
						String name = txtName.getText();
						int r = cboRole.getSelectedIndex() == 0 ? 1 : 2;
						Member item = new Member(username, password, name, r);
						memberDao.add(item);
						btnReset.doClick();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Vui lòng thử lại sau!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				try {
					int id = Integer.parseInt(txtIdMember.getText());
					String username = txtUsername.getText();
					String password = String.valueOf(txtPassword.getPassword());
					String fullname = txtName.getText();
					int role = cboRole.getSelectedIndex() == 0 ? 1 : 2;
					Member item = new Member(id, username, password, fullname, role);
					memberDao.update(item);
					JOptionPane.showMessageDialog(null, "Đã cập nhật thông tin thành công!", "Thành công",
							JOptionPane.INFORMATION_MESSAGE);
					btnReset.doClick();
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "Vui lòng thử lại sau!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnDelete.addActionListener(e -> {
			int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa độc giả này không?", "Xác nhận",
					JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				// Delete the item
				int id = Integer.parseInt(txtIdMember.getText());
				try {
					int countLoanByMember = loanDao.countByMember(id);
					if (countLoanByMember > 0) {
						JOptionPane.showMessageDialog(null, "Độc giả này chưa trả hết sách đã mượn!", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
					} else {
						int opt = JOptionPane.showConfirmDialog(null,
								"Nếu xóa, tất cả các phiếu mượn của độc giả sẽ bị xóa ?", "Bạn có muốn xóa không",
								JOptionPane.YES_NO_OPTION);
						if (opt == JOptionPane.YES_OPTION) {
							loanDao.deleteByMember(id);
							memberDao.delete(id);
							JOptionPane.showMessageDialog(null, "Đã xóa thành công!", "Thành công",
									JOptionPane.INFORMATION_MESSAGE);
							btnReset.doClick();
						}
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Vui lòng thử lại sau!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnReset.addActionListener(e -> {
			// Code to be executed when the button is clicked
			fillDataTableMember(model);
			txtIdMember.setText("");
			resetTextModelMember(infoModel);
			setOffModelMember(infoModel);
			txtUsername.setFocusable(true);
			txtUsername.requestFocus();
			btnAdd.setEnabled(true);
			btnEdit.setEnabled(false);
			btnSave.setEnabled(false);
			btnDelete.setEnabled(false);
			addMember = false;
		});

		JPanel showData = new JPanel();
		showData.setPreferredSize(new Dimension(980, 334));
		showData.setBackground(new Color(238, 240, 168));
		showData.setBorder(new LineBorder(new Color(139, 141, 94), 2, true));
		showData.setLayout(new BorderLayout(0, 0));

		model.addColumn("Mã sách");
		model.addColumn("Tên đăng nhập");
		model.addColumn("Mật khẩu");
		model.addColumn("Họ và tên");
		model.addColumn("Quyền");
		fillDataTableMember(model);

		// Create a table using the model
		JTable table = new JTable(model);
		JTableHeader header = table.getTableHeader();

		// set table to read only
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(new JTextField()) {
				@Override
				public boolean isCellEditable(EventObject anEvent) {
					return false;
				}
			});
		}
		// Create a custom renderer for the cells
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		// Set the custom renderer as the default renderer for all cells
		table.setDefaultRenderer(Object.class, centerRenderer);
		// Adjust the width of each column based on the width of the widest cell in that
		// column
		for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
			TableColumn column = table.getColumnModel().getColumn(columnIndex);
			int maxWidth = 0;
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, columnIndex);
				Object value = table.getValueAt(row, columnIndex);
				Component comp = renderer.getTableCellRendererComponent(table, value, false, false, row, columnIndex);
				maxWidth = Math.max(comp.getPreferredSize().width, maxWidth);
			}
			column.setPreferredWidth(maxWidth);
		}
		header.setDefaultRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				label.setBackground(new Color(221, 221, 227));
				label.setForeground(Color.BLACK);
				label.setFont(label.getFont().deriveFont(Font.BOLD));
				label.setHorizontalAlignment(JLabel.CENTER);
				return label;
			}
		});

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int pos = table.rowAtPoint(e.getPoint());
					int idmember = Integer.parseInt(table.getValueAt(pos, 0).toString());
					Member item = memberDao.getItem(idmember);
					txtIdMember.setText(item.getMember_id() + "");
					txtUsername.setText(item.getUsername());
					txtPassword.setText(item.getPassword());
					txtName.setText(item.getName());
					cboRole.setSelectedIndex(item.getRole() == 1 ? 0 : 1);
					btnEdit.setEnabled(true);
					btnDelete.setEnabled(true);
					addMember = false;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		// Add the table to a scroll pane
		JScrollPane scrollPane = new JScrollPane(table);
		showData.add(scrollPane, BorderLayout.CENTER);

		members.add(infoModel, BorderLayout.WEST);
		members.add(grboxButton, BorderLayout.EAST);
		members.add(showData, BorderLayout.SOUTH);
		return members;
	}

	public void resetTextModelMember(JPanel infoModel) {
		JTextField txtUsername = (JTextField) infoModel.getComponent(3);
		JPasswordField txtPassword = (JPasswordField) infoModel.getComponent(5);
		JTextField txtName = (JTextField) infoModel.getComponent(7);
		JComboBox<String> cboRole = (JComboBox<String>) infoModel.getComponent(9);
		txtUsername.setText("");
		txtPassword.setText("");
		txtName.setText("");
		cboRole.setSelectedIndex(1);
	}

	public void fillDataTableMember(DefaultTableModel model) {
		model.setRowCount(0);
		try {
			for (Member b : memberDao.findAll()) {
				Object data[] = new Object[5];
				data[0] = b.getMember_id();
				data[1] = b.getUsername();
				data[2] = b.getPassword();
				data[3] = b.getName();
				data[4] = (b.getRole() == 2 ? "Độc giả" : "Quản trị viên");
				model.addRow(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void initCRUDComponents(JPanel jpncontainer) {
		jpncontainer.setLayout(new GridLayout(6, 1, 12, 12));

		JButton btnAdd = new JButton("Thêm", new ImageIcon(AdminMainFrame.class.getResource("/icon/add.png")));
		btnAdd.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnAdd.setIconTextGap(20);
		JButton btnEdit = new JButton("Sửa", new ImageIcon(AdminMainFrame.class.getResource("/icon/edit.png")));
		btnEdit.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnEdit.setIconTextGap(20);
		JButton btnDelete = new JButton("Xóa", new ImageIcon(AdminMainFrame.class.getResource("/icon/delete.png")));
		btnDelete.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnDelete.setIconTextGap(20);
		JButton btnSave = new JButton("Lưu", new ImageIcon(AdminMainFrame.class.getResource("/icon/save.png")));
		btnSave.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnSave.setIconTextGap(20);
		JButton btnReset = new JButton("Làm mới", new ImageIcon(AdminMainFrame.class.getResource("/icon/reset.png")));
		btnReset.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnReset.setIconTextGap(10);

		jpncontainer.add(btnAdd);
		jpncontainer.add(btnEdit);
		jpncontainer.add(btnSave);
		jpncontainer.add(btnDelete);
		jpncontainer.add(btnReset);
	}

	JPanel managementBooks() {
		JPanel books = new JPanel();
		DefaultTableModel model = new DefaultTableModel();
		books.setPreferredSize(new Dimension(980, 684));
		books.setBackground(new Color(238, 240, 168));
		books.setLayout(new BorderLayout(0, 0));

		JPanel infoModel = new JPanel();
		infoModel.setPreferredSize(new Dimension(830, 350));
		infoModel.setBackground(new Color(238, 240, 168));
		infoModel.setBorder(new LineBorder(new Color(139, 141, 94), 2, true)); // set line border
		infoModel.setLayout(new GridBagLayout());
		JLabel lbIdBook = new JLabel("Mã sách:", JLabel.CENTER);
		JTextField txtIdBook = new JTextField();
		txtIdBook.setEnabled(false);
		txtIdBook.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtIdBook.setForeground(Color.blue);

		JLabel lbTitle = new JLabel("Tên sách:", JLabel.CENTER);
		JTextField txtTitle = new JTextField();
		txtTitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtTitle.setForeground(Color.blue);

		JLabel lbAuthor = new JLabel("Tác giả:", JLabel.CENTER);
		JTextField txtAuthor = new JTextField();
		txtAuthor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtAuthor.setForeground(Color.blue);

		JLabel lbCategory = new JLabel("Thể loại:", JLabel.CENTER);
		JTextField txtCategory = new JTextField();
		txtCategory.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtCategory.setForeground(Color.blue);

		JLabel lbPublication_year = new JLabel("Năm sản xuất:", JLabel.CENTER);
		JTextField txtPublication_year = new JTextField();
		txtPublication_year.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPublication_year.setForeground(Color.blue);
		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0;
		c1.gridy = 0;
		c1.gridwidth = 1;
		c1.gridheight = 1;
		c1.fill = GridBagConstraints.BOTH;
		c1.weightx = 1.0;
		c1.weighty = 0.5;
		c1.anchor = GridBagConstraints.CENTER;
		c1.insets = new Insets(30, 5, 5, 5);
		infoModel.add(lbIdBook, c1);

		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx = 2;
		c2.gridy = 0;
		c2.gridwidth = 5;
		c2.gridheight = 1;
		c2.fill = GridBagConstraints.BOTH;
		c2.weightx = 5.0;
		c2.weighty = 0.5;
		c2.anchor = GridBagConstraints.CENTER;
		c2.insets = new Insets(30, 5, 5, 5);
		infoModel.add(txtIdBook, c2);

		GridBagConstraints c3 = new GridBagConstraints();
		c3.gridx = 0;
		c3.gridy = 1;
		c3.gridwidth = 1;
		c3.gridheight = 1;
		c3.fill = GridBagConstraints.BOTH;
		c3.weightx = 1.0;
		c3.weighty = 0.5;
		c3.anchor = GridBagConstraints.CENTER;
		c3.insets = new Insets(5, 5, 5, 5);
		infoModel.add(lbTitle, c3);

		GridBagConstraints c4 = new GridBagConstraints();
		c4.gridx = 2;
		c4.gridy = 1;
		c4.gridwidth = 5;
		c4.gridheight = 1;
		c4.fill = GridBagConstraints.BOTH;
		c4.weightx = 5.0;
		c4.weighty = 0.5;
		c4.anchor = GridBagConstraints.CENTER;
		c4.insets = new Insets(5, 5, 5, 5);
		infoModel.add(txtTitle, c4);

		GridBagConstraints c5 = new GridBagConstraints();
		c5.gridx = 0;
		c5.gridy = 2;
		c5.gridwidth = 1;
		c5.gridheight = 1;
		c5.fill = GridBagConstraints.BOTH;
		c5.weightx = 1.0;
		c5.weighty = 0.5;
		c5.anchor = GridBagConstraints.CENTER;
		c5.insets = new Insets(5, 5, 5, 5);
		infoModel.add(lbAuthor, c5);

		GridBagConstraints c6 = new GridBagConstraints();
		c6.gridx = 2;
		c6.gridy = 2;
		c6.gridwidth = 5;
		c6.gridheight = 1;
		c6.fill = GridBagConstraints.BOTH;
		c6.weightx = 5.0;
		c6.weighty = 0.5;
		c6.anchor = GridBagConstraints.CENTER;
		c6.insets = new Insets(5, 5, 5, 5);
		infoModel.add(txtAuthor, c6);

		GridBagConstraints c7 = new GridBagConstraints();
		c7.gridx = 0;
		c7.gridy = 3;
		c7.gridwidth = 1;
		c7.gridheight = 1;
		c7.fill = GridBagConstraints.BOTH;
		c7.weightx = 1.0;
		c7.weighty = 0.5;
		c7.anchor = GridBagConstraints.CENTER;
		c7.insets = new Insets(5, 5, 5, 5);
		infoModel.add(lbCategory, c7);

		GridBagConstraints c8 = new GridBagConstraints();
		c8.gridx = 2;
		c8.gridy = 3;
		c8.gridwidth = 5;
		c8.gridheight = 1;
		c8.fill = GridBagConstraints.BOTH;
		c8.weightx = 5.0;
		c8.weighty = 0.5;
		c8.anchor = GridBagConstraints.CENTER;
		c8.insets = new Insets(5, 5, 5, 5);
		infoModel.add(txtCategory, c8);

		GridBagConstraints c9 = new GridBagConstraints();
		c9.gridx = 0;
		c9.gridy = 4;
		c9.gridwidth = 1;
		c9.gridheight = 1;
		c9.fill = GridBagConstraints.BOTH;
		c9.weightx = 1.0;
		c9.weighty = 0.5;
		c9.anchor = GridBagConstraints.CENTER;
		c9.insets = new Insets(5, 5, 5, 5);
		infoModel.add(lbPublication_year, c9);

		GridBagConstraints c10 = new GridBagConstraints();
		c10.gridx = 2;
		c10.gridy = 4;
		c10.gridwidth = 5;
		c10.gridheight = 1;
		c10.fill = GridBagConstraints.BOTH;
		c10.weightx = 5.0;
		c10.weighty = 0.5;
		c10.anchor = GridBagConstraints.CENTER;
		c10.insets = new Insets(5, 5, 5, 5);
		infoModel.add(txtPublication_year, c10);
		setOffModelBook(infoModel);
		JPanel grboxButton = new JPanel();
		grboxButton.setPreferredSize(new Dimension(150, 350));
		grboxButton.setBackground(new Color(238, 240, 168));
		initCRUDComponents(grboxButton);
		JButton btnAdd = (JButton) grboxButton.getComponent(0);

		JButton btnEdit = (JButton) grboxButton.getComponent(1);
		btnEdit.setEnabled(false);
		JButton btnSave = (JButton) grboxButton.getComponent(2);
		btnSave.setEnabled(false);
		JButton btnDelete = (JButton) grboxButton.getComponent(3);
		btnDelete.setEnabled(false);
		JButton btnReset = (JButton) grboxButton.getComponent(4);
		btnAdd.addActionListener(e -> {
			addBook = true;
			setOnModelBook(infoModel);
			resetTextModelBook(infoModel);
			txtIdBook.setText("");
			txtTitle.requestFocus();
			btnSave.setEnabled(true);
			btnEdit.setEnabled(false);
			btnDelete.setEnabled(false);
		});
		btnEdit.addActionListener(e -> {
			addBook = false;
			setOnModelBook(infoModel);
			txtTitle.requestFocus();
			btnSave.setEnabled(true);
			btnAdd.setEnabled(false);
			btnDelete.setEnabled(false);
		});
		btnSave.addActionListener(e -> {
			if (addBook) {
				try {
					String title = txtTitle.getText();
					String author = txtAuthor.getText();
					String category = txtCategory.getText();
					String publication_year = txtPublication_year.getText();
					if (!publication_year.matches("\\d+")) {
						JOptionPane.showMessageDialog(null, "Vui lòng nhập số cho năm sản xuất", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
					} else {
						Book item = new Book(title, author, category, Integer.parseInt(publication_year));
						bookDao.add(item);
						btnReset.doClick();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Vui lòng thử lại sau!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				try {
					int id = Integer.parseInt(txtIdBook.getText());
					String title = txtTitle.getText();
					String author = txtAuthor.getText();
					String category = txtCategory.getText();
					String publication_year = txtPublication_year.getText();
					Book item = new Book(id, title, author, category, Integer.parseInt(publication_year));
					bookDao.update(item);
					JOptionPane.showMessageDialog(null, "Đã cập nhật thành công!", "Thành công",
							JOptionPane.INFORMATION_MESSAGE);
					btnReset.doClick();
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "Vui lòng thử lại sau!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnDelete.addActionListener(e -> {
			int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa quyển sách này không?", "Xác nhận",
					JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				// Delete the item
				int id = Integer.parseInt(txtIdBook.getText());
				try {
					int countB = loanDao.countByBook(id);
					if (countB > 0) {
						int opt = JOptionPane.showConfirmDialog(null,
								"Nếu xóa, tất cả các phiếu mượn trước đây sẽ bị xóa?", "Xác nhận",
								JOptionPane.YES_NO_OPTION);
						if (opt == JOptionPane.YES_OPTION) {
							loanDao.deleteByBook(id);
							bookDao.delete(id);
							JOptionPane.showMessageDialog(null, "Đã xóa thành công!", "Thành công",
									JOptionPane.INFORMATION_MESSAGE);
							btnReset.doClick();
						}
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Vui lòng thử lại sau!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnReset.addActionListener(e -> {
			// Code to be executed when the button is clicked
			fillDataTableBook(model);
			txtIdBook.setText("");
			resetTextModelBook(infoModel);
			setOffModelBook(infoModel);
			btnAdd.setEnabled(true);
			btnEdit.setEnabled(false);
			btnSave.setEnabled(false);
			btnDelete.setEnabled(false);
			addBook = false;
		});

		JPanel showData = new JPanel();
		showData.setPreferredSize(new Dimension(980, 334));
		showData.setBackground(new Color(238, 240, 168));
		showData.setBorder(new LineBorder(new Color(139, 141, 94), 2, true));
		showData.setLayout(new BorderLayout(0, 0));

		model.addColumn("Mã sách");
		model.addColumn("Tiêu đề");
		model.addColumn("Tác giả");
		model.addColumn("Thể loại");
		model.addColumn("Năm sản xuất");
		fillDataTableBook(model);

		// Create a table using the model
		JTable table = new JTable(model);
		JTableHeader header = table.getTableHeader();

		// set table to read only
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(new JTextField()) {
				@Override
				public boolean isCellEditable(EventObject anEvent) {
					return false;
				}
			});
		}
		// Create a custom renderer for the cells
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		// Set the custom renderer as the default renderer for all cells
		table.setDefaultRenderer(Object.class, centerRenderer);
		// Adjust the width of each column based on the width of the widest cell in that
		// column
		for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
			TableColumn column = table.getColumnModel().getColumn(columnIndex);
			int maxWidth = 0;
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, columnIndex);
				Object value = table.getValueAt(row, columnIndex);
				Component comp = renderer.getTableCellRendererComponent(table, value, false, false, row, columnIndex);
				maxWidth = Math.max(comp.getPreferredSize().width, maxWidth);
			}
			column.setPreferredWidth(maxWidth);
		}

		// Set header of table: color is Black, Font Bold, text align center
		header.setDefaultRenderer(new DefaultTableCellRenderer() {

			/**
					 * 
					 */

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				label.setBackground(new Color(221, 221, 227));
				label.setForeground(Color.BLACK);
				label.setFont(label.getFont().deriveFont(Font.BOLD));
				label.setHorizontalAlignment(JLabel.CENTER);
				return label;
			}
		});

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int pos = table.rowAtPoint(e.getPoint());
					int idbook = Integer.parseInt(table.getValueAt(pos, 0).toString());
					Book item = bookDao.getItem(idbook);

					txtIdBook.setText(item.getBook_id() + "");
					txtTitle.setText(item.getTitle());
					txtAuthor.setText(item.getAuthor());
					txtCategory.setText(item.getCategory());
					txtPublication_year.setText(item.getPublication_year() + "");
					btnEdit.setEnabled(true);
					btnDelete.setEnabled(true);
					addBook = false;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		// Add the table to a scroll pane
		JScrollPane scrollPane = new JScrollPane(table);
		showData.add(scrollPane, BorderLayout.CENTER);

		books.add(infoModel, BorderLayout.WEST);
		books.add(grboxButton, BorderLayout.EAST);
		books.add(showData, BorderLayout.SOUTH);
		return books;
	}

	private void setOffModelBook(JPanel infoModel) {
		// TODO Auto-generated method stub
		JTextField txtTitle = (JTextField) infoModel.getComponent(3);
		JTextField txtAuthor = (JTextField) infoModel.getComponent(5);
		JTextField txtCategory = (JTextField) infoModel.getComponent(7);
		JTextField txtPublication_year = (JTextField) infoModel.getComponent(9);
		txtTitle.setEnabled(false);
		txtAuthor.setEnabled(false);
		txtCategory.setEnabled(false);
		txtPublication_year.setEnabled(false);
	}

	private void fillDataTableBook(DefaultTableModel model) {
		// TODO Auto-generated method stub
		model.setRowCount(0);
		try {
			for (Book b : bookDao.findAll()) {
				Object data[] = new Object[5];
				data[0] = b.getBook_id();
				data[1] = b.getTitle();
				data[2] = b.getAuthor();
				data[3] = b.getCategory();
				data[4] = b.getPublication_year();
				model.addRow(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void resetTextModelBook(JPanel infoModel) {
		// TODO Auto-generated method stub
		JTextField txtTitle = (JTextField) infoModel.getComponent(3);
		JTextField txtAuthor = (JTextField) infoModel.getComponent(5);
		JTextField txtCategory = (JTextField) infoModel.getComponent(7);
		JTextField txtPublication_year = (JTextField) infoModel.getComponent(9);
		txtTitle.setText("");
		txtAuthor.setText("");
		txtCategory.setText("");
		txtPublication_year.setText("");
	}

	private void setOnModelBook(JPanel infoModel) {
		// TODO Auto-generated method stub
		JTextField txtTitle = (JTextField) infoModel.getComponent(3);
		JTextField txtAuthor = (JTextField) infoModel.getComponent(5);
		JTextField txtCategory = (JTextField) infoModel.getComponent(7);
		JTextField txtPublication_year = (JTextField) infoModel.getComponent(9);
		txtTitle.setEnabled(true);
		txtAuthor.setEnabled(true);
		txtCategory.setEnabled(true);
		txtPublication_year.setEnabled(true);
	}

	JPanel managementBorrows() {
		JPanel borrows = new JPanel();
		DefaultTableModel model = new DefaultTableModel();
		borrows.setPreferredSize(new Dimension(980, 684));
		borrows.setBackground(new Color(238, 240, 168));
		borrows.setLayout(new BorderLayout(0, 0));

		JPanel infoModel = new JPanel();
		infoModel.setPreferredSize(new Dimension(830, 350));
		infoModel.setBackground(new Color(238, 240, 168));
		infoModel.setBorder(new LineBorder(new Color(139, 141, 94), 2, true)); // set line border
		infoModel.setLayout(new GridBagLayout());

		JLabel lbIdloan = new JLabel("Mã phiếu mượn:", JLabel.LEADING);
		JTextField txtIdLoan = new JTextField();
		txtIdLoan.setEnabled(false);
		txtIdLoan.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel lbIdBook = new JLabel("Quyển sách:", JLabel.LEADING);
		JTextField txtMaSach = new JTextField();
		txtMaSach.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtMaSach.setEnabled(false);
		JLabel lbIdMember = new JLabel("Độc giả:", JLabel.LEADING);
		JTextField txtIdMember = new JTextField();
		txtIdMember.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtIdMember.setEnabled(false);
		JLabel lbDateIn = new JLabel("Ngày mượn:", JLabel.LEADING);
		JTextField datePicker = new JTextField();
		datePicker.setFont(new Font("Tahoma", Font.BOLD, 14));
		datePicker.setEnabled(false);
		JLabel lbDateOut = new JLabel("Ngày trả:", JLabel.LEADING);
		JTextField datePicker2 = new JTextField();
		datePicker2.setFont(new Font("Tahoma", Font.BOLD, 14));
		datePicker2.setEnabled(false);

		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0;
		c1.gridy = 0;
		c1.gridwidth = 1;
		c1.gridheight = 1;
		c1.fill = GridBagConstraints.BOTH;
		c1.weightx = 1.0;
		c1.weighty = 0.5;
		c1.anchor = GridBagConstraints.CENTER;
		c1.insets = new Insets(30, 5, 5, 5);
		infoModel.add(lbIdloan, c1);

		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx = 2;
		c2.gridy = 0;
		c2.gridwidth = 5;
		c2.gridheight = 1;
		c2.fill = GridBagConstraints.BOTH;
		c2.weightx = 5.0;
		c2.weighty = 0.5;
		c2.anchor = GridBagConstraints.CENTER;
		c2.insets = new Insets(30, 5, 5, 5);
		infoModel.add(txtIdLoan, c2);

		GridBagConstraints c3 = new GridBagConstraints();
		c3.gridx = 0;
		c3.gridy = 1;
		c3.gridwidth = 1;
		c3.gridheight = 1;
		c3.fill = GridBagConstraints.BOTH;
		c3.weightx = 1.0;
		c3.weighty = 0.5;
		c3.anchor = GridBagConstraints.CENTER;
		c3.insets = new Insets(5, 5, 5, 5);
		infoModel.add(lbIdBook, c3);

		GridBagConstraints c4 = new GridBagConstraints();
		c4.gridx = 2;
		c4.gridy = 1;
		c4.gridwidth = 5;
		c4.gridheight = 1;
		c4.fill = GridBagConstraints.BOTH;
		c4.weightx = 5.0;
		c4.weighty = 0.5;
		c4.anchor = GridBagConstraints.CENTER;
		c4.insets = new Insets(5, 5, 5, 5);
		infoModel.add(txtMaSach, c4);

		GridBagConstraints c5 = new GridBagConstraints();
		c5.gridx = 0;
		c5.gridy = 2;
		c5.gridwidth = 1;
		c5.gridheight = 1;
		c5.fill = GridBagConstraints.BOTH;
		c5.weightx = 1.0;
		c5.weighty = 0.5;
		c5.anchor = GridBagConstraints.CENTER;
		c5.insets = new Insets(5, 5, 5, 5);
		infoModel.add(lbIdMember, c5);

		GridBagConstraints c6 = new GridBagConstraints();
		c6.gridx = 2;
		c6.gridy = 2;
		c6.gridwidth = 5;
		c6.gridheight = 1;
		c6.fill = GridBagConstraints.BOTH;
		c6.weightx = 5.0;
		c6.weighty = 0.5;
		c6.anchor = GridBagConstraints.CENTER;
		c6.insets = new Insets(5, 5, 5, 5);
		infoModel.add(txtIdMember, c6);

		GridBagConstraints c7 = new GridBagConstraints();
		c7.gridx = 0;
		c7.gridy = 3;
		c7.gridwidth = 1;
		c7.gridheight = 1;
		c7.fill = GridBagConstraints.BOTH;
		c7.weightx = 1.0;
		c7.weighty = 0.5;
		c7.anchor = GridBagConstraints.CENTER;
		c7.insets = new Insets(5, 5, 5, 5);
		infoModel.add(lbDateIn, c7);

		GridBagConstraints c8 = new GridBagConstraints();
		c8.gridx = 2;
		c8.gridy = 3;
		c8.gridwidth = 5;
		c8.gridheight = 1;
		c8.fill = GridBagConstraints.BOTH;
		c8.weightx = 5.0;
		c8.weighty = 0.5;
		c8.anchor = GridBagConstraints.CENTER;
		c8.insets = new Insets(5, 5, 5, 5);
		infoModel.add(datePicker, c8);

		GridBagConstraints c9 = new GridBagConstraints();
		c9.gridx = 0;
		c9.gridy = 4;
		c9.gridwidth = 1;
		c9.gridheight = 1;
		c9.fill = GridBagConstraints.BOTH;
		c9.weightx = 1.0;
		c9.weighty = 0.5;
		c9.anchor = GridBagConstraints.CENTER;
		c9.insets = new Insets(5, 5, 5, 5);
		infoModel.add(lbDateOut, c9);

		GridBagConstraints c10 = new GridBagConstraints();
		c10.gridx = 2;
		c10.gridy = 4;
		c10.gridwidth = 5;
		c10.gridheight = 1;
		c10.fill = GridBagConstraints.BOTH;
		c10.weightx = 5.0;
		c10.weighty = 0.5;
		c10.anchor = GridBagConstraints.CENTER;
		c10.insets = new Insets(5, 5, 5, 5);
		infoModel.add(datePicker2, c10);
		JPanel grboxButton = new JPanel();
		grboxButton.setPreferredSize(new Dimension(150, 350));
		grboxButton.setBackground(new Color(238, 240, 168));

		JPanel showData = new JPanel();
		showData.setPreferredSize(new Dimension(980, 334));
		showData.setBackground(new Color(238, 240, 168));
		showData.setBorder(new LineBorder(new Color(139, 141, 94), 2, true));
		showData.setLayout(new BorderLayout(0, 0));

		model.addColumn("Mã phiếu mượn");
		model.addColumn("Mã sách");
		model.addColumn("Mã độc giả");
		model.addColumn("Ngày mượn");
		model.addColumn("Ngày trả");
		fillDataTableBorrow(model);

		// Create a table using the model
		JTable table = new JTable(model);
		JTableHeader header = table.getTableHeader();

		// set table to read only
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(new JTextField()) {
				@Override
				public boolean isCellEditable(EventObject anEvent) {
					return false;
				}
			});
		}
		// Create a custom renderer for the cells
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		// Set the custom renderer as the default renderer for all cells
		table.setDefaultRenderer(Object.class, centerRenderer);
		// Adjust the width of each column based on the width of the widest cell in that
		// column
		for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
			TableColumn column = table.getColumnModel().getColumn(columnIndex);
			int maxWidth = 0;
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, columnIndex);
				Object value = table.getValueAt(row, columnIndex);
				Component comp = renderer.getTableCellRendererComponent(table, value, false, false, row, columnIndex);
				maxWidth = Math.max(comp.getPreferredSize().width, maxWidth);
			}
			column.setPreferredWidth(maxWidth);
		}

		// Set header of table: color is Black, Font Bold, text align center
		header.setDefaultRenderer(new DefaultTableCellRenderer() {

			/**
					 * 
					 */

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				label.setBackground(new Color(221, 221, 227));
				label.setForeground(Color.BLACK);
				label.setFont(label.getFont().deriveFont(Font.BOLD));
				label.setHorizontalAlignment(JLabel.CENTER);
				return label;
			}
		});

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int pos = table.rowAtPoint(e.getPoint());
					int idLoan = Integer.parseInt(table.getValueAt(pos, 0).toString());
					Loan item = loanDao.getItem(idLoan);
					txtIdLoan.setText(item.getLoan_id() + "");
					txtMaSach.setText(item.getBook_id() + "");
					txtIdMember.setText(item.getMember_id() + "");
					datePicker.setText(item.getDate_in() + "");
					datePicker2.setText(item.getDate_out() == null ? "Chưa trả" : item.getDate_out().toString());

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		// Add the table to a scroll pane
		JScrollPane scrollPane = new JScrollPane(table);
		showData.add(scrollPane, BorderLayout.CENTER);

		borrows.add(infoModel, BorderLayout.WEST);
		borrows.add(grboxButton, BorderLayout.EAST);
		borrows.add(showData, BorderLayout.SOUTH);
		return borrows;
	}

	private void fillDataTableBorrow(DefaultTableModel model) {
		// TODO Auto-generated method stub
		model.setRowCount(0);
		try {
			for (Loan b : loanDao.findAll()) {
				Object data[] = new Object[5];
				data[0] = b.getLoan_id();
				data[1] = b.getBook_id();
				data[2] = b.getMember_id();
				data[3] = b.getDate_in();
				data[4] = b.getDate_out() == null ? "Chưa trả" : b.getDate_out() + "";
				model.addRow(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	JPanel managementStatistics() {
		JPanel statistics = new JPanel();
		statistics.setPreferredSize(new Dimension(980, 684));
		statistics.setBackground(new Color(238, 240, 168));
		statistics.setLayout(new BoxLayout(statistics, BoxLayout.Y_AXIS));

		int countMember = memberDao.count();
		int countBook = 0;
		try {
			countBook = bookDao.count();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int countLoan = loanDao.count();

		JLabel lbCountMember = new JLabel("Số lượng độc giả: " + countMember, JLabel.CENTER);
		lbCountMember.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbCountMember.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel lbCountBook = new JLabel("Số lượng sách: " + countBook, JLabel.CENTER);
		lbCountBook.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbCountBook.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel lbCountLoan = new JLabel("Số lượng phiếu mượn: " + countLoan, JLabel.CENTER);
		lbCountLoan.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbCountLoan.setAlignmentX(Component.CENTER_ALIGNMENT);
		statistics.add(Box.createRigidArea(new Dimension(0, 250)));
		statistics.add(lbCountMember);
		statistics.add(lbCountBook);
		statistics.add(lbCountLoan);

		return statistics;
	}

	public AdminMainFrame(LoginFrame flogin) {
		frameLogin = flogin;
		frameLogin.setVisible(false);
		memberDao = new MemberDao();
		bookDao = new BookDao();
		loanDao = new LoanDao();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(true);

		getContentPane().setLayout(new BorderLayout(0, 0));
		container = new JPanel();
		container.setBorder(new EmptyBorder(8, 8, 8, 8));
		container.setLayout(new BorderLayout(0, 0));
		container.setPreferredSize(new Dimension(1000, 700));
		// Jpanel slider
		JPanel pnSlide = new JPanel();
		pnSlide.setBackground(Color.LIGHT_GRAY);
		pnSlide.setPreferredSize(new Dimension(200, 700));
		getContentPane().add(pnSlide, BorderLayout.WEST);
		pnSlide.setLayout(new GridLayout(13, 1, 8, 8)); // 18 rows, 1 column, vgap hgap : 8px
		JButton btnMembers = new JButton("Quản lý độc giả");
		btnMembers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				container.removeAll();
				container.add(lbTitleContainer, BorderLayout.NORTH);
				container.add(managementMembers(), BorderLayout.CENTER);
				container.repaint();
				container.revalidate();
				lbTitleContainer.setText("Quản lý độc giả");
			}
		});
		JButton btnBooks = new JButton("Quản lý sách");
		btnBooks.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				container.removeAll();
				container.add(lbTitleContainer, BorderLayout.NORTH);
				container.add(managementBooks(), BorderLayout.CENTER);
				container.repaint();
				container.revalidate();
				lbTitleContainer.setText("Quản lý sách");
			}
		});
		JButton btnBorrows = new JButton("Quản lý mượn trả sách");
		btnBorrows.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				container.removeAll();
				container.repaint();
				container.revalidate();
				container.add(lbTitleContainer, BorderLayout.NORTH);
				container.add(managementBorrows(), BorderLayout.CENTER);
				lbTitleContainer.setText("Quản lý mượn trả sách");
			}
		});
		JButton btnStatistics = new JButton("Thống kê");
		btnStatistics.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				container.removeAll();
				container.add(lbTitleContainer, BorderLayout.NORTH);
				container.add(managementStatistics(), BorderLayout.CENTER);
				container.repaint();
				container.revalidate();
				lbTitleContainer.setText("Thống kê");
			}
		});
		pnSlide.add(btnMembers);
		pnSlide.add(btnBooks);
		pnSlide.add(btnBorrows);
		pnSlide.add(btnStatistics);

		for (int i = 4; i < 12; i++) {
			JPanel tmp = new JPanel();
			tmp.setBackground(Color.LIGHT_GRAY);
			pnSlide.add(tmp);
		}
		JButton btnLogout = new JButton("Đăng xuất");
		btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		pnSlide.add(btnLogout);
		// JPanel header
		JPanel pnHeader = new JPanel();
		pnHeader.setBackground(Color.LIGHT_GRAY);
		pnHeader.setPreferredSize(new Dimension(1200, 50));
		getContentPane().add(pnHeader, BorderLayout.NORTH);
		pnHeader.setLayout(new BorderLayout(0, 0));
		JPanel pnExit = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnExit.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		pnExit.setBackground(Color.LIGHT_GRAY);
		pnExit.setPreferredSize(new Dimension(50, 50));
		pnHeader.add(pnExit, BorderLayout.EAST);
		JLabel lbExit = new JLabel("", JLabel.CENTER);
		lbExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				// System.exit(0);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				pnExit.setBackground(Color.gray);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				pnExit.setBackground(Color.LIGHT_GRAY);
			}
		});
		JLabel lbTitle = new JLabel("QUẢN LÝ THƯ VIỆN", JLabel.CENTER);
		lbTitle.setForeground(Color.BLUE);
		lbTitle.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lbTitle.setPreferredSize(new Dimension(1150, 50));
		pnHeader.add(lbTitle, BorderLayout.CENTER);

		lbExit.setPreferredSize(new Dimension(50, 50));
		lbExit.setIcon(new ImageIcon(AdminMainFrame.class.getResource("/icon/close.png")));
		pnExit.add(lbExit);

		// JPanel container

		getContentPane().add(container, BorderLayout.CENTER);
		lbTitleContainer = new JLabel("Quản lý sách", JLabel.CENTER);
		lbTitleContainer.setForeground(Color.BLUE);
		lbTitleContainer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbTitleContainer.setPreferredSize(new Dimension(1000, 30));
		container.add(lbTitleContainer, BorderLayout.NORTH);
		container.setBackground(new Color(238, 240, 168));
		container.add(managementBooks(), BorderLayout.CENTER);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				flogin.setVisible(true);
			}
		});

		pack();
		setLocationRelativeTo(null);
	}

}
