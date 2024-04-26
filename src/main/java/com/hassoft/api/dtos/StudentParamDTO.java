package com.hassoft.api.dtos;

import java.io.Serializable;

public class StudentParamDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8712103947342433022L;
	private String orgId;
	private String sectionId;
	private String date;
	private String userId;
	private String faceCount;
	private byte[] photo;
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

	//private String sectionId;
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	
	public String getFacecount() {
		return faceCount;
	}
	public void setFacecount(String facecount) {
		this.faceCount = faceCount;
	}
	
	
	
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
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
	
	
}
