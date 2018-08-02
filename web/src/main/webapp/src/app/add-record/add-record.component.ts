import { Component, OnInit } from '@angular/core';
import {AddRecordService} from "./shared/add-record.service";
import {Router} from "@angular/router";
import {Broker} from "./shared/Broker.model";
import {Company} from "./shared/Company.model";
import {CompanyShare} from "./shared/CompanyShare";
import {SharePrice} from "./shared/SharePrice.model";
import {LoginService} from "../login-page/shared/login.service";
import {User} from "../login-page/shared/user.model";
import {COMPANYTYPES, ETFTYPES} from "../mock-data";
import {share} from "rxjs/operators";
import {PortfolioComponent} from "../portfolio/portfolio.component";

@Component({
  providers: [PortfolioComponent],
  selector: 'app-add-record',
  templateUrl: './add-record.component.html',
  styleUrls: ['./add-record.component.css']
})
export class AddRecordComponent implements OnInit {

  constructor(private recordService: AddRecordService,
              private router: Router, private loginService: LoginService,
              private portfolioComponent: PortfolioComponent) { }

  ngOnInit() {
    // this.loginService.currentUser.subscribe(user =>{
    //   this.userLoggedInAddComponent=user;
    //   console.log("add record aeroport de user");
    // });

    this.userLoggedInAddComponent = this.loginService.getCurrentUser();
    this.recordService.getAllBrokers().subscribe(brokers => {
      this.brokers = brokers;
      console.log(this.brokers);
    });
  }


  public userLoggedInAddComponent: User;

  companytypes = COMPANYTYPES;
  etftypes = ETFTYPES;

  noShares: number;

  typeOfCompany: number;
  typeOfETF: number;

  needsUpdated: boolean = false;
  existsSharePrice: boolean = false;
  checkedCompany: boolean = false;
  existsDividendYield: boolean = false;
  existsPE: boolean = false;

  shareFound: SharePrice;
  companyFound: Company;
  price: number;
  divYield: number;
  PE: number;
  bookValue: number;

  NAV: number;
  TER: number;
  gearing: number;
  PD: number;

  brokerEntered: Broker;
  brokerName: string;

  brokers: Broker[];
  // selectedBroker: Broker;


  setBookValue(bookValue: number){
    console.log("setBookValue -- method entered -- add-record-component.ts");
    this.bookValue = bookValue;
  }


  saveDetailsNormal() {

    let company = this.companyFound;
    let sharePrice = this.shareFound;
    let companyShare = {company, sharePrice};


    if (this.needsUpdated == true){
      this.recordService.sendCompleteDetails(companyShare).subscribe();
      if (sharePrice==null){
          this.recordService.getAllSharePrices().subscribe(sharePrices => {
            sharePrice = sharePrices.filter(sp => sp.companyid == company.id)[0];
            this.shareFound = sharePrice;
          })
      }
      this.needsUpdated = false;
    }

    this.addHoldingRecord();
    this.cleanInfoRecord();
  }

  saveDetailsTrust() {

    let company = this.companyFound;
    let sharePrice = this.shareFound;
    let companyShare = {company, sharePrice};



    if (this.needsUpdated == true){
      this.recordService.sendCompleteDetails(companyShare).subscribe();
      if (sharePrice==null){
        this.recordService.getAllSharePrices().subscribe(sharePrices => {
          sharePrice = sharePrices.filter(sp => sp.companyid == company.id)[0];
          this.shareFound = sharePrice;
        })
      }
      this.needsUpdated = false;
    }

    this.addHoldingRecord();
    this.cleanInfoRecord();

  }

  saveDetailsETF() {
    let company = this.companyFound;
    let sharePrice = this.shareFound;
    let companyShare = {company, sharePrice};



    if (this.needsUpdated == true){
      this.recordService.sendCompleteDetails(companyShare).subscribe();
      if (sharePrice==null){
        this.recordService.getAllSharePrices().subscribe(sharePrices => {
          sharePrice = sharePrices.filter(sp => sp.companyid == company.id)[0];
          this.shareFound = sharePrice;
        })
      }
      this.needsUpdated = false;
    }
    this.addHoldingRecord();
    this.cleanInfoRecord();
  }

  getBrokerDetailsDatabase(){
    //get Broker from database;
    this.recordService.getBrokerDetails(this.brokerName).subscribe(broker =>
      this.brokerEntered = broker);
  }



