package com.hassoft.api.controllers;

import com.hassoft.api.dtos.damdtos.DamDataParamDTO;
import com.hassoft.api.dtos.damdtos.DropdownDTO;
import com.hassoft.api.dtos.damdtos.HTNoDTO;
import com.hassoft.api.dtos.damdtos.SchoolPrefDTO;
import com.hassoft.api.services.DamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@MultipartConfig
public class DAMController {

    @Autowired
    private DamService damService;

    @RequestMapping(value = "/getDamDropDowns", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public DropdownDTO getDropDowns(){
        return damService.getAllDropDowns();
    }

    @RequestMapping(value = "/getSchoolPreferences", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
    public List<String> getSchoolPreferences(@RequestBody SchoolPrefDTO prefDto, HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {// REST Endpoint.

        return damService.getSchoolPreferences(prefDto.getDistrictName(), prefDto.getClassId());
    }

    @RequestMapping(value = "/getHallTicketDetails", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
    public HTNoDTO getHallTicketDetails(@RequestBody HTNoDTO htNoDTO, HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {// REST Endpoint.

        return damService.getHallTicketDetails(htNoDTO.getHtNo());
    }

    @RequestMapping(value = "/validateMobileNoDuplicate", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
    public int validateMobileNoDuplicate(@RequestBody String mobileno, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {// REST Endpoint.

        return damService.validateMobileNoDuplicate(mobileno);
    }

    @RequestMapping(value = "/saveDamData", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
    public String saveDamData(@RequestBody DamDataParamDTO paramDTO, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {// REST Endpoint.

        return damService.saveDamData(paramDTO);
    }
}
