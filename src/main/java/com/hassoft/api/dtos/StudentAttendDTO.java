package com.hassoft.api.dtos;

import java.io.Serializable;

public class StudentAttendDTO implements Serializable {

	private String userId;
	private String stdFirstName;
	private String stdMiddleName;
	private String stdLastName;
	private String stdattendStatus;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStdFirstName() {
		return stdFirstName;
	}

	public void setStdFirstName(String stdFirstName) {
		this.stdFirstName = stdFirstName;
	}

	public String getStdMiddleName() {
		return stdMiddleName;
	}

	public void setStdMiddleName(String stdMiddleName) {
		this.stdMiddleName = stdMiddleName;
	}

	public String getStdLastName() {
		return stdLastName;
	}

	public void setStdLastName(String stdLastName) {
		this.stdLastName = stdLastName;
	}

	public String getStdattendStatus() {
		return stdattendStatus;
	}

	public void setStdattendStatus(String stdattendStatus) {
		this.stdattendStatus = stdattendStatus;
	}


}
