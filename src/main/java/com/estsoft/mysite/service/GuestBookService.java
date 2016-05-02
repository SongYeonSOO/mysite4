package com.estsoft.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.dao.GuestBookDao;
import com.estsoft.mysite.vo.GuestBookVo;

@Service
public class GuestBookService {
	
	@Autowired
	private GuestBookDao guestbookDao;

	public Long insert(GuestBookVo vo) {
		return guestbookDao.insert(vo);
	}
	public int delete(GuestBookVo vo){
		return guestbookDao.delete(vo);
	}
	
	public List<GuestBookVo> getList(int page){
		return guestbookDao.getList(page);
	}
	
	public GuestBookVo getVo(Long no){
		return guestbookDao.get(no);	
		}
}
