import { Component, OnInit } from '@angular/core';
import {Company} from "../add-record/shared/Company.model";
import {HoldingRecord} from "../add-record/shared/HoldingRecord.model";
import {AddRecordService} from "../add-record/shared/add-record.service";
import {PortfolioService} from "../portfolio/shared/portfolio.service";
import {Router} from "@angular/router";
import {LoginService} from "../login-page/shared/login.service";
import {TransferService} from "../providers/transfer.service";
import {Broker} from "../add-record/shared/Broker.model";
import {User} from "../login-page/shared/user.model";
import {RECORDS} from "../mock-data";
import {SharePrice} from "../add-record/shared/SharePrice.model";
import {ChartService} from "../bar-chart/shared/chart.service";
import {Group} from "../bar-chart/shared/Group.model";

@Component({
  selector: 'app-add-group',
  templateUrl: './add-group.component.html',
  styleUrls: ['./add-group.component.css']
})
export class AddGroupComponent implements OnInit {

  brokers: Broker[] = [];
  holdingRecords: HoldingRecord[] = [];
  sharePrices: SharePrice[] = [];
  companies: Company[] = [];
  selectedHoldingRecord: HoldingRecord;

  selectedRow: number;
  currentCompanyNamePortfolio: string;

  userLoggedInPortfolioComponent: User;

  selectedHoldingRecords: HoldingRecord[] = [];
  selectedCompanies: Company[] = [];
  groupName: string;
  selectedParentGroup: Group;
  groups: Group[] = [];

  constructor(private router: Router,
              private transferService:TransferService,
              private loginService: LoginService,
              private portfolioService: PortfolioService,
              private recordService: AddRecordService,
              private groupService: ChartService) {

    this.populate();

  }

  ngOnInit() {
  }

  populate(){
    //get loggedIn user
    // this.loginService.currentUser.subscribe(user =>
    // this.userLoggedInPortfolioComponent = user);
    if (this.loginService.getCurrentUser() != null) {
      this.userLoggedInPortfolioComponent = this.loginService.getCurrentUser();

      //get all records
      this.portfolioService.getRecords().subscribe(records => {
        this.holdingRecords = records
          .filter(record => record.userid == this.userLoggedInPortfolioComponent.id);
      });
    }

    //get all brokers
    this.recordService.getAllBrokers().subscribe(brokers => {
      this.brokers = brokers;
      this.loginService.changeBrokers(this.brokers);
    });


    //get all companies
    this.recordService.getAllCompanies().subscribe(companies => {
      this.companies = companies;
      this.loginService.changeCompanies(this.companies);
    });

    //get all sharePrices
    this.recordService.getAllSharePrices().subscribe(shareprices =>{
      this.sharePrices = shareprices.sort((s1, s2)=> s1.id-s2.id);
      this.loginService.changeSharePrices(this.sharePrices);});

    // get all groups
   // this.groupService.getGroups().subscribe(groups => {
   //   this.groups = groups;
   //   this.loginService.changeGroups(this.groups);
   // });
  }

  refreshRecords(){
    console.log("refreshRecords -- portfolio.component.ts");
    this.portfolioService.getRecords().subscribe(records => this.holdingRecords = records);
  }

  getCompanyForRecord(id: number, rec: HoldingRecord): string{
    this.currentCompanyNamePortfolio = this.companies.filter(company => company.id == id)[0].name;
    return this.currentCompanyNamePortfolio
  }

  getCurrentShareForCompany(companyid: number): number{
    return this.sharePrices.filter(shareprice => shareprice.companyid == companyid)[0].price;
  }

  getGain(paid: number, sumNow: number): number{
    let result = paid/sumNow;
    if (result >= 1){
      return (result - 1)*100;
    }
    else
      return (1 - result)*100;
  }

  getBrokerForRecord(brokerid: number): string {
    return this.brokers.filter(broker => broker.id == brokerid)[0].name;
  }


  clickRecord(id: number){
    this.selectedRow = id;
    this.selectedHoldingRecord = this.holdingRecords.filter(hr => hr.id == id)[0];
  }

  addRecord() {
    if (this.selectedHoldingRecord == null) {
      alert("No row selected");
    }

    let k = this.selectedHoldingRecords.length;
    if (!this.isMemberSelectedHR(this.selectedHoldingRecord)) {
        this.selectedHoldingRecords[k] = this.selectedHoldingRecord;
        // console.log("K: " + k); // 0 -> n-1
        // console.log(k + ": " + this.selectedHoldingRecord.noShares + " " + this.selectedHoldingRecord.pricePaid + " | " + this.selectedHoldingRecords[0].noShares);

        let company = this.companies.filter(company => company.id == this.selectedHoldingRecord.companyid)[0];
        if (!this.isMemberSelectedCompany(company)) {
          this.selectedCompanies[k] = this.companies.filter(company => company.id == this.selectedHoldingRecord.companyid)[0];
          // console.log("C: " + this.selectedCompanies[k].pe);
        }
    } else {
      alert("Company added");
    }
  }

  isMemberSelectedCompany(company: Company) {
    // console.log("inside");
    for (let i=0; i<this.selectedCompanies.length; i++) {
      // console.log(this.selectedCompanies[i].id + " " + company.id + " " + (this.selectedCompanies[i].id == company.id));
      if (this.selectedCompanies[i].id == company.id) return true;
    }
    return false;
  }

  isMemberSelectedHR(hr: HoldingRecord) {
    // console.log("inside");
    for (let i=0; i<this.selectedHoldingRecords.length; i++) {
       // console.log(this.selectedHoldingRecords[i].id + " " + hr.id + " " + (this.selectedHoldingRecords[i].id == hr.id));
        if (this.selectedHoldingRecords[i].id == hr.id) return true;
    }
    return false;
  }

  setGroupName(name: string) {
    this.groupName = name;
  }

  /*
  saveGroup() {
    if (this.selectedParentGroup == null) {
      this.groupService.createGroup(this.groupName, 0);
    }
    else {
      this.groupService.createGroup(this.groupName, this.selectedParentGroup.id);
    }
   //  this.groupService.createCompanyGroup(this.selectedCompanies, this.groupName);
  }

  setNewGroup(event) {
    this.selectedParentGroup = this.groups.filter(group=>group.id == event.target.value)[0];
  }
  */

  saveGroup() {
    this.groupService.createGroup(this.selectedCompanies, this.groupName, this.userLoggedInPortfolioComponent);
  }







}
