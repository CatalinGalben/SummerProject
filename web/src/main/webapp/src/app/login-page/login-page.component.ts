import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from "../providers/auth.service";
import * as firebase from "firebase";
import {LoginService} from "./shared/login.service";
@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  showRegister: boolean;
  constructor(public authService: AuthService, private router: Router, private loginService: LoginService) {

  }

  ngOnInit() {
    this.showRegister = false;
  }
  saveDetails(firstName:string, lastName:string, email: string, username: string, password: string, confirmPassword: string, DOB: string)
  {

    if(password.length < 6)
    {
      alert("Password should be at least 6 characters long");
      return;
    }
    if(password != confirmPassword)
    {
      alert("Password and Confirm password fields do not match!");
      this.showRegister = false;
      return;
    }
    this.loginService.createUserEmail(firstName, lastName, email, username, password, DOB);

  }
  registerClicked()
  {
    this.showRegister = !this.showRegister;
  }
  loginGoogle() {

    this.authService.loginWithGoogle().then(() =>
      this.router.navigate([''])).catch(err => alert(err));
  }

  loginEmail(user: string, pass: string) {
    this.authService.loginWithEmail(user, pass).then(() =>
      this.router.navigate([''])).catch(err => alert(err));
  }
}
