package com.siemens.core.service;

import com.siemens.core.model.Company;
import com.siemens.core.model.CompanyGroup;
import com.siemens.core.model.Group;
import com.siemens.core.model.SharePrice;
import com.siemens.core.repository.CompanyGroupRepository;
import com.siemens.core.repository.GroupRepository;
import com.siemens.core.repository.SharePriceRepository;
import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
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
    public void createGroup(Set<Company>companies, String nameOfGroup)
    {
        Group newGroup = Group.builder()
                .name(nameOfGroup)
                .build();
        companies.forEach(
                company ->
                    companyGroupRepo.save(
                            CompanyGroup.builder()
                            .company(company)
                            .group(newGroup)
                            .build()
                    )

        );
    }
    @Override
    public Map<String, String> getBenchmarks(Set<Group> groups){

        //dateOfShare, price, symbol;
        //Possible problem, we get all the possible data stored in the database .

        Map<String, String> benchmark = new HashMap<>();
        List<SharePrice> resultedPrices = null;
        companyGroupRepo.findAll()
                .forEach(
                        companyGroup -> {
                            groups.forEach(
                                  group -> {
                                      if(companyGroup.getGroup().getId().equals(group.getId()))
                                      {
                                          resultedPrices.add(
                                               sharePriceRepo.findAll()
                                               .stream().filter(sp -> (sp.getCompany().getId().equals(companyGroup.getCompany().getId()))
                                               && (LocalDate.parse(sp.getDate()).isAfter(LocalDate.now().minusDays(7)))
                                               )
                                               .findFirst().get()
                                          );
                                      }
                                  }
                            );
                        }
                );
        resultedPrices.forEach(
                rp -> benchmark.put(rp.getCompany().getName(), rp.getPrice()+";"+rp.getDate())
        );
        return benchmark;
    }
}
