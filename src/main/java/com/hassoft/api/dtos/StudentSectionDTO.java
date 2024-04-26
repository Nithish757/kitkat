package com.hassoft.api.dtos;

import java.io.Serializable;

public class StudentSectionDTO implements Serializable {

	private String sectionId;
	private String sectionTitle;
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getSectionTitle() {
		return sectionTitle;
	}
	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}
	
	
}
