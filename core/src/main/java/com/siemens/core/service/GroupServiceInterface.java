package com.siemens.core.service;

import com.siemens.core.model.Company;
import com.siemens.core.model.CompanyGroup;
import com.siemens.core.model.Group;
import com.siemens.core.model.User;
import org.json.JSONObject;

import java.util.List;
import java.util.Set;

public interface GroupServiceInterface {

    void createSubGroup(Set<Company> companies, String nameOfGroup, Group parentGroup); // determine parent ?

    void createGroup(Set<Company> companies, String nameOfGroups, User user);


    JSONObject getBenchmarks(Group groups);

    List<Group> getGroups();

    List<CompanyGroup> getCompanyGroups();

}
