/*
 * @(#)IpServiceImpl.java
 *
 */

package com.winxuan.ec.front.service.ip;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.winxuan.ec.front.model.ip.IP;

/**
 * description
 * @author  huangyixiang
 * @version 2013-1-5
 */
@Service("ipService")
public class IpServiceImpl implements IpService {
	
	private static final Log LOG = LogFactory.getLog(IpServiceImpl.class);
	private static final String IP_TXT_PATH = "ipList";
	private static final Map<Integer, List<IP[]>> IP_MAP = new HashMap<Integer, List<IP[]>>();;
	
	
	static{
		FileReader fr = null;
		BufferedReader br = null;
		try{
			String fileName = IpServiceImpl.class.getClassLoader()
					.getResource(IP_TXT_PATH).getFile();
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			String myreadline;
			while (br.ready()) {
				myreadline = br.readLine();
				if (null != myreadline && !"".equals(myreadline.trim())) {
					String[] s = myreadline.split("-");
					if(s == null || s.length != 2){
						LOG.error("Not the correct format ：" + myreadline);
						continue;
					}
					IP start = new IP(s[0].trim());
					IP end = new IP(s[1].trim());
					if(start.getSegment1() != end.getSegment1()){
						LOG.error("first segment is not the same ：" + myreadline);
					}
					IP[] ipSegment = new IP[]{start, end};
					
					List<IP[]> segmentList = IP_MAP.get(start.getSegment1());
					if(segmentList != null){
						segmentList.add(ipSegment);
					}
					else{
						List<IP[]> newSegmentList = new ArrayList<IP[]>();
						newSegmentList.add(ipSegment);
						IP_MAP.put(start.getSegment1(), newSegmentList);
					}
				}
				
			}
		}
		catch(IOException e){
			LOG.info(e.getMessage(), e);
		}
		finally{
				try {
					if(br != null){
						br.close();
					}
					if(fr != null){
						fr.close();
					}
				} catch (IOException e) {
					LOG.info(e.getMessage(), e);
				}
		}
	}
	
	public boolean isChinaIp(String ipStr){
		IP ip = new IP(ipStr);
		List<IP[]> segmentList = IP_MAP.get(ip.getSegment1());
		if(segmentList == null || segmentList.isEmpty()){
			LOG.info("not in ip list : " + ipStr);
			return false;
		}
		
		int index = binarySearch(segmentList, ip);
//		LOG.debug("index = "+ index + " ip: " + segmentList.get(index)[0]);
		if(ip.getSegment2() == segmentList.get(index)[0].getSegment2()){
			int next = index;
			while(next < segmentList.size() && next >= 0 &&
					segmentList.get(next) != null && 
					ip.getSegment2() == segmentList.get(next)[0].getSegment2()){
//				LOG.debug("比较 next index = " + next);
				IP[] ipSegment = segmentList.get(next);
				IP start = ipSegment[0];
				IP end = ipSegment[1];
				if(ip.beIncluding(start, end)){
					return true;
				}
				next++;
			}
			int prev = index - 1;
			while(prev < segmentList.size() && prev >= 0 &&
					segmentList.get(prev) != null && 
					ip.getSegment2() == segmentList.get(prev)[0].getSegment2()){
//				LOG.debug("比较 prev index = " + prev);
				IP[] ipSegment = segmentList.get(prev);
				IP start = ipSegment[0];
				IP end = ipSegment[1];
				if(ip.beIncluding(start, end)){
					return true;
				}
				prev--;
			}
		}
		else{
			if(index >= 0 && index < segmentList.size()){
//				LOG.debug("没找到，比较 当前 index = " + index);
				IP[] ipSegment = segmentList.get(index);
				IP start = ipSegment[0];
				IP end = ipSegment[1];
				if(ip.beIncluding(start, end)){
					return true;
				}
			}
			int prev = index - 1;
			if(prev >= 0 && prev < segmentList.size()){
//				LOG.debug("没找到，比较 prev index = " + prev);
				IP[] ipSegment = segmentList.get(prev);
				IP start = ipSegment[0];
				IP end = ipSegment[1];
				if(ip.beIncluding(start, end)){
					return true;
				}
			}
			int next = index + 1;
			if(next >= 0 && next < segmentList.size()){
//				LOG.debug("没找到，比较 next index = " + next);
				IP[] ipSegment = segmentList.get(next);
				IP start = ipSegment[0];
				IP end = ipSegment[1];
				if(ip.beIncluding(start, end)){
					return true;
				}
			}
		}
		/*for(IP[] ipSegment : segmentList){
			IP start = ipSegment[0];
			IP end = ipSegment[1];
			if(ip.beIncluding(start, end)){
				return true;
			}
		}*/
		return false;
	}
	
	public int binarySearch(List<IP[]> segmentList, IP desIP){
		int des = desIP.getSegment2();
		int middle = -1;
		int low = 0;
		int high = segmentList.size() - 1;
		while (low <= high) {
			middle = (low + high) / 2;
			int segment2 = segmentList.get(middle)[0].getSegment2();
			if (des == segment2) {
//				LOG.debug("找到对应的index");
				return middle;
			} else if (des < segment2) {
				high = middle - 1;
			} else {
				low = middle + 1;
			}
		}
		return middle;
	}
}
