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
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemDescExample;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	public TbItemMapper mapper;
	@Autowired
	TbItemDescMapper descMapper;
	@Autowired
	TbItemParamItemMapper itemParamItemMapper;

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
	public TaotaoResult createItem(TbItem item, String desc, String itemParams) {
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		item.setStatus((byte) 1);
		mapper.insert(item);
		createItemDesc(itemId, desc);
		createItemParams(itemId, itemParams);
		return TaotaoResult.ok();
	}

	public void createItemDesc(long itemId, String desc) {
		TbItemDesc idesc = new TbItemDesc();
		idesc.setItemId(itemId);
		idesc.setCreated(new Date());
		idesc.setUpdated(new Date());
		idesc.setItemDesc(desc);
		descMapper.insert(idesc);
	}

	public void createItemParams(long itemId, String itemParams) {
		TbItemParamItem iparam = new TbItemParamItem();
		iparam.setItemId(itemId);
		iparam.setCreated(new Date());
		iparam.setUpdated(new Date());
		iparam.setParamData(itemParams);
		itemParamItemMapper.insert(iparam);

	}

	@Override
	public TaotaoResult delete(String ids) {
		if (ids != null) {

			String[] idArr = ids.split(",");
			for (String id : idArr) {
				TbItem item = mapper.selectByPrimaryKey(Long.parseLong(id));
				item.setStatus((byte) 3);
				mapper.updateByPrimaryKeySelective(item);
			}
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult instock(String ids) {
		if (ids != null) {

			String[] idArr = ids.split(",");
			for (String id : idArr) {
				TbItem item = mapper.selectByPrimaryKey(Long.parseLong(id));
				item.setStatus((byte) 2);
				mapper.updateByPrimaryKeySelective(item);
			}
		}
		return TaotaoResult.ok();
	}

	public TaotaoResult reshelf(String ids) {
		if (ids != null) {

			String[] idArr = ids.split(",");
			for (String id : idArr) {
				TbItem item = mapper.selectByPrimaryKey(Long.parseLong(id));
				item.setStatus((byte) 1);
				mapper.updateByPrimaryKeySelective(item);
			}
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult queryItemDesc(long itemId) {
		TbItemDescExample example = new TbItemDescExample();
		example.createCriteria().andItemIdEqualTo(itemId);
		List<TbItemDesc> desc = descMapper.selectByExampleWithBLOBs(example);
		if (desc != null && desc.size() > 0) {
			return TaotaoResult.ok(desc.get(0));
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult queryParamByItemId(long itemId) {
		List<TbItemParamItem> itemParam = itemParamItemMapper.selectByItemId(itemId);
		if (itemParam != null && itemParam.size() > 0) {
			return TaotaoResult.ok(itemParam.get(0));
		}
		return TaotaoResult.ok();
	}

	public TaotaoResult update(TbItem item, String desc, String itemParams) {
		item.setUpdated(new Date());

		mapper.updateByPrimaryKeySelective(item);
		updateDesc(item, desc);
		updateParam(item, itemParams);
		return TaotaoResult.ok();

	}

	public void updateParam(TbItem item, String itemParams) {
		if (item.getId() != null) {
			List<TbItemParamItem> list = itemParamItemMapper.selectByItemId(item.getId());
			if (list != null && list.size() > 0) {
				TbItemParamItem itemParam = list.get(0);
				itemParam.setUpdated(new Date());
				itemParam.setParamData(itemParams);
				TbItemParamItemExample example = new TbItemParamItemExample();
				example.createCriteria().andItemIdEqualTo(item.getId());
				itemParamItemMapper.updateByExampleSelective(itemParam, example);
			}

		}
	}

	public void updateDesc(TbItem item, String desc) {

		if (item.getId() != null) {
			TbItemDesc itemDesc = descMapper.selectByPrimaryKey(item.getId());
			System.out.println("======================="+itemDesc);
			itemDesc.setUpdated(new Date());
			itemDesc.setItemDesc(desc);
			descMapper.updateByPrimaryKeyWithBLOBs(itemDesc);
		}

	}

	public TbItemDesc queryItemDescByItemId(long itemId) {

		TbItemDesc desc = descMapper.selectByPrimaryKey(itemId);
		return desc;

	}
}
