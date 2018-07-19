import { Injectable } from '@angular/core';

@Injectable()
export class TransferService {

  constructor() {}

  private name: string;
  private noShares: number;
  private price: number;


  clearData(){
    this.name = this.noShares = this.price = undefined;
  }

  setData(name: string, noShares: number, price: number) {
    this.name = name;
    this.noShares = noShares;
    this.price = price;
  }

  getName(){
    let temp = this.name;
    this.clearData();
    return temp;
  }

  getNoShares() {
    let temp = this.noShares;
    this.clearData();
    return temp;
  }

  getPrice() {
    let temp = this.price;
    this.clearData();
    return temp;
  }


}
