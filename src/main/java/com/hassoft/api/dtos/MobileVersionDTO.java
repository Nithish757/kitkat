package com.hassoft.api.dtos;

public class MobileVersionDTO {
	
	private int id;
	private int ver_no;
	private String ver_prompt_msg;
	private String ver_desc;
	private boolean ver_status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVer_no() {
		return ver_no;
	}
	public void setVer_no(int ver_no) {
		this.ver_no = ver_no;
	}
	public String getVer_prompt_msg() {
		return ver_prompt_msg;
	}
	public void setVer_prompt_msg(String ver_prompt_msg) {
		this.ver_prompt_msg = ver_prompt_msg;
	}
	public String getVer_desc() {
		return ver_desc;
	}
	public void setVer_desc(String ver_desc) {
		this.ver_desc = ver_desc;
	}
	
	public boolean isVer_status() {
		return ver_status;
	}
	public void setVer_status(boolean ver_status) {
		this.ver_status = ver_status;
	}

	
}
