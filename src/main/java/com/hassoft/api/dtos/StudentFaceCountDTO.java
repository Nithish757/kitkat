package com.hassoft.api.dtos;



public class StudentFaceCountDTO {
    private int faceCount;
    private StudentParamDTO params;

    public int getFaceCount() {
        return faceCount;
    }

    public void setFaceCount(int faceCount) {
        this.faceCount = faceCount;
    }

    public StudentParamDTO getParams() {
        return params;
    }

    public void setParams(StudentParamDTO params) {
        this.params = params;
    }

	public String getDate() {
		// TODO Auto-generated method stub
		return null;
	}
}
