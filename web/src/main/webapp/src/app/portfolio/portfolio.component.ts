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

  showUpdateSharePriceInput = false;
  liquidatedpressed = false;
  isNotActualSharePrice = false;
  noSharesToLiquidate = 0;
  newSharePrice = 0;

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
    this.showUpdateSharePriceInput = false;
    this.liquidatedpressed = false;
    this.isNotActualSharePrice = false;
    this.noSharesToLiquidate = 0;
    this.newSharePrice = 0;
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
      this.sharePrices = shareprices.sort((s1, s2)=> s2.id-s1.id);
      this.loginService.changeSharePrices(this.sharePrices);});
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

  getLastTimeUpdatedSharePrice(companyid: number): string{
    return this.sharePrices.filter(shareprice => shareprice.companyid == companyid)[0].date;
  }

  getGain(paid: number, sumNow: number): number{
    let result = sumNow/paid;
    return result*100-100;
  }

  getCurrentFactor(): number{
    return this.loginService.getCurrentFactor();
  }

  getCurrentCurrencyName(): string {
    return this.loginService.getCurrencyName();
  }

  getBrokerForRecord(brokerid: number): string {
    if (this.brokers.filter(broker => broker.id == brokerid)[0].name)
      return this.brokers.filter(broker => broker.id == brokerid)[0].name;
  }

  clickRecord(id: number){
    this.isNotActualSharePrice = false;
    this.selectedRow = id;
    this.selectedHoldingRecord = this.holdingRecords.filter(hr => hr.id == id)[0];
    this.checkIfSharePriceNeedsUpdate();
  }

  checkIfSharePriceNeedsUpdate(){
    if (this.getLastTimeUpdatedSharePrice(this.selectedHoldingRecord.companyid)!=this.portfolioService.getCurrentDate())
      this.isNotActualSharePrice = true;
    else
      this.isNotActualSharePrice = false;
  }

  showSharePriceInput(){
    this.showUpdateSharePriceInput = true;
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
      this.loginService.getActualDetailsUser(this.userLoggedInPortfolioComponent.id).subscribe(user=>{
        this.loginService.changeUserObservable(user);
      });
      this.ngOnInit();
    });
    this.selectedHoldingRecord = null;
    this.selectedRow = null;
    this.liquidatedpressed = !this.liquidatedpressed;
    this.noSharesToLiquidate = 0;
  }

  updateSharePrice(){
    this.showUpdateSharePriceInput = false;
    this.isNotActualSharePrice = false;
    this.portfolioService.getNewSharePrice(this.selectedHoldingRecord.companyid, this.newSharePrice).subscribe(() => this.ngOnInit())
  }

  setNoSharesLiquidated(noShares: number) {
    this.noSharesToLiquidate = noShares;
  }

  setNewSharePriceToUpdate(newPrice: number){
    this.newSharePrice = newPrice;
  }

  goToAdd() {
    console.log("goToAdd method entered -- portfolio.component.ts");
    this.router.navigate(['addRecord']);
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
