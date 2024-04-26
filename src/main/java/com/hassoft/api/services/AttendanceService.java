package com.hassoft.api.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hassoft.api.daos.StudentAttendanceDAO;
import com.hassoft.api.daos.TeacherAttendanceDAO;
import com.hassoft.api.dtos.DistrictAttendanceDTO;
import com.hassoft.api.dtos.HomeStudentAttendanceDTO;
import com.hassoft.api.dtos.StudentAttendDTO;
import com.hassoft.api.dtos.StudentFaceCountDTO;
import com.hassoft.api.dtos.StudentParamDTO;
import com.hassoft.api.dtos.StudentSectionDTO;
import com.hassoft.api.dtos.TeacherAttendanceDTO;
import com.hassoft.api.dtos.TeacherParamsDTO;

@Service(value = "attendanceService")
public class AttendanceService {

	final static Logger logger = LoggerFactory.getLogger(AttendanceService.class);

	@Autowired
	private StudentAttendanceDAO studentAttendanceDAO;

	@Autowired
	private TeacherAttendanceDAO teacherAttendanceDAO;

	public List<StudentSectionDTO> getSections(String orgId, HttpSession session) throws Exception {

		try {

			if (orgId.equalsIgnoreCase("")) {
				throw new Exception("School code should not be blank");
			}

			return studentAttendanceDAO.getStudentSectionByOrgId(orgId);

		} catch (Exception e) {
//			logger.debug("Error", e);
		}
		return null;
	}

	public List<StudentAttendDTO> getStudentListBySection(String orgId, String sectionId, String date)
			throws Exception {
		try {

			if (orgId.equalsIgnoreCase("")) {
				throw new Exception("School code should not be blank");
			}
			if (sectionId.equalsIgnoreCase("")) {
				throw new Exception("class and section should not be blank");
			}
			Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(date);

			String day = "01";
			Calendar cal = Calendar.getInstance();
			cal.setTime(date1);
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				throw new Exception("Invalid Date");
			int month = cal.get(cal.MONTH) + 1;
			int year = cal.get(cal.YEAR);
			Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(day + "-" + month + "-" + year);
			return studentAttendanceDAO.getStudentListBySection(Integer.parseInt(orgId), Long.parseLong(sectionId),
					date1);

		} catch (Exception e) {
	//		logger.debug("Error", e);
		}
		return null;
	}

	public List<StudentAttendDTO> getHomeStudentListBySection(String orgId, String sectionId, String date)
			throws Exception {
		try {

			if (orgId.equalsIgnoreCase("")) {
				throw new Exception("School code should not be blank");
			}
			if (sectionId.equalsIgnoreCase("")) {
				throw new Exception("class and section should not be blank");
			}
			Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(date);

			String day = "01";
			Calendar cal = Calendar.getInstance();
			cal.setTime(date1);
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				throw new Exception("Invalid Date");
			int month = cal.get(cal.MONTH) + 1;
			int year = cal.get(cal.YEAR);
			Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(day + "-" + month + "-" + year);
			return studentAttendanceDAO.getHomeStudentListBySection(Integer.parseInt(orgId), Long.parseLong(sectionId),
					date1);

		} catch (Exception e) {
		//	logger.debug("Error", e);
		}
		return null;
	}

	public String saveStudentAttendance(List<StudentAttendDTO> students, StudentParamDTO params) throws Exception {
		return studentAttendanceDAO.saveStudentAttendance(students, params);

	}

	public String saveHomeStudentAttendance(HomeStudentAttendanceDTO students, StudentParamDTO params) throws Exception {
		return studentAttendanceDAO.saveHomeStudentAttendance(students, params);

	}
	public List<HomeStudentAttendanceDTO> getHomeStudentAttendanceWithPhotoList(String orgId, String sectionId, String date)
			throws Exception {
		try {

			if (orgId.equalsIgnoreCase("")) {
				throw new Exception("School code should not be blank");
			}
			if (sectionId.equalsIgnoreCase("")) {
				throw new Exception("class and section should not be blank");
			}
			Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(date);

			String day = "01";
			Calendar cal = Calendar.getInstance();
			cal.setTime(date1);
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				throw new Exception("Invalid Date");
			int month = cal.get(cal.MONTH) + 1;
			int year = cal.get(cal.YEAR);
			Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(day + "-" + month + "-" + year);
			return studentAttendanceDAO.getHomeStudentAttendanceWithPhotoList(Integer.parseInt(orgId), Long.parseLong(sectionId),
					date1);
		} catch (Exception e) {
			//logger.debug("Error", e);
		}
		return null;
	}
	public List<TeacherAttendanceDTO> getTeacherList(String orgId, String date,String userId) throws Exception {
		return teacherAttendanceDAO.getTeacherList(orgId, date,userId);
	}
	
	public List<TeacherAttendanceDTO> getTeacherListFacex(String orgId, String date,String userId) throws Exception {
		return teacherAttendanceDAO.getTeacherListFacex(orgId, date,userId);
	}
	
	
	public List<TeacherAttendanceDTO> getOthersList(String orgId, String date) throws Exception {
		return teacherAttendanceDAO.getOthersList(orgId, date);
	}
	public String SaveTeacherAttendance(List<TeacherAttendanceDTO> teachers, TeacherParamsDTO params) throws Exception {
		return teacherAttendanceDAO.saveTeacherAttendance(teachers, params);
	}
	public String SaveTeacherWithPhotoAttendance(List<TeacherAttendanceDTO> teachers, TeacherParamsDTO params) throws Exception {
		return teacherAttendanceDAO.saveTeacherWithPhotoAttendance(teachers, params);
	}
	public List<DistrictAttendanceDTO> getTeachersDistrictWiseSummeryAttendance(DistrictAttendanceDTO attend) throws Exception{
		return teacherAttendanceDAO.getTeachersDistrictWiseSummeryAttendance(attend);
	}
	
	public List<DistrictAttendanceDTO> getStudentSchoolWiseSummeryAttendance(DistrictAttendanceDTO attend) throws Exception{
		return studentAttendanceDAO.getStudentSchoolWiseSummeryAttendance(attend);
	}
	public List<DistrictAttendanceDTO> getStudentDistrictWiseSummeryAttendance(DistrictAttendanceDTO attend) throws Exception{
		return studentAttendanceDAO.getStudentDistrictWiseSummeryAttendance(attend);
	}

	public String saveTeacherWithPhotoFacex(List<TeacherAttendanceDTO> teachers, TeacherParamsDTO params) throws Exception {
		return teacherAttendanceDAO.saveTeacherWithPhotoFacex(teachers, params);
	}
	
	
	public String saveStudentFaceCount(int  studentFaceCount, StudentFaceCountDTO params) throws Exception {
		return studentAttendanceDAO.saveStudentFaceCount(studentFaceCount, params);

	}

	
}
