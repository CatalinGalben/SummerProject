package com.siemens.core.service;

import com.siemens.core.model.*;
import com.siemens.core.repository.CompanyGroupRepository;
import com.siemens.core.repository.CompanyRepository;
import com.siemens.core.repository.GroupRepository;
import com.siemens.core.repository.SharePriceRepository;
import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupServiceInterface {
    private static final Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private CompanyGroupRepository companyGroupRepo;
    @Autowired
    private SharePriceRepository sharePriceRepo;
    @Autowired
    private CompanyRepository companyRepository;
    @Override
    public List<Group> getGroups()
    {
        return groupRepository.findAll();
    }
    @Override
    public List<CompanyGroup> getCompanyGroups()
    {
        return companyGroupRepo.findAll();
    }

    @Override
    public void createSubGroup(Set<Company> companies, String nameOfGroup, Group parentGroup)
    {
        List<CompanyGroup> companyGroups = companyGroupRepo.findAll().stream()
                .filter(companyGroup -> companyGroup.getGroup().getName().equals(parentGroup.getName()))
                .collect(Collectors.toList());

        Group newGroup = Group.builder()
                .parentGroup(parentGroup)
                .name(nameOfGroup)
                .build();

        companies.forEach(
                company -> companyGroups.forEach(
                        companyGroup -> {
                            if(companyGroup.getCompany().getName().equals(company.getName()))
                                companyGroup.setGroup(newGroup);
                        }
                )
        );

    }
    @Override
    public void createGroup(Set<Company>companies, String nameOfGroup, User user)
    {
        Group newGroup = groupRepository.save(Group.builder()
                .name(nameOfGroup)
                .build());

        companies.forEach(
                company ->
                    companyGroupRepo.save(
                            CompanyGroup.builder()
                            .company(companyRepository.findById(company.getId()).get())
                            .group(newGroup)
                            .user(user)
                            .build()
                    )

        );
    }
    @Override
    public JSONObject getBenchmarks(Group group){

        JSONObject benchmark = new JSONObject();
        JSONArray mainArray = new JSONArray();
        JSONObject mainObject = new JSONObject();
        JSONArray companyArray = new JSONArray();



        List<CompanyGroup> groupRecords = companyGroupRepo.findAll()
                .stream().filter(rec -> rec.getGroup().getId().equals(group.getId())).collect(Collectors.toList());

        String recordName = groupRecords.get(0).getGroup().getName();
        groupRecords.forEach(
                record ->
                {
                    Company company = companyRepository.findAll()
                            .stream().filter(c -> c.getId().equals(record.getCompany().getId()))
                            .findFirst().get();


                    List<SharePrice> sharePrices = sharePriceRepo.findAll()
                            .stream()
                            .filter(sp -> sp.getCompany().getId().equals(company.getId()))
                            .collect(Collectors.toList());
                    JSONArray pricesArray = new JSONArray();
                    JSONObject companyItem = new JSONObject();

                    sharePrices.forEach(
                            sp ->
                            {   JSONObject priceObjects = new JSONObject();
                                priceObjects.put("value",sp.getPrice().toString());
                                priceObjects.put("name", sp.getDate());
                                pricesArray.put(priceObjects);
                            }
                    );
                    companyItem.put("name",company.getName());
                    companyItem.put("series",pricesArray);
                    companyArray.put(companyItem);
                }
        );
        mainObject.put("name", recordName);
        mainObject.put("series", companyArray);
        mainArray.put(mainObject);

        benchmark.put("results", mainArray);
        log.trace("Benchmark json:" + benchmark);
        return benchmark;
    }
}
