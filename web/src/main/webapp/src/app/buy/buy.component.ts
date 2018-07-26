import {Component, Input, OnInit} from '@angular/core';
import {TransferService} from "../providers/transfer.service";
import {ActivatedRoute, Params} from "@angular/router";
import {HoldingRecord} from "../add-record/shared/HoldingRecord.model";
import {PortfolioService} from "../portfolio/shared/portfolio.service";
import {AddRecordService} from "../add-record/shared/add-record.service";
import {Company} from "../add-record/shared/Company.model";
import {Broker} from "../add-record/shared/Broker.model";
import {SharePrice} from "../add-record/shared/SharePrice.model";
import {LoginService} from "../login-page/shared/login.service";

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

  navigated = false;
  holdingRecord: HoldingRecord;

  companies: Company[] = [];
  brokers: Broker[] = [];
  sharePrices: SharePrice[] = [];

  constructor(private portfolioService: PortfolioService,
              private route: ActivatedRoute,
              private recordService: AddRecordService,
              private loginService: LoginService
              ) { }

  ngOnInit(): void {
    this.route.params.forEach((params: Params) => {
      if (params['id'] !== undefined) {
        const id = +params['id'];
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
          this.sharePrices = shareprices.sort((s1, s2)=> s1.id-s2.id);
          this.loginService.changeSharePrices(this.sharePrices);});
      } else {
        this.navigated = false;
        this.holdingRecord = new HoldingRecord();
      }
    });
  }

  calcPrice(addedValue : number ) {
    this.totPrice = Number(addedValue) * this.price;
    console.log(this.totPrice);
  }

  setTotalPricePaid(args){
    this.totPrice = this.price*args.target.value;
  }

  getBrokerForRecord(brokerid: number): string {
    return this.brokers.filter(broker => broker.id == brokerid)[0].name;
  }

  getCompanyForRecord(id: number, rec: HoldingRecord): string{
    console.log(this.companies);
    return this.companies.filter(company => company.id == id)[0].name;
  }

  getCurrentShareForCompany(companyid: number): number{
    this.price = this.sharePrices.filter(shareprice => shareprice.companyid == companyid)[0].price;
    return this.price;
  }



}
