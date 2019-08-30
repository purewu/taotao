package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper catMapper;

	@Override
	public List<EUTreeNode> getChildrenNode(long parentId) {
		List<EUTreeNode> list = new ArrayList<>();
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> itemCat = catMapper.selectByExample(example);
		for (TbItemCat item : itemCat) {
			EUTreeNode node = new EUTreeNode();
			node.setId(item.getId());
			node.setText(item.getName());
			node.setState(item.getIsParent() ? "closed" : "open");
			list.add(node);
		}
		return list;
	}

}
