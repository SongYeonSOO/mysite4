package com.estsoft.mysite.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.estsoft.mysite.domain.User;
import com.mysema.query.jpa.impl.JPAQuery;
import static com.estsoft.mysite.domain.QUser.user;

@Repository
public class UserRepository {
	@PersistenceContext // entityManager를 주입하자!
	private EntityManager em;

	public User findOneByEmail(String email) {
		
		JPAQuery query = new JPAQuery(em);
		List<User> list = query.from(user).where(user.email.eq(email)).list(user);
		
		User user = new User();
		if (list.isEmpty()) {
			user = null;
		} else {
			user = list.get(0);
		}
		return user;
/*		TypedQuery<User> query = em.createQuery("select ur from User ur where ur.email=:email", User.class);
		query.setParameter("email", email);
		List<User> list = query.getResultList();
*/		
	}

	// secutiry : authentication + permission(root mode etc...)
	// authentication(합당한 사람이냐!)
	public User findOneByUser(User user0) {
		JPAQuery query = new JPAQuery(em);
		User resultUser = query.from(user).where(user.email.eq(user0.getEmail()),user.password.eq(user0.getPassword())).uniqueResult(user);
		
/*		TypedQuery<User> query = em
				.createQuery("select ur from User ur where ur.email=:email AND ur.password=:password", User.class);
		query.setParameter("email", user.getEmail());
		query.setParameter("password", user.getPassword());

		User userResult = query.getSingleResult();
*/		return resultUser;

	}

	public User findOneByNo(Long no) {
		return em.find(User.class, no);
/*		TypedQuery<User> query = em.createQuery("select ur from User ur where ur.no=:no", User.class);
		query.setParameter("no", no);
		User user = query.getSingleResult();
*/		
	}

	public void Update(User user) {

		User resultUser = findOneByNo(user.getNo());

		if ("".equals(user.getPassword())) {
			resultUser.setName(user.getName());
			resultUser.setGender(user.getGender());
		} else {
			resultUser.setName(user.getName());
			resultUser.setPassword(user.getPassword());
			resultUser.setGender(user.getGender());

		}

	}

	public void save(User user) {
		em.persist(user);
	}
}
