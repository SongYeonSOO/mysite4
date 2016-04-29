package com.estsoft.mysite.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.estsoft.mysite.annotation.AuthUser;
import com.estsoft.mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	

	//@AuthUser뿐만 아니라 모든 parameter가 다 넘어온다.
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
	
		//check @AuthUser annotation
		//parameter조건 확인
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		if(authUser == null){
			return false;
		}
		
		//check parameter type
	//왼쪽이 객체가 아니니까 instanceof가 아님
		if(parameter.getParameterType().equals(UserVo.class)==false){return false;}
		return true;

	}
	
	
	

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer arg1, NativeWebRequest webRequest,
			WebDataBinderFactory arg3) throws Exception {
	
		if(supportsParameter(parameter) == false){
			return WebArgumentResolver.UNRESOLVED;
		}
		//session에서 authUser 가져오기
		//nativeWebRequest에 HTTPServletRequest의 정보들이 들어있을 것임 -> request 필요
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpSession session = request.getSession();
		if(session == null){
			return WebArgumentResolver.UNRESOLVED;
		}
		
		return session.getAttribute("authUser");		
	}

}
