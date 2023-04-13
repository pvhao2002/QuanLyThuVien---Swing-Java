package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.*;

public class BookDao {
	static final String INSERT = "INSERT INTO books(title, author, category, publication_year) VALUES(?,?,?,?)";
	static final String FINDALL = "SELECT * FROM books";
	static final String GET_ITEM = "SELECT * FROM books where book_id = ?";
	static final String UPDATE = "UPDATE books SET title = ?, author = ?, category = ?, publication_year = ?  WHERE book_id = ?";
	static final String DELETE = "DELETE FROM books WHERE book_id = ?";
	static final String COUNT = "SELECT COUNT(*) FROM books";
	static final String SEARCHBYNAME = "SELECT * FROM books WHERE LOWER(title) LIKE LOWER(?)";

	public List<Book> findAllByName(String t) throws SQLException {
		List<Book> list = new ArrayList<>();
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = Dbcontext.getConnection();
			if (cn != null) {
				pst = cn.prepareStatement(SEARCHBYNAME);
				pst.setString(1, "%" + t + "%");
				rs = pst.executeQuery();
				while (rs.next()) {
					int id = rs.getInt("book_id");
					String title = rs.getString("title");
					String author = rs.getString("author");
					String category = rs.getString("category");
					int publication_year = rs.getInt("publication_year");
					list.add(new Book(id, title, author, category, publication_year));
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

	public int count() throws SQLException {
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

	public boolean add(Book item) throws SQLException {
		boolean success = false;
		Connection cnt = null;
		PreparedStatement psmt = null;
		try {
			cnt = Dbcontext.getConnection();
			if (cnt != null) {
				psmt = cnt.prepareStatement(INSERT);
				psmt.setString(1, item.getTitle());
				psmt.setString(2, item.getAuthor());
				psmt.setString(3, item.getCategory());
				psmt.setInt(4, item.getPublication_year());
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
	public List<Book> findAll() throws SQLException {
		List<Book> list = new ArrayList<>();
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = Dbcontext.getConnection();
			if (cn != null) {
				pst = cn.prepareStatement(FINDALL);
				rs = pst.executeQuery();
				while (rs.next()) {
					int id = rs.getInt("book_id");
					String title = rs.getString("title");
					String author = rs.getString("author");
					String category = rs.getString("category");
					int publication_year = rs.getInt("publication_year");
					list.add(new Book(id, title, author, category, publication_year));
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

	public Book getItem(int id) throws Exception {

		Book item = null;
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
				String title = myRs.getString("title");
				String author = myRs.getString("author");
				String category = myRs.getString("category");
				int publication_year = myRs.getInt("publication_year");
				item = new Book(id, title, author, category, publication_year);
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

	public void update(Book item) throws Exception {

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
			myStmt.setString(1, item.getTitle());
			myStmt.setString(2, item.getAuthor());
			myStmt.setString(3, item.getCategory());
			myStmt.setInt(4, item.getPublication_year());
			myStmt.setInt(5, item.getBook_id());
			// execute SQL statement
			myStmt.execute();
		} finally {
			close(myConn, myStmt, null);
		}
	}

	public void delete(int id) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

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

		} catch (Exception e) {

		} finally {
			close(myConn, myStmt, null);
		}

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
