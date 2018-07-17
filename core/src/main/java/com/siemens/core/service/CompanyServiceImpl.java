package com.siemens.core.service;

import com.siemens.core.model.Company;
import com.siemens.core.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CompanyServiceImpl implements  CompanyServiceInterface {
    @Autowired
    private CompanyRepository companyRepository;
    @Override
    public Company findById(Integer id){
        Optional<Company> maybeCompany = companyRepository.findById(id);
        if(maybeCompany.isPresent())
        {
            return maybeCompany.get();
        }
        throw new RuntimeException("The company you are searching for does not exist");
    }
}
