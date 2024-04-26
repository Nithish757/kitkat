package com.hassoft.api.dtos;

import java.io.Serializable;
import java.util.Arrays;

public class TeacherAttendanceDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5167488779095772809L;
	private String userId;
	private String stdFirstName;
	private String stdMiddleName;
	private String stdLastName;
	private String stdattendStatus;
	private String role;
	private byte[] photo;
	private float longitude;
	private float latitude;
	
	private String statusType;
    public String getStatusType() {
		return statusType;
	}
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	public String getMnStatus() {
		return mnStatus;
	}
	public void setMnStatus(String mnStatus) {
		this.mnStatus = mnStatus;
	}
	public String getFnStatus() {
		return fnStatus;
	}
	public void setFnStatus(String fnStatus) {
		this.fnStatus = fnStatus;
	}
	public String getMnLat() {
		return mnLat;
	}
	public void setMnLat(String mnLat) {
		this.mnLat = mnLat;
	}
	public String getMnLng() {
		return mnLng;
	}
	public void setMnLng(String mnLng) {
		this.mnLng = mnLng;
	}
	public String getFnLat() {
		return fnLat;
	}
	public void setFnLat(String fnLat) {
		this.fnLat = fnLat;
	}
	public String getFnLng() {
		return fnLng;
	}
	public void setFnLng(String fnLng) {
		this.fnLng = fnLng;
	}
	public String getMnStatusOn() {
		return mnStatusOn;
	}
	public void setMnStatusOn(String mnStatusOn) {
		this.mnStatusOn = mnStatusOn;
	}
	public String getFnStatusOn() {
		return fnStatusOn;
	}
	public void setFnStatusOn(String fnStatusOn) {
		this.fnStatusOn = fnStatusOn;
	}
	public int getHideShowMn() {
		return hideShowMn;
	}
	public void setHideShowMn(int hideShowMn) {
		this.hideShowMn = hideShowMn;
	}
	public int getHideShowFn() {
		return hideShowFn;
	}
	public void setHideShowFn(int hideShowFn) {
		this.hideShowFn = hideShowFn;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private String mnStatus;
    private String fnStatus;
    private String mnLat;
    private String mnLng;
    private String fnLat;
    private String fnLng;
    private String mnStatusOn;
    private String fnStatusOn;
    private int hideShowMn;
    private int hideShowFn;
    
	
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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
	@Override
	public String toString() {
		return "TeacherAttendanceDTO [userId=" + userId + ", stdFirstName=" + stdFirstName + ", stdMiddleName="
				+ stdMiddleName + ", stdLastName=" + stdLastName + ", stdattendStatus=" + stdattendStatus + ", role="
				+ role + ", photo=" + Arrays.toString(photo) + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", statusType=" + statusType + ", mnStatus=" + mnStatus + ", fnStatus=" + fnStatus + ", mnLat="
				+ mnLat + ", mnLng=" + mnLng + ", fnLat=" + fnLat + ", fnLng=" + fnLng + ", mnStatusOn=" + mnStatusOn
				+ ", fnStatusOn=" + fnStatusOn + ", hideShowMn=" + hideShowMn + ", hideShowFn=" + hideShowFn
				+ ", getStatusType()=" + getStatusType() + ", getMnStatus()=" + getMnStatus() + ", getFnStatus()="
				+ getFnStatus() + ", getMnLat()=" + getMnLat() + ", getMnLng()=" + getMnLng() + ", getFnLat()="
				+ getFnLat() + ", getFnLng()=" + getFnLng() + ", getMnStatusOn()=" + getMnStatusOn()
				+ ", getFnStatusOn()=" + getFnStatusOn() + ", getHideShowMn()=" + getHideShowMn() + ", getHideShowFn()="
				+ getHideShowFn() + ", getPhoto()=" + Arrays.toString(getPhoto()) + ", getRole()=" + getRole()
				+ ", getUserId()=" + getUserId() + ", getStdFirstName()=" + getStdFirstName() + ", getStdMiddleName()="
				+ getStdMiddleName() + ", getStdLastName()=" + getStdLastName() + ", getStdattendStatus()="
				+ getStdattendStatus() + ", getLongitude()=" + getLongitude() + ", getLatitude()=" + getLatitude()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	
}
