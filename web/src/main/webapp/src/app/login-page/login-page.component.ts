import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from "../providers/auth.service";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  constructor(public authService: AuthService, private router: Router) {

  }

  ngOnInit() {
  }

  loginGoogle() {
    this.authService.loginWithGoogle().then((data) => {
      this.router.navigate([''])
    })
  }

  loginEmail(user: string, pass: string) {
    this.authService.loginWithEmail(user, pass).then((data) => {
      this.router.navigate([''])
    })
  }
}
