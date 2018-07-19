import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {TransferService} from "../providers/transfer.service";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  constructor(private router: Router, private transferService: TransferService) { }

  ngOnInit() {
  }

  goToBuy() {
    this.router.navigate(['buy']);
  }


}
