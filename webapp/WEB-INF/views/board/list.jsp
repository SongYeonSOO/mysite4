<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite3/assets/css/board.css" rel="stylesheet"
	type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp">
			<c:param name="header" value="" />
		</c:import>
		<div id="content">
			<div id="board">
				<form id="search_form" action="/mysite3/board" method="post">
					<input type="text" id="kwd" name="kwd" value=""> <input
						type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>

					<!-- 				<c:set var="count" value="${fn.length(list) }" />  -->
					<c:forEach items="${list}" var="vo" varStatus="status">
						<tr>
							<c:choose>
								<c:when test="${vo.depth==0}">
									<td>${requestScope.boardno-status.index}</td>
									<td style="text-align: left; padding-left: 0px"><a
										href="/mysite3/board/view&no=${vo.no}">${vo.title}</a></td>
									<td>${vo.user_name}</td>
									<td>${vo.hit}</td>
									<td>${vo.reg_date}</td>
								</c:when>
								<c:otherwise>
									<td>${requestScope.boardno-status.index}</td>
									<td style="text-align:left; padding-left:${vo.depth*20}px"><img
										src="${pageContext.request.contextPath}/assets/images/reply.png"><a
										href="/mysite3/board/view&no=${vo.no}">${vo.title}</a></td>
									<td>${vo.user_name}</td>
									<td>${vo.hit}</td>
									<td>${vo.reg_date}</td>

								</c:otherwise>
							</c:choose>
							<td><c:if test="${vo.user_no==sessionScope.authUser.no}">

									<a href="/mysite3/board/delete?no=${vo.no}&group_no=${vo.group_no}" class="del">삭제</a>
								</c:if></td>
						</tr>

					</c:forEach>
				</table>
				<div class="pager">
					<ul>

						<c:if test="${pageinfo.currentpage > 5}">
							<li><a href="/mysite3/board?page=${pageinfo.beginpage-1}&kwd=${kwd}">◀</a></li>
						</c:if>
						<c:forEach begin="${pageinfo.beginpage}" end="${pageinfo.maxpage}"
							var="viewpage">
							<c:choose>
							<c:when test="${viewpage==pageinfo.currentpage}">
							<li class="selected"><a href="/mysite3/board?page=${viewpage}&kwd=${kwd}">${viewpage}</a></li>
							</c:when>
							<c:otherwise>
							<li><a href="/mysite3/board?page=${viewpage}&kwd=${kwd}">${viewpage}</a></li>
							</c:otherwise>
							</c:choose>

						</c:forEach>
						<c:if test="${pageinfo.totalpage != pageinfo.maxpage}">
							<li><a href="/mysite3/board?page=${pageinfo.maxpage+1}&kwd=${kwd}">▶</a></li>
						</c:if>

					</ul>
				</div>

				<c:choose>
					<c:when test='${empty authUser}'>
					</c:when>
					<c:otherwise>

						<div class="bottom">
							<a href="/mysite3/board/writeform" id="new-book">글쓰기</a>
						</div>
					</c:otherwise>
				</c:choose>

			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="navigation" value="" />
		</c:import>

		<c:import url="/WEB-INF/views/include/footer.jsp">
			<c:param name="footer" value="" />
		</c:import>
	</div>
</body>
</html>