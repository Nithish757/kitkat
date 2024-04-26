package com.hassoft.api.daos.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hassoft.api.daos.TeacherAttendanceDAO;
import com.hassoft.api.dtos.DistrictAttendanceDTO;
import com.hassoft.api.dtos.StudentFaceCountDTO;
import com.hassoft.api.dtos.TeacherAttendanceDTO;
import com.hassoft.api.dtos.TeacherParamsDTO;
import com.hassoft.api.utils.JdbcConnectionManager;

@Repository
@Qualifier("teacherAttendanceDAO")
public class TeacherAttendanceDAOImpl implements TeacherAttendanceDAO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 572936388770200553L;

	final static Logger logger = LoggerFactory.getLogger(TeacherAttendanceDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<TeacherAttendanceDTO> getTeacherList(String orgId, String date,String userId) throws Exception {
		System.out.println("vamsi"+userId);
		String query1 = "SELECT o_usr_type from org_users where o_usr_id=? ";
		String role="";
		Connection conn1 = null;
		conn1= JdbcConnectionManager.getConnection(jdbcTemplate);
		PreparedStatement pst1 = conn1.prepareStatement(query1);
		pst1.setString(1, userId);
		ResultSet rs1 = pst1.executeQuery();
		while (rs1.next()) {
		role=rs1.getString("o_usr_type");
			System.out.println("krishna"+role);
		}
		
		String query="";
			System.out.println("krishna11"+role);
		if (role.equalsIgnoreCase("3")) {
		// query = "SELECT u.o_usr_id as o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn,IFNULL(o_usr_ln,'') AS o_usr_ln, CASE WHEN o_usrAtndns_d?='P' THEN 'Present' WHEN o_usrAtndns_d?='A' THEN 'Absent'  WHEN o_usrAtndns_d?='L' THEN 'Leave'   WHEN o_usrAtndns_d?='O' THEN 'On Duty' WHEN o_usrAtndns_d?='S' THEN 'Half Day Leave' WHEN o_usrAtndns_d?='C' THEN 'Casual Leave' WHEN o_usrAtndns_d?='W' THEN 'NA' WHEN o_usrAtndns_d?='D' THEN 'Optional Holiday' WHEN o_usrAtndns_d?='H' THEN 'Holiday' WHEN o_usrAtndns_d?='M' THEN 'Maternity Leave' WHEN o_usrAtndns_d?='F' THEN 'Comp Off' WHEN o_usrAtndns_d?='G' THEN 'LOPMG' END AS STATUS, longitude, latitude,o_usr_sphoto  FROM org_users u LEFT JOIN org_userattendance ON (o_usrAtndns_usrId=u.o_usr_id AND date_format(o_usrAtndns_date,'%Y-%c-%d')=date_format(?,'%Y-%c-%d'))    left join iertattendance ie on (u.o_usr_id=ie.o_usr_id and date_format(att_date,'%Y-%c-%d')=date_format(?,'%Y-%c-%d')) WHERE  o_usr_activestatus='Y' AND o_usr_type =3 AND o_usr_orgid=? and u.o_usr_id=? ORDER BY u.o_usr_id";
		// query = "SELECT DISTINCT  o_usr_id,CONCAT(o_usr_fn,' ',o_usr_ln) o_usr_fn ,IFNULL(o_usr_sphoto,'') o_usr_sphoto  ,MAX(CASE WHEN dt=0 THEN STATUS ELSE 0 END) 'mnStatus' ,MAX(CASE WHEN dt=1 THEN STATUS ELSE 0 END) 'fnStatus'   ,MAX(CASE WHEN dt=0 THEN longitude ELSE 0 END) 'mnLng' ,MAX(CASE WHEN dt=1 THEN longitude ELSE 0 END) 'fnLng'   ,MAX(CASE WHEN dt=0 THEN latitude ELSE 0 END) 'mnLat' ,MAX(CASE WHEN dt=1 THEN latitude ELSE 0 END) 'fnLat'  ,MAX(CASE WHEN dt=0 THEN latitude ELSE 0 END) 'mnStatuson' ,MAX(CASE WHEN dt=1 THEN latitude ELSE 0 END) 'fnStatuson'   ,MAX(CASE WHEN dt=0 THEN hideshow ELSE 0 END) 'MN' ,MAX(CASE WHEN dt=1 THEN hideshow ELSE 0 END) 'FN'   FROM (SELECT DISTINCT u.o_usr_id AS o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn,IFNULL(o_usr_ln,'') AS o_usr_ln , CASE WHEN o_usrAtndns_d?='P' THEN 'Present' WHEN o_usrAtndns_d?='A' THEN 'Absent'  WHEN o_usrAtndns_d?='L' THEN 'Leave'   WHEN o_usrAtndns_d?='O' THEN 'On Duty' WHEN o_usrAtndns_d?='S' THEN 'Half Day Leave' WHEN o_usrAtndns_d?='C' THEN 'Casual Leave' WHEN o_usrAtndns_d?='W' THEN 'NA' WHEN o_usrAtndns_d?='D' THEN 'Optional Holiday' WHEN o_usrAtndns_d?='H' THEN 'Holiday'  WHEN o_usrAtndns_d?='M' THEN 'Maternity Leave' WHEN o_usrAtndns_d?='F' THEN 'Comp Off'  WHEN o_usrAtndns_d?='G' THEN 'LOPMG' ELSE 0 END AS STATUS , longitude, latitude,o_usr_sphoto, 0 dt,CASE WHEN DATE_FORMAT(SYSDATE(),'%H:%i:%s') BETWEEN '09:00:00' AND '12:59:59' THEN 1 ELSE 0 END hideshow FROM org_users u  LEFT JOIN org_userattendance ON (o_usrAtndns_usrId=u.o_usr_id  AND DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d')=DATE_FORMAT('2022-03-01','2022-03-01'))     LEFT JOIN iertattendance ie ON (u.o_usr_id=ie.o_usr_id AND DATE_FORMAT(att_date,'%Y-%c-%d')=DATE_FORMAT('2022-03-01','2022-03-01'))  WHERE  o_usr_activestatus='Y' AND o_usr_type =3  AND o_usr_orgid=22839   UNION ALL SELECT DISTINCT u.o_usr_id AS o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn,IFNULL(o_usr_ln,'') AS o_usr_ln , CASE WHEN o_usrAtndns_d1='P' THEN 'Present' WHEN o_usrAtndns_d1='A' THEN 'Absent'  ELSE 0 END AS STATUS , longitude, latitude,o_usr_sphoto,1 dt ,CASE WHEN DATE_FORMAT(SYSDATE(),'%H:%i:%s') BETWEEN '13:00:00' AND '18:59:59' THEN 1 ELSE 0 END hideshow   FROM org_users u  LEFT JOIN org_userattendance1 ON (o_usrAtndns_usrId=u.o_usr_id  AND DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d')=DATE_FORMAT('2022-03-01','2022-03-01'))     LEFT JOIN iertattendance1 ie ON (u.o_usr_id=ie.o_usr_id AND DATE_FORMAT(att_date,'%Y-%c-%d')=DATE_FORMAT('2022-03-01','2022-03-01'))  WHERE  o_usr_activestatus='Y' AND o_usr_type =3  AND o_usr_orgid=22839) a GROUP BY o_usr_id";
		 query = "SELECT DISTINCT  o_usr_id,CONCAT(o_usr_fn,' ',o_usr_ln) o_usr_fn,'' o_usr_mn ,IFNULL(o_usr_sphoto,'') o_usr_sphoto  ,MAX(CASE WHEN dt=0 THEN STATUS ELSE '' END) 'mnStatus' ,MAX(CASE WHEN dt=1 THEN STATUS ELSE '' END) 'fnStatus'    ,MAX(CASE WHEN dt=0 THEN longitude ELSE '' END) 'mnLng' ,MAX(CASE WHEN dt=1 THEN longitude ELSE '' END) 'fnLng'    ,MAX(CASE WHEN dt=0 THEN latitude ELSE '' END) 'mnLat' ,MAX(CASE WHEN dt=1 THEN latitude ELSE '' END) 'fnLat'   ,MAX(CASE WHEN dt=0 THEN att_date ELSE '' END) 'mnStatuson' ,MAX(CASE WHEN dt=1 THEN att_date ELSE '' END) 'fnStatuson'    ,MAX(CASE WHEN dt=0 AND STATUS='' THEN hideshow ELSE 0 END) 'MN' ,MAX(CASE WHEN dt=1 AND STATUS='' THEN hideshow ELSE 0 END) 'FN'    FROM (SELECT DISTINCT u.o_usr_id AS o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn ,IFNULL(o_usr_ln,'') AS o_usr_ln ,  CASE WHEN o_usrAtndns_d?='P' THEN 'Present'  WHEN o_usrAtndns_d?='A' THEN 'Absent'   WHEN o_usrAtndns_d?='L' THEN 'Leave'    WHEN o_usrAtndns_d?='O' THEN 'On Duty'   WHEN o_usrAtndns_d?='S' THEN 'Half Day Leave' WHEN o_usrAtndns_d?='C' THEN 'Casual Leave'  WHEN o_usrAtndns_d?='W' THEN 'NA'  WHEN o_usrAtndns_d?='D' THEN 'Optional Holiday'  WHEN o_usrAtndns_d?='H' THEN 'Holiday'   WHEN o_usrAtndns_d?='M' THEN 'Maternity Leave'  WHEN o_usrAtndns_d?='F' THEN 'Comp Off'   WHEN o_usrAtndns_d?='G' THEN 'LOPMG' ELSE '' END AS STATUS  , longitude, latitude,o_usr_sphoto, 0 dt ,CASE WHEN DATE_FORMAT(SYSDATE(),'%H:%i:%s') BETWEEN '07:00:00' AND '12:59:59' THEN 1 ELSE 0 END hideshow ,created_date att_date FROM org_users u  LEFT JOIN org_userattendance ON (o_usrAtndns_usrId=u.o_usr_id AND DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d')=DATE_FORMAT(?,'%Y-%c-%d'))     LEFT JOIN iertattendance ie ON (u.o_usr_id=ie.o_usr_id AND DATE_FORMAT(att_date,'%Y-%c-%d')=DATE_FORMAT(?,'%Y-%c-%d'))  WHERE  o_usr_activestatus='Y' AND o_usr_type =3  AND o_usr_orgid=? and u.o_usr_id='"+userId+"'   UNION ALL SELECT DISTINCT u.o_usr_id AS o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn,IFNULL(o_usr_ln,'') AS o_usr_ln , CASE WHEN o_usrAtndns_d?='P' THEN 'Present' WHEN o_usrAtndns_d?='A' THEN 'Absent'  WHEN o_usrAtndns_d?='L' THEN 'Leave'   WHEN o_usrAtndns_d?='O' THEN 'On Duty' WHEN o_usrAtndns_d?='S' THEN 'Half Day Leave' WHEN o_usrAtndns_d?='C' THEN 'Casual Leave' WHEN o_usrAtndns_d?='W' THEN 'NA' WHEN o_usrAtndns_d?='D' THEN 'Optional Holiday' WHEN o_usrAtndns_d?='H' THEN 'Holiday'  WHEN o_usrAtndns_d?='M' THEN 'Maternity Leave' WHEN o_usrAtndns_d?='F' THEN 'Comp Off'  WHEN o_usrAtndns_d?='G' THEN 'LOPMG' ELSE '' END AS STATUS  , longitude, latitude,o_usr_sphoto,1 dt ,CASE WHEN DATE_FORMAT(SYSDATE(),'%H:%i:%s') BETWEEN '13:00:00' AND '22:59:59' THEN 1 ELSE 0 END hideshow,created_date att_date FROM org_users u  LEFT JOIN org_userattendance1 ON (o_usrAtndns_usrId=u.o_usr_id   AND DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d')=DATE_FORMAT(?,'%Y-%c-%d'))     LEFT JOIN iertattendance1 ie ON (u.o_usr_id=ie.o_usr_id AND DATE_FORMAT(att_date,'%Y-%c-%d')=DATE_FORMAT(?,'%Y-%c-%d'))  WHERE  o_usr_activestatus='Y' AND o_usr_type =3  AND o_usr_orgid=? and u.o_usr_id='"+userId+"') a GROUP BY o_usr_id";

		}
		else{
			 query = "SELECT DISTINCT  o_usr_id,CONCAT(o_usr_fn,' ',o_usr_ln) o_usr_fn,'' o_usr_mn ,IFNULL(o_usr_sphoto,'') o_usr_sphoto  ,MAX(CASE WHEN dt=0 THEN STATUS ELSE '' END) 'mnStatus' ,MAX(CASE WHEN dt=1 THEN STATUS ELSE '' END) 'fnStatus'    ,MAX(CASE WHEN dt=0 THEN longitude ELSE '' END) 'mnLng' ,MAX(CASE WHEN dt=1 THEN longitude ELSE '' END) 'fnLng'    ,MAX(CASE WHEN dt=0 THEN latitude ELSE '' END) 'mnLat' ,MAX(CASE WHEN dt=1 THEN latitude ELSE '' END) 'fnLat'   ,MAX(CASE WHEN dt=0 THEN att_date ELSE '' END) 'mnStatuson' ,MAX(CASE WHEN dt=1 THEN att_date ELSE '' END) 'fnStatuson'    ,MAX(CASE WHEN dt=0 AND STATUS='' THEN hideshow ELSE 0 END) 'MN' ,MAX(CASE WHEN dt=1 AND STATUS='' THEN hideshow ELSE 0 END) 'FN'    FROM (SELECT DISTINCT u.o_usr_id AS o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn ,IFNULL(o_usr_ln,'') AS o_usr_ln ,  CASE WHEN o_usrAtndns_d?='P' THEN 'Present'  WHEN o_usrAtndns_d?='A' THEN 'Absent'   WHEN o_usrAtndns_d?='L' THEN 'Leave'    WHEN o_usrAtndns_d?='O' THEN 'On Duty'   WHEN o_usrAtndns_d?='S' THEN 'Half Day Leave' WHEN o_usrAtndns_d?='C' THEN 'Casual Leave'  WHEN o_usrAtndns_d?='W' THEN 'NA'  WHEN o_usrAtndns_d?='D' THEN 'Optional Holiday'  WHEN o_usrAtndns_d?='H' THEN 'Holiday'   WHEN o_usrAtndns_d?='M' THEN 'Maternity Leave'  WHEN o_usrAtndns_d?='F' THEN 'Comp Off'   WHEN o_usrAtndns_d?='G' THEN 'LOPMG' ELSE '' END AS STATUS  , longitude, latitude,o_usr_sphoto, 0 dt ,CASE WHEN DATE_FORMAT(SYSDATE(),'%H:%i:%s') BETWEEN '07:00:00' AND '12:59:59' THEN 1 ELSE 0 END hideshow ,created_date att_date FROM org_users u  LEFT JOIN org_userattendance ON (o_usrAtndns_usrId=u.o_usr_id AND DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d')=DATE_FORMAT(?,'%Y-%c-%d'))     LEFT JOIN iertattendance ie ON (u.o_usr_id=ie.o_usr_id AND DATE_FORMAT(att_date,'%Y-%c-%d')=DATE_FORMAT(?,'%Y-%c-%d'))  WHERE  o_usr_activestatus='Y' AND o_usr_type =3  AND o_usr_orgid=?   UNION ALL SELECT DISTINCT u.o_usr_id AS o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn,IFNULL(o_usr_ln,'') AS o_usr_ln , CASE WHEN o_usrAtndns_d?='P' THEN 'Present' WHEN o_usrAtndns_d?='A' THEN 'Absent'  WHEN o_usrAtndns_d?='L' THEN 'Leave'   WHEN o_usrAtndns_d?='O' THEN 'On Duty' WHEN o_usrAtndns_d?='S' THEN 'Half Day Leave' WHEN o_usrAtndns_d?='C' THEN 'Casual Leave' WHEN o_usrAtndns_d?='W' THEN 'NA' WHEN o_usrAtndns_d?='D' THEN 'Optional Holiday' WHEN o_usrAtndns_d?='H' THEN 'Holiday'  WHEN o_usrAtndns_d?='M' THEN 'Maternity Leave' WHEN o_usrAtndns_d?='F' THEN 'Comp Off'  WHEN o_usrAtndns_d?='G' THEN 'LOPMG' ELSE '' END AS STATUS  , longitude, latitude,o_usr_sphoto,1 dt ,CASE WHEN DATE_FORMAT(SYSDATE(),'%H:%i:%s') BETWEEN '13:00:00' AND '22:59:59' THEN 1 ELSE 0 END hideshow,created_date att_date FROM org_users u  LEFT JOIN org_userattendance1 ON (o_usrAtndns_usrId=u.o_usr_id   AND DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d')=DATE_FORMAT(?,'%Y-%c-%d'))     LEFT JOIN iertattendance1 ie ON (u.o_usr_id=ie.o_usr_id AND DATE_FORMAT(att_date,'%Y-%c-%d')=DATE_FORMAT(?,'%Y-%c-%d'))  WHERE  o_usr_activestatus='Y' AND o_usr_type =3  AND o_usr_orgid=? ) a GROUP BY o_usr_id";

		//	 query = "SELECT DISTINCT  o_usr_id,CONCAT(o_usr_fn,' ',o_usr_ln) o_usr_fn ,IFNULL(o_usr_sphoto,'') o_usr_sphoto ,MAX(CASE WHEN dt=0 THEN STATUS ELSE 0 END) 'mnStatus' ,MAX(CASE WHEN dt=1 THEN STATUS ELSE 0 END) 'fnStatus'   ,MAX(CASE WHEN dt=0 THEN longitude ELSE 0 END) 'mnLng' ,MAX(CASE WHEN dt=1 THEN longitude ELSE 0 END) 'fnLng'   ,MAX(CASE WHEN dt=0 THEN latitude ELSE 0 END) 'mnLat' ,MAX(CASE WHEN dt=1 THEN latitude ELSE 0 END) 'fnLat'  ,MAX(CASE WHEN dt=0 THEN latitude ELSE 0 END) 'mnStatuson' ,MAX(CASE WHEN dt=1 THEN latitude ELSE 0 END) 'fnStatuson'   ,MAX(CASE WHEN dt=0 THEN hideshow ELSE 0 END) 'MN' ,MAX(CASE WHEN dt=1 THEN hideshow ELSE 0 END) 'FN'   FROM (SELECT DISTINCT u.o_usr_id AS o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn,IFNULL(o_usr_ln,'') AS o_usr_ln , CASE WHEN o_usrAtndns_d1='P' THEN 'Present' WHEN o_usrAtndns_d1='A' THEN 'Absent'  ELSE 0 END AS STATUS , longitude, latitude,o_usr_sphoto, 0 dt,CASE WHEN DATE_FORMAT(SYSDATE(),'%H:%i:%s') BETWEEN '09:00:00' AND '12:59:59' THEN 1 ELSE 0 END hideshow FROM org_users u  LEFT JOIN org_userattendance ON (o_usrAtndns_usrId=u.o_usr_id  AND DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d')=DATE_FORMAT('2022-03-01','2022-03-01'))     LEFT JOIN iertattendance ie ON (u.o_usr_id=ie.o_usr_id AND DATE_FORMAT(att_date,'%Y-%c-%d')=DATE_FORMAT('2022-03-01','2022-03-01'))  WHERE  o_usr_activestatus='Y' AND o_usr_type =3  AND o_usr_orgid=22839   UNION ALL SELECT DISTINCT u.o_usr_id AS o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn,IFNULL(o_usr_ln,'') AS o_usr_ln , CASE WHEN o_usrAtndns_d1='P' THEN 'Present' WHEN o_usrAtndns_d1='A' THEN 'Absent'  ELSE 0 END AS STATUS , longitude, latitude,o_usr_sphoto,1 dt ,CASE WHEN DATE_FORMAT(SYSDATE(),'%H:%i:%s') BETWEEN '13:00:00' AND '18:59:59' THEN 1 ELSE 0 END hideshow   FROM org_users u  LEFT JOIN org_userattendance1 ON (o_usrAtndns_usrId=u.o_usr_id  AND DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d')=DATE_FORMAT('2022-03-01','2022-03-01'))     LEFT JOIN iertattendance1 ie ON (u.o_usr_id=ie.o_usr_id AND DATE_FORMAT(att_date,'%Y-%c-%d')=DATE_FORMAT('2022-03-01','2022-03-01'))  WHERE  o_usr_activestatus='Y' AND o_usr_type =3  AND o_usr_orgid=22839) a GROUP BY o_usr_id";

		}
		/*String query = "SELECT o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn,IFNULL(o_usr_ln,'') AS o_usr_ln,o_usrAtndns_d? AS STATUS FROM org_users  LEFT JOIN org_userattendance ON o_usrAtndns_usrId=o_usr_id  AND o_usrAtndns_date=? WHERE  o_usr_activestatus='Y' AND o_usr_type=3 AND o_usr_orgid=? ORDER BY o_usr_rollno,o_usr_id ";*/
		//query="SELECT o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn,IFNULL(o_usr_ln,'') AS o_usr_ln,CASE WHEN o_usrAtndns_d?='P' THEN 'Present'  WHEN o_usrAtndns_d?='A' THEN 'Absent'   WHEN o_usrAtndns_d?='L' THEN 'Leave'   WHEN o_usrAtndns_d?='O' THEN 'On Duty' WHEN o_usrAtndns_d?='S' THEN 'Half Day Leave' WHEN o_usrAtndns_d?='C' THEN 'Casual Leave' WHEN o_usrAtndns_d?='W' THEN 'NA' WHEN o_usrAtndns_d?='D' THEN 'Optional Holiday' WHEN o_usrAtndns_d?='H' THEN 'Holiday' WHEN o_usrAtndns_d?='M' THEN 'Maternity Leave' WHEN o_usrAtndns_d?='F' THEN 'Comp Off' WHEN o_usrAtndns_d?='G' THEN 'LOPMG' END AS STATUS,o_role_title as role FROM org_users  LEFT JOIN org_userattendance ON (o_usrAtndns_usrId=o_usr_id AND o_usrAtndns_date=?)  left join org_roles on (o_usr_type = o_role_id and o_usr_orgid = o_role_orgid) WHERE  o_usr_activestatus='Y' AND o_usr_type =8 AND o_usr_orgid=? ORDER BY o_usr_rollno,o_usr_id";
	
		List<TeacherAttendanceDTO> teachers = new ArrayList<TeacherAttendanceDTO>();
		Connection conn = null;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
		java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat("d");
		java.text.SimpleDateFormat sdt = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String s[] = (sdt.format(sdf.parse(date))).split("-");
		String date1 = s[0] + "-" + Integer.parseInt(s[1]) + "-01";
		String date2 = s[0]+"-"+Integer.parseInt(s[1]) +"-"+ Integer.parseInt(s[2]) ;
		try {
			conn = JdbcConnectionManager.getConnection(jdbcTemplate);
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(2, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(3, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(4, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(5, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(6, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(7, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(8, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(9, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(10, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(11, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(12, Integer.parseInt(sd.format(sdf.parse(date))));
			
			pst.setString(13, date1);
			pst.setString(14,date2);
			pst.setLong(15, Long.parseLong(orgId));
	
			
			pst.setInt(16, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(17, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(18, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(19, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(20, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(21, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(22, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(23, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(24, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(25, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(26, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(27, Integer.parseInt(sd.format(sdf.parse(date))));
			
			pst.setString(28, date1);
			pst.setString(29,date2);
			pst.setLong(30, Long.parseLong(orgId));
			
			//		if (role.equalsIgnoreCase("3")) {
		//	pst.setString(16, userId);
		//	}
			
			ResultSet rs = pst.executeQuery();
			System.out.println(pst);
			while (rs.next()) {
				TeacherAttendanceDTO teacher = new TeacherAttendanceDTO();
				teacher.setUserId(rs.getString("o_usr_id"));
				teacher.setStdFirstName(rs.getString("o_usr_fn"));
				teacher.setStdMiddleName(rs.getString("o_usr_mn"));
				teacher.setStdLastName(rs.getString("o_usr_mn"));
				//teacher.setStdattendStatus(rs.getString("status"));
				teacher.setRole("");
				//teacher.setLatitude(rs.getFloat("latitude"));
				//teacher.setLongitude(rs.getFloat("longitude"));
				teacher.setPhoto(rs.getBytes("o_usr_sphoto"));
				
				teacher.setFnLat(rs.getString("fnLat"));
				teacher.setFnLng(rs.getString("fnLng"));
				teacher.setFnStatus(rs.getString("fnStatus"));
				teacher.setFnStatusOn(rs.getString("fnStatuson"));
				teacher.setHideShowFn(rs.getInt("FN"));
				teacher.setHideShowMn(rs.getInt("MN"));
				teacher.setMnLat(rs.getString("mnLat"));
				teacher.setMnLng(rs.getString("mnLng"));
				teacher.setMnStatus(rs.getString("mnStatus"));
				teacher.setMnStatusOn(rs.getString("mnStatuson"));
				System.out.println("kriskhdsdjs"+rs.getString("FN"));
				teachers.add(teacher);
			}
			JdbcConnectionManager.CloseResultSet(rs);
			JdbcConnectionManager.ClosePreparedStatement(pst);
			return teachers;
		} catch (Exception e) {
			logger.debug("Error while reading Teacher List", e);

		} finally {
			JdbcConnectionManager.Close(conn);
		}
		return null;

	}

	
	public List<TeacherAttendanceDTO> getTeacherListFacex(String orgId, String date,String userId) throws Exception {
		System.out.println("vamsi"+userId);
		String query1 = "SELECT o_usr_type from org_users where o_usr_id=? ";
		String role="";
		Connection conn1 = null;
		conn1= JdbcConnectionManager.getConnection(jdbcTemplate);
		PreparedStatement pst1 = conn1.prepareStatement(query1);
		pst1.setString(1, userId);
		ResultSet rs1 = pst1.executeQuery();
		while (rs1.next()) {
		role=rs1.getString("o_usr_type");
			System.out.println("krishna"+role);
		}
		
		String query="";
			System.out.println("krishna11"+role);
		if (role.equalsIgnoreCase("3")) {
		// query = "SELECT u.o_usr_id as o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn,IFNULL(o_usr_ln,'') AS o_usr_ln, CASE WHEN o_usrAtndns_d?='P' THEN 'Present' WHEN o_usrAtndns_d?='A' THEN 'Absent'  WHEN o_usrAtndns_d?='L' THEN 'Leave'   WHEN o_usrAtndns_d?='O' THEN 'On Duty' WHEN o_usrAtndns_d?='S' THEN 'Half Day Leave' WHEN o_usrAtndns_d?='C' THEN 'Casual Leave' WHEN o_usrAtndns_d?='W' THEN 'NA' WHEN o_usrAtndns_d?='D' THEN 'Optional Holiday' WHEN o_usrAtndns_d?='H' THEN 'Holiday' WHEN o_usrAtndns_d?='M' THEN 'Maternity Leave' WHEN o_usrAtndns_d?='F' THEN 'Comp Off' WHEN o_usrAtndns_d?='G' THEN 'LOPMG' END AS STATUS, longitude, latitude,o_usr_sphoto  FROM org_users u LEFT JOIN org_userattendance ON (o_usrAtndns_usrId=u.o_usr_id AND date_format(o_usrAtndns_date,'%Y-%c-%d')=date_format(?,'%Y-%c-%d'))    left join iertattendance ie on (u.o_usr_id=ie.o_usr_id and date_format(att_date,'%Y-%c-%d')=date_format(?,'%Y-%c-%d')) WHERE  o_usr_activestatus='Y' AND o_usr_type =3 AND o_usr_orgid=? and u.o_usr_id=? ORDER BY u.o_usr_id";
		// query = "SELECT DISTINCT  o_usr_id,CONCAT(o_usr_fn,' ',o_usr_ln) o_usr_fn ,IFNULL(o_usr_sphoto,'') o_usr_sphoto  ,MAX(CASE WHEN dt=0 THEN STATUS ELSE 0 END) 'mnStatus' ,MAX(CASE WHEN dt=1 THEN STATUS ELSE 0 END) 'fnStatus'   ,MAX(CASE WHEN dt=0 THEN longitude ELSE 0 END) 'mnLng' ,MAX(CASE WHEN dt=1 THEN longitude ELSE 0 END) 'fnLng'   ,MAX(CASE WHEN dt=0 THEN latitude ELSE 0 END) 'mnLat' ,MAX(CASE WHEN dt=1 THEN latitude ELSE 0 END) 'fnLat'  ,MAX(CASE WHEN dt=0 THEN latitude ELSE 0 END) 'mnStatuson' ,MAX(CASE WHEN dt=1 THEN latitude ELSE 0 END) 'fnStatuson'   ,MAX(CASE WHEN dt=0 THEN hideshow ELSE 0 END) 'MN' ,MAX(CASE WHEN dt=1 THEN hideshow ELSE 0 END) 'FN'   FROM (SELECT DISTINCT u.o_usr_id AS o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn,IFNULL(o_usr_ln,'') AS o_usr_ln , CASE WHEN o_usrAtndns_d?='P' THEN 'Present' WHEN o_usrAtndns_d?='A' THEN 'Absent'  WHEN o_usrAtndns_d?='L' THEN 'Leave'   WHEN o_usrAtndns_d?='O' THEN 'On Duty' WHEN o_usrAtndns_d?='S' THEN 'Half Day Leave' WHEN o_usrAtndns_d?='C' THEN 'Casual Leave' WHEN o_usrAtndns_d?='W' THEN 'NA' WHEN o_usrAtndns_d?='D' THEN 'Optional Holiday' WHEN o_usrAtndns_d?='H' THEN 'Holiday'  WHEN o_usrAtndns_d?='M' THEN 'Maternity Leave' WHEN o_usrAtndns_d?='F' THEN 'Comp Off'  WHEN o_usrAtndns_d?='G' THEN 'LOPMG' ELSE 0 END AS STATUS , longitude, latitude,o_usr_sphoto, 0 dt,CASE WHEN DATE_FORMAT(SYSDATE(),'%H:%i:%s') BETWEEN '09:00:00' AND '12:59:59' THEN 1 ELSE 0 END hideshow FROM org_users u  LEFT JOIN org_userattendance ON (o_usrAtndns_usrId=u.o_usr_id  AND DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d')=DATE_FORMAT('2022-03-01','2022-03-01'))     LEFT JOIN iertattendance ie ON (u.o_usr_id=ie.o_usr_id AND DATE_FORMAT(att_date,'%Y-%c-%d')=DATE_FORMAT('2022-03-01','2022-03-01'))  WHERE  o_usr_activestatus='Y' AND o_usr_type =3  AND o_usr_orgid=22839   UNION ALL SELECT DISTINCT u.o_usr_id AS o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn,IFNULL(o_usr_ln,'') AS o_usr_ln , CASE WHEN o_usrAtndns_d1='P' THEN 'Present' WHEN o_usrAtndns_d1='A' THEN 'Absent'  ELSE 0 END AS STATUS , longitude, latitude,o_usr_sphoto,1 dt ,CASE WHEN DATE_FORMAT(SYSDATE(),'%H:%i:%s') BETWEEN '13:00:00' AND '18:59:59' THEN 1 ELSE 0 END hideshow   FROM org_users u  LEFT JOIN org_userattendance1 ON (o_usrAtndns_usrId=u.o_usr_id  AND DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d')=DATE_FORMAT('2022-03-01','2022-03-01'))     LEFT JOIN iertattendance1 ie ON (u.o_usr_id=ie.o_usr_id AND DATE_FORMAT(att_date,'%Y-%c-%d')=DATE_FORMAT('2022-03-01','2022-03-01'))  WHERE  o_usr_activestatus='Y' AND o_usr_type =3  AND o_usr_orgid=22839) a GROUP BY o_usr_id";
// 		 query = "SELECT DISTINCT  o_usr_id,CONCAT(o_usr_fn,' ',o_usr_ln) o_usr_fn,'' o_usr_mn ,IFNULL(o_usr_sphoto,'') o_usr_sphoto  ,MAX(CASE WHEN dt=0 THEN STATUS ELSE '' END) 'mnStatus' ,MAX(CASE WHEN dt=1 THEN STATUS ELSE '' END) 'fnStatus'    ,MAX(CASE WHEN dt=0 THEN longitude ELSE '' END) 'mnLng' ,MAX(CASE WHEN dt=1 THEN longitude ELSE '' END) 'fnLng'    ,MAX(CASE WHEN dt=0 THEN latitude ELSE '' END) 'mnLat' ,MAX(CASE WHEN dt=1 THEN latitude ELSE '' END) 'fnLat'   ,MAX(CASE WHEN dt=0 THEN att_date ELSE '' END) 'mnStatuson' ,MAX(CASE WHEN dt=1 THEN att_date ELSE '' END) 'fnStatuson'    ,MAX(CASE WHEN dt=0 AND STATUS='' THEN hideshow ELSE 0 END) 'MN' ,MAX(CASE WHEN dt=1 AND STATUS='' THEN hideshow ELSE 0 END) 'FN'    FROM (SELECT DISTINCT u.o_usr_id AS o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn ,IFNULL(o_usr_ln,'') AS o_usr_ln ,  CASE WHEN o_usrAtndns_d?='P' THEN 'Present'  WHEN o_usrAtndns_d?='A' THEN 'Absent'   WHEN o_usrAtndns_d?='L' THEN 'Leave'    WHEN o_usrAtndns_d?='O' THEN 'On Duty'   WHEN o_usrAtndns_d?='S' THEN 'Half Day Leave' WHEN o_usrAtndns_d?='C' THEN 'Casual Leave'  WHEN o_usrAtndns_d?='W' THEN 'NA'  WHEN o_usrAtndns_d?='D' THEN 'Optional Holiday'  WHEN o_usrAtndns_d?='H' THEN 'Holiday'   WHEN o_usrAtndns_d?='M' THEN 'Maternity Leave'  WHEN o_usrAtndns_d?='F' THEN 'Comp Off'   WHEN o_usrAtndns_d?='G' THEN 'LOPMG' ELSE '' END AS STATUS  , longitude, latitude,o_usr_sphoto, 0 dt ,CASE WHEN DATE_FORMAT(SYSDATE(),'%H:%i:%s') BETWEEN '07:00:00' AND '12:59:59' THEN 1 ELSE 0 END hideshow ,created_date att_date FROM org_users u  LEFT JOIN org_userattendance ON (o_usrAtndns_usrId=u.o_usr_id AND DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d')=DATE_FORMAT(?,'%Y-%c-%d'))     LEFT JOIN iertattendance ie ON (u.o_usr_id=ie.o_usr_id AND DATE_FORMAT(att_date,'%Y-%c-%d')=DATE_FORMAT(?,'%Y-%c-%d'))  WHERE  o_usr_activestatus='Y' AND o_usr_type =3  AND o_usr_orgid=? and u.o_usr_id='"+userId+"'   UNION ALL SELECT DISTINCT u.o_usr_id AS o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn,IFNULL(o_usr_ln,'') AS o_usr_ln , CASE WHEN o_usrAtndns_d?='P' THEN 'Present' WHEN o_usrAtndns_d?='A' THEN 'Absent'  WHEN o_usrAtndns_d?='L' THEN 'Leave'   WHEN o_usrAtndns_d?='O' THEN 'On Duty' WHEN o_usrAtndns_d?='S' THEN 'Half Day Leave' WHEN o_usrAtndns_d?='C' THEN 'Casual Leave' WHEN o_usrAtndns_d?='W' THEN 'NA' WHEN o_usrAtndns_d?='D' THEN 'Optional Holiday' WHEN o_usrAtndns_d?='H' THEN 'Holiday'  WHEN o_usrAtndns_d?='M' THEN 'Maternity Leave' WHEN o_usrAtndns_d?='F' THEN 'Comp Off'  WHEN o_usrAtndns_d?='G' THEN 'LOPMG' ELSE '' END AS STATUS  , longitude, latitude,o_usr_sphoto,1 dt ,CASE WHEN DATE_FORMAT(SYSDATE(),'%H:%i:%s') BETWEEN '13:00:00' AND '22:59:59' THEN 1 ELSE 0 END hideshow,created_date att_date FROM org_users u  LEFT JOIN org_userattendance1 ON (o_usrAtndns_usrId=u.o_usr_id   AND DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d')=DATE_FORMAT(?,'%Y-%c-%d'))     LEFT JOIN iertattendance1 ie ON (u.o_usr_id=ie.o_usr_id AND DATE_FORMAT(att_date,'%Y-%c-%d')=DATE_FORMAT(?,'%Y-%c-%d'))  WHERE  o_usr_activestatus='Y' AND o_usr_type =3  AND o_usr_orgid=? and u.o_usr_id='"+userId+"') a GROUP BY o_usr_id";
     query="SELECT DISTINCT  o_usr_id,CONCAT(o_usr_fn,' ',o_usr_ln) o_usr_fn,'' o_usr_mn ,'' o_usr_sphoto  ,'' AS 'fnStatus' ,'' AS 'fnLng'    ,'' AS 'fnLat'   ,'' AS 'fnStatuson' ,'' AS 'FN'    FROM org_users u    WHERE  o_usr_activestatus='Y' AND o_usr_type =3  AND o_usr_orgid=? AND and u.o_usr_id='"+userId+"' and  NOT EXISTS (SELECT 1 FROM facecount1 a WHERE a.usrid=u.o_usr_id)";
			
		}
		else{
	//		 query = "SELECT DISTINCT  o_usr_id,CONCAT(o_usr_fn,' ',o_usr_ln) o_usr_fn,'' o_usr_mn ,IFNULL(o_usr_sphoto,'') o_usr_sphoto  ,MAX(CASE WHEN dt=0 THEN STATUS ELSE '' END) 'mnStatus' ,MAX(CASE WHEN dt=1 THEN STATUS ELSE '' END) 'fnStatus'    ,MAX(CASE WHEN dt=0 THEN longitude ELSE '' END) 'mnLng' ,MAX(CASE WHEN dt=1 THEN longitude ELSE '' END) 'fnLng'    ,MAX(CASE WHEN dt=0 THEN latitude ELSE '' END) 'mnLat' ,MAX(CASE WHEN dt=1 THEN latitude ELSE '' END) 'fnLat'   ,MAX(CASE WHEN dt=0 THEN att_date ELSE '' END) 'mnStatuson' ,MAX(CASE WHEN dt=1 THEN att_date ELSE '' END) 'fnStatuson'    ,MAX(CASE WHEN dt=0 AND STATUS='' THEN hideshow ELSE 0 END) 'MN' ,MAX(CASE WHEN dt=1 AND STATUS='' THEN hideshow ELSE 0 END) 'FN'    FROM (SELECT DISTINCT u.o_usr_id AS o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn ,IFNULL(o_usr_ln,'') AS o_usr_ln ,  CASE WHEN o_usrAtndns_d?='P' THEN 'Present'  WHEN o_usrAtndns_d?='A' THEN 'Absent'   WHEN o_usrAtndns_d?='L' THEN 'Leave'    WHEN o_usrAtndns_d?='O' THEN 'On Duty'   WHEN o_usrAtndns_d?='S' THEN 'Half Day Leave' WHEN o_usrAtndns_d?='C' THEN 'Casual Leave'  WHEN o_usrAtndns_d?='W' THEN 'NA'  WHEN o_usrAtndns_d?='D' THEN 'Optional Holiday'  WHEN o_usrAtndns_d?='H' THEN 'Holiday'   WHEN o_usrAtndns_d?='M' THEN 'Maternity Leave'  WHEN o_usrAtndns_d?='F' THEN 'Comp Off'   WHEN o_usrAtndns_d?='G' THEN 'LOPMG' ELSE '' END AS STATUS  , longitude, latitude,o_usr_sphoto, 0 dt ,CASE WHEN DATE_FORMAT(SYSDATE(),'%H:%i:%s') BETWEEN '07:00:00' AND '12:59:59' THEN 1 ELSE 0 END hideshow ,created_date att_date FROM org_users u  LEFT JOIN org_userattendance ON (o_usrAtndns_usrId=u.o_usr_id AND DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d')=DATE_FORMAT(?,'%Y-%c-%d'))     LEFT JOIN iertattendance ie ON (u.o_usr_id=ie.o_usr_id AND DATE_FORMAT(att_date,'%Y-%c-%d')=DATE_FORMAT(?,'%Y-%c-%d'))  WHERE  o_usr_activestatus='Y' AND o_usr_type =3  AND o_usr_orgid=?   UNION ALL SELECT DISTINCT u.o_usr_id AS o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn,IFNULL(o_usr_ln,'') AS o_usr_ln , CASE WHEN o_usrAtndns_d?='P' THEN 'Present' WHEN o_usrAtndns_d?='A' THEN 'Absent'  WHEN o_usrAtndns_d?='L' THEN 'Leave'   WHEN o_usrAtndns_d?='O' THEN 'On Duty' WHEN o_usrAtndns_d?='S' THEN 'Half Day Leave' WHEN o_usrAtndns_d?='C' THEN 'Casual Leave' WHEN o_usrAtndns_d?='W' THEN 'NA' WHEN o_usrAtndns_d?='D' THEN 'Optional Holiday' WHEN o_usrAtndns_d?='H' THEN 'Holiday'  WHEN o_usrAtndns_d?='M' THEN 'Maternity Leave' WHEN o_usrAtndns_d?='F' THEN 'Comp Off'  WHEN o_usrAtndns_d?='G' THEN 'LOPMG' ELSE '' END AS STATUS  , longitude, latitude,o_usr_sphoto,1 dt ,CASE WHEN DATE_FORMAT(SYSDATE(),'%H:%i:%s') BETWEEN '13:00:00' AND '22:59:59' THEN 1 ELSE 0 END hideshow,created_date att_date FROM org_users u  LEFT JOIN org_userattendance1 ON (o_usrAtndns_usrId=u.o_usr_id   AND DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d')=DATE_FORMAT(?,'%Y-%c-%d'))     LEFT JOIN iertattendance1 ie ON (u.o_usr_id=ie.o_usr_id AND DATE_FORMAT(att_date,'%Y-%c-%d')=DATE_FORMAT(?,'%Y-%c-%d'))  WHERE  o_usr_activestatus='Y' AND o_usr_type =3  AND o_usr_orgid=? ) a GROUP BY o_usr_id";

			  query="SELECT DISTINCT  o_usr_id,CONCAT(o_usr_fn,' ',o_usr_ln) o_usr_fn,'' o_usr_mn ,'' o_usr_sphoto  ,'' AS 'fnStatus' ,'' AS 'fnLng'    ,'' AS 'fnLat'   ,'' AS 'fnStatuson' ,'' AS 'FN'    FROM org_users u    WHERE  o_usr_activestatus='Y' AND o_usr_type =3  AND o_usr_orgid=?  and  NOT EXISTS (SELECT 1 FROM facecount1 a WHERE a.usrid=u.o_usr_id)";
				
			
		//	 query = "SELECT DISTINCT  o_usr_id,CONCAT(o_usr_fn,' ',o_usr_ln) o_usr_fn ,IFNULL(o_usr_sphoto,'') o_usr_sphoto ,MAX(CASE WHEN dt=0 THEN STATUS ELSE 0 END) 'mnStatus' ,MAX(CASE WHEN dt=1 THEN STATUS ELSE 0 END) 'fnStatus'   ,MAX(CASE WHEN dt=0 THEN longitude ELSE 0 END) 'mnLng' ,MAX(CASE WHEN dt=1 THEN longitude ELSE 0 END) 'fnLng'   ,MAX(CASE WHEN dt=0 THEN latitude ELSE 0 END) 'mnLat' ,MAX(CASE WHEN dt=1 THEN latitude ELSE 0 END) 'fnLat'  ,MAX(CASE WHEN dt=0 THEN latitude ELSE 0 END) 'mnStatuson' ,MAX(CASE WHEN dt=1 THEN latitude ELSE 0 END) 'fnStatuson'   ,MAX(CASE WHEN dt=0 THEN hideshow ELSE 0 END) 'MN' ,MAX(CASE WHEN dt=1 THEN hideshow ELSE 0 END) 'FN'   FROM (SELECT DISTINCT u.o_usr_id AS o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn,IFNULL(o_usr_ln,'') AS o_usr_ln , CASE WHEN o_usrAtndns_d1='P' THEN 'Present' WHEN o_usrAtndns_d1='A' THEN 'Absent'  ELSE 0 END AS STATUS , longitude, latitude,o_usr_sphoto, 0 dt,CASE WHEN DATE_FORMAT(SYSDATE(),'%H:%i:%s') BETWEEN '09:00:00' AND '12:59:59' THEN 1 ELSE 0 END hideshow FROM org_users u  LEFT JOIN org_userattendance ON (o_usrAtndns_usrId=u.o_usr_id  AND DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d')=DATE_FORMAT('2022-03-01','2022-03-01'))     LEFT JOIN iertattendance ie ON (u.o_usr_id=ie.o_usr_id AND DATE_FORMAT(att_date,'%Y-%c-%d')=DATE_FORMAT('2022-03-01','2022-03-01'))  WHERE  o_usr_activestatus='Y' AND o_usr_type =3  AND o_usr_orgid=22839   UNION ALL SELECT DISTINCT u.o_usr_id AS o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn,IFNULL(o_usr_ln,'') AS o_usr_ln , CASE WHEN o_usrAtndns_d1='P' THEN 'Present' WHEN o_usrAtndns_d1='A' THEN 'Absent'  ELSE 0 END AS STATUS , longitude, latitude,o_usr_sphoto,1 dt ,CASE WHEN DATE_FORMAT(SYSDATE(),'%H:%i:%s') BETWEEN '13:00:00' AND '18:59:59' THEN 1 ELSE 0 END hideshow   FROM org_users u  LEFT JOIN org_userattendance1 ON (o_usrAtndns_usrId=u.o_usr_id  AND DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d')=DATE_FORMAT('2022-03-01','2022-03-01'))     LEFT JOIN iertattendance1 ie ON (u.o_usr_id=ie.o_usr_id AND DATE_FORMAT(att_date,'%Y-%c-%d')=DATE_FORMAT('2022-03-01','2022-03-01'))  WHERE  o_usr_activestatus='Y' AND o_usr_type =3  AND o_usr_orgid=22839) a GROUP BY o_usr_id";

		}
		/*String query = "SELECT o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn,IFNULL(o_usr_ln,'') AS o_usr_ln,o_usrAtndns_d? AS STATUS FROM org_users  LEFT JOIN org_userattendance ON o_usrAtndns_usrId=o_usr_id  AND o_usrAtndns_date=? WHERE  o_usr_activestatus='Y' AND o_usr_type=3 AND o_usr_orgid=? ORDER BY o_usr_rollno,o_usr_id ";*/
		//query="SELECT o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn,IFNULL(o_usr_ln,'') AS o_usr_ln,CASE WHEN o_usrAtndns_d?='P' THEN 'Present'  WHEN o_usrAtndns_d?='A' THEN 'Absent'   WHEN o_usrAtndns_d?='L' THEN 'Leave'   WHEN o_usrAtndns_d?='O' THEN 'On Duty' WHEN o_usrAtndns_d?='S' THEN 'Half Day Leave' WHEN o_usrAtndns_d?='C' THEN 'Casual Leave' WHEN o_usrAtndns_d?='W' THEN 'NA' WHEN o_usrAtndns_d?='D' THEN 'Optional Holiday' WHEN o_usrAtndns_d?='H' THEN 'Holiday' WHEN o_usrAtndns_d?='M' THEN 'Maternity Leave' WHEN o_usrAtndns_d?='F' THEN 'Comp Off' WHEN o_usrAtndns_d?='G' THEN 'LOPMG' END AS STATUS,o_role_title as role FROM org_users  LEFT JOIN org_userattendance ON (o_usrAtndns_usrId=o_usr_id AND o_usrAtndns_date=?)  left join org_roles on (o_usr_type = o_role_id and o_usr_orgid = o_role_orgid) WHERE  o_usr_activestatus='Y' AND o_usr_type =8 AND o_usr_orgid=? ORDER BY o_usr_rollno,o_usr_id";
	
		List<TeacherAttendanceDTO> teachers = new ArrayList<TeacherAttendanceDTO>();
		Connection conn = null;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
		java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat("d");
		java.text.SimpleDateFormat sdt = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String s[] = (sdt.format(sdf.parse(date))).split("-");
		String date1 = s[0] + "-" + Integer.parseInt(s[1]) + "-01";
		String date2 = s[0]+"-"+Integer.parseInt(s[1]) +"-"+ Integer.parseInt(s[2]) ;
		try {
			conn = JdbcConnectionManager.getConnection(jdbcTemplate);
			PreparedStatement pst = conn.prepareStatement(query);
//			pst.setInt(1, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(2, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(3, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(4, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(5, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(6, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(7, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(8, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(9, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(10, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(11, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(12, Integer.parseInt(sd.format(sdf.parse(date))));
//			
//			pst.setString(13, date1);
//			pst.setString(14,date2);
			pst.setLong(1, Long.parseLong(orgId));
	
			
//			pst.setInt(16, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(17, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(18, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(19, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(20, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(21, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(22, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(23, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(24, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(25, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(26, Integer.parseInt(sd.format(sdf.parse(date))));
//			pst.setInt(27, Integer.parseInt(sd.format(sdf.parse(date))));
//			
//			pst.setString(28, date1);
//			pst.setString(29,date2);
//			pst.setLong(30, Long.parseLong(orgId));
			
			//		if (role.equalsIgnoreCase("3")) {
		//	pst.setString(16, userId);
		//	}
			
			ResultSet rs = pst.executeQuery();
			System.out.println(pst);
			while (rs.next()) {
				TeacherAttendanceDTO teacher = new TeacherAttendanceDTO();
				teacher.setUserId(rs.getString("o_usr_id"));
				teacher.setStdFirstName(rs.getString("o_usr_fn"));
				teacher.setStdMiddleName(rs.getString("o_usr_mn"));
				teacher.setStdLastName(rs.getString("o_usr_mn"));
				//teacher.setStdattendStatus(rs.getString("status"));
				teacher.setRole("");
				//teacher.setLatitude(rs.getFloat("latitude"));
				//teacher.setLongitude(rs.getFloat("longitude"));
//				teacher.setPhoto(rs.getBytes("o_usr_sphoto"));
//				
//				teacher.setFnLat(rs.getString("fnLat"));
//				teacher.setFnLng(rs.getString("fnLng"));
//				teacher.setFnStatus(rs.getString("fnStatus"));
//				teacher.setFnStatusOn(rs.getString("fnStatuson"));
//				teacher.setHideShowFn(rs.getInt("FN"));
//				teacher.setHideShowMn(rs.getInt("MN"));
//				teacher.setMnLat(rs.getString("mnLat"));
//				teacher.setMnLng(rs.getString("mnLng"));
//				teacher.setMnStatus(rs.getString("mnStatus"));
//				teacher.setMnStatusOn(rs.getString("mnStatuson"));
//				System.out.println("kriskhdsdjs"+rs.getString("FN"));
				teachers.add(teacher);
			}
			JdbcConnectionManager.CloseResultSet(rs);
			JdbcConnectionManager.ClosePreparedStatement(pst);
			return teachers;
		} catch (Exception e) {
			logger.debug("Error while reading Teacher List", e);

		} finally {
			JdbcConnectionManager.Close(conn);
		}
		return null;

	}

	
	
	
	
	@Override
	public List<TeacherAttendanceDTO> getOthersList(String orgId, String date) throws Exception {
		
		//	String query = "SELECT o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn,IFNULL(o_usr_ln,'') AS o_usr_ln,CASE WHEN o_usrAtndns_d?='P' THEN 'Present'  WHEN o_usrAtndns_d?='A' THEN 'Absent'   WHEN o_usrAtndns_d?='L' THEN 'Leave'   WHEN o_usrAtndns_d?='O' THEN 'On Duty' WHEN o_usrAtndns_d?='S' THEN 'Half Day Leave' WHEN o_usrAtndns_d?='C' THEN 'Casual Leave' WHEN o_usrAtndns_d?='W' THEN 'NA' WHEN o_usrAtndns_d?='D' THEN 'Optional Holiday' WHEN o_usrAtndns_d?='H' THEN 'Holiday' WHEN o_usrAtndns_d?='M' THEN 'Maternity Leave' WHEN o_usrAtndns_d?='F' THEN 'Comp Off' WHEN o_usrAtndns_d?='G' THEN 'LOPMG' END AS STATUS,o_role_title as role FROM org_users  LEFT JOIN org_userattendance ON (o_usrAtndns_usrId=o_usr_id AND o_usrAtndns_date=?)  left join org_roles on (o_usr_type = o_role_id and o_usr_orgid = o_role_orgid) WHERE  o_usr_activestatus='Y' AND o_usr_type =8 AND o_usr_orgid=? ORDER BY o_usr_rollno,o_usr_id";
		String query = "SELECT o_usr_id,IFNULL(o_usr_fn,'') AS o_usr_fn,IFNULL(o_usr_mn,'') AS o_usr_mn,IFNULL(o_usr_ln,'') AS o_usr_ln,o_usrAtndns_d? AS STATUS FROM org_users  LEFT JOIN org_userattendance ON o_usrAtndns_usrId=o_usr_id  AND o_usrAtndns_date=? WHERE  o_usr_activestatus='Y' AND o_usr_type=3 AND o_usr_orgid=? ORDER BY o_usr_rollno,o_usr_id ";
		List<TeacherAttendanceDTO> teachers = new ArrayList<TeacherAttendanceDTO>();
		Connection conn = null;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
		java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat("d");
		java.text.SimpleDateFormat sdt = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String s[] = (sdt.format(sdf.parse(date))).split("-");
		String date1 = s[0] + "-" + Integer.parseInt(s[1]) + "-01";
		
		try {
			conn = JdbcConnectionManager.getConnection(jdbcTemplate);
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(2, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(3, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(4, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(5, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(6, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(7, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(8, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(9, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(10, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(11, Integer.parseInt(sd.format(sdf.parse(date))));
			pst.setInt(12, Integer.parseInt(sd.format(sdf.parse(date))));
			
			pst.setString(13, date1);
			pst.setLong(14, Long.parseLong(orgId));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				TeacherAttendanceDTO teacher = new TeacherAttendanceDTO();
				teacher.setUserId(rs.getString("o_usr_id"));
				teacher.setStdFirstName(rs.getString("o_usr_fn"));
				teacher.setStdMiddleName(rs.getString("o_usr_mn"));
				teacher.setStdLastName(rs.getString("o_usr_ln"));
				teacher.setStdattendStatus(rs.getString("status"));
				teacher.setRole(rs.getString("role"));
				teachers.add(teacher);
			}
			JdbcConnectionManager.CloseResultSet(rs);
			JdbcConnectionManager.ClosePreparedStatement(pst);
			return teachers;
		} catch (Exception e) {
			logger.debug("Error while reading Teacher List", e);

		} finally {
			JdbcConnectionManager.Close(conn);
		}
		return null;

	}

	@Override
	public String saveTeacherWithPhotoAttendance(List<TeacherAttendanceDTO> teachers, TeacherParamsDTO params) throws Exception {
		Connection conn = null;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
		java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat("d");
		java.text.SimpleDateFormat sdt = new java.text.SimpleDateFormat("yyyy-MM-dd");
		try {
			conn = JdbcConnectionManager.getConnection(jdbcTemplate);
			
			String orgId = params.getOrgId();
			String select_date = params.getDate();
			String s[] = select_date.split("-");
			String sessusrId = params.getUserId();
			// calencar
			Calendar c = Calendar.getInstance();
			int thisdate = c.get(Calendar.DATE);

			int y = c.get(Calendar.YEAR);
			int m = c.get(Calendar.MONTH) + 1;
			int d = c.get(Calendar.DAY_OF_MONTH);
			int dayofweek = c.get(Calendar.DAY_OF_WEEK);
			String date = d + "/" + m + "/" + y;
			int chour = c.get(Calendar.HOUR);
			int chourd = c.get(Calendar.HOUR_OF_DAY);
			int cmin = c.get(Calendar.MINUTE);
			int ampm = c.get(Calendar.AM_PM);
			int sec = c.get(Calendar.SECOND);
			String trackdate = s[2] + "-" + Integer.parseInt(s[1]) + "-" + Integer.parseInt(s[0]);
			String tracktime = chour + ":" + cmin + ":" + sec;

			String col = "o_usrAtndns_d" + Integer.parseInt(s[0]);
			String StatusType = params.getStatusType();
// System.out.println("krishnahayy"+params.getStatusType());
			String date1 = "";
			String upd_query = "";
			String select_query = "";
			String insert_query = "";
			String track_select_query = "";
			String track_update_query = "";
			String track_insert_query = "";
			String get_location_query = "";
			String insert_location_query ="";
			String update_location_query = "";

			int userid = 0;
	if(StatusType.equalsIgnoreCase("MN")) {
			 date1 = s[2] + "-" + Integer.parseInt(s[1]) + "-01";
			 upd_query = "update org_userattendance set "+col +"= ? where o_usrAtndns_usrId= ? and DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d') =  DATE_FORMAT(?,'%Y-%c-%d') ";
			 select_query = "select distinct o_usrAtndns_usrId, "+col+"  from org_userattendance where o_usrAtndns_usrId= ? and DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d')=  DATE_FORMAT(?,'%Y-%c-%d')";
			 insert_query = "insert into org_userattendance (o_usrAtndns_usrId,o_usrAtndns_date,"+col+") values(?,  DATE_FORMAT(?,'%Y-%c-%d'),?)";
			 track_select_query = "select * from org_userattendance_track where o_usrAtndns_type='S' and o_usrAtndns_orgId=? and o_usrAtndns_dateofatt=  DATE_FORMAT(?,'%Y-%c-%d')";
			 track_update_query = "update org_userattendance_track set  o_usrAtndns_lastupdateddate =now(),o_usrAtndns_updatedby=? where o_usrAtndns_type='S' and o_usrAtndns_orgId=? and o_usrAtndns_createddate=DATE_FORMAT(?,'%Y-%c-%d') and o_usrAtndns_dateofatt=DATE_FORMAT(?,'%Y-%c-%d')";
			 track_insert_query = "insert into org_userattendance_track (o_usrAtndns_orgId,o_usrAtndns_dateofatt,o_usrAtndns_lastupdateddate,o_usrAtndns_updatedby,o_usrAtndns_createddate,o_usrAtndns_type) values(?,?,now(),?,?,'S')";
			 get_location_query = "select * from IERTAttendance where o_usr_id=? and DATE_FORMAT(att_date,'%Y-%c-%d') = date_format(?,'%Y-%c-%d')";
			 insert_location_query ="insert into IERTAttendance(o_usr_id,longitude,latitude,o_usr_photo,att_date,o_usr_sphoto) values(?,?,?,?,?,?)";
			 update_location_query = "update IERTAttendance set longitude=?,latitude=?,o_usr_photo=?,o_usr_sphoto=?, created_date=now() where o_usr_id=? and DATE_FORMAT(att_date,'%Y-%c-%d') = DATE_FORMAT(?,'%Y-%c-%d')";
	}	
			else {
				
				 date1 = s[2] + "-" + Integer.parseInt(s[1]) + "-01";
				 upd_query = "update org_userattendance1 set "+col +"= ? where o_usrAtndns_usrId= ? and DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d') =  DATE_FORMAT(?,'%Y-%c-%d') ";
				 select_query = "select distinct o_usrAtndns_usrId, "+col+"  from org_userattendance1 where o_usrAtndns_usrId= ? and DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d')=  DATE_FORMAT(?,'%Y-%c-%d')";
				 insert_query = "insert into org_userattendance1 (o_usrAtndns_usrId,o_usrAtndns_date,"+col+") values(?,  DATE_FORMAT(?,'%Y-%c-%d'),?)";
				 track_select_query = "select * from org_userattendance_track1 where o_usrAtndns_type='S' and o_usrAtndns_orgId=? and o_usrAtndns_dateofatt=  DATE_FORMAT(?,'%Y-%c-%d')";
				 track_update_query = "update org_userattendance_track1 set  o_usrAtndns_lastupdateddate =now(),o_usrAtndns_updatedby=? where o_usrAtndns_type='S' and o_usrAtndns_orgId=? and o_usrAtndns_createddate=DATE_FORMAT(?,'%Y-%c-%d') and o_usrAtndns_dateofatt=DATE_FORMAT(?,'%Y-%c-%d')";
				 track_insert_query = "insert into org_userattendance_track1 (o_usrAtndns_orgId,o_usrAtndns_dateofatt,o_usrAtndns_lastupdateddate,o_usrAtndns_updatedby,o_usrAtndns_createddate,o_usrAtndns_type) values(?,?,now(),?,?,'S')";
				 get_location_query = "select * from IERTAttendance1 where o_usr_id=? and DATE_FORMAT(att_date,'%Y-%c-%d') = date_format(?,'%Y-%c-%d')";
				 insert_location_query ="insert into IERTAttendance1(o_usr_id,longitude,latitude,o_usr_photo,att_date,o_usr_sphoto) values(?,?,?,?,?,?)";
				 update_location_query = "update IERTAttendance1 set longitude=?,latitude=?,o_usr_photo=?,o_usr_sphoto=?, created_date=now() where o_usr_id=? and DATE_FORMAT(att_date,'%Y-%c-%d') = DATE_FORMAT(?,'%Y-%c-%d')";

			}
			
			for (TeacherAttendanceDTO teacher : teachers) {
				String att="P";
				 switch (teacher.getStdattendStatus()) {
	               case "Absent":
	                   att="A";
	                   
	                   break;
	               case "Leave":
	               	att="L";
	                   ;
	                   break;
	               case "On Duty":
	               	att="O";
	                   ;
	                   break;
	               case "Half Day Leave":
	               	att="S";
	                   ;
	                   break;
	               case "Casual Leave":
	               	att="C";
	                   ;
	                   break;
	               case "NA":
	               	att="W";
	                   ;
	                   break;
	               case "Optional Holiday":
	               	att="D";
	                   ;
	                   break;
	               case "Holiday":
	               	att="H";
	                   ;
	                   break;
	               case "Maternity Leave":
	               	att="M";
	                   ;
	                   break;
	               case "Comp Off":
	               	att="F";
	                   ;
	                   break;
	               case "LOPMG":
	               	att="G";
	                   ;
	                   break;

	           }

		
				PreparedStatement select_teacher_pstm = conn.prepareStatement(select_query);
				
				select_teacher_pstm.setString(1, teacher.getUserId());
				select_teacher_pstm.setString(2, date1);
				ResultSet rs = select_teacher_pstm.executeQuery();
				
				if (!rs.next()) {
					PreparedStatement insert_pstmt = conn.prepareStatement(insert_query);
					insert_pstmt.setString(1, teacher.getUserId());
					insert_pstmt.setString(2, date1);
					insert_pstmt.setString(3, att);
					insert_pstmt.executeUpdate();

					JdbcConnectionManager.ClosePreparedStatement(insert_pstmt);
				} 
				
				JdbcConnectionManager.CloseResultSet(rs);
				JdbcConnectionManager.ClosePreparedStatement(select_teacher_pstm);
				
				String col2="";
				if(StatusType.equalsIgnoreCase("MN")) {
					col2="org_userattendance";
				}
				else {
					col2="org_userattendance1";
				}
					PreparedStatement update_pstmt = conn.prepareStatement(
							"UPDATE "+col2+" SET "+ col +" = ? WHERE o_usrAtndns_usrId=? AND   DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d')=  DATE_FORMAT(?,'%Y-%c-%d')");
										 
				
					update_pstmt.setString(1, att);
					 update_pstmt.setString(2, teacher.getUserId());
					 update_pstmt.setString(3, date1);
					 
					 update_pstmt.executeUpdate();
					 JdbcConnectionManager.ClosePreparedStatement(update_pstmt);
				
			

			//photo insertion
			PreparedStatement update_loc_pstmt=null;
			PreparedStatement insert_loc_pstmt=null;
			PreparedStatement p_ins = conn.prepareStatement(get_location_query);
				p_ins.setString(1, teacher.getUserId());
				p_ins.setString(2, trackdate);
					ResultSet photo_rs = p_ins.executeQuery();
					if(null == params){
						
					}
					else{
					if (photo_rs.next()) {
						
						update_loc_pstmt = conn.prepareStatement(update_location_query);
					
							try{
								//int precision =  (int)Math.pow(10, 6);
							//update_loc_pstmt.setFloat(1,precision*teacher.getLongitude() );
							update_loc_pstmt.setFloat(1,teacher.getLongitude() );
							}catch(Exception e){
								update_loc_pstmt.setFloat(1,0 );
							}
							try{
								//int precision =  (int)Math.pow(10, 6);
							update_loc_pstmt.setFloat(2,teacher.getLatitude());
							}catch(Exception e){
								update_loc_pstmt.setFloat(2,0);	
							}
							update_loc_pstmt.setString(3, null);
							update_loc_pstmt.setBytes(4, params.getPhoto());
							update_loc_pstmt.setLong(5, Long.parseLong(teacher.getUserId()));
							update_loc_pstmt.setString(6, trackdate);
							update_loc_pstmt.executeUpdate();
							
						} else {
							insert_loc_pstmt = conn.prepareStatement(insert_location_query);			
									
							insert_loc_pstmt.setLong(1, Long.parseLong(teacher.getUserId()));
							try{
								//int precision =  (int)Math.pow(10, 6);
								// insert_loc_pstmt.setFloat(2,precision*teacher.getLongitude() );
								insert_loc_pstmt.setFloat(2,teacher.getLongitude() );
								}catch(Exception e){
									insert_loc_pstmt.setFloat(2,0 );
								}
								try{
									//int precision =  (int)Math.pow(10, 6);
									insert_loc_pstmt.setFloat(3,teacher.getLatitude());
									//insert_loc_pstmt.setFloat(3,precision*teacher.getLatitude());
								}catch(Exception e){
									insert_loc_pstmt.setFloat(3,0);	
								}
							insert_loc_pstmt.setString(4, null);
							insert_loc_pstmt.setString(5,  trackdate);
							//	insert_loc_pstmt.setBytes(5, null);
							insert_loc_pstmt.setBytes(6,params.getPhoto());
							insert_loc_pstmt.executeUpdate();
						}
					}//end of if
					//end of photo insertion
			PreparedStatement track_pstmt = conn.prepareStatement(track_select_query);
			track_pstmt.setString(1,params.getOrgId());
			track_pstmt.setString(2, date1);
			 rs = track_pstmt.executeQuery();
			if (rs.next()) {
				PreparedStatement update_track_pstmt = conn.prepareStatement(track_update_query);
				update_track_pstmt.setString(1, params.getUserId());
				update_track_pstmt.setString(2, params.getOrgId());
				update_track_pstmt.setString(3, trackdate);
				update_track_pstmt.setString(4, date1);

		//		update_track_pstmt.executeUpdate();
			//	JdbcConnectionManager.ClosePreparedStatement(update_track_pstmt);
			} else {
				PreparedStatement insert_track_pstmt = conn.prepareStatement(track_insert_query);
				insert_track_pstmt.setString(1, params.getOrgId());
				insert_track_pstmt.setString(2, trackdate);
				insert_track_pstmt.setString(3, params.getUserId());
				insert_track_pstmt.setString(4, date1);

/*				insert_track_pstmt.executeUpdate();
				JdbcConnectionManager.ClosePreparedStatement(insert_track_pstmt);*/
			}
			}
			return "Attendance Submited";
		} catch (Exception e) {
			return "Transaction failed";
		} finally {
			JdbcConnectionManager.Close(conn);
		}
	}
	

	@Override
	public String saveTeacherAttendance(List<TeacherAttendanceDTO> teachers, TeacherParamsDTO params) throws Exception {
		Connection conn = null;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
		java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat("d");
		java.text.SimpleDateFormat sdt = new java.text.SimpleDateFormat("yyyy-MM-dd");
		try {
			conn = JdbcConnectionManager.getConnection(jdbcTemplate);
			String orgId = params.getOrgId();
			String select_date = params.getDate();
			String s[] = select_date.split("-");
			String sessusrId = params.getUserId();
			// calencar
			Calendar c = Calendar.getInstance();
			int thisdate = c.get(Calendar.DATE);

			int y = c.get(Calendar.YEAR);
			int m = c.get(Calendar.MONTH) + 1;
			int d = c.get(Calendar.DAY_OF_MONTH);
			int dayofweek = c.get(Calendar.DAY_OF_WEEK);
			String date = d + "/" + m + "/" + y;
			int chour = c.get(Calendar.HOUR);
			int chourd = c.get(Calendar.HOUR_OF_DAY);
			int cmin = c.get(Calendar.MINUTE);
			int ampm = c.get(Calendar.AM_PM);
			int sec = c.get(Calendar.SECOND);
			String trackdate = s[2] + "-" + Integer.parseInt(s[1]) + "-" + Integer.parseInt(s[0]);
			String tracktime = chour + ":" + cmin + ":" + sec;

			String col = "o_usrAtndns_d" + Integer.parseInt(s[0]);

			int userid = 0;
			String date1 = s[2] + "-" + Integer.parseInt(s[1]) + "-01";
			String upd_query = "update org_userattendance set "+col +"= ? where o_usrAtndns_usrId= ? and DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d') =  DATE_FORMAT(?,'%Y-%c-%d') ";
			String select_query = "select distinct o_usrAtndns_usrId, "+col+"  from org_userattendance where o_usrAtndns_usrId= ? and DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d')=  DATE_FORMAT(?,'%Y-%c-%d')";
			String insert_query = "insert into org_userattendance (o_usrAtndns_usrId,o_usrAtndns_date,"+col+") values(?,  DATE_FORMAT(?,'%Y-%c-%d'),?)";
			String track_select_query = "select * from org_userattendance_track where o_usrAtndns_type='S' and o_usrAtndns_orgId=? and o_usrAtndns_dateofatt=  DATE_FORMAT(?,'%Y-%c-%d')";
			String track_update_query = "update org_userattendance_track set  o_usrAtndns_lastupdateddate =now(),o_usrAtndns_updatedby=? where o_usrAtndns_type='S' and o_usrAtndns_orgId=? and o_usrAtndns_createddate=DATE_FORMAT(?,'%Y-%c-%d') and o_usrAtndns_dateofatt=DATE_FORMAT(?,'%Y-%c-%d')";
			String track_insert_query = "insert into org_userattendance_track (o_usrAtndns_orgId,o_usrAtndns_dateofatt,o_usrAtndns_lastupdateddate,o_usrAtndns_updatedby,o_usrAtndns_createddate,o_usrAtndns_type) values(?,?,now(),?,?,'S')";
	
			for (TeacherAttendanceDTO teacher : teachers) {
				String att=teacher.getStdattendStatus().trim();
				 switch (teacher.getStdattendStatus()) {
	                case "A":
	                    att="A";
	                    
	                    break;
	                case "Leave":
	                	att="L";
	                    ;
	                    break;
	                case "On Duty":
	                	att="O";
	                    ;
	                    break;
	                case "Half Day Leave":
	                	att="S";
	                    ;
	                    break;
	                case "Casual Leave":
	                	att="C";
	                    ;
	                    break;
	                case "NA":
	                	att="W";
	                    ;
	                    break;
	                case "Optional Holiday":
	                	att="D";
	                    ;
	                    break;
	                case "Holiday":
	                	att="H";
	                    ;
	                    break;
	                case "Maternity Leave":
	                	att="M";
	                    ;
	                    break;
	                case "Comp Off":
	                	att="F";
	                    ;
	                    break;
	                case "LOPMG":
	                	att="G";
	                    ;
	                    break;

	            }

				PreparedStatement select_teacher_pstm = conn.prepareStatement(select_query);
				
				select_teacher_pstm.setString(1, teacher.getUserId());
				select_teacher_pstm.setString(2, date1);
				ResultSet rs = select_teacher_pstm.executeQuery();
				if (!rs.next()) {
					PreparedStatement insert_pstmt = conn.prepareStatement(insert_query);
					insert_pstmt.setString(1, teacher.getUserId());
					insert_pstmt.setString(2, date1);
					insert_pstmt.setString(3, att);
					insert_pstmt.executeUpdate();
					JdbcConnectionManager.ClosePreparedStatement(insert_pstmt);
				} 
				
				JdbcConnectionManager.CloseResultSet(rs);
				JdbcConnectionManager.ClosePreparedStatement(select_teacher_pstm);
				
					PreparedStatement update_pstmt = conn.prepareStatement(
							"UPDATE org_userattendance SET "+ col +" = ? WHERE o_usrAtndns_usrId=? AND   DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d')=  DATE_FORMAT(?,'%Y-%c-%d')");
									 update_pstmt.setString(1, att);
					 update_pstmt.setString(2, teacher.getUserId());
					 update_pstmt.setString(3, date1);
					 
					 update_pstmt.executeUpdate();
					 JdbcConnectionManager.ClosePreparedStatement(update_pstmt);
				
			

			PreparedStatement track_pstmt = conn.prepareStatement(track_select_query);
			track_pstmt.setString(1,params.getOrgId());
			track_pstmt.setString(2, date1);
			 rs = track_pstmt.executeQuery();
			if (rs.next()) {
				PreparedStatement update_track_pstmt = conn.prepareStatement(track_update_query);
				update_track_pstmt.setString(1, params.getUserId());
				update_track_pstmt.setString(2, params.getOrgId());
				update_track_pstmt.setString(3, trackdate);
				update_track_pstmt.setString(4, date1);

				update_track_pstmt.executeUpdate();
				JdbcConnectionManager.ClosePreparedStatement(update_track_pstmt);
			} else {
				PreparedStatement insert_track_pstmt = conn.prepareStatement(track_insert_query);
				insert_track_pstmt.setString(1, params.getOrgId());
				insert_track_pstmt.setString(2, trackdate);
				insert_track_pstmt.setString(3, params.getUserId());
				insert_track_pstmt.setString(4, date1);

				insert_track_pstmt.executeUpdate();
				JdbcConnectionManager.ClosePreparedStatement(insert_track_pstmt);
			}
			}
			return "Attendance Submited";
		} catch (Exception e) {
		//	logger.error("Error", e);
			return "Transaction failed";
		} finally {
			JdbcConnectionManager.Close(conn);
		}
	}

	public List<DistrictAttendanceDTO> getTeachersSchoolWiseSummeryAttendance(DistrictAttendanceDTO attend) {
		List<DistrictAttendanceDTO> attends = new ArrayList<DistrictAttendanceDTO>();
		String overallreport ="select hr.h_reg_title title, o_usr_orgid orgid,  count(case when ifnull(o_usrAtndns_d?,'A') !='A' then 1 end) as present,count(case when ifnull(o_usrAtndns_d?,'A')='A' then 1 end) as absent FROM org_userattendance att  inner JOIN org_users u ON (u.o_usr_id= att.o_usrAtndns_usrId and u.o_usr_activestatus='Y') right join hasdb.has_registrations hr on (hr.h_reg_id = u.o_usr_orgId) WHERE  DATE_FORMAT(o_usrAtndns_date,'%Y-%c-%d') = DATE_FORMAT(?,'%Y-%c-%d')  and o_usr_type=3 and u.o_usr_orgId=? group by o_usr_orgid ";
		Connection conn = null;
		PreparedStatement result = null; 
		try {
			
			conn = JdbcConnectionManager.getConnection(jdbcTemplate);
			
			result = conn.prepareStatement(overallreport);
			java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat("d");
			java.text.SimpleDateFormat sdt = new java.text.SimpleDateFormat("yyyy-MM-dd");
			int d= Integer.parseInt(sd.format(sdt.parse(attend.getDate())));
			String s[] = attend.getDate().split("-");
			
			String date1 = s[2] + "-" + Integer.parseInt(s[1]) + "-01";
			result.setInt(1,d);
			result.setInt(2,d);
			result.setString(3, date1);
			result.setString(4,attend.getSchoolId());
			ResultSet rs = result.executeQuery();
			System.out.println(rs);
			if (rs.next()) {
				DistrictAttendanceDTO att = new DistrictAttendanceDTO();
				att.setDistrictName(attend.getDistrictName());
				att.setSchoolId(rs.getString("title"));
				att.setSchoolName(rs.getString("orgid"));
				att.setTotalAbsent(rs.getInt("absents"));
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
	public List<DistrictAttendanceDTO> getTeachersDistrictWiseSummeryAttendance(DistrictAttendanceDTO attend) {
		List<DistrictAttendanceDTO> attends = new ArrayList<DistrictAttendanceDTO>();
		String overallreport ="SELECT h_districtlist_title,hr.h_reg_title as title, o_usr_orgid, o_usr_type,COUNT(CASE WHEN o_usrAtndns_d?='P' THEN 1 END) AS present,COUNT(CASE WHEN IFNULL(o_usrAtndns_d?,'A')!='P' THEN 1 END) AS absent FROM org_userattendance att LEFT JOIN org_users u ON (u.o_usr_id= att.o_usrAtndns_usrId AND u.o_usr_activestatus='Y') RIGHT JOIN hasdb.has_registrations hr ON (hr.h_reg_id = u.o_usr_orgId ) LEFT JOIN  hasdb.has_organizationaddress ON h_add_reg_id=h_reg_id LEFT JOIN  hasdb.has_districtlist ON h_districtlist_id=h_add_field1 WHERE o_usr_type IN(3,8) AND o_usrAtndns_date=? AND  h_districtlist_title=? GROUP BY o_usr_orgid";
		Connection conn = null;
		java.text.SimpleDateFormat sdt = new java.text.SimpleDateFormat("yyyy-MM-dd");
		
		String select_date = attend.getDate();
		String s[] = select_date.split("-");
		String date1 = s[2] + "-" + Integer.parseInt(s[1]) + "-01";
		
		PreparedStatement result = null; 
		try {
			
			conn = JdbcConnectionManager.getConnection(jdbcTemplate);
			result = conn.prepareStatement(overallreport);
			java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat("d");
			int d= Integer.parseInt(sd.format(sdt.parse(attend.getDate())));
			
			result.setInt(1,d);
			result.setInt(2,d);
			result.setString(3, date1);
			result.setString(4,attend.getDistrictName());
			ResultSet rs = result.executeQuery();
			System.out.println("krishna"+attend.getDistrictName());
			if (rs.next()) {
				DistrictAttendanceDTO att = new DistrictAttendanceDTO();
				att.setDistrictName(attend.getDistrictName());
				att.setSchoolId(rs.getString("o_usr_orgid"));
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
//	public String saveTeacherWithPhotoFacex(List<TeacherAttendanceDTO> teachers, TeacherParamsDTO params)
//			throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public String saveTeacherWithPhotoFacex(List<TeacherAttendanceDTO> teachers, TeacherParamsDTO params) {
		Connection con = null;
		try {

			con = JdbcConnectionManager.getConnection(jdbcTemplate);

			
			String insert_location_query ="insert into facecount1(orgid,usrid,date,count,photo,lat,lon) values(?,?,?,?,?,?,?)";
			PreparedStatement insert_loc_pstmt = con.prepareStatement(insert_location_query);			
		//	ResultSet rs = insert_loc_pstmt.executeQuery();
			System.out.println("kkk"+insert_location_query);
				//	PreparedStatement insert_loc_pstmt1 = con.prepareStatement(insert_loc_pstmt1);
					insert_loc_pstmt.setString(1, params.getOrgId());
					insert_loc_pstmt.setString(2, params.getUserId());
					insert_loc_pstmt.setString(3,params.getDate());
					insert_loc_pstmt.setString(4,params.getStatusType());
					insert_loc_pstmt.setBytes(5,params.getPhoto());
					
					insert_loc_pstmt.setFloat(6, params.getLatitude());
					insert_loc_pstmt.setFloat(7, params.getLongitude());
					System.out.println("vammmm"+insert_loc_pstmt);
					insert_loc_pstmt.executeUpdate();
					System.out.println("kkkk"+insert_loc_pstmt);
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
