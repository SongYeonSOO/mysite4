package com.estsoft.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.estsoft.mysite.annotation.Auth;
import com.estsoft.mysite.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// HANDLER METHOD가 아닌 경우		
		// handler default servlet은 없는 url, image url --> error발생 --> 앞에서 check 필요
		// default servlet 실행
		if(handler instanceof HandlerMethod == false){ return true;} 
		
		//진짜 HANDLER METHOD인 경우
		// 이 type인 annotation에 대해서  가져옴
		Auth auth = ((HandlerMethod)handler).getMethodAnnotation(Auth.class);
		
		// @Auth가 없는 컨트롤러 핸들러
		// 접근제어가 필요없음
		if(auth == null){
			return true;
		}
		
		HttpSession session = request.getSession();
		if(session == null){
			response.sendRedirect(request.getContextPath()+"/user/loginform");
			return false;
		}
		UserVo authUser =(UserVo)session.getAttribute("authUser");
		if(authUser == null){
			response.sendRedirect(request.getContextPath()+"/user/loginform");
		}
		
			return true;
	}

}
