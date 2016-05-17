package com.estsoft.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estsoft.mysite.domain.Guestbook;
import com.estsoft.mysite.repository.GuestbookRepository;

@Service
@Transactional
public class GuestBookService {
	
	@Autowired
	private GuestbookRepository guestbookR;

	public Guestbook save(Guestbook vo) {
		return guestbookR.save(vo);
	}
	public Boolean delete(Guestbook vo){
		return guestbookR.remove(vo);
	}
	
	public List<Guestbook> getList(int page){
		return guestbookR.findAll(page);
	}
	
	public Guestbook getGuestBook(Long no){
		return guestbookR.findOne(no);	
		}
}
