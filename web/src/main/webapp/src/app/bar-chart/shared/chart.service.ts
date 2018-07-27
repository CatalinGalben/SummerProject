import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";

import { Company } from "../../add-record/shared/Company.model";
import {Group} from "./Group.model";
import {CompanyGroup} from "./CompanyGroup.model";
import {HoldingRecord} from "../../add-record/shared/HoldingRecord.model";


@Injectable({
  providedIn: 'root'
})
export class ChartService {

  private companyUrl = 'http://localhost:8080/api/companies';
  private groupUrl = 'http://localhost:8080/api/groups';
  private companiesGroupUrl = 'http://localhost:8080/api/companygroups';
  private createGroupUrl = 'http://localhost:8080/api/creategroup';
  private createCompanyGroupUrl = 'http://localhost:8080/api/createCompanyGroup';

  constructor(private httpClient: HttpClient) { }


  getGroups(): Observable<Group[]>{
    return this.httpClient.get<Group[]>(this.groupUrl);
  }

  getCompanies(): Observable<Company>{
    return this.httpClient.get<Company>(this.companyUrl);
  }

  getCompanyGroups(): Observable<CompanyGroup>{
    return this.httpClient.get<CompanyGroup>(this.companiesGroupUrl);
  }

  createGroup(name: string, parentgroupid: number): Observable<Group>{
    let id = 0;
    let group = {id, name, parentgroupid};
    console.log("CREATE " + name + " " + parentgroupid);
    return this.httpClient.post<Group>(this.createGroupUrl, group);
  }

  createCompanyGroup(companies: Company[], groupName: string): Observable<CompanyGroup>{
    let groupWrapper = {companies, groupName};
    return this.httpClient.post<CompanyGroup>(this.createCompanyGroupUrl, groupWrapper);
  }











}
