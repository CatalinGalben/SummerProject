import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Router} from "@angular/router";
import {TransferService} from "../providers/transfer.service";
import { Location } from '@angular/common';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  @Output() closeEvent = new EventEmitter<string>(); // menu close icon

  constructor(private router: Router, private transferService: TransferService,  private location: Location) { }

  ngOnInit() {
  }


  // tell the parent, app.component, to close the menu
  closeMenu() {
    this.closeEvent.next();
  }


  goBack() {
    this.location.back();
  }


  goToBuy() {
    this.router.navigate(['addRecord']);
  }

  goToChart() {
    this.router.navigate(['barChart']);
  }

  goToCreateGroup() {
    this.router.navigate(['addGroup']);
  }

  goToCurrency(){
    this.router.navigate(['currency']);
  }

  goToPortfolio(){
    this.router.navigate(['']);
  }

}
