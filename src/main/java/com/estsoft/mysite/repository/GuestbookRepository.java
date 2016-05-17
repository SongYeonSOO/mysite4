package com.estsoft.mysite.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.estsoft.mysite.domain.Guestbook;
@Repository
public class GuestbookRepository {

	// bean 설정에 manager를 만든 적은 없었다! -> autowired 불가능
	@PersistenceContext // entityManager를 주입하자!
	private EntityManager em;

	public Guestbook save(Guestbook guestbook) {
		guestbook.setRegDate(new Date());
		em.persist(guestbook); // guestbook은 controller에서 왔음 --> guestbook은 영속화
								// 되어있음 ; 다시 빼서 줄 필요가 없음
		em.flush();
		return guestbook;
	}

	public List<Guestbook> findAll(int page) {
		TypedQuery<Guestbook> query = em.createQuery("select gb from Guestbook gb order by gb.regDate desc", Guestbook.class);
		query.setFirstResult( ( page-1) * 5 );
		query.setMaxResults( 5 );		
		
		List<Guestbook> list = query.getResultList();
		System.out.println("list"+list);
		return list;
	}
	


	public Guestbook findOne(Long no) {
		TypedQuery<Guestbook> query = em.createQuery("select gb from Guestbook gb where gb.no=:no", Guestbook.class);
		query.setParameter("no", no);
		Guestbook guestbook = query.getSingleResult();
		return guestbook;
	}

	public Boolean remove(Guestbook guestbook) {
		if (guestbook.getNo() == null || guestbook.getPassword() == null) {
			return false;
		}
		TypedQuery<Guestbook> query = em
				.createQuery("select gb from Guestbook gb where gb.no=:no and gb.password =:password", Guestbook.class);
		query.setParameter("no", guestbook.getNo());

		query.setParameter("password", guestbook.getPassword());

		for (Guestbook guestbook2 : query.getResultList()) {
			System.out.println(guestbook2);
			if (guestbook2.getNo() == guestbook.getNo()) {
				em.remove(guestbook2);
				return true;
			}
		}

		return false;
	}
}