  addHoldingRecord(){
    if (this.noShares == 0){
      alert("You need to buy at least 1 share!");
      return;
    }
    if (this.userLoggedInAddComponent.balance < this.bookValue){
      alert("You don't have enough money!");
      return;
    }

    if (this.typeOfCompany==1 && this.shareFound){
      this.recordService.addNormalCompany(this.userLoggedInAddComponent.id, this.brokerEntered.id, this.companyFound.id, this.bookValue, this.noShares)
        .subscribe(hr => {
          //todo Add to existing holding records / refresh list of holding records
          this.loginService.getActualDetailsUser(this.userLoggedInAddComponent.id).subscribe(user=>{
            this.loginService.changeUserObservable(user);
            this.router.navigate([""]).then(()=>this.portfolioComponent.refreshRecords());
          })
        });
    }
    if (this.typeOfCompany==2 && this.shareFound){
      this.recordService.addTrust(this.userLoggedInAddComponent.id, this.brokerEntered.id, this.companyFound.id, this.bookValue, this.noShares, this.gearing, this.PD, 0, this.TER)
        .subscribe(hr => {
          //todo Add to existing holding records / refresh list of holding records
          this.loginService.getActualDetailsUser(this.userLoggedInAddComponent.id).subscribe(user=>{
            this.loginService.changeUserObservable(user);
            this.router.navigate([""]).then(()=>this.portfolioComponent.refreshRecords());
          })
        });
    }
    if (this.typeOfCompany==3 && this.shareFound){
      this.recordService.addETF(this.userLoggedInAddComponent.id, this.brokerEntered.id, this.companyFound.id, this.bookValue, this.noShares, 0, this.TER, this.typeOfETF)
        .subscribe(hr => {
          //todo Add to existing holding records / refresh list of holding records
          this.loginService.getActualDetailsUser(this.userLoggedInAddComponent.id).subscribe(user=>{
            this.loginService.changeUserObservable(user);
            this.router.navigate([""]).then(()=>this.portfolioComponent.refreshRecords());
          })
        });
    }
  }


  getCurrentFactor(): number {
    return this.loginService.getCurrentFactor();
  }

  getCurrentCurrencyName(): string {
    return this.loginService.getCurrencyName();
  }




  cleanInfoRecord(){
    // this.selectedBroker = null;
    this.brokerName = null;
    this.brokerEntered = null;
    this.divYield = null;
    this.PE = null;
    this.NAV = null;
    this.TER = null;
    this.gearing = null;
    this.PD = null;
    this.price = null;

    this.companyFound = null;
    this.shareFound = null;

    this.needsUpdated = false;

    this.existsSharePrice = false;
    this.checkedCompany = false;
    this.existsDividendYield = false;
    this.existsPE = false;

    this.typeOfCompany= null;
    this.typeOfETF = null;
  }


  setBrokerName(name: string) {
    console.log("setBrokerName -- method entered - add-record.component.ts");
    this.brokerName = name;
  }

  // setNewBroker(event){
  //   console.log(event);
  //   this.selectedBroker = this.brokers.filter(broker=>broker.id == event.target.value)[0];
  //   console.log(this.selectedBroker);
  // }



  checkCompany(symbol: string){
    console.log("method: checkCompany -- " + symbol + " -- front-end");
    this.recordService.getCompanyDetails(symbol).subscribe(cs => {
      console.log(cs);
      if (cs.company.name == null && cs.sharePrice.date == null) {
        alert("The company you're trying to add already exists in your portfolio. If you wish to add shares to the existing company, try clicking on the company then click on the Add button");
        this.checkedCompany = false;
        this.router.navigate([""]);
        return
      }
      this.companyFound = cs.company;
      this.shareFound = cs.sharePrice;
      // this.companyFound.dividendYield == 0 ?
      //   (this.existsDividendYield = false, this.divYield = null, this.needsUpdated = true) : (this.existsDividendYield = true, this.divYield = this.companyFound.dividendYield);
      // this.companyFound.pe == 0 ?
      //   () : ();
      // this.shareFound.price == 0 ?
      //   () : ();
      // this.checkedCompany = true;
      if(this.companyFound.dividendYield == 0)
      {
        this.existsDividendYield = false; this.divYield = null; this.needsUpdated = true
      }
      else{
        this.existsDividendYield = true; this.divYield = this.companyFound.dividendYield;
      }
      if(this.companyFound.pe == 0)
      {
        this.existsPE = false; this.PE = null; this.needsUpdated = true
      }
      else{
        this.existsPE = true; this.PE = this.companyFound.pe
      }
      if(this.shareFound.price == 0)
      {
        this.existsSharePrice = false; this.price = null; this.needsUpdated = true
      }
      else {
        this.existsSharePrice = true; this.price = this.shareFound.price
      }
      this.checkedCompany = true;
    });

  }

  setNewTypeCompany(event){
    this.typeOfCompany = event.target.value;
  }

  setNewTypeETF(event){
    console.log("Am intrat in setNewTypeETF cu: " + event.target.value);
    this.typeOfETF = event.target.value;
  }

  setSharePriceHTML(price: number){
    console.log("setSharePriceHTML -- "+ price);
    this.shareFound.price = price;
  }

  setDivYieldHTML(divYield: number){
    console.log("setDivYieldHTML -- "+ divYield);
    this.companyFound.dividendYield = divYield;
  }

  setPEHTML(PE: number) {
    console.log("setPEHTML -- "+ PE);
    this.companyFound.pe = PE;
  }

  setNumberOfShares(noShares: number){
    console.log("setNumberOfShares --" + noShares);
    this.noShares = noShares;
  }
  setNavHtml(NAV: number)
  {
    console.log("NAV "+ NAV);
    this.NAV = NAV;
  }
  setTerHtml(TER:number)
  {
    console.log("TER"+TER);
    this.TER = TER;
  }
  setGearing(gearing:number)
  {
    this.gearing = gearing;
  }
  setPd(PD:number)
  {
    this.PD = PD;
  }


}
