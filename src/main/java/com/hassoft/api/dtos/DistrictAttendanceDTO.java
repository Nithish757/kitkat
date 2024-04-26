package com.hassoft.api.dtos;

import java.io.Serializable;

public class DistrictAttendanceDTO  implements Serializable{

	private String schoolName;
	private String schoolId;
	private int totalPresent;
	private int totalAbsent;
	private String districtName;
	private String date;
	private String classname;
	
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public int getTotalPresent() {
		return totalPresent;
	}
	public void setTotalPresent(int totalPresent) {
		this.totalPresent = totalPresent;
	}
	public int getTotalAbsent() {
		return totalAbsent;
	}
	public void setTotalAbsent(int totalAbsent) {
		this.totalAbsent = totalAbsent;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	@Override
	public String toString() {
		return "DistrictAttendanceDTO [schoolName=" + schoolName + ", schoolId=" + schoolId + ", totalPresent="
				+ totalPresent + ", totalAbsent=" + totalAbsent + ", districtName=" + districtName + ", date=" + date
				+ "]";
	}
	
	
}
