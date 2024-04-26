package com.hassoft.api.services;

import com.hassoft.api.daos.impl.DamDAOImpl;
import com.hassoft.api.dtos.damdtos.DamDataParamDTO;
import com.hassoft.api.dtos.damdtos.DropdownDTO;
import com.hassoft.api.dtos.damdtos.HTNoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class DamService {
    @Autowired
    private DamDAOImpl damDAO;

    public DropdownDTO getAllDropDowns(){
        return damDAO.getAllDropDowns();
    }

    public List<String> getSchoolPreferences(String districtName, int classId){
        return damDAO.getSchoolPreferences(districtName, classId);
    }

    public HTNoDTO getHallTicketDetails(String htNo){
        return damDAO.getHallTicketDetails(htNo);
    }

    public int validateMobileNoDuplicate(String mobileno){
        return damDAO.validateMobileNoDuplicate(mobileno);
    }

    public String saveDamData(DamDataParamDTO paramDTO){
        return damDAO.saveDamData(paramDTO);
    }
}
