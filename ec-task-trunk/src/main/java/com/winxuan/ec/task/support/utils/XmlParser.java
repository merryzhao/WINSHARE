package com.winxuan.ec.task.support.utils;

import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

/**
 * xml解析
 * @author Administrator
 *
 */
public class XmlParser {
	public static Object parse(File file) throws ParserConfigurationException, SAXException, IOException{		
		SAXParserFactory  saxParerfactory = SAXParserFactory.newInstance();
		SAXParser parser = saxParerfactory.newSAXParser();
		MyHandler myhandler = new MyHandler();
		parser.parse(file,myhandler);
		Stack stack = myhandler.getStack();
		return stack.pop();
	}
}
