package com.hassoft.api.dtos;

import java.io.Serializable;
import java.util.List;

public class TeacherAttendanceParamsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5896813168098690680L;
	private List<TeacherAttendanceDTO> teachers;
	private TeacherParamsDTO params;

	public List<TeacherAttendanceDTO> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<TeacherAttendanceDTO> teachers) {
		this.teachers = teachers;
	}

	public TeacherParamsDTO getParams() {
		return params;
	}

	public void setParams(TeacherParamsDTO params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "TeacherAttendanceParamsDTO [teachers=" + teachers + ", params=" + params + "]";
	}

}
