package com.estsoft.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public BoardVo getView(Long no, boolean isview){
		System.out.println("Service getView ok");
		return boardDao.view(no, isview);
	}
	
	public Map<String, Object> SearchList(String kwd, Long page){
		
		
		//page에 이용!!!
		 int COUNT_LIST = 5;
		 int COUNT_PAGE = 5;
		Long currentpage = page;
		Long beginpage = currentpage - ((currentpage-1)%COUNT_PAGE);
		Long totalpage = (long) Math.ceil(boardDao.Count(kwd)/(float)COUNT_PAGE);
		Long maxpage = null;
		if(totalpage>=beginpage+COUNT_PAGE-1){
			maxpage = beginpage+COUNT_PAGE-1;
		}else{
			maxpage = totalpage;
		}
		
		Long boardno = boardDao.Count(kwd)-(currentpage-1)*COUNT_PAGE;
		System.out.println("1231231231" + boardno);
		
		Map<String, Long> pageinfo = new HashMap<String, Long>();
		pageinfo.put("beginpage", beginpage);
		pageinfo.put("totalpage", totalpage);
		pageinfo.put("maxpage", maxpage);
		pageinfo.put("currentpage", currentpage);		
		
		List<BoardVo> list = boardDao.SearchList(kwd, page);
		
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageinfo", pageinfo);
		map.put("boardno", boardno);
		map.put("list", list);
		return map;
		}
	
	public void ModifyUpdate(BoardVo vo){
		boardDao.ModifyUpdate(vo);	
		return;
		}

}
