package com.winxuan.ec.front.controller.cps;

import java.util.Map;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-10
 */
public class CpsFactory {
	private Map<Long, Cps> cpss;

	public void setCpss(Map<Long, Cps> cpss) {
		this.cpss = cpss;
	}

	public Cps get(Long id) {
		return cpss.get(id);
	}
}
