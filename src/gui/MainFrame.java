package gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import dao.*;
import entity.Book;
import entity.DetailLoan;
import entity.Member;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.EventObject;

public class MainFrame extends JFrame {

	private LoanDao loanDao;
	private MemberDao memberDao;
	private BookDao bookDao;

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LoginFrame frame = new LoginFrame();
//					MemberDao aa = new MemberDao();
//					Member usera = aa.getItem(2);
//					MainFrame f = new MainFrame(frame, usera);
//					f.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	private LoginFrame flogin;
	private Member currentMember;

	public MainFrame(LoginFrame f, Member m) {
		loanDao = new LoanDao();
		memberDao = new MemberDao();
		bookDao = new BookDao();
		currentMember = m;
		flogin = f;
		flogin.setVisible(false);
		setUndecorated(true);
		setBounds(100, 100, 450, 300);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				flogin.setVisible(true);
			}
		});
		getContentPane().setLayout(new BorderLayout(0, 0));

		// JPanel header
		JPanel header = new JPanel();
		header.setPreferredSize(new Dimension(1200, 50));
		header.setLayout(new BorderLayout(0, 0));
		JPanel pnExit = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnExit.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		pnExit.setPreferredSize(new Dimension(50, 50));

		header.add(pnExit, BorderLayout.EAST);
		JLabel lbExit = new JLabel("", JLabel.CENTER);
		lbExit.setPreferredSize(new Dimension(50, 50));
		lbExit.setIcon(new ImageIcon(MainFrame.class.getResource("/icon/close.png")));
		pnExit.add(lbExit);
		lbExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
				pnExit.setBackground(new Color(240, 240, 240));
			}
		});

		JLabel title = new JLabel("QUẢN LÝ THƯ VIỆN", JLabel.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 20));
		header.add(title, BorderLayout.CENTER);

		// slider
		JPanel slider = new JPanel(new GridLayout(15, 1, 8, 8));
		slider.setPreferredSize(new Dimension(200, 700));
		getContentPane().add(slider, BorderLayout.WEST);
		JButton btnTraCuu = new JButton("Tra cứu");
		btnTraCuu.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JButton btnDSMuon = new JButton("Danh sách mượn");
		btnDSMuon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JButton btnInfo = new JButton("Thông tin cá nhân");
		btnInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		slider.add(btnTraCuu);
		slider.add(btnDSMuon);
		slider.add(btnInfo);
		for (int i = 3; i <= 13; i++) {
			JPanel tmp = new JPanel();
			slider.add(tmp);
		}
		JButton btnLogout = new JButton("Đăng xuất");
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 14));
		slider.add(btnLogout);
		btnLogout.addActionListener(e -> {
			dispose();
		});

		// Container
		JPanel container = new JPanel();
		container.setBackground(new Color(254, 232, 182));
		getContentPane().add(container, BorderLayout.CENTER);
		container.add(pnBorrowBook());

		btnTraCuu.addActionListener(e -> {
			container.removeAll();
			container.add(pnTraCuu());
			container.repaint();
			container.revalidate();

		});
		btnDSMuon.addActionListener(e -> {
			container.removeAll();
			container.add(pnBorrowBook());
			container.repaint();
			container.revalidate();
		});
		btnInfo.addActionListener(e -> {
			container.removeAll();
			container.add(pnInfoMember());
			container.repaint();
			container.revalidate();
		});
		getContentPane().add(header, BorderLayout.NORTH);
		pack();
		setLocationRelativeTo(null);

	}

	private JPanel pnInfoMember() {
		// TODO Auto-generated method stub

		JPanel container = new JPanel(new BorderLayout(0, 0));
		container.setForeground(Color.BLUE);
		container.setPreferredSize(new Dimension(500, 300));
		JLabel lbTitle = new JLabel("Thông tin cá nhân", JLabel.CENTER);
		lbTitle.setForeground(Color.BLUE);
		lbTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
		lbTitle.setPreferredSize(new Dimension(1000, 50));
		lbTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JPanel content = new JPanel(new GridLayout(6, 1, 4, 4));
		JLabel lbUsername = new JLabel("Tên đăng nhập: " + currentMember.getUsername(), JLabel.LEADING);
		lbUsername.setForeground(Color.BLUE);
		lbUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbUsername.setPreferredSize(new Dimension(900, 50));
		JLabel lbPassword = new JLabel("Mật khẩu: " + currentMember.getPassword(), JLabel.LEADING);
		lbPassword.setForeground(Color.BLUE);
		lbPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbPassword.setPreferredSize(new Dimension(900, 50));
		JLabel lbName = new JLabel("Họ và tên: " + currentMember.getName(), JLabel.LEADING);
		lbName.setForeground(Color.BLUE);
		lbName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbName.setPreferredSize(new Dimension(900, 50));
		container.add(lbTitle, BorderLayout.NORTH);
		content.add(lbUsername);
		content.add(lbPassword);
		content.add(lbName);
		container.add(content, BorderLayout.CENTER);
		return container;
	}

	public JPanel pnTraCuu() {
		JPanel container = new JPanel(new BorderLayout(0, 0));
		container.setPreferredSize(new Dimension(1000, 700));
		JPanel headContainer = new JPanel(new BorderLayout(0, 0));
		headContainer.setPreferredSize(new Dimension(1000, 110));
		// headContainer.setBorder(new LineBorder(new Color(139, 141, 94), 2, true));
		JPanel pnSearch = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnSearch.getLayout();
		flowLayout.setHgap(20);
		flowLayout.setVgap(0);
		flowLayout.setAlignment(FlowLayout.LEADING);
		// pnSearch.setBorder(new LineBorder(new Color(139, 141, 94), 2, true));
		pnSearch.setPreferredSize(new Dimension(1000, 75));
		JLabel lbSearch = new JLabel("Tìm kiếm", SwingConstants.CENTER);
		lbSearch.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lbSearch.setPreferredSize(new Dimension(100, 75));
		pnSearch.add(lbSearch);
		JTextField txtSeach = new JTextField();
		txtSeach.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtSeach.setPreferredSize(new Dimension(700, 30));
		pnSearch.add(txtSeach);
		JButton btnSearch = new JButton("Tìm kiếm");
		btnSearch.setPreferredSize(new Dimension(100, 30));
		pnSearch.add(btnSearch);

		// Panel
		JLabel lbTitle = new JLabel("Danh sách các cuốn sách trong thư viện", JLabel.CENTER);
		lbTitle.setPreferredSize(new Dimension(1000, 25));
		lbTitle.setFont(new Font("Tahoma", Font.PLAIN, 24));
		headContainer.add(lbTitle, BorderLayout.CENTER);

		// JPanel table data
		JPanel pnTableData = new JPanel(new BorderLayout(0, 0));
		pnTableData.setPreferredSize(new Dimension(900, 500));
		pnTableData.setBorder(new LineBorder(new Color(139, 141, 94), 2, true));
		container.add(pnTableData, BorderLayout.CENTER);
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Mã sách");
		model.addColumn("Tiêu đề");
		model.addColumn("Tác giả");
		model.addColumn("Thể loại");
		model.addColumn("Năm sản xuất");
		fillDataTableTraCuu(model);
		// Create a table using the model
		JTable table = new JTable(model);
		JTableHeader header = table.getTableHeader();
//
//		// set table to read only
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(new JTextField()) {
				@Override
				public boolean isCellEditable(EventObject anEvent) {
					return false;
				}
			});
		}
