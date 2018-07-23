import { Component, OnInit } from '@angular/core';
import {AddRecordService} from "./shared/add-record.service";
import {Router} from "@angular/router";
import {Broker} from "./shared/Broker.model";

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
  existsDividendYield: boolean = false;
  existsPE: boolean = false;
  price: number;
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
    console.log(broker);
    this.selectedBroker = broker;
  }


}
