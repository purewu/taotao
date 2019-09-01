package com.taotao.service;

import org.springframework.web.bind.annotation.PathVariable;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemService {

	TbItem getItemByItemId(long itemId);

	EUDataGridResult getItemList(int page, int rows);

	TaotaoResult createItem(TbItem item, String desc, String itemParams);

	TaotaoResult delete(String ids);

	TaotaoResult instock(String ids);

	TaotaoResult reshelf(String ids);

	TaotaoResult queryItemDesc(long itemId);

	TaotaoResult queryParamByItemId(long itemId);

	TaotaoResult update(TbItem item,String desc,String itemParams);

	TbItemDesc queryItemDescByItemId(long itemId);

}
