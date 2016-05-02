package com.estsoft.mysite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

import com.estsoft.DB.DBConnection;
import com.estsoft.mysite.exception.InsertSQLException;
import com.estsoft.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao {
	/*
	 * @Autowired private DBConnection dbConnection;
	 */
	@Autowired
	private DataSource dataSource;
	@Autowired
	private SqlSession sqlSession;

	public GuestBookDao() {

	}

	/*
	 * public GuestBookDao(DBConnection mySQLWebDBConnection) {
	 * this.dbConnection = mySQLWebDBConnection; }
	 */
	@SuppressWarnings("finally")
	public Long insert(GuestBookVo vo) {
		// parameter 없는 것 sqlSession.insert("")
		// object가 없으면 map으로 한다
		// mapping은 한 번에 하나만 할 수 있다

		int count = sqlSession.insert("guestbook.insert", vo);
		// 원래 return no해야함 지금은 test용
		return vo.getNo();
	}

	public int delete(GuestBookVo vo) {
		if (vo.getNo() == null || vo.getPasswd() == null) {
			return 0;
		}
		int countDeleted = sqlSession.delete("guestbook.delete", vo);
		/*
		 * Map<String,Object> map = new HashMap<String,Object>();
		 * map.put("no",vo.getNo()); map.put("passwd",vo.getPasswd());
		 * 
		 * int countDeleted = sqlSession.delete("geustbook.delete2",map);
		 */ System.out.println("countDeleted: " + countDeleted);
		return countDeleted;
	}

	// stopwatch로 시간재기 -> aop가 필요한 example ; around이용하면 용이하다
	public List<GuestBookVo> getList() {
		// sqlSession.selectList("", arg1);
		// sqlSession.selectList("guestbook.selectList"); id가 겹칠 때 namespace를
		// 이용해서 처리함
/*		StopWatch stopWatch = new StopWatch();
		stopWatch.start();*/
		List<GuestBookVo> list = sqlSession.selectList("guestbook.selectList");

/*		stopWatch.stop();
		System.out.println("[Execution Time][GuestBookDao.getList]:" + stopWatch.getTotalTimeMillis() + "millis");
*/		return list;
	}

	public List<GuestBookVo> getList(long page) {

		long num = (page - 1) * 5;
		List<GuestBookVo> list = sqlSession.selectList("guestbook.selectByPage", num);
		return list;
	}

	public GuestBookVo get(Long no) {
		GuestBookVo vo = sqlSession.selectOne("guestbook.selectByNo", no);
		return vo;
	}
}
