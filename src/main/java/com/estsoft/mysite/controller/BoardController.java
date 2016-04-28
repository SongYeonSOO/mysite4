package com.estsoft.mysite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estsoft.mysite.annotation.Auth;
import com.estsoft.mysite.service.BoardService;
import com.estsoft.mysite.vo.BoardVo;
import com.estsoft.mysite.vo.UserVo;

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
		
		BoardVo vo=boardService.getView(no, true);
		vo.setNo(no);
		
		model.addAttribute("vo",vo);
		return "board/view";
	}

	@RequestMapping("/boardmodifyform")
	public String boardModifyForm(@ModelAttribute BoardVo vo,Model model) {
		model.addAttribute("vo", vo);
		return "board/modify";
	}
	
	@RequestMapping("/boardmodify")
	public String boardModify(@ModelAttribute BoardVo vo,HttpSession session) {
		UserVo userVo = (UserVo)session.getAttribute("authUser");
		vo.setUser_no(userVo.getNo());		

		System.out.println("BC: VO"+vo);
		
		BoardVo boardVo=boardService.getView(vo.getNo(), false);
		
		boardVo.setNo(vo.getNo());
		boardVo.setTitle(vo.getTitle());
		boardVo.setContent(vo.getContent());
		
		System.out.println("BC : boardVo"+boardVo);
		boardService.ModifyUpdate(boardVo);

		return "redirect:/board";
	}
	

	@RequestMapping("/writeform")
	public String writeForm(@RequestParam(value = "no", required = true, defaultValue = "-1") Long no, Model model)  {
		model.addAttribute("no",no);
		return "board/write";
}
	@Auth
	@RequestMapping("/write")
		public String write(@RequestParam(value = "no", required = true, defaultValue = "-1") Long no, HttpSession session,@RequestParam(value = "title", required = true, defaultValue = "") String title,@RequestParam(value = "content", required = true, defaultValue = "") String content)  {
		UserVo userVo = (UserVo)session.getAttribute("authUser");
		BoardVo vo = new BoardVo();
		vo.setUser_no(userVo.getNo());
		vo.setUser_name(userVo.getName());
		
		if(no !=-1){
		BoardVo superVo=boardService.getView(no, false);
		vo.setOrder_no(superVo.getOrder_no());
		vo.setDepth(superVo.getDepth());
		vo.setGroup_no(superVo.getGroup_no());
		}
		
		vo.setTitle(title);
		vo.setContent(content);

		System.out.println("Vo"+vo);
		boardService.insert(vo);
		return "redirect:/board";
	}

	@RequestMapping("/delete")
	public String delete(@ModelAttribute BoardVo vo)  {
		boardService.delete(vo);
		return "redirect:/board";
	}
}
