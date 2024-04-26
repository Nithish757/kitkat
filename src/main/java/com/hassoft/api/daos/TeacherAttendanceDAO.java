package com.hassoft.api.daos;

import java.util.List;

import com.hassoft.api.dtos.DistrictAttendanceDTO;
import com.hassoft.api.dtos.TeacherAttendanceDTO;
import com.hassoft.api.dtos.TeacherParamsDTO;

public interface TeacherAttendanceDAO {

	public List<TeacherAttendanceDTO> getTeacherList(String orgId, String date,String userId) throws Exception;
	public List<TeacherAttendanceDTO> getOthersList(String orgId, String date) throws Exception;
	public String saveTeacherWithPhotoAttendance(List<TeacherAttendanceDTO> teachers, TeacherParamsDTO params) throws Exception;
	public String saveTeacherAttendance(List<TeacherAttendanceDTO> teachers, TeacherParamsDTO params) throws Exception;
	public List<DistrictAttendanceDTO> getTeachersDistrictWiseSummeryAttendance(DistrictAttendanceDTO attend);
	public List<TeacherAttendanceDTO> getTeacherListFacex(String orgId, String date,String userId) throws Exception;
	public String saveTeacherWithPhotoFacex(List<TeacherAttendanceDTO> teachers, TeacherParamsDTO params) throws Exception;
	
	
}
