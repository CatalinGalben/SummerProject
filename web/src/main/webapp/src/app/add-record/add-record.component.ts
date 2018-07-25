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

@Component({
  selector: 'app-add-record',
  templateUrl: './add-record.component.html',
  styleUrls: ['./add-record.component.css']
})
export class AddRecordComponent implements OnInit {

  constructor(private recordService: AddRecordService,
              private router: Router, private loginService: LoginService) { }

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

  NAV: number;
  TER: number;
  gearing: number;
  PD: number;

  brokers: Broker[];
  selectedBroker: Broker;



  saveDetailsNormal() {

    let company = this.companyFound;
    let sharePrice = this.shareFound;
    let companyShare = {company, sharePrice};


    if (this.needsUpdated == true){
      this.recordService.sendCompleteDetails(companyShare).subscribe();
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
      this.needsUpdated = false;
    }
    this.addHoldingRecord();
    this.cleanInfoRecord();
  }



  addHoldingRecord(){
    if (this.typeOfCompany==1){
      this.recordService.addNormalCompany(this.userLoggedInAddComponent.id, this.selectedBroker.id, this.companyFound.id, this.shareFound.price*this.noShares, this.noShares)
        .subscribe(hr => {
          //todo Add to existing holding records / refresh list of holding records
        })
    }
    if (this.typeOfCompany==2){
      this.recordService.addTrust(this.userLoggedInAddComponent.id, this.selectedBroker.id, this.companyFound.id, this.shareFound.price*this.noShares, this.noShares, this.gearing, this.PD, this.NAV, this.TER)
        .subscribe(hr => {
          //todo Add to existing holding records / refresh list of holding records
        })
    }
    if (this.typeOfCompany==3){
      this.recordService.addETF(this.userLoggedInAddComponent.id, this.selectedBroker.id, this.companyFound.id, this.shareFound.price*this.noShares, this.noShares,  this.NAV, this.TER, this.typeOfETF)
        .subscribe(hr => {
          //todo Add to existing holding records / refresh list of holding records
        })
    }
  }

  cleanInfoRecord(){
    this.selectedBroker = null;
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



  setNewBroker(event){
    console.log(event);
    this.selectedBroker = this.brokers.filter(broker=>broker.id == event.target.value)[0];
    console.log(this.selectedBroker);
  }



  checkCompany(symbol: string){
    console.log("method: checkCompany -- " + symbol + " -- front-end");
    this.recordService.getCompanyDetails(symbol).subscribe(cs => {
      console.log(cs);
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
    })
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
