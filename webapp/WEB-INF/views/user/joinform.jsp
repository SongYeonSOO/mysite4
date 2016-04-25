<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite3/assets/css/user.css" rel="stylesheet"
	type="text/css">
<!--  	<link href="${pageContext.request.contextPath}/assets/css/user.css" rel="stylesheet"> -->
<script type="text/javascript"
	src="/mysite3/assets/js/jquery/jquery-1.9.0.js">
	
</script>
<script type="text/javascript">
	$(function() {
//ajax ; 나중에 하자
		$("#join-form2").submit(function() {

			//submit해도 무시당함  return false;
			//return true; 일때만 가능

			//1. 이름 유효성 체크
			var name = $("#name").val();
			if (name == "") {
				alert("이름 없다!");
				$("#name").val("").focus();
				return false;
			}

			//2. 이메일 유효성 체크
			// 2-1. 입력체크
			var email = $("#email").val();
			if (email == "") {
				alert("이메일 없다!");
				$("#email").val("").focus();
				return false;
			}
			//2-2. 이메일 중복 체크
			if ($("#img-checkemail").is(":visible") == false) {
				alert("이메일 체크!!!!!!!");
				return false;
			}

			//3. 패스워드 유효성 체크
			var passwd = $("#passwd").val();
			if (passwd == "") {
				alert("패스워드 없다!");
				$("#passwd").val("").focus();
				return false;
			}

			//4. checkbox 유효성 체크
			var check = $("#agree-prov").is(":checked");
			if (check == false) {
				alert("약관 동의!!!!!!!!!!");
				return false;
			}
		});
		$("#email").change(function() {
			$("#btn-checkemail").show();
			$("#img-checkemail").hide();

		});
		$("#btn-checkemail").click(function() {
			var email = $("#email").val(); // value가 아니라 val이라는 ftn
			if (email == "") {
				return;
			}

			console.log(email); // email이 나온다는 것은 비어있지않다는 것
			$.ajax({
				//ajax가 js가 참조할 수 있는 객체로 만들어줌! 

				url : "/mysite3/user/checkemail?email=" + email, //요청 url
				type : "get", //통신 방식 get/post
				dataType : "json", //수신데이터타입
				data : "", //post방식인 경우 서버에 전달할 parameter data

				// contentType:""					보내는 data가 json형식인 경우 반드시 post방식으로 보내야 한다 
				//contentType:"application/json"
				//data: "{"a":"checkemail",email:"kickscar@gmail.com"}""
				// 성공 시 call-back
				success : function(response) {
					console.log(response.result + ":" + response.data);

					if (response.result != "success") {
						return;
					}

					if (response.data == false) {
						alert("이미 존재하는 이메일 다른 거");

						//email칸 비우고 다시 입력할 수 있도록
						$("#email").val("").focus();
						return;
					}

					//사용가능한 이메일
					//api 함수의 특정 api를 보이는 것은 show를 이용함
					$("#btn-checkemail").hide();
					$("#img-checkemail").show();
				},
				// 실패시 call-back
				error : function(jqXHR, status, error) {
					console.error(status + ":" + error);
				}
			});
		});

	});
</script>


</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
		<div id="content">
			<div id="user">

				<form id="join-form" name="joinForm" method="post"
					action="/mysite3/user/join">
					<label class="block-label" for="name">이름</label> <input id="name"
						name="name" type="text" value=""> <label
						class="block-label" for="email">이메일</label> <input id="email"
						name="email" type="text" value=""> <input type="button"
						value="id 중복체크" id="btn-checkemail"> <img
						id="img-checkemail" style="display: none;"
						src="/mysite3/assets/images/check.png"> <label
						class="block-label">패스워드</label> <input id="passwd"
						name="passwd" type="password" value="">

					<fieldset>
						<legend>성별</legend>
						<label>여</label> <input type="radio" name="gender" value="F"
							checked="checked"> <label>남</label> <input type="radio"
							name="gender" value="M">
					</fieldset>

					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>

					<input type="submit" value="가입하기">

				</form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/include/navigation.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
	</div>
</body>
</html>