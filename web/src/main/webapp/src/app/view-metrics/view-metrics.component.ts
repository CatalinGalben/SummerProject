import { Component, OnInit } from '@angular/core';
import {AddRecordService} from "../add-record/shared/add-record.service";
import {LoginService} from "../login-page/shared/login.service";
import {Company} from "../add-record/shared/Company.model";
import {ActivatedRoute, Params} from "@angular/router";
import {PortfolioService} from "../portfolio/shared/portfolio.service";
import {HoldingRecord} from "../add-record/shared/HoldingRecord.model";
import {YEARSMETRICS} from "../mock-data";

@Component({
  selector: 'app-view-metrics',
  templateUrl: './view-metrics.component.html',
  styleUrls: ['./view-metrics.component.css']
})
export class ViewMetricsComponent implements OnInit {

  company: Company;
  holdingRecord: HoldingRecord;
  years = YEARSMETRICS;


  navigated = false;

  constructor(private recordService: AddRecordService,
              private loginService: LoginService,
              private route: ActivatedRoute,
              private portfolioService: PortfolioService) {
  }

  ngOnInit() {
    this.route.params.forEach((params: Params) => {
      if (params['id'] !== undefined) {
        const id = +params['id'];
        this.navigated = true;
        this.portfolioService.getRecords().subscribe(hr => {
          this.holdingRecord = hr.filter(hrs => hrs.id == id)[0];
          this.recordService.getAllCompanies().subscribe(companies => {
            this.company = companies.filter(comp => comp.id == this.holdingRecord.companyid)[0];
            this.loginService.changeCompanies(companies);
          });
        });

        //get all companies

      }

    });
  }

  getCompanyName(): string {
    if (this.company)
      return this.company.name;
  }

  getCompanyDivYield(): string {
    if (this.company)
      return this.company.dividendYield.toString();
  }

  getCompanyPE(): string{
    if (this.company)
      return this.company.pe.toString();
  }

}
