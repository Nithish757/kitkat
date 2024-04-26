package com.hassoft.api.dtos;

import java.util.Arrays;

public class HomeStudentAttendanceDTO {
	private String userId;
	private String stdFirstName;
	private String stdMiddleName;
	private String stdLastName;
	private String stdattendStatus;
	private float longitude;
	private float latitude;
	private String photo_path;
	private byte[] photo;
	

	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
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
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public String getPhoto_path() {
		return photo_path;
	}
	public void setPhoto_path(String photo_path) {
		this.photo_path = photo_path;
	}
	
	

}
