package com.estsoft.mysite.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
@Entity
@Table(name = "user")
public class User {
	
	@Id
	@Column(name = "userno")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long user_no;
	@Column(name = "name", nullable = false, length = 100)
	@NotEmpty(message="name!!!!!!!!!")
	private String name;
	
	@Column(name = "email", nullable = false, length = 100)
	@NotEmpty
	@Email	(message="email!!!!!!!!!!!")
	private String email;
	@Column(name = "passwd", nullable = false, length = 32)
	@NotEmpty(message="password!!!!!!!")
	private String password;
	@Column(name = "gender", nullable = false, columnDefinition = "enum('FEMALE','MALE')")
	@Enumerated(value = EnumType.STRING)			//enum 처리를 위해서 넣어줘야함
	private Gender gender;

	@OneToMany(mappedBy = "authUser")
	private List<Board> boards = new ArrayList<Board>();
	

	public Long getNo() {
		return user_no;
	}
	public void setNo(Long no) {
		this.user_no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public List<Board> getBoards() {
		return boards;
	}
	public void setBoards(List<Board> boards) {
		this.boards = boards;
	}
	@Override
	public String toString() {
		return "User [no=" + user_no + ", name=" + name + ", email=" + email + ", password=" + password + ", gender="
				+ gender + "]";
	}
	
}
