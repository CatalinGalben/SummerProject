package com.siemens.core.service;

import com.siemens.core.model.Company;

import java.util.List;

public interface CompanyServiceInterface {

    Company findById(Integer id);
    List<Company> getAllCompanies();
}
