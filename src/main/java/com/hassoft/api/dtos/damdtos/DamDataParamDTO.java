package com.hassoft.api.dtos.damdtos;

import java.util.Arrays;

public class DamDataParamDTO {

    private String htNo;
    private String cName;
    private String fName;
    private String gender;
    private String caste;
    private String mobileno;
    private String districtName;
    private String mandalName;
    private String type;
    private String exam;
    private byte[] photo;
    private String prefSchool;
    private String prefDistrict;
    private String refType;
    private String ref;

    private String appliedClass;

    public String getAppliedClass() {
        return appliedClass;
    }

    public void setAppliedClass(String appliedClass) {
        this.appliedClass = appliedClass;
    }

    public String getHtNo() {
        return htNo;
    }

    public void setHtNo(String htNo) {
        this.htNo = htNo;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getMandalName() {
        return mandalName;
    }

    public void setMandalName(String mandalName) {
        this.mandalName = mandalName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPrefSchool() {
        return prefSchool;
    }

    public void setPrefSchool(String prefSchool) {
        this.prefSchool = prefSchool;
    }

    public String getPrefDistrict() {
        return prefDistrict;
    }

    public void setPrefDistrict(String prefDistrict) {
        this.prefDistrict = prefDistrict;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public String toString() {
        return "DamDataParamDTO{" +
                "htNo='" + htNo + '\'' +
                ", cName='" + cName + '\'' +
                ", fName='" + fName + '\'' +
                ", gender='" + gender + '\'' +
                ", caste='" + caste + '\'' +
                ", mobileno='" + mobileno + '\'' +
                ", districtName='" + districtName + '\'' +
                ", mandalName='" + mandalName + '\'' +
                ", type='" + type + '\'' +
                ", exam='" + exam + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", prefSchool='" + prefSchool + '\'' +
                ", prefDistrict='" + prefDistrict + '\'' +
                ", refType='" + refType + '\'' +
                ", ref='" + ref + '\'' +
                ", appliedClass='" + appliedClass + '\'' +
                '}';
    }
}
