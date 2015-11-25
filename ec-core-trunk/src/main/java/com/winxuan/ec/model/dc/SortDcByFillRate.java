package com.winxuan.ec.model.dc;

import java.util.Comparator;


/**
 ******************************* 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-7-29 下午2:02:17  --!
 * @description:按照覆盖率对DC排序
 ********************************
 */
public class SortDcByFillRate implements Comparator<DcRule>{
	@Override
	public int compare(DcRule o1, DcRule o2) {
		if(o1.getFillRate()>o2.getFillRate()){
			return 1;
		}
		if(o1.getFillRate()<o2.getFillRate()){
			return -1;
		}
		return 0;
	}

}
