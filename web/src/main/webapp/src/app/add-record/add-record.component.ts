import { Component, OnInit } from '@angular/core';
import {AddRecordService} from "./shared/add-record.service";
import {Router} from "@angular/router";
import {Broker} from "./shared/Broker.model";
import {Company} from "./shared/Company.model";
import {CompanyShare} from "./shared/CompanyShare";
import {SharePrice} from "./shared/SharePrice.model";

@Component({
  selector: 'app-add-record',
  templateUrl: './add-record.component.html',
  styleUrls: ['./add-record.component.css']
})
export class AddRecordComponent implements OnInit {

  constructor(private recordService: AddRecordService,
              private router: Router) { }

  ngOnInit() {
   this.recordService.getAllBrokers().subscribe(brokers =>
   {
     this.brokers = brokers;
     console.log(this.brokers);
   });
  }




  existsSharePrice: boolean = false;
  checkedCompany: boolean = false;
  existsDividendYield: boolean = false;
  existsPE: boolean = false;

  shareFound: SharePrice;
  companyFound: Company;
  price: number;
  divYield: number;
  PE: number;

  brokers: Broker[];
  selectedBroker: Broker;



  saveDetails(name: string, price: number, noShares: number, divYield: number, PE: number) {
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
    if (Number.isInteger(Number(divYield)) == false)
    {
      alert("The Dividend Yield has to be an integer");
      return;
    }
    if (Number.isInteger(Number(PE)) == false)
    {
      alert("The price to earning has to be an integer");
      return;
    }
  }



  setNewBroker(broker: Broker){
    console.log(broker + "-- front-end");
    this.selectedBroker = broker;
  }

  checkCompany(symbol: string){
    console.log(symbol + "-- front-end");
    this.recordService.getCompanyDetails(symbol).subscribe(cs => {
      this.companyFound = cs.company;
      this.shareFound = cs.sharePrice;
      this.companyFound.divYield == null ?
        (this.existsDividendYield = false, this.divYield = null) : (this.existsDividendYield = true, this.divYield = this.companyFound.divYield);
      this.companyFound.PE == null ?
        (this.existsPE = false, this.PE = null) : (this.existsPE = true, this.PE = this.companyFound.PE);
      this.shareFound.price == null ?
        (this.existsSharePrice = false, this.price = null) : (this.existsSharePrice = true, this.price = this.shareFound.price);
      console.log("Am I reaching this?");
      this.checkedCompany = true;
    })
  }


}
