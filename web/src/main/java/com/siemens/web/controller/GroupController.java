package com.siemens.web.controller;

import com.siemens.core.model.Company;
import com.siemens.core.model.CompanyGroup;
import com.siemens.core.service.GroupServiceInterface;
import com.siemens.web.converter.CompanyGroupConverter;
import com.siemens.web.converter.GroupConverter;
import com.siemens.web.dto.CompanyGroupDTO;
import com.siemens.web.dto.GroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class GroupController {
    @Autowired
    private GroupServiceInterface groupService;
    @Autowired
    private GroupConverter groupConverter;
    @Autowired
    private CompanyGroupConverter companyGroupConverter;
    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public Set<GroupDTO> getGroups()
    {
        return (Set<GroupDTO>) groupConverter.convertModelsToDtos(groupService.getGroups());
    }
    @RequestMapping(value = "/companygroups", method = RequestMethod.GET)
    public Set<CompanyGroupDTO> getCompanyGroups()
    {
        return (Set<CompanyGroupDTO>)companyGroupConverter.convertModelsToDtos(groupService.getCompanyGroups());
    }
    @RequestMapping(value = "/creategroup", method = RequestMethod.POST)
    public void createGroup()
    {

    }
}
