package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String showIdex() {
		return "index";
	}

	@RequestMapping("/{page}")
	public String showPage(@PathVariable("page") String page) {
		return page;

	}
}
