import { Component, OnInit } from '@angular/core';
import { RECORDS } from '../mock-data';
import {Router} from "@angular/router";
import {TransferService} from "../providers/transfer.service";
import {Record} from "../record";
import {v} from "@angular/core/src/render3";

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
    this.transferService.setData(name, noShares, price);

    this.selectedRow = name;
  }


  insertionSort(rec: Record[], n:number)
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
