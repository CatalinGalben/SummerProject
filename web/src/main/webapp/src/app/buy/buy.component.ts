import {Component, Input, OnInit} from '@angular/core';
import {TransferService} from "../providers/transfer.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {HoldingRecord} from "../add-record/shared/HoldingRecord.model";
import {PortfolioService} from "../portfolio/shared/portfolio.service";
import {AddRecordService} from "../add-record/shared/add-record.service";
import {Company} from "../add-record/shared/Company.model";
import {Broker} from "../add-record/shared/Broker.model";
import {SharePrice} from "../add-record/shared/SharePrice.model";
import {LoginService} from "../login-page/shared/login.service";
import {User} from "../login-page/shared/user.model";
import {isNumber} from "util";

@Component({
  selector: 'app-buy',
  templateUrl: './buy.component.html',
  styleUrls: ['./buy.component.css']
})
export class BuyComponent implements OnInit {

  name: string;
  noShares: number;
  price: number;
  @Input() no: number;
  totPrice: number;
  totalDividendForUser: number;

  userId: number;
  user: User;
  shareId: number;
  broker: Broker;

  buy: boolean;


  companysymbol: string;
  company: Company;


  navigated = false;
  holdingRecord: HoldingRecord;

  companies: Company[] = [];
  brokers: Broker[] = [];
  sharePrices: SharePrice[] = [];

  constructor(private portfolioService: PortfolioService,
              private route: ActivatedRoute,
              private recordService: AddRecordService,
              private loginService: LoginService,
              private router: Router
              ) { }

  ngOnInit(): void {
    this.route.params.forEach((params: Params) => {
      if (params['id'] !== undefined) {
        const id = +params['id'];
        this.buy = params['buy']==1;
        this.navigated = true;
        this.portfolioService.getRecords().subscribe(hr => (this.holdingRecord = hr.filter(hrs => hrs.id==id)[0]));

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
          this.sharePrices = shareprices.sort((s1, s2)=> s2.id-s1.id); //aici sunt sortate descrescator
          this.loginService.changeSharePrices(this.sharePrices);});
      } else {
        this.navigated = false;
        this.holdingRecord = new HoldingRecord();
      }
    });

    this.user = this.loginService.getCurrentUser();
    this.userId = this.user.id;
  }

  setNoSharesAndPrice(){
      this.noShares = this.holdingRecord.noShares;
      this.price = this.sharePrices.filter(share => share.companyid == this.holdingRecord.companyid)[0].price;
  }

  setTotalPricePaid(args){
    this.noShares = args.target.value;
    this.totPrice = this.price*args.target.value + this.price*args.target.value*this.broker.shareFee;
  }



  getBrokerShareFeeForRecord(): number{
    return this.broker.shareFee*100;
  }

  getBrokerDividendFeeForRecord(): number{
    return this.broker.dividendFee*100;
  }


  getBrokerNameForRecord(brokerid: number): string {
    this.broker = this.brokers.filter(broker => broker.id == brokerid)[0];
    return this.broker.name;
  }

  getFeeForTotalPrice(): number {
    return this.broker.shareFee*this.totPrice;
  }

  getFeeForDividend(): number{
    return this.noShares*this.price*(this.company.dividendYield/100)*this.broker.dividendFee;
  }

  getFinalDividend(): number{
    if (this.noShares == null || this.price == null){
      this.setNoSharesAndPrice()
    }
    this.totalDividendForUser = this.noShares*this.price*(this.company.dividendYield/100) - this.noShares*this.price*(this.company.dividendYield/100)*this.broker.dividendFee;
    return this.totalDividendForUser;
  }

  getCompanyForRecord(id: number, rec: HoldingRecord): string{
    console.log(this.companies);
    this.company = this.companies.filter(company => company.id == id)[0];
    this.companysymbol = this.company.name;
    return this.companysymbol;
  }

  getCurrentShareForCompany(companyid: number): number{
    this.shareId = this.sharePrices.filter(shareprice => shareprice.companyid == companyid)[0].id;
    this.price = this.sharePrices.filter(shareprice => shareprice.companyid == companyid)[0].price;
    return this.price;
}

  addDividend(){
    this.loginService.addDividendService(this.price, this.companysymbol, this.broker.id, this.userId).subscribe(user => {
      this.loginService.changeUserObservable(user);
      this.user = user;
      this.router.navigate(['']);
    })

  }



  addToExisting(){
    if (this.user.balance < this.totPrice){
      alert("You don't have enough money!");
      return;
    }
    this.portfolioService.getUpdatedRecords(this.broker.id, this.userId, this.shareId, this.holdingRecord.id, this.price*this.noShares, this.noShares).subscribe();
    console.log("addToExisting -- buy.component.ts -- id: " + this.userId);
    this.loginService.getActualDetailsUser(this.userId).subscribe(user=>{
      console.log(user.balance);
      this.loginService.changeUserObservable(user);
    });
    this.router.navigate([""]);
  }
}
