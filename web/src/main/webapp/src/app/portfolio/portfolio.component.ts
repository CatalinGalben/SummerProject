import { Component, OnInit } from '@angular/core';
import {RECORDS} from '../mock-data';
import {Router} from "@angular/router";
import {TransferService} from "../providers/transfer.service";
import {Mockmodels} from "../mockmodels";
import {v} from "@angular/core/src/render3";
import {LoginService} from "../login-page/shared/login.service";
import {PortfolioService} from "./shared/portfolio.service";
import {User} from "../login-page/shared/user.model";
import {HoldingRecord} from "../add-record/shared/HoldingRecord.model";
import {AddRecordService} from "../add-record/shared/add-record.service";
import {Broker} from "../add-record/shared/Broker.model";
import {SharePrice} from "../add-record/shared/SharePrice.model";
import {Company} from "../add-record/shared/Company.model";

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.css']
})

export class PortfolioComponent implements OnInit {

  brokers: Broker[] = [];
  holdingRecords: HoldingRecord[] = [];
  sharePrices: SharePrice[] = [];
  companies: Company[] = [];
  selectedHoldingRecord: HoldingRecord;

  liquidatedpressed = false;
  noSharesToLiquidate = 0;

  currentCompanyNamePortfolio: string;

  records = RECORDS;
  selectedRow : number;
  userLoggedInPortfolioComponent: User;
  newBalanceValue: number;


  constructor(private router: Router,
              private transferService:TransferService,
              private loginService: LoginService,
              private portfolioService: PortfolioService,
              private recordService: AddRecordService) {

    this.populate();
  }


  ngOnInit() {
    this.brokers = [];
    this.holdingRecords = [];
    this.sharePrices = [];
    this.companies = [];
    this.selectedHoldingRecord = null;
    this.populate();
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
  }
  goToAdd() {
    this.router.navigate(['addRecord']);
  }

  rowClicked(name: string, noShares: number, price: number) {
    this.transferService.setData(name, noShares, price);
  }

  refreshRecords(){
    console.log("refreshRecords -- portfolio.component.ts");
    this.portfolioService.getRecords().subscribe(records => this.holdingRecords = records);
  }

  getCompanyForRecord(id: number, rec: HoldingRecord): string{
    this.currentCompanyNamePortfolio = this.companies.filter(company => company.id == id)[0].name;
    return this.currentCompanyNamePortfolio
  }

  getBookValueLost(): number {
    return (this.selectedHoldingRecord.pricePaid/this.selectedHoldingRecord.noShares)*this.noSharesToLiquidate;
  }

  getLiquidatedMoney(): number {
    return this.noSharesToLiquidate*this.getCurrentShareForCompany(this.selectedHoldingRecord.companyid)
  }

  getCurrentShareForCompany(companyid: number): number{
    return this.sharePrices.filter(shareprice => shareprice.companyid == companyid)[0].price;
  }

  getGain(paid: number, sumNow: number): number{
    let result = sumNow/paid;
    return result*100-100;
  }

  getBrokerForRecord(brokerid: number): string {
    return this.brokers.filter(broker => broker.id == brokerid)[0].name;
  }

  clickRecord(id: number){
    this.selectedRow = id;
    this.selectedHoldingRecord = this.holdingRecords.filter(hr => hr.id == id)[0];
  }

  pressLiquidate() {
    this.liquidatedpressed = !this.liquidatedpressed;
    this.noSharesToLiquidate = null;
  }

  liquidate(){
    console.log("liquidate method entered -- portfolio.component.ts");
    if (!Number.isInteger(parseFloat(this.noSharesToLiquidate.toString()))){
      alert("The number of shares should be a natural number!");
      return;
    }
    if (this.selectedHoldingRecord.noShares < this.noSharesToLiquidate){
      alert("The number of shares you want to liquidate is bigger than the number of shares you have for this company");
      return;
    }
    this.portfolioService.liquidateRecord(this.currentCompanyNamePortfolio, this.noSharesToLiquidate).subscribe(_=>{
      this.refreshRecords();
      this.loginService.getActualDetailsUser(this.userLoggedInPortfolioComponent.id).subscribe(user=>{
        this.loginService.changeUserObservable(user);
      });
    });
    this.selectedHoldingRecord = null;
    this.selectedRow = null;
    this.liquidatedpressed = !this.liquidatedpressed;
    this.noSharesToLiquidate = 0;
  }

  setNoSharesLiquidated(noShares: number) {
    this.noSharesToLiquidate = noShares;
  }


  gotoDetail(): void {
    console.log("gotoDetail method entered -- portfolio.component.ts");
    this.router.navigate(['/buy', this.selectedHoldingRecord.id, 1]);
  }

  gotoAddDividend(): void {
    console.log("gotoDetail method entered -- portfolio.component.ts");
    this.router.navigate(['/buy', this.selectedHoldingRecord.id, 2]);
  }

  setNewBalanceUser(newBalance: number) {
    this.newBalanceValue = newBalance;
    console.log("setNewBalanceUser method entered -- portfolio.component.ts");
    this.loginService.setNewBalanceUserService(this.userLoggedInPortfolioComponent.id, this.newBalanceValue).subscribe(user =>{
      this.loginService.changeUserObservable(user);
      this.userLoggedInPortfolioComponent = user;
    })
  }


  insertionSort(rec: HoldingRecord[], n:number)
  {
    let i, key, j;
    for (i = 1; i < n; i++)
    {
      key = rec[i];
      j = i-1;

      /* Move elements of rec, that are
         greater than key, to one position ahead
         of their current position */
      while (j >= 0 && this.getCompanyForRecord(rec[j].companyid, rec[j])> this.getCompanyForRecord(key.companyid, key))
      {
        rec[j+1] = rec[j];
        j = j-1;
      }
      rec[j+1] = key;
      }
    }

}
