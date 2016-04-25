<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="com.estsoft.mysite.vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="header">

	<h1>
		<a href="/mysite3/main">MySite</a>
	</h1>
	<ul>
		<c:choose>
			<c:when test="${empty authUser}">
				<li><a href="/mysite3/user/loginform">로그인</a>
				<li>
				<li><a href="/mysite3/user/joinform">회원가입</a>
				<li>
			</c:when>
			<c:otherwise>
				<li><a href="/mysite3/user/modifyform">회원정보수정</a>
				<li>
				<li><a href="/mysite3/user/logout">로그아웃</a>
				<li>
				<li>${sessionScope.authUser.name}님안녕하세요^^;</li>
				<!-- 		<li>${authUser.name}님 안녕하세요 ^^;</li> 로 해도 가능하다 -->

			</c:otherwise>
		</c:choose>

	</ul>
</div>