package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.*;

import entity.*;

public class LoanDao {
	static final String INSERT = "INSERT INTO loans(book_id, member_id, date_in) VALUES(?,?,NOW())";
	static final String FINDALL = "SELECT * FROM loans";
	static final String GET_ITEM = "SELECT * FROM loans where loan_id = ?";
	static final String UPDATE = "UPDATE loans SET book_id = ?, member_id = ?, date_out = ?, date_in = ?  WHERE loan_id = ?";
	static final String DELETE = "DELETE FROM loans WHERE loan_id = ?";
	static final String DELETEBYMEMBER = "DELETE FROM loans WHERE member_id = ?";
	static final String DELETEBYBOOK = "DELETE FROM loans WHERE book_id = ?";
	static final String COUNT = "SELECT COUNT(*) FROM loans";
	static final String CHECKVALID = "SELECT COUNT(*) FROM loans WHERE member_id = ? AND date_in BETWEEN DATE_SUB(NOW(), INTERVAL 1 WEEK) AND NOW();";
	static final String GETALLBORROW = "SELECT loan_id, books.book_id, title, author, category, publication_year, date_in, date_out FROM loans, books "
			+ "WHERE loans.book_id = books.book_id and loans.member_id = ?";

	static final String COUNTBYMEMBER = "SELECT COUNT(*) FROM loans where member_id = ? and date_out is NULL";
	static final String COUNTBYBOOK = "SELECT COUNT(*) FROM loans where book_id = ? and date_out is NULL";
	static final String RETURNBOOK = "UPDATE loans SET date_out = NOW() WHERE loan_id = ?";

	public boolean returnBook(int loanid) {
		boolean result = false;
		try (Connection conn = Dbcontext.getConnection()) {
			PreparedStatement pst = conn.prepareStatement(RETURNBOOK);
			pst.setInt(1, loanid);
			pst.execute();
			result = true;
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			result = false;
		}
		return result;
	}

	public int countByBook(int bid) {
		int count = 0;
		try (Connection conn = Dbcontext.getConnection()) {
			PreparedStatement pst = conn.prepareStatement(COUNTBYBOOK);
			pst.setInt(1, bid);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}

		return count;
	}

	public int countByMember(int memid) {
		int count = 0;
		try (Connection conn = Dbcontext.getConnection()) {
			PreparedStatement pst = null;
			pst = conn.prepareStatement(COUNTBYMEMBER);
			pst.setInt(1, memid);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}

		return count;
	}

