package com.authority.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ext树菜单
 * 
 * @author Administrator
 * @date 2011-10-19 下午10:04:45
 */
public class Tree implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String text;
	private String iconCls;
	private boolean expanded;
	private boolean leaf;
	private String url;
	private Object data;
	private List<Tree> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	public void addChildren(Tree node){
		if(this.children==null){
			this.children = new ArrayList<Tree>();
		}
		this.children.add(node);
	}
	public int childrenSize(){
		if(this.children==null){
			return 0;
		}
		return this.children.size();
	}
}
