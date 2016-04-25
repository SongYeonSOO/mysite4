package com.estsoft.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.dao.UserDao;
import com.estsoft.mysite.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	public void join(UserVo vo) {
		userDao.insert(vo);
		// mail 보내기? -> @Autowired private MailSender mailSender;
		// ~~
	}

	public UserVo login(UserVo vo) {
		UserVo authUser = userDao.get(vo);
		// vo에 email,passwd setting 되어있어야함

		return authUser;

		// Test Coverage; code중에서 test인것은? 80%이상이 되어야한다아아아
	}

	public UserVo getUser(String email) {
		UserVo userVo = userDao.get(email);
		return userVo;
	}

	public void modifyUser(UserVo vo) {
			userDao.Update(vo);
		return;
	}
}
