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

import com.estsoft.mysite.service.BoardService;
import com.estsoft.mysite.vo.BoardVo;
import com.estsoft.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping("")
//	@ResponseBody
	public String list(@RequestParam(value = "kwd", required = true, defaultValue = "") String kwd,
			@RequestParam(value = "page", required = true, defaultValue = "1L") Long page, Model model) {
		
		List<BoardVo> list = boardService.SearchList(kwd, page);
		model.addAttribute("list",list);
		
		return "board/list";	
		
	}
	// 	BoardVo vo =boardService.getView(no, true);

	// isview
	@RequestMapping("/view")
	public String view(@RequestParam(value = "no", required = true, defaultValue = "-1") Long no) {
		return "board/view";
	}

	@RequestMapping("/boardmodifyform")
	public String boardModifyForm() {
		return "board/modify";
	}

	@RequestMapping("/boardmodify")
	@ResponseBody
	public Map<String, Object> boardModify(@ModelAttribute BoardVo vo,HttpSession session) {
		UserVo userVo = (UserVo)session.getAttribute("authUser");
		vo.setUser_no(userVo.getNo());		
		boardService.ModifyUpdate(vo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		// map.put("data", delete);
		return map;
	}

	@RequestMapping("/writeform")
	public String writeForm() {

		return "board/write";
	}

	@RequestMapping("/write")
	@ResponseBody
	public Map<String, Object> write(@ModelAttribute BoardVo vo, HttpSession session) {
		UserVo userVo = (UserVo)session.getAttribute("authUser");
		vo.setUser_no(userVo.getNo());
		boardService.insert(vo);
		boardService.SearchList("", 1L);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data", vo);
		return map;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@ModelAttribute BoardVo vo) {
		boardService.delete(vo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		// map.put("data", delete);
		return map;
	}
}
