package com.winxuan.ec.task.support.utils;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * xml解析处理
 * @author luosh
 *
 */
public class MyHandler extends DefaultHandler{
	private static final Log LOG = LogFactory.getLog(MyHandler.class);
	private StringBuffer currentValue;
	private StringBuffer endNodePath ;
	private StringBuffer startNodePath ;
	private StringBuffer addNodePath ;
	private Stack<Object> stack ;
	private Map<String,String> attmap ;
	
	public MyHandler(){
		super();
		currentValue = new StringBuffer();
		addNodePath = new StringBuffer("/add");
		endNodePath = new StringBuffer("/end");
		startNodePath = new StringBuffer("/start");
		ClassMethodUtil.setMethodMap();
		stack = new Stack<Object>();
	}

	@Override
	public void startElement(String uri,String localName,String qName,Attributes attributes) throws SAXException {
		endNodePath.append("/"+qName);
		startNodePath.append("/"+qName);		
		addNodePath.append("/"+qName);		
		String start = startNodePath.toString();
		try {
			if(ClassMethodUtil.getClassName(start)==null){
				return;
			}
			Method method = ClassMethodUtil.getMethod(start);
			if(method==null){
				Object obj = ClassMethodUtil.getClass(start).newInstance();
				stack.push(obj);
			}else if(method!=null){
				Object obj = ClassMethodUtil.getMethod(start).invoke(stack.peek());
				stack.push(obj);
			}
		} catch (Exception e) {
			LOG.info(e.getMessage());
			return;
		}
		if(attributes.getLength()>0){
			attmap = new LinkedHashMap<String, String>();
		}else{attmap=null;}
		for (int i = 0; i < attributes.getLength(); i++) {		
			attmap.put(attributes.getLocalName(i), attributes.getValue(i));
		}
	}
	@Override
	public void endElement(String uri, String localName, String qName)
	throws SAXException {
		String end = endNodePath.toString();
		String add = addNodePath.toString();
		try{
			if(ClassMethodUtil.getString(end)==null&&ClassMethodUtil.getString(add)==null){
				return;
			}
			Method method=null;
			//添加属性
			if(attmap!=null){
				Set<String> key = attmap.keySet();
				for (Iterator it = key.iterator(); it.hasNext();) {
		            String s = (String) it.next();
		            String endTemp = end+"/"+s;
					method = ClassMethodUtil.getMethod(endTemp, String.class);
					if(method!=null){
						method.invoke(stack.peek(),attmap.get(s));
					}
		        }
			}
			method = ClassMethodUtil.getMethod(end, String.class);
			if(method!=null){
				method.invoke(stack.peek(),currentValue.toString());
			}
			if(ClassMethodUtil.getString(add)!=null){
				Object obj = stack.pop();
					//List<Object> objList = (List<Object>)stack.peek();
				try{
					Method methodAdd = stack.peek().getClass().getMethod("add", Object.class);//getClass获取对象是不是运行是的类
					methodAdd.invoke(stack.peek(), obj);
					return;
				}catch (NoSuchMethodException e) {
					LOG.info(e.getMessage());
					return;
				}
			}
			if (method == null) {
				method = ClassMethodUtil.getMethod(end, List.class);
				List<Object> objList = (List<Object>) stack.pop();
				method.invoke(stack.peek(), objList);
				return;
			}
			
		}catch (Exception e) {
			LOG.info(e.getMessage());
			return;
		}finally{
			currentValue = new StringBuffer();
			endNodePath.replace(endNodePath.lastIndexOf("/" + qName), endNodePath
					.length(), "");
			startNodePath.replace(startNodePath.lastIndexOf("/" + qName), startNodePath.length(),"");
			addNodePath.replace(addNodePath.lastIndexOf("/" + qName), addNodePath.length(),"");
		}
	}
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException{
		currentValue.append(new String(ch,start,length).trim().toString());
	}
	
	public Map<String, String> getAttr(Attributes attributes) {
		if(attributes==null){
			return null;
		}
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < attributes.getLength(); i++) {
			map.put(attributes.getLocalName(i), attributes.getValue(i));
		}
		return map;
	}

	public Stack<Object> getStack() {
		return stack;
	}

	public void setStack(Stack<Object> stack) {
		this.stack = stack;
	}
}
