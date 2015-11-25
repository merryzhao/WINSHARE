package com.winxuan.ec.task.support.quartz.invoke;

import java.io.Serializable;

import org.springframework.jdbc.core.JdbcTemplate;


/**
 * 
 * @author  HideHai
 * @version 1.0,2011-9-1
 */
public class MethodInvokingDataMap implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1801818000825744095L;


	private static final ThreadLocal<Object[]> CONTEXT_LOCAL = new ThreadLocal<Object[]>();

	private static JdbcTemplate jdbcTemplate;
	private static JdbcTemplate jdbcTemplateEcCore;
	private static JdbcTemplate jdbcTemplateMdm;
	private static JdbcTemplate jdbcTemplateErp;
	private static JdbcTemplate jdbcTemplateFZ;
	private static JdbcTemplate jdbcTemplateUnion;
	private static JdbcTemplate jdbcTemplateRobot;
	private static JdbcTemplate jdbcTemplateChannel;
	public static void register(){
		Object[] locals = new Object[8];
		locals[0] = jdbcTemplate;
		locals[1] = jdbcTemplateEcCore;
		locals[2] = jdbcTemplateMdm;
		locals[3] = jdbcTemplateErp;
		locals[4] = jdbcTemplateFZ;
		locals[5] = jdbcTemplateUnion;
		locals[6] = jdbcTemplateRobot;
		locals[7] = jdbcTemplateChannel;
		CONTEXT_LOCAL.set(locals);
	}

	


	public static JdbcTemplate getJdbcTemplate(){
		Object[] locals = (Object[]) CONTEXT_LOCAL.get();
		return locals==null?null:(JdbcTemplate) locals[0];
	}
	public static JdbcTemplate getJdbcTemplateEcCore(){
		Object[] locals = (Object[]) CONTEXT_LOCAL.get();
		return locals==null?null:(JdbcTemplate) locals[1];
	}
	public static JdbcTemplate getJdbcTemplateMdm(){
		Object[] locals = (Object[]) CONTEXT_LOCAL.get();
		return locals==null?null:(JdbcTemplate) locals[2];
	}
	public static JdbcTemplate getJdbcTemplateErp(){
		Object[] locals = (Object[]) CONTEXT_LOCAL.get();
		return locals==null?null:(JdbcTemplate) locals[3];
	}
	public static JdbcTemplate getJdbcTemplateFZ(){
		Object[] locals = (Object[]) CONTEXT_LOCAL.get();
		return locals==null?null:(JdbcTemplate) locals[4];
	}
	public static JdbcTemplate getJdbcTemplateUnion(){
		Object[] locals = (Object[]) CONTEXT_LOCAL.get();
		return locals==null?null:(JdbcTemplate) locals[5];
	}
	
	public static JdbcTemplate getJdbcTemplateRobot() {
		Object[] locals = (Object[]) CONTEXT_LOCAL.get();
		return locals==null?null:(JdbcTemplate) locals[6];
	}
	
	public static JdbcTemplate getJdbcTemplateChannel() {
		Object[] locals = (Object[]) CONTEXT_LOCAL.get();
		return locals==null?null:(JdbcTemplate) locals[7];
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public  void setJdbcTemplateEcCore(JdbcTemplate jdbcTemplateEcCore) {
		this.jdbcTemplateEcCore = jdbcTemplateEcCore;
	}
	public void setJdbcTemplateMdm(JdbcTemplate jdbcTemplateMdm) {
		this.jdbcTemplateMdm = jdbcTemplateMdm;
	}
	public void setJdbcTemplateErp(JdbcTemplate jdbcTemplateErp) {
		this.jdbcTemplateErp = jdbcTemplateErp;
	}
	public void setJdbcTemplateFZ(JdbcTemplate jdbcTemplateFZ) {
		this.jdbcTemplateFZ = jdbcTemplateFZ;
	}
	public void setJdbcTemplateUnion(JdbcTemplate jdbcTemplateUnion) {
		this.jdbcTemplateUnion = jdbcTemplateUnion;
	}
	
	public void setJdbcTemplateRobot(JdbcTemplate jdbcTemplateRobot) {
		this.jdbcTemplateRobot = jdbcTemplateRobot;
	}
	
	public void setJdbcTemplateChannel(JdbcTemplate jdbcTemplateChannel) {
		this.jdbcTemplateChannel = jdbcTemplateChannel;
	}

}
