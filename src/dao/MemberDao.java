package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Member;

public class MemberDao {
	static final String LOGIN = "SELECT * FROM members " + "WHERE username = ? AND password = ?";
	static final String INSERT = "INSERT INTO members(username, password, name, role) VALUES(?,?,?,?)";
	static final String CHECKDUPUSERNAME = "SELECT * FROM members WHERE username=?";
	static final String FINDALL = "SELECT * FROM members";
	static final String GET_USER = "SELECT * FROM members where member_id = ?";
	static final String UPDATE_USER = "UPDATE members SET password = ?, name = ?, role = ?  WHERE member_id = ?";
	static final String DELETE = "DELETE FROM members WHERE member_id = ?";
	static final String COUNT = "SELECT COUNT(*) FROM members";

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

	public Member login(String username, String password) {
		Member result = null;
		try {
			Connection cnt = Dbcontext.getConnection();
			PreparedStatement psmt = cnt.prepareStatement(LOGIN);
			psmt.setString(1, username);
			psmt.setString(2, password);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("member_id");
				String name = rs.getString("name");
				int role = rs.getInt("role");
				result = new Member(id, username, password, name, role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean add(Member account) throws SQLException {
		boolean success = false;
		Connection cnt = null;
		PreparedStatement psmt = null;
		try {
			cnt = Dbcontext.getConnection();
			if (cnt != null) {
				psmt = cnt.prepareStatement(INSERT);
				psmt.setString(1, account.getUsername());
				psmt.setString(2, account.getPassword());
				psmt.setString(3, account.getName());
				psmt.setInt(4, account.getRole());
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

	public boolean checkDupUserName(String username) throws SQLException {
		boolean check = false;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			cn = Dbcontext.getConnection();
			if (cn != null) {
				pst = cn.prepareStatement(CHECKDUPUSERNAME);
				pst.setString(1, username);
				rs = pst.executeQuery();
				if (rs.next()) {
					check = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pst != null) {
				pst.close();
			}
			if (cn != null) {
				cn.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return check;
	}

	// Hien thi danh sach account
	public List<Member> findAll() throws SQLException {
		List<Member> list = new ArrayList<>();
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = Dbcontext.getConnection();
			if (cn != null) {
				pst = cn.prepareStatement(FINDALL);
				rs = pst.executeQuery();
				while (rs.next()) {
					int id = rs.getInt("member_id");
					String username = rs.getString("username");
					String password = rs.getString("password");
					String name = rs.getString("name");
					int role = rs.getInt("role");
					list.add(new Member(id, username, password, name, role));
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

	public Member getItem(int id) throws Exception {

		Member user = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get connection to database
			myConn = Dbcontext.getConnection();

			// create sql to get selected student
			String sql = GET_USER;

			// create prepared statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, id);

			// execute statement
			myRs = myStmt.executeQuery();

			// retrieve data from result set row
			if (myRs.next()) {
				String username = myRs.getString("username");
				String passWord = myRs.getString("password");
				String name = myRs.getString("name");
				int role = myRs.getInt("role");

				user = new Member(id, username, passWord, name, role);
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
		return user;
	}

	public void update(Member user) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = Dbcontext.getConnection();

			// create SQL update statement
			String sql = UPDATE_USER;

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, user.getPassword());
			myStmt.setString(2, user.getName());
			myStmt.setInt(3, user.getRole());
			myStmt.setInt(4, user.getMember_id());

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
