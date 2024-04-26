package com.hassoft.api.dtos;

import java.io.Serializable;
import java.util.List;

public class StudentAttendanceParamDTO implements Serializable {

	private static final long serialVersionUID = 66897817575792935L;

	private List<StudentAttendDTO> students;
	private StudentParamDTO params;

	public List<StudentAttendDTO> getStudents() {
		return students;
	}

	public void setStudents(List<StudentAttendDTO> students) {
		this.students = students;
	}

	public StudentParamDTO getParams() {
		return params;
	}

	public void setParams(StudentParamDTO params) {
		this.params = params;
	}


}
