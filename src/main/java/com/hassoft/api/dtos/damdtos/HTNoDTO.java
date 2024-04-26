package com.hassoft.api.dtos.damdtos;

public class HTNoDTO {

    private String htNo;
    private String cName;
    private String fName;
    private String gender;
    private String caste;
    private String mobileno;
    private String districtName;
    private String mandalName;
    private String type;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getcName() {
        return cName;
    }

    public String getHtNo() {
        return htNo;
    }

    public void setHtNo(String htNo) {
        this.htNo = htNo;
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

    @Override
    public String toString() {
        return "HTNoDTO{" +
                "htNo='" + htNo + '\'' +
                ", cName='" + cName + '\'' +
                ", fName='" + fName + '\'' +
                ", gender='" + gender + '\'' +
                ", caste='" + caste + '\'' +
                ", mobileno='" + mobileno + '\'' +
                ", districtName='" + districtName + '\'' +
                ", mandalName='" + mandalName + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
