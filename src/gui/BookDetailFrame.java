package gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import dao.LoanDao;
import entity.Book;
import entity.Loan;
import entity.Member;

public class BookDetailFrame extends JFrame {

	private Book currentBook;
	private LoanDao dao;
	private Member currentMember;
	public BookDetailFrame(Book b, Member m) {
		currentBook = b;
		currentMember = m;
		dao = new LoanDao();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(new Dimension(600, 350));
		setTitle("BOOK DETAIL");
		setLocationRelativeTo(null);
		setBackground(new Color(254, 232, 182));
		getContentPane().setLayout(new BorderLayout(0, 0));
		// Title of book
		JPanel pnTitle = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnTitle.getLayout();
		flowLayout.setHgap(30);
		flowLayout.setAlignment(FlowLayout.LEADING);
		flowLayout.setVgap(0);
		pnTitle.setPreferredSize(new Dimension(600, 50));
		pnTitle.setBackground(new Color(254, 232, 182));
		JLabel lbIcon = new JLabel("", JLabel.CENTER);
		lbIcon.setIcon(new ImageIcon(BookDetailFrame.class.getResource("/icon/bookdetail.png")));
		lbIcon.setPreferredSize(new Dimension(50, 50));
		JLabel lbTitle = new JLabel(currentBook.getTitle(), SwingConstants.LEADING);
		lbTitle.setPreferredSize(new Dimension(350, 50));
		lbTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
		pnTitle.add(lbIcon);
		pnTitle.add(lbTitle);

		// other infomation of book
		JPanel pnContent = new JPanel(new GridLayout(4, 1, 16, 16));
		pnContent.setBackground(new Color(254, 232, 182));

		JPanel pnAuthor = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) pnAuthor.getLayout();
		flowLayout_1.setHgap(30);
		flowLayout_1.setAlignment(FlowLayout.LEADING);
		flowLayout_1.setVgap(0);
		pnAuthor.setBackground(new Color(254, 232, 182));
		JLabel lbAuthor1 = new JLabel("Tác giả:", JLabel.LEADING);
		lbAuthor1.setForeground(Color.BLUE);
		lbAuthor1.setFont(new Font("Tahoma", Font.ITALIC, 20));
		pnAuthor.add(lbAuthor1);
		lbAuthor1.setPreferredSize(new Dimension(150, 50));
		pnContent.add(pnAuthor);
		JLabel lbAuthor2 = new JLabel(currentBook.getAuthor(), JLabel.CENTER);
		lbAuthor2.setForeground(Color.BLUE);
		lbAuthor2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pnAuthor.add(lbAuthor2);
		lbAuthor2.setPreferredSize(new Dimension(200, 50));

		JPanel pnCategory = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) pnCategory.getLayout();
		flowLayout_2.setHgap(30);
		flowLayout_2.setAlignment(FlowLayout.LEADING);
		flowLayout_2.setVgap(0);
		pnCategory.setBackground(new Color(254, 232, 182));
		JLabel lbCategory1 = new JLabel("Thể loại:", JLabel.LEADING);
		lbCategory1.setForeground(Color.BLUE);
		lbCategory1.setFont(new Font("Tahoma", Font.ITALIC, 20));
		pnCategory.add(lbCategory1);
		lbCategory1.setPreferredSize(new Dimension(150, 50));
		pnContent.add(pnCategory);
		JLabel lbCategory2 = new JLabel(currentBook.getCategory(), JLabel.CENTER);
		lbCategory2.setForeground(Color.BLUE);
		lbCategory2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pnCategory.add(lbCategory2);
		lbCategory2.setPreferredSize(new Dimension(200, 50));

		JPanel pnPublicYear = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) pnPublicYear.getLayout();
		flowLayout_3.setHgap(30);
		flowLayout_3.setAlignment(FlowLayout.LEADING);
		flowLayout_3.setVgap(0);
		pnPublicYear.setBackground(new Color(254, 232, 182));
		JLabel lbPublicYear1 = new JLabel("Năm sản xuất:", JLabel.LEADING);
		lbPublicYear1.setForeground(Color.BLUE);
		lbPublicYear1.setFont(new Font("Tahoma", Font.ITALIC, 20));
		pnPublicYear.add(lbPublicYear1);
		lbPublicYear1.setPreferredSize(new Dimension(150, 50));
		pnContent.add(pnPublicYear);
		JLabel lbPublicYear2 = new JLabel(currentBook.getPublication_year() + "", JLabel.CENTER);
		lbPublicYear2.setForeground(Color.BLUE);
		lbPublicYear2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pnPublicYear.add(lbPublicYear2);
		lbPublicYear2.setPreferredSize(new Dimension(200, 50));

		JPanel pnBorrow = new JPanel();
		pnBorrow.setFont(new Font("Tahoma", Font.PLAIN, 20));
		FlowLayout flowLayout_4 = (FlowLayout) pnBorrow.getLayout();
		flowLayout_4.setVgap(0);
		pnBorrow.setBackground(new Color(254, 232, 182));
		JButton btnBorrow = new JButton("Mượn sách");
		btnBorrow.setPreferredSize(new Dimension(100, 30));
		btnBorrow.addActionListener(e->{
			try {
				if(dao.checkValidBorrow(currentMember.getMember_id())) {
					dao.add(new Loan(currentBook.getBook_id(), currentMember.getMember_id()));
					JOptionPane.showMessageDialog(null, "Mượn sách thành công", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Bạn đã mượn quá 3 quyển sách trong tuần này", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		});
		pnBorrow.add(btnBorrow);
		pnContent.add(pnBorrow);

		getContentPane().add(pnTitle, BorderLayout.NORTH);
		getContentPane().add(pnContent, BorderLayout.CENTER);
	}

}
