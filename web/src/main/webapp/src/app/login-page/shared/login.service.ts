import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {User} from "./user.model";
import {BehaviorSubject} from "rxjs/internal/BehaviorSubject";
import {Subject} from "rxjs/internal/Subject";


@Injectable()
export class LoginService {

  private userLoggedInService = new Subject<User>();
  currentUser = this.userLoggedInService.asObservable();

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
    console.log(this.addUrl  + " login service - front-end");
    return this.httpClient.get<User>(url)
  }

  changeUser(userLogged: User){
    this.userLoggedInService.next(userLogged)
  }

}
