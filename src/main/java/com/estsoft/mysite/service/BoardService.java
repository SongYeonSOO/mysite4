package com.estsoft.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estsoft.mysite.domain.Board;
import com.estsoft.mysite.repository.BoardRepository;

@Transactional
@Service
public class BoardService {
	@Autowired
	private BoardRepository boardR;
	
	public void insert(Board board) {

		boardR.save(board);
	}
	public void delete(Board board){
		boardR.delete(board);
	}
	
	public Board getView(Long no, boolean isview){
		return boardR.view(no, isview);
	}
	
	public Map<String, Object> SearchList(String kwd, Long page){
		
		
		//page에 이용!!!
		 int COUNT_LIST = 5;
		 int COUNT_PAGE = 5;
		Long currentpage = page;
		Long beginpage = currentpage - ((currentpage-1)%COUNT_PAGE);
		Long totalpage = (long) Math.ceil(boardR.Count(kwd)/(float)COUNT_PAGE);
		Long maxpage = null;
		if(totalpage>=beginpage+COUNT_PAGE-1){
			maxpage = beginpage+COUNT_PAGE-1;
		}else{
			maxpage = totalpage;
		}
		
		Long boardno = boardR.Count(kwd)-(currentpage-1)*COUNT_PAGE;
		System.out.println("1231231231" + boardno);
		
		Map<String, Long> pageinfo = new HashMap<String, Long>();
		pageinfo.put("beginpage", beginpage);
		pageinfo.put("totalpage", totalpage);
		pageinfo.put("maxpage", maxpage);
		pageinfo.put("currentpage", currentpage);		
		
		List<Board> list = boardR.SearchList(kwd, page);
		
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageinfo", pageinfo);
		map.put("boardno", boardno);
		map.put("list", list);
		return map;
		}
	
	public void ModifyUpdate(Board board){
		boardR.ModifyUpdate(board);	
		return;
		}

}