//		// Create a custom renderer for the cells
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
//
//		// Set the custom renderer as the default renderer for all cells
		table.setDefaultRenderer(Object.class, centerRenderer);
//		// Adjust the width of each column based on the width of the widest cell in that
//		// column
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
//
//		// Set header of table: color is Black, Font Bold, text align center
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
//
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (e.getClickCount() == 2) {
						int pos = table.rowAtPoint(e.getPoint());
						int idbook = Integer.parseInt(table.getValueAt(pos, 0).toString());
						Book item = bookDao.getItem(idbook);
						BookDetailFrame bookdetail = new BookDetailFrame(item, currentMember);
						bookdetail.setVisible(true);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnSearch.addActionListener(e -> {
			model.setRowCount(0);
			try {
				String text = txtSeach.getText();
				for (Book b : bookDao.findAllByName(text)) {
					Object data[] = new Object[5];
					data[0] = b.getBook_id();
					data[1] = b.getTitle();
					data[2] = b.getAuthor();
					data[3] = b.getCategory();
					data[4] = b.getPublication_year();
					model.addRow(data);
				}
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		});
		// Add the table to a scroll pane
		JScrollPane scrollPane = new JScrollPane(table);
		pnTableData.add(scrollPane, BorderLayout.CENTER);
		headContainer.add(pnSearch, BorderLayout.NORTH);
		container.add(headContainer, BorderLayout.NORTH);
		return container;
	}

	private void fillDataTableTraCuu(DefaultTableModel model) {
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

	private void fillDataTableBorrow(DefaultTableModel model) {
		// TODO Auto-generated method stub
		model.setRowCount(0);
		try {
			for (DetailLoan b : loanDao.getAllBorrow(currentMember.getMember_id())) {
				Object data[] = new Object[8];
				data[0] = b.getBook_id();
				data[1] = b.getTitle();
				data[2] = b.getAuthor();
				data[3] = b.getCategory();
				data[4] = b.getPublication_year();
				data[5] = b.getLoan_id();
				data[6] = b.getDate_in();
				data[7] = b.getDate_out() == null ? "NULL" : b.getDate_out();
				model.addRow(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public JPanel pnBorrowBook() {
		JPanel pnContainer = new JPanel(new BorderLayout(0, 0));
		pnContainer.setPreferredSize(new Dimension(1000, 700));
		pnContainer.setBackground(new Color(254, 232, 182));

		JPanel top = new JPanel();
		FlowLayout flowLayout = (FlowLayout) top.getLayout();
		flowLayout.setHgap(30);
		flowLayout.setVgap(0);
		top.setPreferredSize(new Dimension(1000, 50));
		JLabel lbIcon = new JLabel("", JLabel.CENTER);
		lbIcon.setIcon(new ImageIcon(MainFrame.class.getResource("/icon/borrowBook.png")));
		JLabel lbTitle = new JLabel("Thông tin các quyển sách đã mượn", JLabel.LEADING);
		lbTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbIcon.setPreferredSize(new Dimension(50, 50));
		lbTitle.setPreferredSize(new Dimension(500, 50));
		top.add(lbIcon);
		top.add(lbTitle);

		JPanel pnTableData = new JPanel();
		pnTableData.setPreferredSize(new Dimension(900, 500));
		pnTableData.setBorder(new LineBorder(new Color(139, 141, 94), 2, true));
		pnContainer.add(pnTableData, BorderLayout.CENTER);
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Mã sách");
		model.addColumn("Tiêu đề");
		model.addColumn("Tác giả");
		model.addColumn("Thể loại");
		model.addColumn("Năm sản xuất");
		model.addColumn("Mã phiếu mượn");
		model.addColumn("Ngày mượn");
		model.addColumn("Ngày trả");
		fillDataTableBorrow(model);
		// Create a table using the model
		JTable table = new JTable(model);
		JTableHeader header = table.getTableHeader();
//
//		// set table to read only
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(new JTextField()) {
				@Override
				public boolean isCellEditable(EventObject anEvent) {
					return false;
				}
			});
		}
//		// Create a custom renderer for the cells
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
//
//		// Set the custom renderer as the default renderer for all cells
		table.setDefaultRenderer(Object.class, centerRenderer);
//		// Adjust the width of each column based on the width of the widest cell in that
//		// column
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
//
//		// Set header of table: color is Black, Font Bold, text align center
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
					if (e.getClickCount() == 2) {
						int opt = JOptionPane.showConfirmDialog(null, "Bạn muốn trả cuốn sách này?", "Xác nhận",
								JOptionPane.YES_NO_OPTION);
						if (opt == JOptionPane.YES_NO_OPTION) {
							int pos = table.rowAtPoint(e.getPoint());
							int idLoan = Integer.parseInt(table.getValueAt(pos, 5).toString());
							loanDao.returnBook(idLoan);
							JOptionPane.showMessageDialog(null, "Trả sách thành công!", "Thành công",
									JOptionPane.INFORMATION_MESSAGE);
							fillDataTableBorrow(model);
						}
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		// Add the table to a scroll pane
		JScrollPane scrollPane = new JScrollPane(table);

		// JPanel total book
		JPanel footer = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) footer.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEADING);
		flowLayout_1.setVgap(0);
		footer.setPreferredSize(new Dimension(1000, 100));
		footer.setBackground(new Color(186, 188, 123));
		JLabel totalBook = new JLabel("Tổng số lượt mượn: " + model.getRowCount());
		totalBook.setFont(new Font("Tahoma", Font.PLAIN, 20));
		totalBook.setPreferredSize(new Dimension(300, 100));
		footer.add(totalBook);
		pnContainer.add(top, BorderLayout.NORTH);
		pnContainer.add(scrollPane, BorderLayout.CENTER);
		pnContainer.add(footer, BorderLayout.SOUTH);
		return pnContainer;
	}
}
