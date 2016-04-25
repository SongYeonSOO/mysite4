package com.estsoft.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.dao.BoardDao;
import com.estsoft.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;
	
	public void insert(BoardVo vo) {

		boardDao.insert(vo);
		return ;
	}
	public void delete(BoardVo vo){
		boardDao.delete(vo);
		return;
	}
	
	public BoardVo getList(Long no, boolean isview){
		return boardDao.view(no, isview);
	}
	
	public List<BoardVo> SearchList(String kwd, Long page){
		return boardDao.SearchList(kwd, page);	
		}
	public void ModifyUpdate(BoardVo vo){
		boardDao.ModifyUpdate(vo);	
		}

}
