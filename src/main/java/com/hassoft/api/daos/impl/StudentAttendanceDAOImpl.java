package com.hassoft.api.daos.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hassoft.api.daos.StudentAttendanceDAO;
import com.hassoft.api.dtos.DistrictAttendanceDTO;
import com.hassoft.api.dtos.HomeStudentAttendanceDTO;
import com.hassoft.api.dtos.StudentAttendDTO;
import com.hassoft.api.dtos.StudentFaceCountDTO;
import com.hassoft.api.dtos.StudentParamDTO;
import com.hassoft.api.dtos.StudentSectionDTO;
import com.hassoft.api.dtos.TeacherAttendanceDTO;
import com.hassoft.api.utils.JdbcConnectionManager;

@Repository
@Qualifier("studentAttendanceDAO")
public class StudentAttendanceDAOImpl implements StudentAttendanceDAO, Serializable {

	private static final long serialVersionUID = -4689109735895501150L;
	final static Logger logger = LoggerFactory.getLogger(StudentAttendanceDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<StudentAttendDTO> getStudentListBySection(int sectionid, long orgId, Date date) {

		String query = "SELECT o_usr_id,IFNULL(o_usr_fn,'') as o_usr_fn,IFNULL(o_usr_mn,'') as o_usr_mn,IFNULL(o_usr_ln,'') as o_usr_ln,o_usrAtndns_d? as status FROM org_studentassignedclass  INNER JOIN org_users ON (o_stdasgncls_usrid=o_usr_id) INNER JOIN org_assignedclasssections ON(o_asgnclsSec_id=o_stdasgncls_secid) INNER JOIN org_assignedclasses ON(o_asgnclssec_clsid=o_asgncls_id) LEFT JOIN org_userattendance ON o_usrAtndns_usrId=o_usr_id AND o_usrAtndns_date=? WHERE o_stdasgncls_status='Y' AND o_usr_activestatus='Y' AND o_stdasgncls_secId=? AND o_usr_orgid=? and o_usr_homestudy is false ORDER BY o_usr_rollno,o_usr_id  ";
		List<StudentAttendDTO> students = new ArrayList<StudentAttendDTO>();
		Connection conn = null;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat("d");
		String s[] =  sdf.format(date).split("-");
		
		String date1 = s[0] + "-" + Integer.parseInt(s[1]) + "-01";
		
		try {
			conn = JdbcConnectionManager.getConnection(jdbcTemplate);
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, Integer.parseInt(s[2]));			
			pst.setString(2, date1);
			pst.setLong(3, orgId);
			pst.setLong(4, sectionid);
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				StudentAttendDTO student = new StudentAttendDTO();
				student.setUserId(rs.getString("o_usr_id"));
				student.setStdFirstName(rs.getString("o_usr_fn"));
				student.setStdMiddleName(rs.getString("o_usr_mn"));
				student.setStdLastName(rs.getString("o_usr_ln"));
				student.setStdattendStatus(rs.getString("status"));
				students.add(student);
			}
			JdbcConnectionManager.CloseResultSet(rs);
			JdbcConnectionManager.ClosePreparedStatement(pst);
			return students;
		} catch (Exception e) {
			logger.debug("Error while reading class roles", e);

		} finally {
			JdbcConnectionManager.Close(conn);
		}
		return null;

	}

	@Override
	public List<StudentAttendDTO> getHomeStudentListBySection(int sectionid, long orgId, Date date) {

		String query = "SELECT o_usr_id,IFNULL(o_usr_fn,'') as o_usr_fn,IFNULL(o_usr_mn,'') as o_usr_mn,IFNULL(o_usr_ln,'') as o_usr_ln,o_usrAtndns_d? as status FROM org_studentassignedclass  INNER JOIN org_users ON (o_stdasgncls_usrid=o_usr_id) INNER JOIN org_assignedclasssections ON(o_asgnclsSec_id=o_stdasgncls_secid) INNER JOIN org_assignedclasses ON(o_asgnclssec_clsid=o_asgncls_id) right outer JOIN org_userattendance ON o_usrAtndns_usrId=o_usr_id AND o_usrAtndns_date=? WHERE o_stdasgncls_status='Y' AND o_usr_activestatus='Y' AND o_stdasgncls_secId=? AND o_usr_orgid=? and o_usr_homestudy is true ORDER BY o_usr_rollno,o_usr_id  ";
		List<StudentAttendDTO> students = new ArrayList<StudentAttendDTO>();
		Connection conn = null;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat("d");
		String s[] =  sdf.format(date).split("-");
		
		String date1 = s[0] + "-" + Integer.parseInt(s[1]) + "-01";
		
		try {
			conn = JdbcConnectionManager.getConnection(jdbcTemplate);
			PreparedStatement pst = conn.prepareStatement(query);
			
			pst.setInt(1, Integer.parseInt(s[2]));			
			pst.setString(2, date1);
			pst.setLong(3, orgId);
			pst.setLong(4, sectionid);
			ResultSet rs = pst.executeQuery();
			
			
			while (rs.next()) {
				StudentAttendDTO student = new StudentAttendDTO();
				student.setUserId(rs.getString("o_usr_id"));
				student.setStdFirstName(rs.getString("o_usr_fn"));
				student.setStdMiddleName(rs.getString("o_usr_mn"));
				student.setStdLastName(rs.getString("o_usr_ln"));
				student.setStdattendStatus(rs.getString("status"));
				students.add(student);
			}
			JdbcConnectionManager.CloseResultSet(rs);
			JdbcConnectionManager.ClosePreparedStatement(pst);
			return students;
		} catch (Exception e) {
//			logger.debug("Error while reading class roles", e);

		} finally {
			JdbcConnectionManager.Close(conn);
		}
		return null;

	}

	@Override
	public List<StudentSectionDTO> getStudentSectionByOrgId(String orgId) {

		String query = "SELECT DISTINCT o_asgnclssec_id,o_clslist_title as title FROM org_studentassignedclass c INNER JOIN org_users ON o_usr_id=o_stdasgncls_usrid INNER JOIN org_assignedclasssections o ON(o.o_asgnclssec_id=c.o_stdasgncls_secid) INNER JOIN org_assignedclasses m ON(m.o_asgncls_id=c.o_stdasgncls_clsid AND m.o_asgnCls_Id=o.o_asgnClsSec_clsId ) INNER JOIN hasdb.has_registrations ON h_reg_id=o_usr_orgid  INNER JOIN org_assignedstreams s ON(s.o_asgnSt_Id=m.o_asgnCls_stId AND h_reg_mainid=o_asgnst_orgId) INNER JOIN hasdb.has_streams t ON(t.h_stream_id=s.o_asgnSt_streamId) INNER JOIN org_academiccalendar z ON(z.o_acadCal_Id=s.o_asgnSt_acadCalId) INNER JOIN org_classlist cl ON (m.o_Asgncls_clsid=cl.o_clslist_id) INNER JOIN hasdb.has_sectionslist hsl ON (o.o_asgnclssec_secid=hsl.h_seclist_id) INNER JOIN org_assignedsecgrps sg ON (sg.o_asgnsecgrp_secid=o.o_Asgnclssec_id) INNER JOIN hasdb.has_sectiongroups  hg ON (sg.o_asgnsecgrp_grpid=hg.h_secgrp_id) WHERE  z.o_acadCal_Status='Y' AND c.o_stdasgncls_status='Y' AND h_reg_Id=? AND NOT EXISTS(SELECT 1 FROM facecount a WHERE h_reg_id=a.orgid AND STR_TO_DATE(a.date,'%d-%m-%Y')=STR_TO_DATE(DATE_FORMAT(SYSDATE(),'%Y-%m-%d'),'%Y-%m-%d') AND a.classid=o_asgnclssec_id) ";
		List<StudentSectionDTO> sections = new ArrayList<StudentSectionDTO>();
		Connection conn = null;

		try {
			conn = JdbcConnectionManager.getConnection(jdbcTemplate);
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setLong(1, Long.parseLong(orgId));
			//pst.setLong(2, Long.parseLong(orgId));
			System.out.println(pst);
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				StudentSectionDTO section = new StudentSectionDTO();
				section.setSectionId(rs.getString(1));
				section.setSectionTitle(rs.getString(2));
				sections.add(section);
			}
			JdbcConnectionManager.CloseResultSet(rs);
			JdbcConnectionManager.ClosePreparedStatement(pst);
			return sections;
		} catch (Exception e) {
	//		logger.debug("Error while reading class roles", e);
		} finally {
			JdbcConnectionManager.Close(conn);
		}
		return null;

	}

	@Override
	public String saveStudentAttendance(List<StudentAttendDTO> students, StudentParamDTO params) {
		
		Connection con = null;
		
		try {

			con = JdbcConnectionManager.getConnection(jdbcTemplate);

			Calendar c = Calendar.getInstance();
			c.setTime(new SimpleDateFormat("dd-MM-yyyy").parse(params.getDate()));
			int thisdate = c.get(Calendar.DATE);
			int y = c.get(Calendar.YEAR);
			int m = c.get(Calendar.MONTH) + 1;
			int d = c.get(Calendar.DAY_OF_MONTH);
			int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
			String date = d + "/" + m + "/" + y;
			int chour = c.get(Calendar.HOUR);
			int chourd = c.get(Calendar.HOUR_OF_DAY);
			int cmin = c.get(Calendar.MINUTE);
			int ampm = c.get(Calendar.AM_PM);
			int sec = c.get(Calendar.SECOND);
			String trackdate = y + "-" + m + "-" + d;
			System.out.println(trackdate);
			
			String date1 = y + "-" + m + "-01";
			String col = "o_usrAtndns_d" + d;
			String upd_query = "update org_userattendance set "+col+" = ? where o_usrAtndns_usrId= ? and DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d') = ? ";			
			String select_query = "select distinct o_usrAtndns_usrId, "+col+"  from org_userattendance where o_usrAtndns_usrId= ? and o_usrAtndns_date=?";			
			String insert_query = "insert into org_userattendance (o_usrAtndns_usrId,o_usrAtndns_date,"+col+") values(?,?,?)";			
			String track_select_query = "select * from org_userattendance_track where o_usrAtndns_type='S' and o_usrAtndns_orgId=? and o_usrAtndns_dateofatt=?";			
			String track_update_query = "update org_userattendance_track set  o_usrAtndns_lastupdateddate =now(),o_usrAtndns_updatedby=? where o_usrAtndns_type='S' and o_usrAtndns_orgId=? and o_usrAtndns_createddate=DATE_FORMAT(?,'%Y-%c-%d') and o_usrAtndns_dateofatt=DATE_FORMAT(?,'%Y-%c-%d')";			
			String track_insert_query = "insert into org_userattendance_track (o_usrAtndns_orgId,o_usrAtndns_dateofatt,o_usrAtndns_lastupdateddate,o_usrAtndns_updatedby,o_usrAtndns_createddate,o_usrAtndns_type) values(?,?,now(),?,?,'S')";
			String photo_insert = "insert into studentattendancephoto (o_usr_id,o_usr_sphoto,att_date) values (?,?,?)";
			String photo_select = "select   o_usr_id,created_date,o_usr_sphoto,att_date from studentattendancephoto where o_usr_id=? and DATE_FORMAT(att_date,'%Y-%c-%d')=?";
			String photo_update = "update studentattendancephoto set o_usr_sphoto=? where o_usr_id=? and DATE_FORMAT(att_date,'%Y-%c-%d')=?";String leave = "";
			PreparedStatement update_pstmt = con.prepareStatement(upd_query);
			for (StudentAttendDTO student : students) {
				PreparedStatement select_student_pstm = con.prepareStatement(select_query);
				
				select_student_pstm.setLong(1, Long.parseLong(student.getUserId()));
				select_student_pstm.setString(2, date1);
				ResultSet rs = select_student_pstm.executeQuery();
				if (!rs.next()) {
					PreparedStatement insert_pstmt = con.prepareStatement(insert_query);
					insert_pstmt.setLong(1, Long.parseLong(student.getUserId()));
					insert_pstmt.setString(2, date1);
					insert_pstmt.setString(3, student.getStdattendStatus());	
					insert_pstmt.executeUpdate();

					JdbcConnectionManager.ClosePreparedStatement(insert_pstmt);
				} else {
					leave = rs.getString(2);
					if (null == leave || leave.isEmpty()) {
						leave = "A";
					}
				}
				JdbcConnectionManager.CloseResultSet(rs);
				JdbcConnectionManager.ClosePreparedStatement(select_student_pstm);
				
				update_pstmt.setString(1, student.getStdattendStatus());
				update_pstmt.setLong(2, Long.parseLong(student.getUserId()));
				update_pstmt.setString(3, date1);
				// update_pstmt.setString(5, trackdate);
				update_pstmt.addBatch();
			}
			update_pstmt.executeBatch();
			JdbcConnectionManager.ClosePreparedStatement(update_pstmt);
			PreparedStatement track_pstmt = con.prepareStatement(track_select_query);
			track_pstmt.setString(1,params.getOrgId());
			track_pstmt.setString(2, date1);
			ResultSet rs = track_pstmt.executeQuery();
			if (rs.next()) {
				PreparedStatement update_track_pstmt = con.prepareStatement(track_update_query);
				update_track_pstmt.setLong(1, Long.parseLong(params.getUserId()));
				update_track_pstmt.setLong(2, Long.parseLong(params.getOrgId()));
				update_track_pstmt.setString(3, trackdate);
				update_track_pstmt.setString(4, date1);

				update_track_pstmt.executeUpdate();
				JdbcConnectionManager.ClosePreparedStatement(update_track_pstmt);
			} else {
				PreparedStatement insert_track_pstmt = con.prepareStatement(track_insert_query);
				insert_track_pstmt.setLong(1, Long.parseLong(params.getOrgId()));
				insert_track_pstmt.setString(2, trackdate);
				insert_track_pstmt.setLong(3, Long.parseLong(params.getUserId()));
				insert_track_pstmt.setString(4, date1);

				insert_track_pstmt.executeUpdate();
				JdbcConnectionManager.ClosePreparedStatement(insert_track_pstmt);
			}
			try{
			if(params.getPhoto() !=null ){
			PreparedStatement photo_select_pstmt = con.prepareStatement(photo_select);
			photo_select_pstmt.setString(1,params.getUserId() );
			photo_select_pstmt.setString(2, trackdate);
			ResultSet photo_select_rs=photo_select_pstmt.executeQuery();
			
				if(photo_select_rs.next()){
					
					PreparedStatement photo_update_pstmt = con.prepareStatement(photo_update);
					photo_update_pstmt.setBytes(1,params.getPhoto() );
					photo_update_pstmt.setString(2, params.getUserId());
					photo_update_pstmt.setString(3,trackdate);
					photo_update_pstmt.executeUpdate();
							
				}
				else{
					
					PreparedStatement photo_update_pstmt = con.prepareStatement(photo_insert);
					photo_update_pstmt.setString(1, params.getUserId());
					photo_update_pstmt.setBytes(2,params.getPhoto() );
					photo_update_pstmt.setString(3, trackdate);
					photo_update_pstmt.executeUpdate();
				}
			
				JdbcConnectionManager.ClosePreparedStatement(photo_select_pstmt);
			}	
			}catch(Exception e1){
				
			}
			
			
			
			return "Attendance Submited";
		} catch (Exception e) {			
		//	logger.debug("Error", e);
			return "failed";
		} finally {
			JdbcConnectionManager.Close(con);
		}
	}
	@Override
	public String saveHomeStudentAttendance(HomeStudentAttendanceDTO students, StudentParamDTO params) {
		Connection con = null;
		try {

			con = JdbcConnectionManager.getConnection(jdbcTemplate);

			Calendar c = Calendar.getInstance();
			c.setTime(new SimpleDateFormat("dd-MM-yyyy").parse(params.getDate()));
			int thisdate = c.get(Calendar.DATE);
			int y = c.get(Calendar.YEAR);
			int m = c.get(Calendar.MONTH) + 1;
			int d = c.get(Calendar.DAY_OF_MONTH);
			int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
			String date = d + "/" + m + "/" + y;
			int chour = c.get(Calendar.HOUR);
			int chourd = c.get(Calendar.HOUR_OF_DAY);
			int cmin = c.get(Calendar.MINUTE);
			int ampm = c.get(Calendar.AM_PM);
			int sec = c.get(Calendar.SECOND);
			String trackdate = y + "-" + m + "-" + d;

			String date1 = y + "-" + m + "-01";
			String col = "o_usrAtndns_d" + d;
			String upd_query = "update org_userattendance set "+col+" = ? where o_usrAtndns_usrId= ? and DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d') = ? ";			
			String select_query = "select distinct o_usrAtndns_usrId, "+col+"  from org_userattendance where o_usrAtndns_usrId= ? and o_usrAtndns_date=?";			
			String insert_query = "insert into org_userattendance (o_usrAtndns_usrId,o_usrAtndns_date,"+col+") values(?,?,?)";			
			String track_select_query = "select * from org_userattendance_track where o_usrAtndns_type='S' and o_usrAtndns_orgId=? and o_usrAtndns_dateofatt=?";			
			String track_update_query = "update org_userattendance_track set  o_usrAtndns_lastupdateddate =now(),o_usrAtndns_updatedby=? where o_usrAtndns_type='S' and o_usrAtndns_orgId=? and o_usrAtndns_createddate=DATE_FORMAT(?,'%Y-%c-%d') and o_usrAtndns_dateofatt=DATE_FORMAT(?,'%Y-%c-%d')";			
			String track_insert_query = "insert into org_userattendance_track (o_usrAtndns_orgId,o_usrAtndns_dateofatt,o_usrAtndns_lastupdateddate,o_usrAtndns_updatedby,o_usrAtndns_createddate,o_usrAtndns_type) values(?,?,now(),?,?,'S')";
			String get_location_query = "select * from homestudentattendance where o_usr_id=? and DATE_FORMAT(att_date,'%Y-%c-%d') = date_format(?,'%Y-%c-%d')";
			String insert_location_query ="insert into homestudentattendance(o_usr_id,longitude,latitude,o_usr_photo,att_date,o_usr_sphoto) values(?,?,?,?,?,?)";
			String update_location_query = "update homestudentattendance set longitude=?,latitude=?,o_usr_photo=?,o_usr_sphoto=?, created_date=now() where o_usr_id=? and DATE_FORMAT(att_date,'%Y-%c-%d') = DATE_FORMAT(?,'%Y-%c-%d')";
			
			String leave = "";
			PreparedStatement update_pstmt = con.prepareStatement(upd_query);
			//for (HomeStudentAttendanceDTO student : students) {
			HomeStudentAttendanceDTO student = students;
				PreparedStatement select_student_pstm = con.prepareStatement(select_query);
				PreparedStatement insert_loc_pstmt = con.prepareStatement(insert_location_query);			
				PreparedStatement update_loc_pstmt = con.prepareStatement(update_location_query);
				
				select_student_pstm.setLong(1, Long.parseLong(student.getUserId()));
				select_student_pstm.setString(2, date1);
				ResultSet rs = select_student_pstm.executeQuery();
				if (!rs.next()) {
					PreparedStatement insert_pstmt = con.prepareStatement(insert_query);
					insert_pstmt.setLong(1, Long.parseLong(student.getUserId()));
					insert_pstmt.setString(2, date1);
					insert_pstmt.setString(3,"P");
					insert_pstmt.executeUpdate();

					JdbcConnectionManager.ClosePreparedStatement(insert_pstmt);
				} else {
					leave = rs.getString(2);
					if (null == leave || leave.isEmpty()) {
						leave = "A";
					}
				}
				JdbcConnectionManager.CloseResultSet(rs);
				JdbcConnectionManager.ClosePreparedStatement(select_student_pstm);
				
				update_pstmt.setString(1, "P");
				update_pstmt.setLong(2, Long.parseLong(student.getUserId()));
				update_pstmt.setString(3, date1);
				update_pstmt.addBatch();
			//location update
				PreparedStatement location_pstmt = con.prepareStatement(get_location_query);
				location_pstmt.setString(1,params.getUserId());
				location_pstmt.setString(2, trackdate);
				
				ResultSet loc_rs = location_pstmt.executeQuery();
				if (loc_rs.next()) {
					 update_loc_pstmt = con.prepareStatement(update_location_query);
			
					try{
						//int precision =  (int)Math.pow(10, 6);
					// update_loc_pstmt.setFloat(1,precision*student.getLongitude());
					update_loc_pstmt.setFloat(1,student.getLongitude());
					}catch(Exception e){
						update_loc_pstmt.setFloat(1,0 );
					}
					try{
						// int precision =  (int)Math.pow(10, 6);
					//update_loc_pstmt.setFloat(2,precision*student.getLatitude());
					update_loc_pstmt.setFloat(2,student.getLatitude());
					}catch(Exception e){
						update_loc_pstmt.setFloat(2,0);	
					}
					update_loc_pstmt.setString(3, student.getPhoto_path());
					update_loc_pstmt.setBytes(4, student.getPhoto());
					update_loc_pstmt.setLong(5, Long.parseLong(student.getUserId()));
					update_loc_pstmt.setString(6, trackdate);
					update_loc_pstmt.addBatch();
					
				} else {
					 insert_loc_pstmt = con.prepareStatement(insert_location_query);			
							
					insert_loc_pstmt.setLong(1, Long.parseLong(student.getUserId()));
					try{
						//int precision =  (int)Math.pow(10, 6);
						//insert_loc_pstmt.setFloat(2,precision*student.getLongitude() );
						insert_loc_pstmt.setFloat(2,student.getLongitude() );
						}catch(Exception e){
							insert_loc_pstmt.setFloat(2,0 );
						}
						try{
							// int precision =  (int)Math.pow(10, 6);
							// insert_loc_pstmt.setFloat(3,precision*student.getLatitude());
							insert_loc_pstmt.setFloat(3,student.getLatitude());
						}catch(Exception e){
							insert_loc_pstmt.setFloat(3,0);	
						}
					insert_loc_pstmt.setString(4, student.getPhoto_path());
					insert_loc_pstmt.setString(5,  trackdate);
					//	insert_loc_pstmt.setBytes(5, null);
					insert_loc_pstmt.setBytes(6,student.getPhoto());
					insert_loc_pstmt.addBatch();
				}
				JdbcConnectionManager.CloseResultSet(loc_rs);
				
				JdbcConnectionManager.CloseResultSet(rs);
				JdbcConnectionManager.ClosePreparedStatement(select_student_pstm);
				
				update_pstmt.setString(1, student.getStdattendStatus());
				update_pstmt.setLong(2, Long.parseLong(student.getUserId()));
				update_pstmt.setString(3, date1);
				//update_pstmt.setBytes(4, trackdate);
				update_pstmt.addBatch();
			
			update_pstmt.executeBatch();
			try{
			insert_loc_pstmt.executeBatch();
			}catch(Exception e3){}
			try{
			update_loc_pstmt.executeBatch();
			}catch(Exception ee){}
			JdbcConnectionManager.ClosePreparedStatement(update_pstmt);
			PreparedStatement track_pstmt = con.prepareStatement(track_select_query);
			track_pstmt.setString(1,params.getOrgId());
			track_pstmt.setString(2, date1);
			ResultSet rs_1 = track_pstmt.executeQuery();
			if (rs_1.next()) {
				PreparedStatement update_track_pstmt = con.prepareStatement(track_update_query);
				update_track_pstmt.setLong(1, Long.parseLong(params.getUserId()));
				update_track_pstmt.setLong(2, Long.parseLong(params.getOrgId()));
				update_track_pstmt.setString(3, trackdate);
				update_track_pstmt.setString(4, date1);

				update_track_pstmt.executeUpdate();
				JdbcConnectionManager.ClosePreparedStatement(update_track_pstmt);
			} else {
				PreparedStatement insert_track_pstmt = con.prepareStatement(track_insert_query);
				insert_track_pstmt.setLong(1, Long.parseLong(params.getOrgId()));
				insert_track_pstmt.setString(2, trackdate);
				insert_track_pstmt.setLong(3, Long.parseLong(params.getUserId()));
				insert_track_pstmt.setString(4, date1);

				insert_track_pstmt.executeUpdate();
				JdbcConnectionManager.ClosePreparedStatement(insert_track_pstmt);
			}
			//logger.debug("writing to disk");
			
			return "Attendance Submited";
		} catch (Exception e) {			
			//logger.debug("Error", e);
			return "failed";
		} finally {
			JdbcConnectionManager.Close(con);
		}
	}			
		
	
	/*
	 SELECT hsa.o_usr_sphoto,u.o_usr_id,IFNULL(o_usr_fn,'') as o_usr_fn,IFNULL(o_usr_mn,'') as o_usr_mn,IFNULL(o_usr_ln,'') 
as o_usr_ln,o_usrAtndns_d13 as status,o_usratndns_date as date 
FROM org_studentassignedclass  INNER JOIN org_users u ON (o_stdasgncls_usrid=u.o_usr_id) 
INNER JOIN org_assignedclasssections ON(o_asgnclsSec_id=o_stdasgncls_secid) 
INNER JOIN org_assignedclasses ON(o_asgnclssec_clsid=o_asgncls_id) LEFT JOIN org_userattendance 
ON (o_usrAtndns_usrId=o_usr_id and o_usratndns_date='2019-08-01') left outer join homestudentattendance hsa on(u.o_usr_id= hsa.o_usr_id) 
WHERE o_stdasgncls_status='Y' AND o_usr_activestatus='Y' AND
o_stdasgncls_secId=7233 AND o_usr_orgid=22686 ORDER BY o_usr_rollno,u.o_usr_id ;

and o_usr_homestudy is true

	 */
	@Override
	public List<HomeStudentAttendanceDTO> getHomeStudentAttendanceWithPhotoList(int sectionid, long orgId, Date date) throws Exception {
		String query = "SELECT hsa.o_usr_sphoto as photo, u.o_usr_id as o_usr_id,IFNULL(o_usr_fn,'') as o_usr_fn,IFNULL(o_usr_mn,'') as o_usr_mn,IFNULL(o_usr_ln,'') as o_usr_ln,ifnull(o_usrAtndns_d?,'A') as status,ifnull(hsa.longitude,0) as longitude,ifnull(hsa.latitude,0) as latitude FROM org_studentassignedclass  INNER JOIN org_users u ON (o_stdasgncls_usrid=o_usr_id) INNER JOIN org_assignedclasssections ON(o_asgnclsSec_id=o_stdasgncls_secid) INNER JOIN org_assignedclasses ON(o_asgnclssec_clsid=o_asgncls_id) left outer JOIN org_userattendance ON (o_usrAtndns_usrId=o_usr_id  AND o_usrAtndns_date=?) left outer join homestudentattendance hsa on(o_usrAtndns_usrId= hsa.o_usr_id and  att_date=?)  WHERE o_stdasgncls_status='Y' AND o_usr_activestatus='Y' AND o_stdasgncls_secId=? AND o_usr_orgid=? and o_usr_homestudy is true ORDER BY o_usr_rollno,o_usr_id  ";
		List<HomeStudentAttendanceDTO> students = new ArrayList<HomeStudentAttendanceDTO>();
		Connection conn = null;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat("d");
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int thisdate = c.get(Calendar.DATE);
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DAY_OF_MONTH);
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		String date3 = d + "/" + m + "/" + y;
		int chour = c.get(Calendar.HOUR);
		int chourd = c.get(Calendar.HOUR_OF_DAY);
		int cmin = c.get(Calendar.MINUTE);
		int ampm = c.get(Calendar.AM_PM);
		int sec = c.get(Calendar.SECOND);
		String trackdate = y + "-" + m + "-" + d;

		
		String date1 = y + "-" + m + "-01";
		
		try {
			conn = JdbcConnectionManager.getConnection(jdbcTemplate);
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, d);
			pst.setString(2, date1);
			pst.setString(3, trackdate);
			pst.setLong(4, orgId);
			pst.setLong(5, sectionid);
			//logger.debug(pst.toString());
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				HomeStudentAttendanceDTO student = new HomeStudentAttendanceDTO();
				student.setUserId(rs.getString("o_usr_id"));
				student.setStdFirstName(rs.getString("o_usr_fn"));
				student.setStdMiddleName(rs.getString("o_usr_mn"));
				student.setStdLastName(rs.getString("o_usr_ln"));
				student.setStdattendStatus(rs.getString("status"));
				student.setLongitude(rs.getFloat("longitude"));
				student.setLatitude(rs.getFloat("latitude"));
				try{
				String photo = Base64.getEncoder().encodeToString(rs.getBytes("photo"));
				student.setPhoto(rs.getBytes("photo"));
				}catch(Exception e){
					student.setPhoto(rs.getBytes("photo"));
				}
				students.add(student);
			}
			JdbcConnectionManager.CloseResultSet(rs);
			JdbcConnectionManager.ClosePreparedStatement(pst);
			return students;
		} catch (Exception e) {
			//logger.debug("Error while reading class roles", e);

		} finally {
			JdbcConnectionManager.Close(conn);
		}
		return null;

	}

	public List<DistrictAttendanceDTO> getStudentSchoolWiseSummeryAttendance(DistrictAttendanceDTO attend){
		
		List<DistrictAttendanceDTO> attends = new ArrayList<DistrictAttendanceDTO>();
		String overallreport ="select hr.h_reg_title title, o_usr_orgid orgid,o_clslist_title class_name, count(case when o_usrAtndns_d?='P' then 1 end) as present,count(case when ifnull(o_usrAtndns_d?,'A')='A' then 1 end) as absent FROM org_userattendance att left JOIN org_studentassignedclass sac  ON (att.o_usrAtndns_usrId=sac.o_stdasgncls_usrId  AND date_format(att.o_usrAtndns_date,'%Y-%c-%d')=date_format(?,'%Y-%c-%d'))  left join org_assignedclasses m ON(m.o_asgncls_id=o_stdasgncls_clsid AND m.o_asgnCls_Id=sac.o_stdasgncls_clsid ) left join org_classlist cl ON (m.o_Asgncls_clsid=cl.o_clslist_id) left JOIN org_users u ON (u.o_usr_id= sac.o_stdasgncls_usrid and u.o_usr_activestatus='Y') right join hasdb.has_registrations hr on (hr.h_reg_id = u.o_usr_orgId) WHERE o_stdasgncls_status='Y'   and o_usr_type=6 and u.o_usr_orgid=? group by o_usr_orgid,o_asgncls_id ";
		Connection conn = null;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
		java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat("d");
		java.text.SimpleDateFormat sdt = new java.text.SimpleDateFormat("yyyy-MM-dd");
		System.out.println("kkri"+sdt);
		String ldate = attend.getDate();
		String s[] = ldate.split("-");
		String date1 = s[2] + "-" + Integer.parseInt(s[1]) + "-01";
		String date2 = s[2]+"-"+Integer.parseInt(s[1]) +"-"+ Integer.parseInt(s[0]) ;
		
		try{
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(attend.getDate()));
		int thisdate = c.get(Calendar.DATE);
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DAY_OF_MONTH);
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		String date3 = d + "/" + m + "/" + y;
		int chour = c.get(Calendar.HOUR);
		int chourd = c.get(Calendar.HOUR_OF_DAY);
		int cmin = c.get(Calendar.MINUTE);
		int ampm = c.get(Calendar.AM_PM);
		int sec = c.get(Calendar.SECOND);
		String trackdate = y + "-" + m + "-" + d;

		
		 date1 = y + "-" + m + "-01";
		 
		}catch(Exception e){}
		
		PreparedStatement result = null; 
		try {
			int day= Integer.parseInt(sd.format(sdf.parse(attend.getDate())));	
			conn = JdbcConnectionManager.getConnection(jdbcTemplate);
			result =  conn.prepareStatement(overallreport);
			result.setInt(1,day);
			result.setInt(2, day);
			result.setString(3, date1);
			result.setInt(4,Integer.parseInt(attend.getSchoolId() ));
			System.out.println(result.toString());
			ResultSet rs = result.executeQuery();
			System.out.println(rs);
			if (rs.next()) {
				DistrictAttendanceDTO att = new DistrictAttendanceDTO();
				att.setDistrictName(attend.getDistrictName());
				att.setSchoolId(rs.getString("orgid"));
				att.setSchoolName(rs.getString("title"));
				att.setClassname(rs.getString("class_name"));
				att.setTotalAbsent(rs.getInt("absent"));
				att.setTotalPresent(rs.getInt("present"));
				attends.add(att);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		finally{
			JdbcConnectionManager.ClosePreparedStatement(result);
			
			JdbcConnectionManager.Close(conn);
		}
		return attends;
	}
	
public List<DistrictAttendanceDTO> getStudentDistrictWiseSummeryAttendance(DistrictAttendanceDTO attend){
		
		List<DistrictAttendanceDTO> attends = new ArrayList<DistrictAttendanceDTO>();
		String overallreport ="SELECT h_districtlist_title,hr.h_reg_title as title, o_usr_orgid as orgid, o_usr_type,COUNT(CASE WHEN o_usrAtndns_d?='P' THEN 1 END) AS present,COUNT(CASE WHEN IFNULL(o_usrAtndns_d?,'A')='A' THEN 1 END) AS absent FROM org_userattendance att LEFT JOIN org_users u ON (u.o_usr_id= att.o_usrAtndns_usrId AND u.o_usr_activestatus='Y') RIGHT JOIN hasdb.has_registrations hr ON (hr.h_reg_id = u.o_usr_orgId ) LEFT JOIN  hasdb.has_organizationaddress ON h_add_reg_id=h_reg_id LEFT JOIN  hasdb.has_districtlist ON h_districtlist_id=h_add_field1 WHERE o_usr_type=6 AND o_usrAtndns_date=? AND  h_districtlist_title=? GROUP BY o_usr_orgid;";
		Connection conn = null;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
		java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat("d");
		java.text.SimpleDateFormat sdt = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String s[] = attend.getDate().split("-");
		
		String date1 = s[0] + "-" + Integer.parseInt(s[1]) + "-01";

		PreparedStatement result = null; 
		try {
			
			conn = JdbcConnectionManager.getConnection(jdbcTemplate);
			result =  conn.prepareStatement(overallreport);
			result.setString(4,attend.getDistrictName());
			result.setInt(1, Integer.parseInt(sd.format(sdf.parse(attend.getDate()))));
			result.setInt(2, Integer.parseInt(sd.format(sdf.parse(attend.getDate()))));
			result.setString(3, date1);
			ResultSet rs = result.executeQuery();
			System.out.println(rs);
			if (rs.next()) {
				DistrictAttendanceDTO att = new DistrictAttendanceDTO();
				att.setDistrictName(attend.getDistrictName());
				att.setSchoolId(rs.getString("orgid"));
				att.setSchoolName(rs.getString("title"));
				att.setTotalAbsent(rs.getInt("absent"));
				att.setTotalPresent(rs.getInt("present"));
				attends.add(att);
			}
			
		}catch(Exception e){
			
			return null;
		}
		finally{
			JdbcConnectionManager.ClosePreparedStatement(result);
			
			JdbcConnectionManager.Close(conn);
		}
		return attends;
	}

@Override
public String saveStudentFaceCount(int  studentFaceCount, StudentFaceCountDTO params) {
	Connection con = null;
	try {

		con = JdbcConnectionManager.getConnection(jdbcTemplate);

		Calendar c = Calendar.getInstance();
		c.setTime(new SimpleDateFormat("dd-MM-yyyy").parse(params.getParams().getDate()));
		int thisdate = c.get(Calendar.DATE);
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DAY_OF_MONTH);
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		String date = d + "/" + m + "/" + y;
		int chour = c.get(Calendar.HOUR);
		int chourd = c.get(Calendar.HOUR_OF_DAY);
		int cmin = c.get(Calendar.MINUTE);
		int ampm = c.get(Calendar.AM_PM);
		int sec = c.get(Calendar.SECOND);
		String trackdate = y + "-" + m + "-" + d;
		String insert_location_query ="insert into facecount(orgid,classid,date,count,photo,lat,lon) values(?,?,?,?,?,?,?)";
		PreparedStatement insert_loc_pstmt = con.prepareStatement(insert_location_query);			
	//	ResultSet rs = insert_loc_pstmt.executeQuery();
		System.out.println("kkk"+insert_location_query);
			//	PreparedStatement insert_loc_pstmt1 = con.prepareStatement(insert_loc_pstmt1);
				insert_loc_pstmt.setString(1, params.getParams().getOrgId());
				insert_loc_pstmt.setString(2, params.getParams().getSectionId());
				insert_loc_pstmt.setString(3,params.getParams().getDate());
				insert_loc_pstmt.setString(4,String.valueOf(params.getFaceCount()));
				insert_loc_pstmt.setBytes(5,params.getParams().getPhoto());
				
				insert_loc_pstmt.setFloat(6, params.getParams().getLatitude());
				insert_loc_pstmt.setFloat(7, params.getParams().getLongitude());
				System.out.println(""+insert_loc_pstmt);
				insert_loc_pstmt.executeUpdate();
				//System.out.println();
				JdbcConnectionManager.ClosePreparedStatement(insert_loc_pstmt);
		
			
						//logger.debug("writing to disk");
		
		return "Attendance Submited";
	} catch (Exception e) {			
		logger.debug("Error", e);
		return "failed";
	} finally {
		JdbcConnectionManager.Close(con);
	}
}
}		

	



