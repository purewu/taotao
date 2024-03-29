package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.service.ItemCatService;

@RequestMapping("/item/cat")
@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;

	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getChildrenNode(@RequestParam(value = "id", defaultValue = "0") long id) {
		List<EUTreeNode> childrenNode = itemCatService.getChildrenNode(id);
		return childrenNode;

	}
}
