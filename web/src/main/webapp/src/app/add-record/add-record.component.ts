import { Component, OnInit } from '@angular/core';
import {AddRecordService} from "./shared/add-record.service";
import {Router} from "@angular/router";
import {Broker} from "./shared/Broker.model";
import {Company} from "./shared/Company.model";
import {CompanyShare} from "./shared/CompanyShare";
import {SharePrice} from "./shared/SharePrice.model";
import {LoginService} from "../login-page/shared/login.service";
import {User} from "../login-page/shared/user.model";

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
      this.userLoggedInAddComponent = user;
    });
  }


  public userLoggedInAddComponent: User;

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



  saveDetails(name: string, price: number, noShares: number, dividendYield: number, PE: number, NAV: number, TER: number, gearing: number, PD: number) {
    console.log("Price: " + price);
    if (Number.isInteger(Number(price)) == false)
    {
      alert("The share price has to be an integer");
      return;
    }

    if (Number.isInteger(Number(noShares)) == false)
    {
      alert("The number of shares has to be an integer");
      return;
    }
    if (Number.isInteger(Number(dividendYield)) == false)
    {
      alert("The Dividend Yield has to be an integer");
      return;
    }
    if (Number.isInteger(Number(PE)) == false)
    {
      alert("The price to earning has to be an integer");
      return;
    }
    this.noShares = noShares;
    this.companyFound.dividendYield = dividendYield;
    this.companyFound.pe = PE;
    this.shareFound.price = price;
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

  addHoldingRecord(){
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
      this.companyFound.dividendYield == null ?
        (this.existsDividendYield = false, this.divYield = null, this.needsUpdated = true) : (this.existsDividendYield = true, this.divYield = this.companyFound.dividendYield);
      this.companyFound.pe == null ?
        (this.existsPE = false, this.PE = null, this.needsUpdated = true) : (this.existsPE = true, this.PE = this.companyFound.pe);
      this.shareFound.price == null ?
        (this.existsSharePrice = false, this.price = null, this.needsUpdated = true) : (this.existsSharePrice = true, this.price = this.shareFound.price);
      this.checkedCompany = true;
    })
  }

  setNewTypeCompany(type: number){
    this.typeOfCompany = type;
  }

  setNewTypeETF(type: number){
    this.typeOfETF = type;
  }

}
