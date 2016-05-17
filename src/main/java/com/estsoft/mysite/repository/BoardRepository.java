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

@Repository
public class BoardRepository {

	@PersistenceContext
	private EntityManager em;

	// 새 게시글 삽입(원글, 답글 모두 이용?) content랑 title, user_no 받아옴
	public void save(Board board) {

		board.setRegDate(new Date());


		TypedQuery<Long> query = em.createQuery("SELECT Max(b.groupNo) from Board b", Long.class);
		Long Mgroup_no = query.getSingleResult();
		if (Mgroup_no == null)
			Mgroup_no = 0L;
		System.out.println(Mgroup_no);

		// 원글
		if (board.getGroupNo() == null) {
			board.setHit(0L);
			board.setGroupNo(Mgroup_no + 1);
			board.setDepth(0L);
			board.setOrderNo(1L);
			em.persist(board);
		}
		// 답글
		else {
			
			Board boardR = new Board();
			boardR.setRegDate(board.getRegDate());
			boardR.setHit(0L);
			boardR.setTitle(board.getTitle());
			boardR.setContent(board.getContent());
			boardR.setGroupNo(board.getGroupNo());
			boardR.setDepth(board.getDepth() + 1);
			boardR.setOrderNo(board.getOrderNo());
			boardR.setUser(board.getUser());
			
			System.out.println("답글 ++++++++++++"+board);
			updateRe(boardR);
			em.persist(boardR);
		}

	}

	public Board view(Long no, boolean isview) {

		TypedQuery<Board> query = em.createQuery("select bd from Board bd where bd.no=:no", Board.class);
		query.setParameter("no", no);
		Board board = query.getSingleResult();
		if (isview == true) {
			board.setHit(board.getHit() + 1);
		}
		return board;
	}

	public Long Count(String kwd) {
		
		TypedQuery<Long> query = em.createQuery("select count(b) from Board b where (b.title like :like OR b.content like :like)", Long.class);
		query.setParameter("like", "%" + kwd + "%");
		Long count = query.getSingleResult();
		
		return count;
	}

	// 게시판 리스트를 만들자
	public List<Board> SearchList(String kwd, Long page) {


		TypedQuery<Board> query = em.createQuery("select b from Board b join b.authUser u WHERE b.title like :like OR b.content like :like order by b.groupNo desc,b.orderNo", Board.class);
		query.setParameter("like", "%" + kwd + "%");
		query.setFirstResult(Integer.parseInt(String.valueOf((page - 1) * 5)));
		query.setMaxResults( 5 );
		
		List<Board> list = query.getResultList();
		if(list.isEmpty()) return null;
		return list;

	}

	// **** Action에서는 updateRe 이후에 insertRe를 해야한다
	// updateRe: 같은 그룹 내의 orderno보다 큰 orderNo는 다 증가시켜준다
	public void updateRe(Board vo) {

		TypedQuery<Board> query = em.createQuery("select b from Board b where b.groupNo=?1", Board.class);
		query.setParameter(1, vo.getGroupNo());
		List<Board> list = query.getResultList();
		for (Board b : list) {
			if (b.getOrderNo() > vo.getOrderNo()) {
				b.setOrderNo(b.getOrderNo()+1);
			}
		}

	}

	// 게시글 수정 : 원글, 답글 모두 사용
	public void ModifyUpdate(Board vo) {
		
		TypedQuery<Board> query = em.createQuery("select b from Board b join b.authUser u where  b.no=:no", Board.class);
		query.setParameter("no", vo.getNo());

		List<Board> list = query.getResultList();
		for(Board b : list){
			if(b.getNo() == vo.getNo()){
				b.setTitle(vo.getTitle());
				b.setContent(vo.getContent());
				b.setRegDate(new Date());
			}
		}

	}

	// 게시글 삭제 : 원글, 답글 모두 사용
	public void delete(Board vo) {
		
		TypedQuery<Long> query = em.createQuery("select Max(b.depth) from Board b where b.groupNo=:groupNo", Long.class);
		query.setParameter("groupNo", vo.getGroupNo());
		Long mdepth = query.getSingleResult();

		System.out.println( mdepth );
		TypedQuery<Board> delquery = em.createQuery("select b from Board b join b.authUser u",Board.class);
		for(Board b:delquery.getResultList()){
			if(b.getNo() == vo.getNo() && b.getDepth()== mdepth){
				em.remove(b);
			}
		}

	}
}
