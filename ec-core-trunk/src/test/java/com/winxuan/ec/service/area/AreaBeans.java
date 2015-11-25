package com.winxuan.ec.service.area;

import com.winxuan.ec.model.area.Area;

/**
 * 区域相关实体创建工具
 * @author Heyadong
 *
 */
public class AreaBeans {
	
	private static final Long CHINA = 23L;
	private static final Long SI_CHUAN = 175L;
	private static final Long CHENG_DU = 1507L;
	private static final Long QING_YANG_QU = 1509L;
	/**
	 * 成都市 CODE 1507
	 * @return
	 */
	public static Area createAreaCity(){
		
		return new Area(CHENG_DU);
	}
	/**
	 * 中国 CODE 23
	 * @return
	 */
	public static Area createAreaCountry(){
		return new Area(CHINA);
	}
	/**
	 * 四川省 CODE 175
	 * @return
	 */
	public static Area createAreaProvince(){
		return new Area(SI_CHUAN);
	}
	/**
	 * 青羊区 CODE 1509L
	 * @return
	 */
	public static Area createAreaDistrict(){
		
		return new Area(QING_YANG_QU);
	}
}
