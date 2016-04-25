package com.estsoft.mysite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import com.estsoft.DB.DBConnection;
import com.estsoft.mysite.vo.GuestBookVo;
@Repository
public class GuestBookDao {
	@Autowired
	private DBConnection dbConnection;
	public GuestBookDao() {
		
	}
/*	public GuestBookDao(DBConnection mySQLWebDBConnection) {
		this.dbConnection = mySQLWebDBConnection;
	}
*/
	@SuppressWarnings("finally")
	public Long insert(GuestBookVo vo) {
		Long no = 0L;
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {

			conn = dbConnection.getConnection();
			String sql = "INSERT INTO guestbook VALUES(null, ?, now(), ?, password(?))";

			// System.out.println(vo.getFirstName());
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getMessage());
			pstmt.setString(3, vo.getPasswd());
			pstmt.executeUpdate();

			// 현재 insert된 vo의 id를 가져올 수 있다.
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT last_insert_id()");
			if (rs.next()) {
				no = rs.getLong(1);
			}
		} catch (SQLException ex) {
			System.out.println("Error" + ex);
			ex.printStackTrace();
		} finally {
			return no;
		}

	}

	public int delete(GuestBookVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {

			if (vo.getNo() == null || vo.getPasswd() == null) {
				return 0;
			}

			conn = dbConnection.getConnection();
			String sql = "DELETE FROM guestbook WHERE no=? AND passwd= password(?)";

			// System.out.println(vo.getFirstName());
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getPasswd());
			pstmt.executeUpdate();
			return 1;

		} catch (SQLException ex) {
			System.out.println("Error" + ex);
			ex.printStackTrace();
		}
		return 1;
	}

	public List<GuestBookVo> getList() {
		List<GuestBookVo> list = new ArrayList<GuestBookVo>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = dbConnection.getConnection();
			stmt = conn.createStatement();

			String sql = "SELECT no, name, DATE_FORMAT(reg_date, '%Y-%m-%d %h:%i:%s'), message " + "FROM guestbook "
					+ "ORDER BY reg_date desc";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String reg_date = rs.getString(3);
				String message = rs.getString(4);

				GuestBookVo vo = new GuestBookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setReg_date(reg_date);
				vo.setMessage(message);

				list.add(vo);
			}

		} catch (SQLException ex) {
			System.out.println("error:" + ex);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return list;

	}

	public List<GuestBookVo> getList(int page) {
		List<GuestBookVo> list = new ArrayList<GuestBookVo>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = dbConnection.getConnection();
			stmt = conn.createStatement();

			String sql = "SELECT no, name, DATE_FORMAT(reg_date, '%Y-%m-%d %h:%i:%s'), message " + "FROM guestbook "
					+ "ORDER BY reg_date desc " + "LIMIT " + (page - 1) * 5 + ", 5"; // PAGE에
																						// 맞게!
																						// prepareStatement안쓰고싶어서!
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String reg_date = rs.getString(3);
				String message = rs.getString(4);

				GuestBookVo vo = new GuestBookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setReg_date(reg_date);
				vo.setMessage(message);

				list.add(vo);
			}

		} catch (SQLException ex) {
			System.out.println("error:" + ex);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return list;

	}

	//// checkkkkkkkkkkkkkkkkkkkkkkkk!
	public GuestBookVo get(Long no) {

		GuestBookVo vo = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = dbConnection.getConnection();
			stmt = conn.createStatement();

			String sql = "SELECT no, name, DATE_FORMAT(reg_date, '%Y-%m-%d %h:%i:%s'), message " + "FROM guestbook "
					+ "WHERE no =" + no + " ORDER BY reg_date desc";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Long no1 = rs.getLong(1);
				String name = rs.getString(2);
				String reg_date = rs.getString(3);
				String message = rs.getString(4);

				vo = new GuestBookVo();
				vo.setNo(no1);
				vo.setName(name);
				vo.setReg_date(reg_date);
				vo.setMessage(message);

			}

		} catch (SQLException ex) {
			System.out.println("error:" + ex);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return vo;
	}
}
