import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {User} from "./user.model";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private httpClient: HttpClient) { }

  private addUrl = 'http://localhost:8080/api/users';

  createUserEmail(firstName:string, lastName:string, email: string, username: string, password: string, DOB: string): Observable<User> {
    const addUser = "register";
    const url = `${this.addUrl}/${addUser}`;
    let id: number = 0;
    console.log("before add");
    let userAdded = {id, firstName, lastName, email, username, password, DOB};

    return this.httpClient
      .post<User>(url, userAdded);
  }

}
