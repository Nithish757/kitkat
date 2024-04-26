package com.hassoft.api.dtos;

import java.io.Serializable;

public class TeacherParamsDTO implements Serializable {

	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -269308223660632064L;
	private String orgId;
	 private String date;
	 private String userId;
	 private byte[] photo;
	 private float longitude;
	 private float latitude;
	 private String statusType;
	 private String faceXImageId;
	 
	 

	 
	public String getFaceXImageId() {
		return faceXImageId;
	}

	public void setFaceXImageId(String faceXImageId) {
		this.faceXImageId = faceXImageId;
	}

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
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

	@Override
	public String toString() {
		return "TeacherParamsDTO [orgId=" + orgId + ", date=" + date + ", userId=" + userId + "]";
	}

}
