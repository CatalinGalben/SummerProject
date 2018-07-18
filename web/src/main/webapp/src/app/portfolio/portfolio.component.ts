import { Component, OnInit } from '@angular/core';
import { RECORDS } from '../mock-data';
import {Router} from "@angular/router";

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.css']
})

export class PortfolioComponent implements OnInit {

  records = RECORDS;

  constructor(private router: Router) { }

  ngOnInit() {
  }

  goToAdd() {
    this.router.navigate(['interrogate']);
  }

}
