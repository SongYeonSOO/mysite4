package com.estsoft.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.estsoft.mysite.service.UserService;
import com.estsoft.mysite.vo.UserVo;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	UserService userService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String email = request.getParameter("email");
		String passwd = request.getParameter("passwd");

		// interceptor는 webappcontainer에 있음
		// interceptor를 web관련된 부분이라서 Web application context에 있음

		// Web application context(IOC Container 가져오기 )
		// applicationContext ; container가 전역객체이다 -> (servlet)context범위안에 있음 ;
		// (servlet: project범위)
		// application / session / request / page 범위가 있는데 그 중 application 범위인 것임
		// ;
		// container는 project내내 쓰기 때문!?!
		// request는 setAttribute()로 request범위에 담는다. 근데 얘는 getServletContext를 하면
		// servlet context가 return한다고 그건 application context라고
		// 그래서 application 범위로 해야한다고(tomcat이 닫힐때까지 돌리자고)

		// root에서 autowired된 걸 가져오고싶으니깐 uerService를 new로 만들면 안됨
		// root context에서 끌고와라
		/*
		 * ApplicationContext applicationContext =
		 * WebApplicationContextUtils.getWebApplicationContext(
		 * request.getServletContext() ); UserService userService =
		 * applicationContext.getBean( UserService.class );
		 */

		UserVo userVo = new UserVo();
		userVo.setEmail(email);
		userVo.setPasswd(passwd);

		//login service 호출(login 작업)
		UserVo authUser = userService.login(userVo);

		if(authUser == null){
			//request.getContextPath() ; -> /mysite3까지임 
			response.sendRedirect(request.getContextPath()+"/user/loginform");
		
			//의미상 끝났지만 그래도 false를 return 써주시오
			return false;
		}
		
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		response.sendRedirect(request.getContextPath()+"/main");
		return false;
		
	}
}
