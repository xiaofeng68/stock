package com.stock.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.authority.pojo.Tree;

/**
 * 核心题材菜单
 * 
 * @author Administrator
 * @date 2011-10-25 下午9:53:59
 */
public class TreeCoreKeys {

	private List<Map<String,Object>> list;
	private Tree root;	
	private Map<String,Tree> busTreeMap = new HashMap<String,Tree>();
	public TreeCoreKeys(List<Map<String,Object>> list) {
		this.list = list;
		this.root = new Tree();
	}

	/**
	 * 组合json
	 * 
	 * @param list
	 * @param node
	 */
	public Tree getCoreKeysNode() {
		root.setId("-1");
		root.setText("");
		root.setChildren(new ArrayList<Tree>());
		if (list == null) {
			return root;
		}
		for(Map<String,Object> map  : list){
			String bus = (String) map.get("bus");
			Tree tree = busTreeMap.get(bus);
			if(tree==null){
				tree = new Tree();
//				tree.setExpanded(true);
				busTreeMap.put(bus,tree);
				root.addChildren(tree);
			}
			Tree childrenTree = new Tree();
			childrenTree.setLeaf(true);
			childrenTree.setText(map.get("code") + "【" + map.get("name")+ "】");
			childrenTree.setData(map.get("content"));
			tree.addChildren(childrenTree);
			tree.setText(bus + "-" + tree.childrenSize());
		}
		return root;
	}
}
