import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {LoginService} from "../login-page/shared/login.service";
import {PortfolioComponent} from "../portfolio/portfolio.component";
import {Currency} from "../add-record/shared/Currency.model";
import {CurrencyExchange} from "../add-record/shared/CurrencyExchange.model";

@Component({
  selector: 'app-change-currency',
  templateUrl: './change-currency.component.html',
  styleUrls: ['./change-currency.component.css']
})
export class ChangeCurrencyComponent implements OnInit {

  currencies: Currency[];
  selectedCurrencyId: number;
  currencyExchangeChanged: CurrencyExchange;

  constructor(private router: Router, private loginService: LoginService) { }

  ngOnInit() {
    this.loginService.getAllCurrencies().subscribe(currencies=> {
        this.currencies = currencies;
        console.log(this.currencies);
      })
  };

  setNewCurrency(args){
    this.selectedCurrencyId = args.target.value;
  }

  changeCurrency(){
    this.currencyExchangeChanged =  this.loginService.getNewCurrencyExchange(this.selectedCurrencyId);
    this.loginService.changeSymbolNameObservable(this.currencies.filter(cu => cu.id == this.selectedCurrencyId)[0].symbol);
    this.loginService.changeFactorObservable(this.currencyExchangeChanged.factor);
    this.router.navigate(['']);
  }


}
