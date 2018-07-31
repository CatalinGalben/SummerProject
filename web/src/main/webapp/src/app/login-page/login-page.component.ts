import {Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from "../providers/auth.service";
import * as firebase from "firebase";
import {LoginService} from "./shared/login.service";
import {User} from "./shared/user.model";
import {Subscription} from "rxjs/internal/Subscription";


@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit{

  showRegister: boolean;
  userLoggedIn: User;
  sub: Subscription;
  constructor(public authService: AuthService, private router: Router, private loginService: LoginService) {
  }

  ngOnInit() {
    this.showRegister = false;
    // this.userLoggedIn = this.loginService.getCurrentUser();
    this.loginService.currentUserForLogin.subscribe(user=> this.userLoggedIn = user);
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
    this.loginService.createUserEmail(firstName, lastName, email, username, password, DOB).subscribe(_=>{
      console.log("works");
      alert("Your account has been created!");
    });
    this.registerClicked();
  }

  getLoginDetails(username: string, password: string) {
    if (password.length < 6)
    {
      alert("Password should be at least 6 characters long");
      return;
    }
    this.sub = this.loginService.loginUserEmail(username, password).subscribe(user => {
      this.loginService.changeUserObservable(user);
      if (this.userLoggedIn.username != null){
        this.router.navigate(['']).then(
          ()=>this.loginService.getActualCurrencyExchanges().subscribe(
            ce => this.loginService.changeCurrencyExchangesObservable(ce)
          )
        );
      }
      else
        alert("Invalid Username or password");
    })
  }

  registerClicked()
  {
    this.showRegister = !this.showRegister;
  }
  loginGoogle() {

    this.authService.loginWithGoogle().then(() =>
      this.router.navigate([''])).catch(err => alert(err));
  }

}
