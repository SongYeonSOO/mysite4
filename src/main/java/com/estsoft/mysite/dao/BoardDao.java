package com.estsoft.mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.DB.DBConnection;
import com.estsoft.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	/*
	 * @Autowired private DBConnection dbConnection;
	 */
	@Autowired
	private DataSource dataSource;
	@Autowired
	private SqlSession sqlSession;

	public BoardDao() {

	}

	/*
	 * public BoardDao(DBConnection dbConnection) { this.dbConnection =
	 * dbConnection; }
	 */
	// 게시글 보자
	public BoardVo view(Long no, boolean isview) {
		BoardVo boardVo = sqlSession.selectOne("board.view", no);
		if (isview == true) {
			boardVo.setHit(boardVo.getHit() + 1);
			UpdateHit(no);
		}
		return boardVo;
	}

	// Hit count증가 -> page 읽기 시 자동으로 증가함
	private void UpdateHit(Long no) {
		// result: 그냥 return value
		int result = sqlSession.update("board.updateHit", no);
		return;
	}

	public Long Count(String kwd) {
		Long count = sqlSession.selectOne("board.count", kwd);
		return count;
	}

	// 게시판 리스트를 만들자
	public List<BoardVo> SearchList(String kwd, Long page) {
		System.out.println("BoardDao SearchList kwd:"+kwd+"pg"+page);

		Long num = (page - 1) * 5;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("kwd", kwd);
		map.put("num", num);
		List<BoardVo> list = sqlSession.selectList("board.searchList", map);
		System.out.println("boardDaoSearchList:"+list);
		return list;

	}

	// **** Action에서는 updateRe 이후에 insertRe를 해야한다
	// updateRe: 같은 그룹 내의 orderno보다 큰 orderNo는 다 증가시켜준다
	public void updateRe(BoardVo vo) {
		// result: 그냥 return value
		int result = sqlSession.update("board.updateRe", vo);
		return;
	}

	// 새 게시글 삽입(원글, 답글 모두 이용?) content랑 title, user_no 받아옴
	public void insert(BoardVo vo) {

		if (vo.getOrder_no() == null) {
			sqlSession.insert("board.insert", vo);
		} else {
			vo.setDepth(vo.getDepth() + 1);
			sqlSession.insert("board.insert2", vo);
		}
	}

	// 게시글 수정 : 원글, 답글 모두 사용
	public void ModifyUpdate(BoardVo vo) {
		sqlSession.update("board.modifyUpdate", vo);
	}

	// 게시글 삭제 : 원글, 답글 모두 사용
	public void delete(BoardVo vo) {
		Long mdepth = sqlSession.selectOne("board.maxdepth", vo);
		vo.setDepth(mdepth);
		sqlSession.delete("board.delete", vo);
		return;

	}

}
