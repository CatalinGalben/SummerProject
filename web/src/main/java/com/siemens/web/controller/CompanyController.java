package com.siemens.web.controller;

import com.siemens.core.service.CompanyServiceInterface;
import com.siemens.web.converter.CompanyConverter;
import com.siemens.web.dto.CompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class CompanyController {
    @Autowired
    private CompanyServiceInterface companyService;
    @Autowired
    private CompanyConverter companyConverter;

    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    public Set<CompanyDTO> getCompanies()
    {
        return (Set<CompanyDTO>) companyConverter.convertModelsToDtos(companyService.getAllCompanies());
    }

}
