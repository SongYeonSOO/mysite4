package com.estsoft.mysite.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estsoft.mysite.service.UserService;
import com.estsoft.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/joinform")
	public String joinform() {
		// return "/WEB-INF/views/user/joinform.jsp";

		return "user/joinform";

	}

	// submit 눌렀을때 보내자!
	@RequestMapping("/join")
	// model로 안쓰는건 join에 보낼게 없으니깐 일단은 modelAttribute만 이용한다
	public String join(@ModelAttribute UserVo vo) {
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}

	// success시 /user/joinsuccess.jsp로 넘어가자! (forwarding)
	@RequestMapping("/joinsuccess")
	public String joinSuccess() {

		return "user/joinsuccess";
	}

	@RequestMapping("/loginform")
	public String loginform() {
		return "user/loginform";
	}
/*
	@RequestMapping("/login")
	public String login(@ModelAttribute UserVo vo, HttpSession session) {
		// 조만간 session처리도 배울 것이다

		UserVo userVo = userService.login(vo);
		if (userVo == null) {
			// login 실패!
			return "user/loginfailform";
		}
		// login 성공!
		session.setAttribute("authUser", userVo);
		return "redirect:/main";

	}
*/
/*	// 조만간 session처리도 배울 것이다

	@RequestMapping("/logout")
	public String logout(@ModelAttribute UserVo vo, HttpSession session) {
		
		//인증 유무 체크
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser != null){
			session.removeAttribute("authUser");
			session.invalidate();
		}
		
		return "redirect:/main";

	}
*/	
	@RequestMapping("/checkemail")
	@ResponseBody
	public Map<String,Object> checkEmail(@RequestParam(value="email", required=true, defaultValue="")String email) {
		UserVo vo= userService.getUser(email);

		Map<String,Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data", vo==null);
		
		//do not use JSONObject!!!! json string을 return하기 위해서 그냥 객체를 return 함
		return map;
	}

	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		return "안녕 hello!!";
	}
	
	@RequestMapping("/modifyform")
	public String modifyform() {
		return "user/modifyform";
	}
	
	@RequestMapping("/modify")
	public String modify(@ModelAttribute UserVo vo, HttpSession session) {
		
		//인증 유무 체크
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser != null){
			vo.setNo(authUser.getNo());
			userService.modifyUser(vo);
			session.setAttribute("authUser", vo);
		}

		return "redirect:/main";
	}

}
