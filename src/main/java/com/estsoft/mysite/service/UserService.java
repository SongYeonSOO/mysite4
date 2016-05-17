package com.estsoft.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estsoft.mysite.dao.UserDao;
import com.estsoft.mysite.domain.User;
import com.estsoft.mysite.repository.UserRepository;
import com.estsoft.mysite.vo.UserVo;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserRepository userR;

	public void join(User user) {
		userR.save(user);
	}

	public User login(User user) {
		User authUser = userR.findOneByUser(user);
		// vo에 email,passwd setting 되어있어야함

		return authUser;

		// Test Coverage; code중에서 test인것은? 80%이상이 되어야한다아아아
	}

	public User getUser(String email) {
		User userVo = userR.findOneByEmail(email);
		return userVo;
	}

	public void modifyUser(User vo) {
		userR.Update(vo);
	}
}
