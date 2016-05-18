package com.estsoft.mysite.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.estsoft.mysite.domain.Board;
import com.estsoft.mysite.domain.Guestbook;
import com.estsoft.mysite.vo.BoardVo;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import static com.estsoft.mysite.domain.QBoard.board;
import static com.estsoft.mysite.domain.QGuestbook.guestbook;
import static com.estsoft.mysite.domain.QUser.user;


@Repository
public class BoardRepository {

	@PersistenceContext
	private EntityManager em;

	// 새 게시글 삽입(원글, 답글 모두 이용?) content랑 title, user_no 받아옴
	public void save(Board board1) {
		JPAQuery query = new JPAQuery(em);
		board1.setRegDate(new Date());
		Long Mgroup_no = query.from(board).uniqueResult(board.groupNo.max());

//		TypedQuery<Long> query = em.createQuery("SELECT Max(b.groupNo) from Board b", Long.class);
//		Long Mgroup_no = query.getSingleResult();
		if (Mgroup_no == null)
			Mgroup_no = 0L;
		System.out.println(Mgroup_no);

		// 원글
		if (board1.getGroupNo() == null) {
			board1.setHit(0L);
			board1.setGroupNo(Mgroup_no + 1);
			board1.setDepth(0L);
			board1.setOrderNo(1L);
			em.persist(board1);
		}
		// 답글
		else {
			
			Board boardR = new Board();
			boardR.setRegDate(board1.getRegDate());
			boardR.setHit(0L);
			boardR.setTitle(board1.getTitle());
			boardR.setContent(board1.getContent());
			boardR.setGroupNo(board1.getGroupNo());
			boardR.setDepth(board1.getDepth() + 1);
			boardR.setOrderNo(board1.getOrderNo()+1);
			boardR.setUser(board1.getUser());
			
			System.out.println("답글 ++++++++++++"+board);
			updateRe(boardR);
			em.persist(boardR);
		}

	}

	public Board view(Long no, boolean isview) {

		Board board = em.find(Board.class, no);
		if (isview == true) {
			board.setHit(board.getHit() + 1);
		}
		return board;
	}

	public Long Count(String kwd) {
		JPAQuery query = new JPAQuery(em);
		Long count = query.from(board).where(board.title.like("%" + kwd + "%").or(board.content.like("%" + kwd + "%"))).count();
		/*TypedQuery<Long> query = em.createQuery("select count(b) from Board b where (b.title like :like OR b.content like :like)", Long.class);
		query.setParameter("like", "%" + kwd + "%");
		Long count = query.getSingleResult();
		*/
		return count;
	}

	// 게시판 리스트를 만들자
	public List<Board> SearchList(String kwd, Long page) {
		JPAQuery query = new JPAQuery(em);
		List<Board> list = query.from(board).join(board.authUser, user).where(board.title.like("%" + kwd + "%").or(board.content.like("%" + kwd + "%"))).orderBy(board.groupNo.desc()).orderBy(board.orderNo.asc()).offset((page-1)*5).limit(5).list(board);
		

/*		TypedQuery<Board> query = em.createQuery("select b from Board b join b.authUser u WHERE b.title like :like OR b.content like :like order by b.groupNo desc,b.orderNo", Board.class);
		query.setParameter("like", "%" + kwd + "%");
		query.setFirstResult(Integer.parseInt(String.valueOf((page - 1) * 5)));
		query.setMaxResults( 5 );
		
		List<Board> list = query.getResultList();*/
		if(list.isEmpty()) return null;
		return list;

	}

	// **** Action에서는 updateRe 이후에 insertRe를 해야한다
	// updateRe: 같은 그룹 내의 orderno보다 큰 orderNo는 다 증가시켜준다
	public void updateRe(Board vo) {
		JPAQuery query = new JPAQuery(em);
		List<Board> list = query.from(board).where(board.groupNo.eq(vo.getGroupNo())).list(board);
		
/*		TypedQuery<Board> query = em.createQuery("select b from Board b where b.groupNo=?1", Board.class);
		query.setParameter(1, vo.getGroupNo());
		List<Board> list = query.getResultList();
*/
		for (Board b : list) {
			if (b.getOrderNo() >= vo.getOrderNo()) {
				b.setOrderNo(b.getOrderNo()+1);
			}
		}

	}

	// 게시글 수정 : 원글, 답글 모두 사용
	public void ModifyUpdate(Board vo) {
		JPAQuery query = new JPAQuery(em);		
		List<Board> list = query.from(board).join(board.authUser, user).where(board.no.eq(vo.getNo())).list(board);
/*		TypedQuery<Board> query = em.createQuery("select b from Board b join b.authUser u where  b.no=:no", Board.class);
		query.setParameter("no", vo.getNo());

		List<Board> list = query.getResultList();
*/		for(Board b : list){
			if(b.getNo() == vo.getNo()){
				b.setTitle(vo.getTitle());
				b.setContent(vo.getContent());
				b.setRegDate(new Date());
			}
		}

	}

	// 게시글 삭제 : 원글, 답글 모두 사용
	public void delete(Board vo) {
		JPAQuery query = new JPAQuery(em);	
		JPAQuery delquery = new JPAQuery(em);	
		Long mdepth = query.from(board).where(board.groupNo.eq(vo.getGroupNo())).uniqueResult(board.depth.max());
		
		
/*		TypedQuery<Long> query = em.createQuery("select Max(b.depth) from Board b where b.groupNo=:groupNo", Long.class);
		query.setParameter("groupNo", vo.getGroupNo());
		Long mdepth = query.getSingleResult();
*/
		
	     JPADeleteClause deleteClause = new JPADeleteClause( em, board );
	     Long count = deleteClause.where(board.no.eq(vo.getNo()),board.depth.eq(mdepth)).execute();
/*		TypedQuery<Board> delquery = em.createQuery("select b from Board b join b.authUser u",Board.class);
		
		for(Board b:delquery.getResultList()){
			if(b.getNo() == vo.getNo() && b.getDepth()== mdepth){
				em.remove(b);
			}
		}
*/
	}
}
