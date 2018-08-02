package com.siemens.web.controller;

import com.siemens.core.model.Company;
import com.siemens.core.model.CompanyGroup;
import com.siemens.core.model.Group;
import com.siemens.core.model.User;
import com.siemens.core.service.GroupServiceInterface;
import com.siemens.web.converter.CompanyConverter;
import com.siemens.web.converter.CompanyGroupConverter;
import com.siemens.web.converter.GroupConverter;
import com.siemens.web.converter.UserConverter;
import com.siemens.web.dto.CompanyGroupDTO;
import com.siemens.web.dto.GroupDTO;
import com.siemens.web.dto.GroupWrapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class GroupController {
    @Autowired
    private GroupServiceInterface groupService;
    @Autowired
    private GroupConverter groupConverter;
    @Autowired
    private CompanyGroupConverter companyGroupConverter;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private CompanyConverter companyConverter;
    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public Set<GroupDTO> getGroups()
    {
        List<Group> groupList = groupService.getGroups();

        return (Set<GroupDTO>) groupConverter.convertModelsToDtos(groupList);
    }
    @RequestMapping(value = "/companygroups", method = RequestMethod.GET)
    public Set<CompanyGroupDTO> getCompanyGroups()
    {
        return (Set<CompanyGroupDTO>)companyGroupConverter.convertModelsToDtos(groupService.getCompanyGroups());
    }
    @RequestMapping(value = "/creategroup", method = RequestMethod.POST)
    public void createGroup(@RequestBody final GroupWrapper groupWrapper)
    {

        User user = userConverter.convertDtoToModel(groupWrapper.getUser());
        String groupName = groupWrapper.getGroupName();
        Set<Company> companies = (Set<Company>)companyConverter.convertDtosToModel(groupWrapper.getCompanies());
        groupService.createGroup(companies, groupName, user);
    }
    @RequestMapping(value = "/group/benchmark", method = RequestMethod.POST)
    public String getBenchmarks(@RequestBody final GroupDTO groupDTO)
    {
        return groupService.getBenchmarks(groupConverter.convertDtoToModel(groupDTO)).toString();
    }
}
