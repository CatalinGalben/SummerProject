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
    this.recordService.getAllBrokers().subscribe(brokers => {
      this.brokers = brokers;
      console.log(this.brokers);
    });
    this.loginService.currentUser.subscribe(user => {
      console.log("ba");
      this.userLoggedInAddComponent = user;
      console.log(this.userLoggedInAddComponent);
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
      this.recordService.sendCompleteDetails(companyShare);
      this.needsUpdated = false;
    }

    this.addHoldingRecord();

  }

  saveDetailsTrust(NAV: number, TER: number, gearing: number, PD: number) {

    let company = this.companyFound;
    let sharePrice = this.shareFound;
    let companyShare = {company, sharePrice};

    this.NAV = NAV;
    this.TER = TER;
    this.gearing = gearing;
    this.PD = PD;

    if (this.needsUpdated == true){
      this.recordService.sendCompleteDetails(companyShare);
      this.needsUpdated = false;
    }

    this.addHoldingRecord();

  }

  saveDetailsETF(NAV: number, TER: number) {
    let company = this.companyFound;
    let sharePrice = this.shareFound;
    let companyShare = {company, sharePrice};

    this.NAV = NAV;
    this.TER = TER;


    if (this.needsUpdated == true){
      this.recordService.sendCompleteDetails(companyShare);
      this.needsUpdated = false;
    }

    this.addHoldingRecord();

  }



  addHoldingRecord(){
    console.log(this.userLoggedInAddComponent);
    if (this.typeOfCompany==1){
      this.recordService.addNormalCompany(this.userLoggedInAddComponent.id, this.selectedBroker.id, this.companyFound.id, this.shareFound.price*this.noShares, this.noShares)
        .subscribe(hr => {
          //todo Add to existing holding records / refresh list of holding records
        })
    }
    if (this.typeOfCompany==2){
      this.recordService.addTrust(this.userLoggedInAddComponent.id, this.selectedBroker.id, this.companyFound.id, this.shareFound.price*this.noShares, this.noShares, this.gearing, this.PD)
        .subscribe(hr => {
          //todo Add to existing holding records / refresh list of holding records
        })
    }
    if (this.typeOfCompany==3){
      this.recordService.addETF(this.userLoggedInAddComponent.id, this.selectedBroker.id, this.companyFound.id, this.shareFound.price*this.noShares, this.noShares, this.typeOfETF)
        .subscribe(hr => {
          //todo Add to existing holding records / refresh list of holding records
        })
    }
  }



  setNewBroker(broker: Broker){
    console.log(broker + "-- front-end");
    this.selectedBroker = broker;
  }



  checkCompany(symbol: string){
    console.log("method: checkCompany -- " + symbol + " -- front-end");
    this.recordService.getCompanyDetails(symbol).subscribe(cs => {
      console.log(cs);
      this.companyFound = cs.company;
      this.shareFound = cs.sharePrice;
      this.companyFound.dividendYield == 0 ?
        (this.existsDividendYield = false, this.divYield = null, this.needsUpdated = true) : (this.existsDividendYield = true, this.divYield = this.companyFound.dividendYield);
      this.companyFound.pe == 0 ?
        (this.existsPE = false, this.PE = null, this.needsUpdated = true) : (this.existsPE = true, this.PE = this.companyFound.pe);
      this.shareFound.price == 0 ?
        (this.existsSharePrice = false, this.price = null, this.needsUpdated = true) : (this.existsSharePrice = true, this.price = this.shareFound.price);
      this.checkedCompany = true;
    })
  }

  setNewTypeCompany(event){
    this.typeOfCompany = event.target.value;
  }

  setNewTypeETF(type: number){
    console.log("Am intrat in setNewTypeETF cu: " + type);
    this.typeOfETF = type;
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


}
