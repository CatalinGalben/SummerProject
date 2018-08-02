import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";

import { Company } from "../../add-record/shared/Company.model";
import {Group} from "./Group.model";
import {CompanyGroup} from "./CompanyGroup.model";
import {HoldingRecord} from "../../add-record/shared/HoldingRecord.model";
import {User} from "../../login-page/shared/user.model";

@Injectable({
  providedIn: 'root'
})
export class ChartService {

  private companyUrl = 'http://localhost:8080/api/companies';
  private groupUrl = 'http://localhost:8080/api/groups';
  private companiesGroupUrl = 'http://localhost:8080/api/companygroups';
  private createGroupUrl = 'http://localhost:8080/api/creategroup';
  private createCompanyGroupUrl = 'http://localhost:8080/api/creategroup';
  private getBenchmarksUrl = 'http://localhost:8080/api/group';

  constructor(private httpClient: HttpClient) { }


  getGroups(): Observable<Group[]>{
    return this.httpClient.get<Group[]>(this.groupUrl);
  }

  getCompanyGroups(): Observable<CompanyGroup[]>{
    return this.httpClient.get<CompanyGroup[]>(this.companiesGroupUrl);
  }

  createGroup(companies: Company[], groupName: string, user: User): Observable<CompanyGroup>{
    let groupWrapper = {companies, groupName, user};

    console.log("SERVICE: " + groupName);
    console.log(companies);

    return this.httpClient.post<CompanyGroup>(this.createCompanyGroupUrl, groupWrapper);
  }

  getBenchmarks(id: number, name: string) : Observable<String> {
    const getBenchm = "benchmark";
    const url = `${this.getBenchmarksUrl}/${getBenchm}`;
    let group = {id, name};

    return this.httpClient.post<String>(url, group);
  }



}
