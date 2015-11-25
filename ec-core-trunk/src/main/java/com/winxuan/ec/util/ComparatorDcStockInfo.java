package com.winxuan.ec.util;

import java.util.Comparator;

/**
 * 对仓库集货排序
 * @author heshuai
 *
 */
public class ComparatorDcStockInfo implements Comparator<DcStockInfo> {
		
	private static ComparatorDcStockInfo instance=null;
	 private ComparatorDcStockInfo(){}
	
	public static ComparatorDcStockInfo getInstance() {
	      if(instance==null){
	      instance=new ComparatorDcStockInfo();
	      }
	      return instance;
	  }
	 
	 
	@Override
	public int compare(DcStockInfo arg0, DcStockInfo arg1) {
		 //首先比较满足率，如果满足率相同，则比较区域优先级
		  int flag=arg1.getStock().compareTo(arg0.getStock());
		  if(flag==0){
		   return arg0.getLever().compareTo(arg1.getLever());
		  }else{
		   return flag;
		  }  
	}	
}
