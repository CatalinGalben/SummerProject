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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupServiceInterface {
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

        //dateOfShare, price, symbol;
        //Possible problem, we get all the possible data stored in the database .

//        Map<String, String> benchmark = new HashMap<>();
//        List<SharePrice> resultedPrices = null;
//        companyGroupRepo.findAll()
//                .forEach(
//                        companyGroup -> {
//                            groups.forEach(
//                                  group -> {
//                                      if(companyGroup.getGroup().getId().equals(group.getId()))
//                                      {
//                                          resultedPrices.add(
//                                               sharePriceRepo.findAll()
//                                               .stream().filter(sp -> (sp.getCompany().getId().equals(companyGroup.getCompany().getId()))
//                                               && (LocalDate.parse(sp.getDate()).isAfter(LocalDate.now().minusDays(7)))
//                                               )
//                                               .findFirst().get()
//                                          );
//                                      }
//                                  }
//                            );
//                        }
//                );
//        resultedPrices.forEach(
//                rp -> benchmark.put(rp.getCompany().getName(), rp.getPrice()+";"+rp.getDate())
//        );
        JSONObject benchmark = new JSONObject();
        JSONArray mainArray = new JSONArray();
        JSONObject mainObject = new JSONObject();
        JSONArray companyArray = new JSONArray();

        JSONArray pricesArray = new JSONArray();

        List<CompanyGroup> groupRecords = companyGroupRepo.findAll()
                .stream().filter(rec -> rec.getGroup().getId().equals(group.getId())).collect(Collectors.toList());

        groupRecords.forEach(
                record ->
                {
                    List<Company> companies = companyRepository.findAll()
                            .stream().filter(c -> c.getId().equals(record.getCompany().getId()))
                            .collect(Collectors.toList());
                    companies.forEach(
                            c ->
                            {
                                List<SharePrice> sharePrices = sharePriceRepo.findAll()
                                        .stream()
                                        .filter(sp -> sp.getCompany().getId().equals(c.getId()))
                                        .collect(Collectors.toList());
                                JSONObject companyItem = new JSONObject();
                                sharePrices.forEach(
                                        sp ->
                                        {   JSONObject priceObjects = new JSONObject();
                                            priceObjects.put("value:",sp.getPrice().toString());
                                            priceObjects.put("name:", sp.getDate());
                                            pricesArray.put(priceObjects);
                                        }
                                );
                                companyItem.put("name:",c.getName());
                                companyItem.put("series:",pricesArray);
                                companyArray.put(companyItem);
                            }
                    );
                }
        );
        return benchmark;
    }
}
