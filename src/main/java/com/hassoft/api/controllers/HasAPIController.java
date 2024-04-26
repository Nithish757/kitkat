package com.hassoft.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hassoft.api.daos.LoginDAO;
import com.hassoft.api.daos.impl.LoginDAOImpl;
import com.hassoft.api.dtos.DistrictAttendanceDTO;
import com.hassoft.api.dtos.HomeStudentAttendanceDTO;
import com.hassoft.api.dtos.HomeStudentAttendanceParamDTO;
import com.hassoft.api.dtos.StudentAttendDTO;
import com.hassoft.api.dtos.StudentAttendanceParamDTO;
import com.hassoft.api.dtos.StudentFaceCountDTO;
import com.hassoft.api.dtos.StudentParamDTO;
import com.hassoft.api.dtos.StudentSectionDTO;
import com.hassoft.api.dtos.TeacherAttendanceDTO;
import com.hassoft.api.dtos.TeacherAttendanceParamsDTO;
import com.hassoft.api.dtos.TeacherParamsDTO;
import com.hassoft.api.dtos.UserLoginDTO;

import com.hassoft.api.services.AttendanceService;
import com.hassoft.api.services.LoginService;

@RestController
public class HasAPIController {
	
	@Autowired
	private ConfigurableApplicationContext context;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome() {// Welcome page, non-rest
		return "SAMS WEBSERVICE STARTED.";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_ATOM_XML_VALUE })
	public UserLoginDTO login(@RequestBody UserLoginDTO user, HttpServletRequest request, HttpServletResponse response)
			throws Exception {// REST Endpoint.
		LoginService loginService = (LoginService) context.getBean("loginService");
		//System.out.println("kriiiii"+user.getOrgid());
		return loginService.getLoginValidate(user, request.getSession());

	}

	@RequestMapping(value = "/getSections", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_ATOM_XML_VALUE })
	public List<StudentSectionDTO> getSections(@RequestBody String orgId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();

		// convert JSON string to Map
		map = mapper.readValue(orgId, new TypeReference<Map<String, String>>(){});
		AttendanceService attendanceService = (AttendanceService) context.getBean("attendanceService");
		List<StudentSectionDTO> sections = attendanceService.getSections(map.get("orgId").toString(), request.getSession());
		return sections;

	}

	@RequestMapping(value = "/getStudentList", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
	public List<StudentAttendDTO> getStudentList(@RequestBody StudentParamDTO attendance, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// REST Endpoint.

		AttendanceService attendanceService = (AttendanceService) context.getBean("attendanceService");
		return attendanceService.getStudentListBySection(attendance.getOrgId(), attendance.getSectionId(),
				attendance.getDate());
	}

	@RequestMapping(value = "/getHomeStudentList", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
	public List<StudentAttendDTO> getHomeStudentList(@RequestBody StudentParamDTO attendance, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// REST Endpoint.
		AttendanceService attendanceService = (AttendanceService) context.getBean("attendanceService");
		return attendanceService.getHomeStudentListBySection(attendance.getOrgId(), attendance.getSectionId(),
				attendance.getDate());
	}

	@RequestMapping(value = "/getHomeStudentWithPhotoList", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
	public List<HomeStudentAttendanceDTO> getHomeStudentWithPhotoList(@RequestBody StudentParamDTO attendance, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// REST Endpoint.

		AttendanceService attendanceService = (AttendanceService) context.getBean("attendanceService");
		return attendanceService.getHomeStudentAttendanceWithPhotoList(attendance.getOrgId(), attendance.getSectionId(),attendance.getDate());
	}// // on
	
	@RequestMapping(value = "/saveStudentAttendance", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
	public String saveStudentAttendance(@RequestBody StudentAttendanceParamDTO attendance, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// REST Endpoint.

		AttendanceService attendanceService = (AttendanceService) context.getBean("attendanceService");
		return attendanceService.saveStudentAttendance(attendance.getStudents(), attendance.getParams());

	}

	@RequestMapping(value = "/saveHomeStudentAttendance", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
	public String saveHomeStudentAttendance(@RequestBody List<HomeStudentAttendanceParamDTO> attendance, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// REST Endpoint.
		AttendanceService attendanceService = (AttendanceService) context.getBean("attendanceService");
		return attendanceService.saveHomeStudentAttendance(attendance.get(0).getStudents(), attendance.get(0).getParams());

	}
	
	
	@RequestMapping(value = "/saveStudentFaceCount", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
	public String saveStudentFaceCount(@RequestBody StudentFaceCountDTO attendance, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// REST Endpoint.
		AttendanceService attendanceService = (AttendanceService) context.getBean("attendanceService");
		System.out.println("krishnannn"+request.getParameterNames().toString());
		return attendanceService.saveStudentFaceCount(attendance.getFaceCount(), attendance);

	}
	

	/*@PostMapping("/saveHomeStudentAttendance")
    public ResponseEntity<String> saveHomeStudentAttendance(@RequestBody HomeStudentAttendanceParamDTO attendance){
		System.out.println("api control"+attendance.toString());
		AttendanceService attendanceService = (AttendanceService) context.getBean("attendanceService");
		
		return new ResponseEntity(attendanceService.saveHomeStudentAttendance(attendance.getStudents(), attendance.getParams()),HttpStatus.OK);

    }*/
	@RequestMapping(value = "/getTeacherList", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
	public List<TeacherAttendanceDTO> getTeacherList(@RequestBody TeacherParamsDTO teacher, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// REST Endpoint.

		AttendanceService attendanceService = (AttendanceService) context.getBean("attendanceService");
		return attendanceService.getTeacherList(teacher.getOrgId(), teacher.getDate(),teacher.getUserId());
		
	}
	@RequestMapping(value = "/getOthersList", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
	public List<TeacherAttendanceDTO> getOthersList(@RequestBody TeacherParamsDTO teacher, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// REST Endpoint.

		AttendanceService attendanceService = (AttendanceService) context.getBean("attendanceService");
		return attendanceService.getOthersList(teacher.getOrgId(), teacher.getDate());
	}

	@RequestMapping(value = "/saveTeacherAttendance", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
	public String saveTeacherAttendance(@RequestBody TeacherAttendanceParamsDTO attendance, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// REST Endpoint.

		AttendanceService attendanceService = (AttendanceService) context.getBean("attendanceService");
		return attendanceService.SaveTeacherAttendance(attendance.getTeachers(), attendance.getParams());

	}
	@RequestMapping(value = "/saveTeacherWithPhotoAttendance", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
	public String saveTeacherWithPhotoAttendance(@RequestBody TeacherAttendanceParamsDTO attendance, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// REST Endpoint.
		System.out.println("saveTeacherWithPhotoAttendance");
		AttendanceService attendanceService = (AttendanceService) context.getBean("attendanceService");
		return attendanceService.SaveTeacherWithPhotoAttendance(attendance.getTeachers(), attendance.getParams());

	}

	@RequestMapping(value = "/districtWiseTeacherAttendance", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
	public List<DistrictAttendanceDTO> districtWiseTeacherAttendance(@RequestBody DistrictAttendanceDTO attendance, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// REST Endpoint.

		AttendanceService attendanceService = (AttendanceService) context.getBean("attendanceService");
		return attendanceService.getTeachersDistrictWiseSummeryAttendance(attendance);

	}
	@RequestMapping(value = "/districtWiseStudentAttendance", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
	public List<DistrictAttendanceDTO> districtWiseStudentAttendance(@RequestBody DistrictAttendanceDTO attendance, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// REST Endpoint.

		AttendanceService attendanceService = (AttendanceService) context.getBean("attendanceService");
		return attendanceService.getStudentDistrictWiseSummeryAttendance(attendance);

	}
	@RequestMapping(value = "/studentSchoolWiseSummeryAttendance", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
	public List<DistrictAttendanceDTO> organizationWiseStudentAttendance(@RequestBody DistrictAttendanceDTO attendance, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// REST Endpoint.

		AttendanceService attendanceService = (AttendanceService) context.getBean("attendanceService");
		return attendanceService.getStudentSchoolWiseSummeryAttendance(attendance);

	}
	
	@RequestMapping(value = "/saveTeacherWithPhotoFacex", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
	public String saveTeacherWithPhotoFacex(@RequestBody TeacherAttendanceParamsDTO attendance, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// REST Endpoint.
		
		AttendanceService attendanceService = (AttendanceService) context.getBean("attendanceService");
		System.out.println("saveTeacherWithPhotoFacex ---" + attendance.getParams().getFaceXImageId());
		return attendanceService.saveTeacherWithPhotoFacex(attendance.getTeachers(), attendance.getParams());

	}
	
	@RequestMapping(value = "/getTeacherListFacex", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
	public List<TeacherAttendanceDTO> getTeacherListFacex(@RequestBody TeacherParamsDTO teacher, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// REST Endpoint.

		AttendanceService attendanceService = (AttendanceService) context.getBean("attendanceService");
		return attendanceService.getTeacherListFacex(teacher.getOrgId(), teacher.getDate(),teacher.getUserId());
		
	}
}
