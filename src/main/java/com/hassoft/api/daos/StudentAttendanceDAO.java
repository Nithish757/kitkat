package com.hassoft.api.daos;


import java.util.Date;
import java.util.List;

import com.hassoft.api.dtos.DistrictAttendanceDTO;
import com.hassoft.api.dtos.HomeStudentAttendanceDTO;
import com.hassoft.api.dtos.StudentAttendDTO;
import com.hassoft.api.dtos.StudentFaceCountDTO;
import com.hassoft.api.dtos.StudentParamDTO;
import com.hassoft.api.dtos.StudentSectionDTO;

public interface StudentAttendanceDAO {

	public List<StudentAttendDTO> getStudentListBySection(int sectionid,long orgId,Date date);
	public List<StudentAttendDTO> getHomeStudentListBySection(int sectionid,long orgId,Date date);
	public List<StudentSectionDTO> getStudentSectionByOrgId(String orgId);
	public String saveStudentAttendance(List<StudentAttendDTO> students,StudentParamDTO params);
	public String saveHomeStudentAttendance(HomeStudentAttendanceDTO students,StudentParamDTO params);
	public List<HomeStudentAttendanceDTO> getHomeStudentAttendanceWithPhotoList(int sectionid, long orgId, Date date) throws Exception;
	public List<DistrictAttendanceDTO> getStudentSchoolWiseSummeryAttendance(DistrictAttendanceDTO attend);
	public List<DistrictAttendanceDTO> getStudentDistrictWiseSummeryAttendance(DistrictAttendanceDTO attend);
	public String saveStudentFaceCount(int studentFaceCount, StudentFaceCountDTO params);
}
