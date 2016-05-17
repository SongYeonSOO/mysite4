package com.estsoft.mysite.controller;


import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.estsoft.mysite.annotation.Auth;
import com.estsoft.mysite.annotation.AuthUser;
import com.estsoft.mysite.domain.Board;
import com.estsoft.mysite.domain.User;
import com.estsoft.mysite.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping("")
	public String list(@RequestParam(value = "kwd", required = true, defaultValue = "") String kwd,
			@RequestParam(value = "page", required = true, defaultValue = "1") Long page, Model model) {

		Map<String, Object> map = boardService.SearchList(kwd, page);
		
		model.addAttribute("pageinfo", map.get("pageinfo"));
		model.addAttribute("boardno",map.get("boardno"));
		model.addAttribute("list",map.get("list"));
	
		return "board/list";	
		
	}
	// 	BoardVo vo =boardService.getView(no, true);

	// isview
	@RequestMapping("/view")
	public String view(@RequestParam(value = "no", required = true, defaultValue = "-1") Long no, Model model,HttpSession session) {
		
		Board vo=boardService.getView(no, true);
		vo.setNo(no);
		
		model.addAttribute("vo",vo);
		return "board/view";
	}

	@RequestMapping("/boardmodifyform")
	public String boardModifyForm(@ModelAttribute Board vo,Model model) {
		model.addAttribute("vo", vo);
		return "board/modify";
	}
	
	@RequestMapping("/boardmodify")
	public String boardModify(@ModelAttribute Board vo,HttpSession session) {
		User userVo = (User)session.getAttribute("authUser");
		User user = new User();
		user.setNo(userVo.getNo());

		System.out.println("BC: VO"+vo);
		
		Board boardVo=boardService.getView(vo.getNo(), false);
		
		boardVo.setNo(vo.getNo());
		boardVo.setTitle(vo.getTitle());
		boardVo.setContent(vo.getContent());
		
		System.out.println("BC : boardVo"+boardVo);
		boardService.ModifyUpdate(boardVo);

		return "redirect:/board";
	}
	

	@RequestMapping("/writeform")
	public String writeForm(@ModelAttribute Board vo,Model model)  {
		model.addAttribute("vo",vo);
		return "board/write";
}
	@Auth
	@RequestMapping("/write")
	//@AuthUser로 받는 parameter는 반드시 인증된 사용자가 넘어오게된다
		public String write(@AuthUser User authUser,HttpSession session, @ModelAttribute Board vo)  {
		/*UserVo userVo = (UserVo)session.getAttribute("authUser");*/
		System.out.println("write:::::::::::::::::::::::::::::auth::::::::"+authUser);
		System.out.println("write:::::::::::::::::::::::::::::vo::::::::"+vo);

		User user = new User();
		user.setNo(authUser.getNo());
		user.setName(authUser.getName());
		System.out.println("before"+vo);
		vo.setUser(user);
		System.out.println("after"+vo);
		if(vo.getNo()!= null){
		Board superVo=boardService.getView(vo.getNo(), false);
		vo.setOrderNo(superVo.getOrderNo());
		vo.setDepth(superVo.getDepth());
		vo.setGroupNo(superVo.getGroupNo());
		}
		
		boardService.insert(vo);
		return "redirect:/board";
	}

	@RequestMapping("/delete")
	public String delete(@ModelAttribute Board vo)  {
		boardService.delete(vo);
		return "redirect:/board";
	}
}
