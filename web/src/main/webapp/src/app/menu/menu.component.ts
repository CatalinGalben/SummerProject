import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {TransferService} from "../providers/transfer.service";
import { Location } from '@angular/common';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  constructor(private router: Router, private transferService: TransferService,  private location: Location) { }

  ngOnInit() {
  }

  goToBuy() {
    this.router.navigate(['buy']);
  }

  goBack() {
    this.location.back();
  }

  goToChart() {
    this.router.navigate(['barChart']);
  }

}
