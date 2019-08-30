package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	public TbItemMapper mapper;

	@Override
	public TbItem getItemByItemId(long itemId) {
		TbItem item = new TbItem();
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = mapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			item = list.get(0);
			return item;
		}
		return null;
	}

	@Override
	public EUDataGridResult getItemList(int page, int rows) {
		EUDataGridResult result = new EUDataGridResult();
		TbItemExample example = new TbItemExample();
		PageHelper.startPage(page, rows);
		List<TbItem> list = mapper.selectByExample(example);
		result.setRows(list);
		PageInfo<TbItem> info = new PageInfo<>(list);
		result.setTotal(info.getTotal());
		return result;
	}

	@Override
	public TaotaoResult createItem(TbItem item) {
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		item.setStatus((byte) 1);
		mapper.insert(item);
		return TaotaoResult.ok();
	}
}
