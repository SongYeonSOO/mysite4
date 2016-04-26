<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/guestbook.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>


<script type="text/javascript">
	var page = 1;
	var dialogDelete = null;

	var fetchList = function() {
		$.ajax({
			// 내가 정한 주소 p: page
			//브라우저에서 "/mysite/guestbook?a=ajax-list&p="로 자동으로 바뀐다!!!!
			url : "${pageContext.request.contextPath}/guestbook/list?p="
					+ page,
			type : "get",
			dataType : "json", //이거 안쓰면 parsing error난다! listaction이 실행되기 때문
			data : "", //get방식이니깐 본문에 들어가는 data가 없음
			success : function(response) {
				console.log(response);

				if (response.result != "success") {
					return;
				}
				if (response.data.length == 0) {
					console.log("end!");
					//$("btn-next").hide(); 와 동일!
					$("btn-next").attr("disabled", true);
					return;
				}
				// data: array , vo : object
				$.each(response.data, function(index, vo) {
					console.log(index + ":" + vo);
					renderHtml(vo);
				});
				page++;
			},
			error : function(xhr, status, error) {
				console.log(status + ":" + error);
			}// xhr : xml http request

		});
	}

	var renderHtml = function(vo) {

		// 이 방법 대신 js template library를 사용한다. ex> EJS, undercore.js 등

		var html = "<li id=li-"+vo.no+"><table><tr>" + "<td>" + vo.name
				+ "</td>" + "<td>" + vo.reg_date + "</td>"
				+ "<td><a href='#' class='a-del' data-no="+vo.no+">삭제</a></td>"
				+ "</tr><tr>"
				//g: global
				+ "<td colspan=3>" + vo.message.replace(/\r\n/g, "<br>")
				+ "</td>" + "</tr> </table> </li>";

		//gb-list 뒤에 추가
		// cf> prepend : 앞에 추가
		$("#gb-list").append(html);

	}
	var renderHtmlPre = function(vo) {
		// 이 방법 대신 js template library를 사용한다. ex> EJS, undercore.js 등
		var html = "<li id=li-"+vo.no+"><table><tr>" + "<td>" + vo.name
				+ "</td>" + "<td>" + vo.reg_date + "</td>"
				+ "<td><a href='#' class='a-del' data-no="+vo.no+">삭제</a></td>"
				+ "</tr><tr>"
				//g: global
				+ "<td colspan=3>"
				+ vo.message.replace(/\r\n/g, "<br>").replace(/\n/g, "<br>")
				+ "</td>" + "</tr> </table> </li>";

		//gb-list 뒤에 추가
		// cf> prepend : 앞에 추가
		$("#gb-list").prepend(html);

	}
	$(function() {

		//\"/mysite/guestbook?a=deleteform\"
		$("#form-insert").submit(
				function(event) {
					event.preventDefault(); // posting이 안되도록 하자!

					//posting안되니까 ajax쓰자!
					var name = $("#name").val(); // value가 아니라 val이라는 ftn
					var passwd = $("#passwd").val(); // value가 아니라 val이라는 ftn
					var message = $("#message").val(); // value가 아니라 val이라는 ftn

					//form reset

					console.log("this: " + this);
					this.reset();
					$.ajax({
						url : "${pageContext.request.contextPath}/guestbook/insert",
						type : "post",
						dataType : "json", //이거 안쓰면 parsing error난다! listaction이 실행되기 때문
						data : "name=" + name + "&passwd=" + passwd
								+ "&message=" + message,
						success : function(response) {

							console.log(response);

							if (response.result != "success") {
								return;
							}
							console.log(response.data);
							renderHtmlPre(response.data);
						},
						error : function(xhr, status, error) {
							console.log(status + ":" + error);
						}// xhr : xml http request

					})

				})

		/* 이거로 만들어도됨
		$("#btn-next").click(function(){});	 */

		$("#btn-next").on("click", function() {
			console.log("clicked");
			fetchList();
		});

		//window가 scroll했을 때 세 가지의 값을 구해서 끝에 닿았는 지 확인
		$(window).scroll(
				function() {
					var documentHeight = $(document).height();
					var windowHeight = $(window).height();
					var scrollTop = $(window).scrollTop();
					console.log($(document).height() + ":" + windowHeight + ":"
							+ scrollTop);

				})

		//dialog 객체 생성

		//live event 미리 event를 정의해놓고  event 발생 시 진행함
		$(document).on("click", ".a-del", function() {
			event.preventDefault();
			//this : a
			var no = $(this).attr("data-no");
			$("#del-no").val(no);
			dialogDelete.dialog("open");

		});

		dialogDelete = $("#dialog-form").dialog({
			autoOpen : false,
			height : 300,
			width : 350,
			modal : true,
			buttons : {
				"삭제" : function() {
					console.log(this);
					console.log("no : " + $("#del-no").val() + "   password : " + $("#del-password").val());
					var no = $("#del-no").val();
					var passwd = $("#del-password").val();
					this.reset();
					console.log("no : " + no + "   password : " + passwd);
					$.ajax({
						url : "${pageContext.request.contextPath}/guestbook/delete",
						type : "post",
						dataType : "json", //이거 안쓰면 parsing error난다! listaction이 실행되기 때문
						data : "no=" + no + "&passwd=" + passwd,
						success : function(response) {

							console.log(response);

							if (response.result != "success") {
								return;
							}
							console.log(response.data);
							if (response.data == 1) {
								$("#li-" + no).remove();
							}

						},
						error : function(xhr, status, error) {
							console.log(status + ":" + error);
						}// xhr : xml http request

					})
					dialogDelete.dialog("close");
				},
				"취소" : function() {
					this.reset();
					dialogDelete.dialog("close");
				}

			},
			close : function() {
			}
		});

		//최초 data 가져오기 (처음 5개 출력해서 보여주기)
		fetchList();
	$("#del-password").keyup(function(){
		console.log($("#del-password").val())
	});

	});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp">
			<c:param name="header" value="" />
		</c:import>

		<div id="content">
			<div id="guestbook">
				<!-- 	action 빠이!!		<form action="/mysite/guestbook" method="post"> -->
				<form id="form-insert">
					<input type="hidden" name="a" value="insert">
					<table>
						<tr>
							<td>이름</td>
							<td><input type="text" name="name" id="name"></td>
							<td>비밀번호</td>
							<td><input type="password" name="passwd" id="passwd"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="message" id="message"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>

				<ul id="gb-list">

				</ul>

				<div style="margin-top: 20px; text-align: center">
					<!-- <img src=> 여기에 그림넣고 !!!!!! -->
					<button id="btn-next">다음 가져오기</button>
				</div>

			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="navigation" value="" />
		</c:import>

		<c:import url="/WEB-INF/views/include/footer.jsp">
			<c:param name="footer" value="" />
		</c:import>



		<div id="dialogMsg" title="pop up!">
			<p>Hello ! ! ! !</p>
		</div>


		<div title="메세지 비밀번호 입력">
			<p class="validateTips">메세지의 비밀번호 입력</p>

			<form id="dialog-form">
				<label for="password">Password</label> <input type="hidden"	id="del-no" value="">
					<input type="password" name="password" id="del-password" value="a">
<!--  class="text ui-widget-content ui-corner-all" -->
				<!-- Allow form submission with keyboard without duplicating the dialog button -->
				<input type="submit" tabindex="-1" style="position: absolute; top: -1000px">
			</form>
		</div>
	</div>

</body>
</html>