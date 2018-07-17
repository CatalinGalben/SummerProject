import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from "../providers/auth.service";
import * as firebase from "firebase";
@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  showRegister: boolean;
  constructor(public authService: AuthService, private router: Router) {

  }

  ngOnInit() {
    this.showRegister = false;
  }
  saveDetails(email:string, password:string, confirmPassword: string)
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
    this.authService.createNewAccount(email, password);

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
