package com.winxuan.ec.front.controller.presentcard;

import java.util.regex.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.winxuan.ec.exception.PresentCardException;


/**
 * 
 * @author cast911
 *
 */
public class PresentCardForm {
	
	@NotEmpty(message="礼品卡不能为空!")
	private String card;
	@NotEmpty(message="密码不能为空!")
	private String password;
	private String newPassWord;

	public String getNewPassWord() {
		return newPassWord;
	}
	public void setNewPassWord(String newPassWord) {
		this.newPassWord = newPassWord;
	}
	public String getCard() throws PresentCardException {
		String regex = "[0-9]*";
		Pattern p = Pattern.compile(regex);
		if(!p.matcher(this.card).matches()){
			throw new PresentCardException("礼品只能输入数字");
		}
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


}
