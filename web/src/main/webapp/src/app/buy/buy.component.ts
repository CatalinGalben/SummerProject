import {Component, Input, OnInit} from '@angular/core';
import {TransferService} from "../providers/transfer.service";

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

  constructor(private transferService:TransferService) { }

  ngOnInit() {
    this.name = this.transferService.getName();
    this.noShares = this.transferService.getNoShares();
    this.price = this.transferService.getPrice();
  }

  calcPrice(searchValue : number ) {
    this.totPrice = Number(searchValue) * 10;
    console.log(this.totPrice);
  }





}
