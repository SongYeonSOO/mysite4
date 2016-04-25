package com.estsoft.mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.DB.DBConnection;
import com.estsoft.mysite.vo.UserVo;

@Repository // autowired 시 자동생성가능하도록
public class UserDao {

	@Autowired // bean설정을 통해 webdbconnection을 했으니깐 autowired를 통해 자동생성
	private DBConnection dbConnection;

	public UserDao() {
	}

	public UserVo get(String email) {
		UserVo userVo = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dbConnection.getConnection();

			// 있는 지 확인용
			String sql = "SELECT no, email FROM user WHERE email=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				Long no = rs.getLong(1);
				String email1 = rs.getString(2);

				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setEmail(email1);
			}

			return userVo;

		} catch (SQLException e) {

			System.out.println("error:" + e);
			e.printStackTrace();

			return null;
			// 여기서 return을 만나도 finally 진행가능

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}

		}
	}

	// secutiry : authentication + permission(root mode etc...)
	// authentication(합당한 사람이냐!)
	public UserVo get(String email, String password) {
		UserVo userVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dbConnection.getConnection();

			// ?: email, passwd
			String sql = "SELECT no, name, email, passwd, gender FROM user WHERE email=? AND passwd=password(?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				System.out.println("Authentication ok");
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String gender = rs.getString(5);

				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setName(name);
				userVo.setEmail(email);
				userVo.setPasswd(password);
				userVo.setGender(gender);

			}

		} catch (SQLException e) {

			System.out.println("error:" + e);
			e.printStackTrace();
			return null;
			// 여기서 return을 만나도 finally 진행가능

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}

		}
		return userVo;

	}

	//
	public UserVo get(UserVo vo) {
		UserVo userVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dbConnection.getConnection();

			// ?: name, email, passwd, gender
			// String sql = "INSERT INTO user VALUES(null, ?, ?, password(?),
			// ?)";

			String sql = "SELECT no, name, email, passwd, gender FROM user WHERE email=? AND passwd=password(?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getEmail());
			pstmt.setString(2, vo.getPasswd());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				System.out.println("DAO IFF");
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String passwd = rs.getString(4);
				String gender = rs.getString(5);

				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setName(name);
				userVo.setEmail(email);
				userVo.setPasswd(passwd);
				userVo.setGender(gender);

			}

		} catch (SQLException e) {

			System.out.println("error:" + e);
			e.printStackTrace();

			// return null;
			// 여기서 return을 만나도 finally 진행가능

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}

		}
		return userVo;

	}

	public UserVo get(Long no) {
		UserVo userVo = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dbConnection.getConnection();

			// ?: name, email, passwd, gender

			String sql = "SELECT name, gender FROM user WHERE no=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				String name = rs.getString(1);
				String gender = rs.getString(2);

				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setName(name);
				userVo.setGender(gender);

			}

		} catch (SQLException e) {

			System.out.println("error:" + e);
			e.printStackTrace();

			return null;
			// 여기서 return을 만나도 finally 진행가능

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}

		}
		return userVo;

	}

	public void Update(UserVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbConnection.getConnection();

			// ?: name, email, passwd, gender

			if ("".equals(vo.getPasswd())) {
				String sql = "UPDATE user SET name=?, gender=? WHERE no=?";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getGender());
				pstmt.setLong(3, vo.getNo());
			} else {
				String sql = "UPDATE user SET name=?, passwd=password(?), gender=? WHERE no=?";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getPasswd());
				pstmt.setString(3, vo.getGender());
				pstmt.setLong(4, vo.getNo());

			}
			pstmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}

		}
	}

	public void insert(UserVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbConnection.getConnection();

			// ?: name, email, passwd, gender
			String sql = "INSERT INTO user VALUES(null, ?, ?, password(?), ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPasswd());
			pstmt.setString(4, vo.getGender());

			pstmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}

		}

	}
	/*
	 * public void //register //insert String sql2 =
	 * "SELECT no, name, email, gender FROM user WHERE email=? AND passwd=password(?)"
	 * ;
	 * 
	 * //modify String sql3 = ;
	 */
}
