/*
 * @(#)Tree.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author liuan
 * 
 * @param <E>
 */
public abstract class Tree<E extends TreeAware<E>> implements TreeAware<E>,Serializable {


	private static final long serialVersionUID = -3304124697722914503L;

	public boolean hasChildren() {
		return getChildren() != null && !getChildren().isEmpty();
	}

	public List<E> getAvailableChildren() {
		if (!hasChildren()) {
			return null;
		}
		List<E> availableChildren = new ArrayList<E>();
		for (E child : getChildren()) {
			if (child.isAvailable()) {
				availableChildren.add(child);
			}
		}
		return availableChildren;
	}

	public boolean hasAvailableChildren() {
		return getAvailableChildren() != null
				&& !getAvailableChildren().isEmpty();
	}

	public void addChild(E child) {
		Set<E> children = getChildren();
		if (children == null) {
			children = new HashSet<E>();
			setChildren(children);
		}
		int maxIndex = -1;
		for (E existedChild : getChildren()) {
			int index = existedChild.getIndex();
			maxIndex = maxIndex < index ? index : maxIndex;
		}
		child.setIndex(maxIndex + 1);
		child.setParent(extracted());
		children.add(child);
	}

	@SuppressWarnings("unchecked")
	private E extracted() {
		return (E) this;
	}
	
	public boolean isRoot() {
		return getParent() == null;
	}

	public boolean isParent(E another) {
		if (another == null) {
			return false;
		}
		E anotherParent = another.getParent();
		if (anotherParent == null) {
			return false;
		}
		return equals(anotherParent);
	}

	public boolean isGrandParent(E another) {
		if (another == null || another.isRoot()) {
			return false;
		}
		E anotherParent = another.getParent();
		if (anotherParent != null) {
			if (anotherParent.equals(extracted())) {
				return true;
			} else {
				return isGrandParent(anotherParent);
			}
		}
		return false;
	}

	public boolean isChild(E another) {
		if (another == null) {
			return false;
		}
		return another.isParent(extracted());
	}

	public boolean isGrandChild(E another) {
		if (another == null) {
			return false;
		}
		return another.isGrandParent(extracted());
	}

	public List<E> getParentList() {
		if (getParent() == null) {
			return null;
		}
		List<E> result = new ArrayList<E>();
		E current = extracted();
		while (true) {
			E parent = current.getParent();
			if (parent == null) {
				break;
			}
			result.add(parent);
			current = parent;
		}
		Collections.reverse(result);
		return result;
	}

	public List<E> getLeafChildren() {
		if (getChildren() == null || getChildren().isEmpty()) {
			return null;
		}
		List<E> result = new ArrayList<E>();
		Set<E> children = getChildren();
		for (E child : children) {
			if (!child.hasChildren()) {
				result.add(child);
			} else {
				List<E> childLeafChildren = child.getLeafChildren();
				if (childLeafChildren != null && !childLeafChildren.isEmpty()) {
					result.addAll(childLeafChildren);
				}
			}
		}
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Tree)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		E other = (E) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}
}
