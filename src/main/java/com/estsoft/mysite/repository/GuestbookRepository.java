package com.estsoft.mysite.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.estsoft.mysite.domain.Guestbook;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import static com.estsoft.mysite.domain.QGuestbook.guestbook;
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
		
		JPAQuery query = new JPAQuery(em);
		List<Guestbook> list = query.from(guestbook).orderBy(guestbook.regDate.desc()).offset((page-1)*5).limit(5).list(guestbook);
		
		/*
		TypedQuery<Guestbook> query = em.createQuery("select gb from Guestbook gb order by gb.regDate desc", Guestbook.class);
		query.setFirstResult( ( page-1) * 5 );
		query.setMaxResults( 5 );		
		
		List<Guestbook> list = query.getResultList();
*/		System.out.println("list"+list);
		return list;
	}
	


	public Guestbook findOne(Long no) {
		JPAQuery query = new JPAQuery(em);
		return query.from(guestbook).where(guestbook.no.eq(no)).singleResult(guestbook);
		
/*		TypedQuery<Guestbook> query = em.createQuery("select gb from Guestbook gb where gb.no=:no", Guestbook.class);
		query.setParameter("no", no);
		Guestbook guestbook = query.getSingleResult();
		return guestbook;
*/	}

	public Boolean remove(Guestbook guestbook1) {
		if (guestbook1.getNo() == null || guestbook1.getPassword() == null) {
			return false;
		}
		JPAQuery query = new JPAQuery(em);
		List<Guestbook> list = query.from(guestbook).where(guestbook.no.eq(guestbook1.getNo()),guestbook.password.eq(guestbook1.getPassword())).list(guestbook);
		
		/*TypedQuery<Guestbook> query = em
				.createQuery("select gb from Guestbook gb where gb.no=:no and gb.password =:password", Guestbook.class);
		query.setParameter("no", guestbook.getNo());

		query.setParameter("password", guestbook.getPassword());

*/		for (Guestbook guestbook2 : list) {
			if (guestbook2.getNo() == guestbook1.getNo()) {
				em.remove(guestbook2);
				return true;
			}
		}

		return false;
	}
}
