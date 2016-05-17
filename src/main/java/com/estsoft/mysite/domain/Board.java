package com.estsoft.mysite.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.transaction.annotation.Transactional;
@Entity
@Table(name = "board")
public class Board {
	@Id
	@Column(name = "no")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;
	@Column(name = "title", nullable = false, length = 100)
	@NotEmpty(message = "제목!!!!!!")
	private String title;
	@Column(name = "message", nullable = false)
	@Lob
	@NotEmpty(message = "내용!!!!!!!!!")
	private String content;
	@Column(name = "reg_date", nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date regDate;
	@Column(name = "group_no", nullable = false)
	private Long groupNo;
	@Column(name = "order_no", nullable = false)
	private Long orderNo;
	@Column(name = "depth", nullable = false)
	private Long depth;
	@Column(name = "hit", nullable = false)
	private Long hit;

	/*
	@Column(name = "user_no", nullable = false)
	private Long userNo;
	@Column(name = "user_name", nullable = false)
	private String userName;
	*/
/*	*/
	@ManyToOne
	@JoinColumn(name="user_no", nullable = false)
	private User authUser;
	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date date) {
		this.regDate = date;
	}

/*	public Long getUserNo() {
		return userNo;
	}

	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}*/

	public Long getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(Long groupNo) {
		this.groupNo = groupNo;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public Long getDepth() {
		return depth;
	}

	public void setDepth(Long depth) {
		this.depth = depth;
	}

	public Long getHit() {
		return hit;
	}

	public void setHit(Long hit) {
		this.hit = hit;
	}

	public User getUser() {
		return authUser;
	}

	public void setUser(User user) {
		if(this.authUser != null){
			this.authUser.getBoards().remove(this);
		}
		this.authUser = user;
		if(user==null){
			return ;}else{
			user.getBoards().add(this);
		}
	}
	
	
	@Override
	public String toString() {
		return "Board [no=" + no + ", title=" + title + ", content=" + content + ", regDate=" + regDate + ", groupNo="
				+ groupNo + ", orderNo=" + orderNo + ", depth=" + depth + ", hit=" + hit + "]";
	}


/*	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
*/


}
