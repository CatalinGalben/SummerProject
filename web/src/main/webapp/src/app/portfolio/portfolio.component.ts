import { Component, OnInit } from '@angular/core';
import { RECORDS } from '../mock-data';
import {Router} from "@angular/router";
import {TransferService} from "../providers/transfer.service";

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.css']
})

export class PortfolioComponent implements OnInit {

  records = RECORDS;
  selectedRow : string;

  constructor(private router: Router, private transferService:TransferService) { }

  ngOnInit() {
  }

  goToAdd() {
    this.router.navigate(['addRecord']);
  }

  rowClicked(name: string, noShares: number, price: number) {
    console.log("rowClicked: " + name);
    this.transferService.setData(name, noShares, price);

    this.selectedRow = name;

    console.log("PORT price " + price);

  }

}
