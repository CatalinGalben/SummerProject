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

  constructor(private router: Router, private transferService:TransferService) { }

  ngOnInit() {
  }

  goToAdd() {
    this.router.navigate(['interrogate']);
  }

  rowClicked(name: string, noShares: number, price: number) {
    console.log("rowClicked: " + name);
    this.transferService.setData(name, noShares, price);

  }

}