	public static void main(String[] args) {
		try {
			for (DetailLoan a : new LoanDao().getAllBorrow(2)) {
				System.out.println(a.toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public List<DetailLoan> getAllBorrow(int member_id) throws SQLException {
		List<DetailLoan> list = new ArrayList<>();
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = Dbcontext.getConnection();
			if (cn != null) {
				pst = cn.prepareStatement(GETALLBORROW);
				pst.setInt(1, member_id);
				rs = pst.executeQuery();
				while (rs.next()) {
					int loan_id = rs.getInt("loan_id");
					int book_id = rs.getInt("book_id");
					String title = rs.getString("title");
					String authorr = rs.getString("author");
					String category = rs.getString("category");
					int publication_year = rs.getInt("publication_year");
					Date datein = rs.getDate("date_in");
					Date dateout = rs.getDate("date_out");
					list.add(new DetailLoan(book_id, loan_id, title, authorr, category, publication_year, dateout,
							datein));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pst != null) {
				pst.close();
			}
			if (cn != null) {
				cn.close();
			}
		}
		return list;
	}

	public boolean checkValidBorrow(int member_id) {
		boolean result = false;
		try (Connection con = Dbcontext.getConnection()) {
			try (PreparedStatement stmt = con.prepareStatement(CHECKVALID)) {
				stmt.setInt(1, member_id);

				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next() && rs.getInt(1) >= 3) {
						result = false;
					} else {
						result = true;
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("An error occurred: " + e.getMessage());
		}
		return result;
	}

	public int count() {
		int d = 0;
		try (Connection conn = Dbcontext.getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(COUNT);
			if (rs.next()) {
				d = rs.getInt(1);
				System.out.println("Number of rows in: " + d);
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return d;
	}

	public boolean add(Loan item) throws SQLException {
		boolean success = false;
		Connection cnt = null;
		PreparedStatement psmt = null;
		try {
			cnt = Dbcontext.getConnection();
			if (cnt != null) {
				psmt = cnt.prepareStatement(INSERT);
				psmt.setInt(1, item.getBook_id());
				psmt.setInt(2, item.getMember_id());
				success = psmt.executeUpdate() > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (psmt != null) {
				psmt.close();
			}
			if (cnt != null) {
				cnt.close();
			}
		}
		return success;
	}

	// Hien thi danh sach account
	public List<Loan> findAll() throws SQLException {
		List<Loan> list = new ArrayList<>();
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = Dbcontext.getConnection();
			if (cn != null) {
				pst = cn.prepareStatement(FINDALL);
				rs = pst.executeQuery();
				while (rs.next()) {
					int id = rs.getInt("loan_id");
					int book_id = rs.getInt("book_id");
					int member_id = rs.getInt("member_id");
					Date date_out = rs.getDate("date_out");
					Date date_in = rs.getDate("date_in");
					list.add(new Loan(id, book_id, member_id, date_out, date_in));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pst != null) {
				pst.close();
			}
			if (cn != null) {
				cn.close();
			}
		}
		return list;
	}

	public Loan getItem(int id) throws Exception {

		Loan item = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get connection to database
			myConn = Dbcontext.getConnection();

			// create sql to get selected student
			String sql = GET_ITEM;

			// create prepared statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, id);

			// execute statement
			myRs = myStmt.executeQuery();

			// retrieve data from result set row
			if (myRs.next()) {
				int book_id = myRs.getInt("book_id");
				int member_id = myRs.getInt("member_id");
				Date date_out = myRs.getDate("date_out");
				Date date_in = myRs.getDate("date_in");
				item = new Loan(id, book_id, member_id, date_out, date_in);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (myRs != null) {
				myRs.close();
			}
			if (myStmt != null) {
				myStmt.close();
			}
			if (myConn != null) {
				myConn.close();
			}
		}
		return item;
	}

	public void update(Loan item) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = Dbcontext.getConnection();

			// create SQL update statement
			String sql = UPDATE;

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, item.getBook_id());
			myStmt.setInt(2, item.getMember_id());
			myStmt.setString(3, item.getDate_out().toString());
			myStmt.setString(4, item.getDate_in().toString());
			myStmt.setInt(5, item.getLoan_id());
			// execute SQL statement
			myStmt.execute();
		} finally {
			close(myConn, myStmt, null);
		}
	}

	public boolean delete(int id) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		boolean result = false;
		try {
			// get connection to database
			myConn = Dbcontext.getConnection();

			// create SQL delete statement
			String sql = DELETE;

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, id);

			// execute SQL statement
			myStmt.executeUpdate();
			result = true;
		} catch (Exception e) {
			result = false;
		} finally {
			close(myConn, myStmt, null);
		}
		return result;
	}

	public boolean deleteByMember(int id) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		boolean result = false;
		try {
			// get connection to database
			myConn = Dbcontext.getConnection();

			// create SQL delete statement
			String sql = DELETEBYMEMBER;

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, id);

			// execute SQL statement
			myStmt.executeUpdate();
			result = true;
		} catch (Exception e) {
			result = false;
		} finally {
			close(myConn, myStmt, null);
		}
		return result;
	}

	public boolean deleteByBook(int id) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		boolean result = false;
		try {
			// get connection to database
			myConn = Dbcontext.getConnection();

			// create SQL delete statement
			String sql = DELETEBYBOOK;

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, id);

			// execute SQL statement
			myStmt.executeUpdate();
			result = true;
		} catch (Exception e) {
			result = false;
		} finally {
			close(myConn, myStmt, null);
		}
		return result;
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

		try {
			if (myRs != null) {
				myRs.close();
			}

			if (myStmt != null) {
				myStmt.close();
			}

			if (myConn != null) {
				myConn.close(); // doesn't really close it ... just puts back in connection pool
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}
