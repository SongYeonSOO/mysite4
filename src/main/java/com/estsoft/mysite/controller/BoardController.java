package com.estsoft.mysite.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estsoft.mysite.service.BoardService;
import com.estsoft.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping("")
	public String index(@RequestParam(value = "kwd", required = true, defaultValue = "") String kwd,
			@RequestParam(value = "page", required = true, defaultValue = "-1") Long page) {
		boardService.SearchList(kwd, page);
		return "board/list";
	}

	// , @RequestParam(value = "no", required = true, defaultValue = "") boolean
	// isview
	@RequestMapping("/view")
	public String view(@RequestParam(value = "no", required = true, defaultValue = "") Long no) {
		boardService.getList(no, true);
		return "board/view";
	}

	@RequestMapping("/boardmodifyform")
	public String boardModifyForm() {
		return "board/modify";
	}

	@RequestMapping("/boardmodify")
	@ResponseBody
	public Map<String, Object> boardModify(@ModelAttribute BoardVo vo) {
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
	public Map<String, Object> write(@ModelAttribute BoardVo vo) {
		boardService.insert(vo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		// map.put("data", delete);
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
