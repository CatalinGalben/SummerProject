import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {User} from "./user.model";
import {BehaviorSubject} from "rxjs/internal/BehaviorSubject";
import {Subject} from "rxjs/internal/Subject";
import {Broker} from "../../add-record/shared/Broker.model";
import {Company} from "../../add-record/shared/Company.model";
import {SharePrice} from "../../add-record/shared/SharePrice.model";
import {Group} from "../../bar-chart/shared/Group.model";
import {CurrencyExchange} from "../../add-record/shared/CurrencyExchange.model";
import {Currency} from "../../add-record/shared/Currency.model";


@Injectable()
export class LoginService {

  private userLoggedInService = new Subject<User>();
  currentUser: User;
  currentFactor = 1;
  currentCurrencyName = "EUR";
  currentCurrencyExchanges: CurrencyExchange[] = [];
  currentUserForLogin = this.userLoggedInService.asObservable();

  currentBrokers;
  private brokersServiceVariable = new Subject<Broker[]>();
  allBrokers = this.brokersServiceVariable.asObservable();

  factorServiceVariable = new Subject<number>();
  factorOnService = this.factorServiceVariable.asObservable();

  private currencyExchangesServiceVariable = new Subject<CurrencyExchange[]>();
  allCurrencyExchanges = this.currencyExchangesServiceVariable.asObservable();

  private companiesServiceVariable = new Subject<Company[]>();
  allCompanies = this.companiesServiceVariable.asObservable();

  private sharePricesServiceVariable = new Subject<SharePrice[]>();
  allSharePrices = this.sharePricesServiceVariable.asObservable();

  private groupServiceVariable = new Subject<Group[]>();
  allGroups = this.groupServiceVariable.asObservable();

  constructor(private httpClient: HttpClient) { }

  private addUrl = 'http://localhost:8080/api/users';
  private currencyUrl = 'http://localhost:8080/api/currency';

  createUserEmail(firstName:string, lastName:string, email: string, username: string, password: string, dob: string): Observable<User> {
    const addUser = "register";
    const url = `${this.addUrl}/${addUser}`;
    let id: number = 0;
    let balance: number = 0.0;
    let type: string = "user";
    console.log(url + " login service - front-end");
    let userAdded = {id, firstName, lastName, email, username, password, type, balance, dob};
    console.log("before return");
    console.log(userAdded);
    return this.httpClient.post<User>(url, userAdded);
  }

  loginUserEmail(username: string, password: string): Observable<User>{
    const url = `${this.addUrl}/${username}/${password}`;
    console.log(url  + " login service -- front-end");
    return this.httpClient.get<User>(url)
  }

  getActualDetailsUser(key: number):Observable<User>{
    const url = `${this.addUrl}/${key}`;
    console.log(url  + " getActualDetailsUser -- front-end");
    return this.httpClient.get<User>(url);
  }
  addDividendService(newBalanceValue: number, symbol:string, userKey: number): Observable<User>{
    const dividend = "dividend";
    const url = `${this.addUrl}/${dividend}/${symbol}/${userKey}`;
    console.log(url + " addDividendService -- front-end");
    return this.httpClient.post<User>(url, {newBalanceValue});
  }

  setNewBalanceUserService(key: number, newBalanceValue: number): Observable<User>{
    const balance = "balance";
    const url =`${this.addUrl}/${balance}/${key}`;
    console.log(url);
    return this.httpClient.post<User>(url, {newBalanceValue});
  }

  getActualCurrencyExchanges(): Observable<CurrencyExchange[]>{
    const allCE = "currencyexchanges";
    const url =`${this.currencyUrl}/${allCE}`;
    console.log(url);
    return this.httpClient.get<CurrencyExchange[]>(url);
  }

  getAllCurrencies(): Observable<Currency[]>{
    const allC = "currencies";
    const url =`${this.currencyUrl}/${allC}`;
    return this.httpClient.get<Currency[]>(url);
  }



  getNewCurrencyExchange(id: number): CurrencyExchange {
    return this.currentCurrencyExchanges.filter(CE => CE.currencyid2==id)[0];
  }

  changeUser(userLogged: User){
    this.currentUser = userLogged;
  }

  changeBrokers(brokers: Broker[]){
    this.brokersServiceVariable.next(brokers);
  }

  changeCompanies(companies: Company[]){
    this.companiesServiceVariable.next(companies);
  }

  changeSharePrices(shareprices: SharePrice[]){
    this.sharePricesServiceVariable.next(shareprices);
  }

  changeCurrencyExchangesObservable(currencyExchanges: CurrencyExchange[]){
    this.currencyExchangesServiceVariable.next(currencyExchanges);
    this.currentCurrencyExchanges = currencyExchanges;
  }

  changeSymbolNameObservable(symbol: string){
    this.currentCurrencyName = symbol;
  }

  getCurrentUser(): User {
    return this.currentUser;
  }

  getCurrencyName(): string {
    return this.currentCurrencyName;
  }

  getCurrentFactor(): number {
    return this.currentFactor;
  }

  changeFactorObservable(newFactor: number){
    this.factorServiceVariable.next(newFactor);
    this.currentFactor = newFactor;
  }


  changeUserObservable(user: User){
    this.userLoggedInService.next(user);
    this.currentUser = user;
  }

  changeGroups(groups: Group[]){
    this.groupServiceVariable.next(groups);
  }



}
