package com.hassoft.api.daos.impl;

import com.hassoft.api.dtos.StudentAttendDTO;
import com.hassoft.api.dtos.damdtos.DamDataParamDTO;
import com.hassoft.api.dtos.damdtos.DropdownDTO;
import com.hassoft.api.dtos.damdtos.HTNoDTO;
import com.hassoft.api.dtos.damdtos.KeyValueDTO;
import com.hassoft.api.utils.JdbcConnectionManager;
import com.mysql.jdbc.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class DamDAOImpl {
    final static Logger logger = LoggerFactory.getLogger(DamDAOImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public DropdownDTO getAllDropDowns(){
        //get districts
        DropdownDTO dropdownDTO  = new DropdownDTO();
        Map<String,List<String>> districts = new HashMap<>();
        Map<String,List<KeyValueDTO>> applyfors = new HashMap<>();
        Map<String,List<String>> references = new HashMap<>();

        Connection conn = null;
        String query = "select distinct distid,distname,mandalname from org_directdistrict order by 2";

        try {
            conn = JdbcConnectionManager.getConnection(jdbcTemplate);
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            List<String> mandals = new ArrayList<>();
            while (rs.next()){
              String distName = rs.getString("distname").trim();
              String mandalName = rs.getString("mandalname").trim();
              if(districts.containsKey(distName)){
                  mandals.add(mandalName);
                  districts.put(distName,mandals);
              }else{
                  mandals = new ArrayList<>();
                  mandals.add(mandalName);
                  districts.put(distName,mandals);
              }

            }
            Map<String, List<String>> sortedDistricts = new TreeMap<String, List<String>>(districts);

            //get Applyfor and classes
            applyfors = getApplyFor();

            // get community
            List<String> communityArr = new ArrayList<>();
            communityArr = getCastes();

            // get district Preference
            List<KeyValueDTO> distPrefs = getDistrictPreference();

            // get References
            references = getReferences();


            JdbcConnectionManager.CloseResultSet(rs);
            JdbcConnectionManager.ClosePreparedStatement(pst);

            dropdownDTO.setDistricts(sortedDistricts);
            dropdownDTO.setApplyFors(applyfors);
            dropdownDTO.setCommunities(communityArr);
            dropdownDTO.setPrefDistricts(distPrefs);
            dropdownDTO.setReferences(references);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            JdbcConnectionManager.Close(conn);
        }
        return dropdownDTO;
    }

    private List<String> getCastes() {
        List<String> castes = new ArrayList<>();
        String query = "SELECT * FROM iert.org_directcaste";
        Connection conn = null;
        try {
            conn = JdbcConnectionManager.getConnection(jdbcTemplate);
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                castes.add(rs.getString("caste").trim());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return castes;
    }

    private Map<String, List<KeyValueDTO>> getApplyFor() {
        Map<String,List<KeyValueDTO>> applyfors = new HashMap<>();
        Map<String,String> standard = new HashMap<>();
        standard.put("RS","School");
        standard.put("RJC","Inter");
        standard.put("RDC","Degree");
        for (Map.Entry<String, String> entry : standard.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            List<KeyValueDTO> courses = new ArrayList<>();

            String query = "select distinct id,class,type from org_directclass where type=? order by 1";
            Connection conn = null;
            try {
                conn = JdbcConnectionManager.getConnection(jdbcTemplate);
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, entry.getKey());
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    KeyValueDTO valueDTO = new KeyValueDTO();
                    valueDTO.setId(rs.getInt("id"));
                    valueDTO.setName(rs.getString("class").trim());
                    courses.add(valueDTO);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            applyfors.put(entry.getValue(), courses);
        }
        return applyfors;
    }

    private List<KeyValueDTO> getDistrictPreference(){
        //get districts pref
        List<KeyValueDTO> strings = new ArrayList<>();

        Connection conn = null;
        String query = "SELECT distinct district_id,district_name FROM hasdw_swdtg21.school_dm1 order by 2";

        try {
            conn = JdbcConnectionManager.getConnection(jdbcTemplate);
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                KeyValueDTO valueDTO = new KeyValueDTO();
                valueDTO.setId(rs.getInt("district_id"));
                valueDTO.setName(rs.getString("district_name"));
                strings.add(valueDTO);
            }
            JdbcConnectionManager.CloseResultSet(rs);
            JdbcConnectionManager.ClosePreparedStatement(pst);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            JdbcConnectionManager.Close(conn);
        }
        return strings;
    }

    private Map<String, List<String>> getReferences() {
        Map<String,List<String>> references = new HashMap<>();
        Map<String,String> govEmpls = new HashMap<>();
        govEmpls.put("MP","MP");
        govEmpls.put("MLA","MLA");
        govEmpls.put("MLC","MLC");
        govEmpls.put("1","Parents");
        govEmpls.put("2","Self");
        govEmpls.put("3","Ministers");
        govEmpls.put("4","Other Public Reference");
        govEmpls.put("5","Others");
        for (Map.Entry<String, String> entry : govEmpls.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            List<String> courses = new ArrayList<>();

            String query = "SELECT distinct id,reflist FROM org_directreflist where type=? order by 2";
            Connection conn = null;
            try {
                conn = JdbcConnectionManager.getConnection(jdbcTemplate);
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, entry.getKey());
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    courses.add(rs.getString("reflist").trim());
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            references.put(entry.getValue(), courses);
        }
        return references;
    }

    public List<String> getSchoolPreferences(String districtName, int classId) {

        String query = "SELECT DISTINCT a.id,org_name FROM org_directpschool a INNER JOIN org_directclass b ON(a.type=b.type ) WHERE distname=? AND b.id=?  union select '-1','Any School/College' ORDER BY 2";
        List<String> schoolPrefs = new ArrayList<String>();
        Connection conn = null;
        try {
            conn = JdbcConnectionManager.getConnection(jdbcTemplate);
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, districtName);
            pst.setInt(2, classId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                schoolPrefs.add(rs.getString("org_name").trim());
            }
            JdbcConnectionManager.CloseResultSet(rs);
            JdbcConnectionManager.ClosePreparedStatement(pst);
            return schoolPrefs;
        } catch (Exception e) {
           e.printStackTrace();

        } finally {
            JdbcConnectionManager.Close(conn);
        }
        return null;

    }

    public HTNoDTO getHallTicketDetails(String htNo) {

        String query = "SELECT htno ht,cname,Gender,caste,mobile,FatherName,mandalname,distname,type FROM org_directht WHERE htno=?";
        HTNoDTO htNoDTO = new HTNoDTO();
        Connection conn = null;
        try {
            conn = JdbcConnectionManager.getConnection(jdbcTemplate);
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, htNo);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                htNoDTO.setHtNo(rs.getString("ht"));
                htNoDTO.setcName(rs.getString("cname").trim());
                htNoDTO.setfName(rs.getString("FatherName").trim());
                htNoDTO.setCaste(rs.getString("caste"));
                htNoDTO.setGender(rs.getString("Gender"));
                htNoDTO.setMobileno(rs.getString("mobile"));
                htNoDTO.setDistrictName(rs.getString("distname").trim());
                htNoDTO.setMandalName(rs.getString("mandalname").trim());
                htNoDTO.setType(rs.getString("type"));
            }
            JdbcConnectionManager.CloseResultSet(rs);
            JdbcConnectionManager.ClosePreparedStatement(pst);
            return htNoDTO;
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            JdbcConnectionManager.Close(conn);
        }
        return null;

    }

    public int validateMobileNoDuplicate(String mobileno) {

        String query = "SELECT count(*) FROM org_directadmit WHERE o_usr_mobileno=?";
        int count = 0;
        Connection conn = null;
        try {
            conn = JdbcConnectionManager.getConnection(jdbcTemplate);
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, mobileno);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                count = rs.getInt(1);
            }
            JdbcConnectionManager.CloseResultSet(rs);
            JdbcConnectionManager.ClosePreparedStatement(pst);
            return count;
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            JdbcConnectionManager.Close(conn);
        }
        return 0;

    }

    public String saveDamData(DamDataParamDTO params) {
        Connection con = null;
        try {

            con = JdbcConnectionManager.getConnection(jdbcTemplate);
            String insert_location_query ="insert into org_directadmit(o_usr_exam"
                    + ",o_usr_htno,o_usr_appliedclass,o_usr_typeschl,o_usr_fn,o_usr_gender"
                    + ",o_usr_fathersname,o_usr_cast,o_usr_dist,o_usr_mandal"
                    + ",o_usr_mobileno,o_usr_predist,o_usr_preschl"
                    + ",o_usr_reftype,o_usr_reference,createddate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE())";
            PreparedStatement insert_loc_pstmt = con.prepareStatement(insert_location_query, Statement.RETURN_GENERATED_KEYS);
            insert_loc_pstmt.setString(1,params.getExam());
            insert_loc_pstmt.setString(2,params.getHtNo());
            insert_loc_pstmt.setString(3,params.getAppliedClass());
            insert_loc_pstmt.setString(4,params.getType());
            insert_loc_pstmt.setString(5,params.getcName());
            insert_loc_pstmt.setString(6,params.getGender());
            insert_loc_pstmt.setString(7, params.getfName());
            insert_loc_pstmt.setString(8, params.getCaste());
            insert_loc_pstmt.setString(9, params.getDistrictName());
            insert_loc_pstmt.setString(10, params.getMandalName());
            insert_loc_pstmt.setString(11, params.getMobileno());
            insert_loc_pstmt.setString(12, params.getPrefDistrict());
            insert_loc_pstmt.setString(13, params.getPrefSchool());
            insert_loc_pstmt.setString(14, params.getRefType());
            insert_loc_pstmt.setString(15, params.getRef());

            System.out.println(""+insert_loc_pstmt);
            insert_loc_pstmt.executeUpdate();
            long generatedId = 0;
            try (ResultSet rs = insert_loc_pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getLong(1);
                }
            } catch (SQLException s) {
                s.printStackTrace();
            }
            JdbcConnectionManager.ClosePreparedStatement(insert_loc_pstmt);

            if(params.getPhoto()!=null){
                Path target = Paths.get("E:\\Hassoft\\" + generatedId + ".png");
                Files.write(target, params.getPhoto());
                System.out.println("image created" + target.toString());
            }


            return "Submitted Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Error while saving dam data ", e);
            return "failed to submit";
        } finally {
            JdbcConnectionManager.Close(con);
        }
    }


}
