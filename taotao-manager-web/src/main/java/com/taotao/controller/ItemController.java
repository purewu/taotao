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
import com.taotao.pojo.TbItemDesc;
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
	public TaotaoResult createItem(TbItem item, String desc, String itemParams) {
		TaotaoResult result = itemService.createItem(item, desc, itemParams);
		return result;
	}

	@RequestMapping("/item/delete")
	@ResponseBody
	public TaotaoResult delete(String ids) {
		TaotaoResult result = itemService.delete(ids);
		return result;

	}

	@RequestMapping("/item/instock")
	@ResponseBody
	public TaotaoResult instock(String ids) {
		TaotaoResult instock = itemService.instock(ids);
		return instock;

	}

	@RequestMapping("/item/reshelf")
	@ResponseBody
	public TaotaoResult reshelf(String ids) {
		TaotaoResult reshelf = itemService.reshelf(ids);
		return reshelf;

	}

	@RequestMapping("/item/desc/{itemId}")
	@ResponseBody
	public TaotaoResult queryItemDescByItemId(@PathVariable("itemId") long itemId) {
		TbItemDesc result = itemService.queryItemDescByItemId(itemId);
		return TaotaoResult.ok(result);

	}

	@RequestMapping("/item/queryParam/{itemId}")
	@ResponseBody
	public TaotaoResult queryParamByItemId(@PathVariable("itemId") long itemId) {
		TaotaoResult itemParam = itemService.queryParamByItemId(itemId);
		return itemParam;

	}

	@RequestMapping("/item/update")
	@ResponseBody
	public TaotaoResult update(TbItem item,String desc,String itemParams) {
		TaotaoResult update = itemService.update(item,desc,itemParams);
		return update;

	}
}
