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

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.css']
})

export class PortfolioComponent implements OnInit {

  brokers: Broker[];
  records = RECORDS;
  selectedRow : string;
  userLoggedInPortfolioComponent: User;
  holdingRecords: HoldingRecord[];

  constructor(private router: Router,
              private transferService:TransferService,
              private loginService: LoginService,
              private portfolioService: PortfolioService,
              private recordService: AddRecordService) { }

  ngOnInit() {
    //get loggedIn user
    this.loginService.currentUser.subscribe(user =>
    this.userLoggedInPortfolioComponent = user);

    //get all records
    this.portfolioService.getRecords().subscribe(records => {
      this.holdingRecords = records
        .filter(record => record.userid == this.userLoggedInPortfolioComponent.id);
    });

    //get all brokers
    this.recordService.getAllBrokers().subscribe(brokers =>
    this.brokers = brokers);
    this.loginService.changeBrokers(this.brokers);
  }

  goToAdd() {
    this.router.navigate(['addRecord']);
  }

  rowClicked(name: string, noShares: number, price: number) {
    this.transferService.setData(name, noShares, price);

    this.selectedRow = name;
  }


  insertionSort(rec: Mockmodels[], n:number)
  {
    let i, key, j;
    for (i = 1; i < n; i++)
    {
      key = this.records[i];
      j = i-1;

      /* Move elements of arr[0..i-1], that are
         greater than key, to one position ahead
         of their current position */
      while (j >= 0 && this.records[j].name > key.name)
      {
        this.records[j+1] = this.records[j];
        j = j-1;
      }
      this.records[j+1] = key;
      }
    }

}
