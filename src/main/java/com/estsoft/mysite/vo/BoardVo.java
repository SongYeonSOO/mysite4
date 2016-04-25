package com.estsoft.mysite.vo;

public class BoardVo {
	private Long no;
	private String title;
	private String content;
	private String reg_date;
	private Long user_no;
	private Long group_no;
	private Long order_no;
	private Long depth;
	private Long hit;
	private String user_name;
	
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

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public Long getUser_no() {
		return user_no;
	}

	public void setUser_no(Long user_no) {
		this.user_no = user_no;
	}

	public Long getGroup_no() {
		return group_no;
	}

	public void setGroup_no(Long group_no) {
		this.group_no = group_no;
	}

	public Long getOrder_no() {
		return order_no;
	}

	public void setOrder_no(Long order_no) {
		this.order_no = order_no;
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

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", reg_date=" + reg_date
				+ ", user_no=" + user_no + ", group_no=" + group_no + ", order_no=" + order_no + ", depth=" + depth
				+ ", hit=" + hit + ", user_name=" + user_name + "]";
	}

	


}
