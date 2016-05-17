package com.estsoft.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.estsoft.mysite.domain.User;
import com.estsoft.mysite.service.UserService;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	UserService userService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		User user = new User();
		user.setEmail(email);
		user.setPassword(password);

		User authUser = userService.login(user);

		if (authUser == null) {

			response.sendRedirect(request.getContextPath() + "/user/loginform");

			return false;
		}

		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		response.sendRedirect(request.getContextPath() + "/main");
		return false;

	}
}
