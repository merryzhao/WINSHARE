package com.winxuan.ec.admin.controller.authority;

import java.io.Serializable;

/**
 * 用户密码修改
 * @author sunflower
 *
 */
public class ModifyUserForm implements Serializable{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6620602285443583066L;
	private String oldPassword;
	private String newPassword;
	private String confirmNewPassword;
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}


}
