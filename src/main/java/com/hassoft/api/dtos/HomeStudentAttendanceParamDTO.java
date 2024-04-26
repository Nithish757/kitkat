package com.hassoft.api.dtos;

import java.io.Serializable;
import java.util.List;

public class HomeStudentAttendanceParamDTO implements Serializable {

	private static final long serialVersionUID = 66897817575792935L;

	private HomeStudentAttendanceDTO students;
	private StudentParamDTO params;

	
	
	public HomeStudentAttendanceParamDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentParamDTO getParams() {
		return params;
	}

	public void setParams(StudentParamDTO params) {
		this.params = params;
	}
	public HomeStudentAttendanceDTO getStudents() {
		return students;
	}

	public void setStudents(HomeStudentAttendanceDTO students) {
		this.students = students;
	}

	

}
