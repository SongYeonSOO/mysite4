package com.estsoft.mysite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estsoft.mysite.domain.Guestbook;
import com.estsoft.mysite.service.GuestBookService;


@Controller
@RequestMapping("/guestbook")
public class GuestBookController {

	@Autowired
	private GuestBookService guestbookService;

	@RequestMapping("/index")
	public String index() {
		return "guestbook/ajax-main";
	}

	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(value = "p", required = true, defaultValue = "-1") int page) {
		List<Guestbook> guestlist = guestbookService.getList(page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data", guestlist);
		return map;
	}

	@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insert(@ModelAttribute Guestbook guestbook1) {

		Guestbook guestbook = guestbookService.save(guestbook1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data", guestbook);
		return map;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@ModelAttribute Guestbook guestbook) {
		Boolean delete = guestbookService.delete(guestbook);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data", delete);
		return map;
	}
}
