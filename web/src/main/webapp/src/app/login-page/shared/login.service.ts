import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {User} from "./user.model";
import {BehaviorSubject} from "rxjs/internal/BehaviorSubject";
import {Subject} from "rxjs/internal/Subject";
import {Broker} from "../../add-record/shared/Broker.model";
import {Company} from "../../add-record/shared/Company.model";
import {SharePrice} from "../../add-record/shared/SharePrice.model";


@Injectable()
export class LoginService {

  private userLoggedInService = new Subject<User>();
  currentUser: User;
  currentUserForLogin = this.userLoggedInService.asObservable();

  currentBrokers;
  private brokersServiceVariable = new Subject<Broker[]>();
  allBrokers = this.brokersServiceVariable.asObservable();

  private companiesServiceVariable = new Subject<Company[]>();
  allCompanies = this.companiesServiceVariable.asObservable();

  private sharePricesServiceVariable = new Subject<SharePrice[]>();
  allSharePrices = this.sharePricesServiceVariable.asObservable();

  constructor(private httpClient: HttpClient) { }

  private addUrl = 'http://localhost:8080/api/users';

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
    console.log(this.addUrl  + " login service -- front-end");
    return this.httpClient.get<User>(url)
  }

  getActualDetailsUser(key: number):Observable<User>{
    const url = `${this.addUrl}/${key}`;
    console.log(this.addUrl  + " getActualDetailsUser -- front-end");
    return this.httpClient.get<User>(url);
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

  getCurrentUser(): User {

    return this.currentUser;
  }

  changeUserObservable(user: User){
    this.userLoggedInService.next(user);
    this.currentUser = user;
  }



}
