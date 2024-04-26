package com.hassoft.api.dtos;

import java.io.Serializable;

public class UserLoginDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String userPassword;
	private int version;
	private String district;
	private String orgid;
	private String userId;
	private String firstName;
	private String type;

	 private float longitude;
	 private float latitude;
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

		

	
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "UserLoginDTO [userName=" + userName + ", userPassword=" + userPassword + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", getLongitude()=" + getLongitude() + ", getLatitude()=" + getLatitude()
				+ ", getDistrict()=" + getDistrict() + ", getOrgid()=" + getOrgid() + ", getUserId()=" + getUserId()
				+ ", getUserName()=" + getUserName() + ", getUserPassword()=" + getUserPassword() + ", getVersion()="
				+ getVersion() + ", getFirstName()=" + getFirstName() + ", getType()=" + getType() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	

}
