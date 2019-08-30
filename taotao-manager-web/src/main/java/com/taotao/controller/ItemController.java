package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	public ItemService itemService;

	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable("itemId") long itemId) {
		return itemService.getItemByItemId(itemId);

	}

	@RequestMapping("/item/list")
	@ResponseBody
	public EUDataGridResult getItemList(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam("rows") int rows) {
		EUDataGridResult itemList = itemService.getItemList(page, rows);
		if (itemList != null) {
			return itemList;
		}
		return null;

	}

	@RequestMapping(value = "/item/save", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createItem(TbItem item) {
		TaotaoResult result = itemService.createItem(item);
		return result;
	}

}
