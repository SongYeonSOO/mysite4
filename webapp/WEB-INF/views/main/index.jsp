<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite4/assets/css/main.css" rel="stylesheet"
	type="text/css">

</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp">
			<c:param name="header" value="" />
		</c:import>
		<div id="wrapper">
			<div id="content">
				<div id="site-introduction">
					<img id="profile" src="/mysite4/assets/images/FullSizeRender.jpg">
					<h2>배고프다</h2>
					<p>
						돈까스먹고싶다. <br> <br> <a href="/mysite4/guestbook/index">방명록</a>에 글 남기기<br>
					</p>
				</div>
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