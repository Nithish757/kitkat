package com.hassoft.api.dtos.damdtos;


public class SchoolPrefDTO {

    private String districtName;
    private int classId;

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "SchoolPrefDTO{" +
                "districtName='" + districtName + '\'' +
                ", classId=" + classId +
                '}';
    }
}
