/*
 * @(#)TreeAware.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.tree;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author liuan
 * 
 * @param <E>
 */
public interface TreeAware<E> extends Serializable{

	Serializable getId();

	boolean isAvailable();

	int getIndex();
	
	void setIndex(int index);

	E getParent();

	void setParent(E parent);

	Set<E> getChildren();

	void setChildren(Set<E> children);

	/**
	 * 添加子节点，设置父子关系，子节点的index递增1
	 * 
	 * @param child
	 */
	void addChild(E child);
	
	/**
	 * 判断此对象是否是参数中对象的孩子
	 * 
	 * @param another
	 * @return
	 */
	boolean isChild(E another);

	/**
	 * 判断此对象是否是参数中对象的父亲
	 * 
	 * @param another
	 * @return
	 */
	boolean isParent(E another);

	/**
	 * 判断此对象是否是参数中对象的后代
	 * 
	 * @param another
	 * @return
	 */
	boolean isGrandChild(E another);

	/**
	 * 判断此对象是否是参数中对象的祖先
	 * 
	 * @param another
	 * @return
	 */
	boolean isGrandParent(E another);
	
	/**
	 * 是否是根节点
	 * 
	 * @return
	 */
	boolean isRoot();
	
	/**
	 * 是否还有子节点
	 * 
	 * @return
	 */
	boolean hasChildren();
	
	/**
	 * 得到有效的节点
	 * 
	 * @return
	 */
	List<E> getAvailableChildren();
	
	/**
	 * 是否有有效的子节点
	 * 
	 * @return
	 */
	boolean hasAvailableChildren();
	
	/**
	 * 得到所有叶子节点
	 */
	List<E> getLeafChildren();

	/**
	 * 得到所有父级分类，从根节点到当前节点的父节点
	 * 
	 * @return
	 */
	List<E> getParentList();
}
