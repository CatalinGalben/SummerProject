import {Component, Input, OnInit} from '@angular/core';
import {AngularFireDatabase, AngularFireList} from "angularfire2/database";
import {Router} from '@angular/router';
import {AuthService} from "./providers/auth.service";
import {trigger, state, style, transition, animate} from  '@angular/animations';
import {User} from "./login-page/shared/user.model";
import {LoginService} from "./login-page/shared/login.service";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  animations: [
    trigger('slideInOut', [
      state('in', style({
        transform: 'translate3d(0, 0, 0)'
      })),
      state('out', style({
        transform: 'translate3d(100%, 0, 0)'
      })),
      transition('in => out', animate('400ms ease-in-out')),
      transition('out => in', animate('400ms ease-in-out'))
    ]),
  ],

})

export class AppComponent implements OnInit{


  public userLoggedInAppComponent: User;
  public user_displayName: String;
  public user_email: String;
  public user_balance: number;
  public isLoggedIn: Boolean;
  public currentCurrenyId: number;

  menuState:string = 'out';
  public user_id: number;


  constructor(private authService: AuthService, private router: Router, public loginService: LoginService) {

    authService.angularFireAuth.authState.subscribe(
      (auth)=> {
        if (auth == null) {
          this.isLoggedIn = false;
          router.navigate(['loginGoogle']);
          this.user_displayName = '';
          this.user_email = '';
        }
        else {
          this.isLoggedIn = true;
          router.navigate(['']);
          this.user_displayName = auth.displayName;
          this.user_email = auth.email;
        }
      }
    )


  }

  ngOnInit(){
    //   if (this.userLoggedInAppComponent != null)
    // this.userLoggedInAppComponent = this.loginService.getCurrentUser();
    //    if (this.userLoggedInAppComponent != null) {
    //     this.user_displayName = this.userLoggedInAppComponent.firstName + " " + this.userLoggedInAppComponent.lastName;
    //     this.user_email = this.userLoggedInAppComponent.email;
    //     this.user_id = this.userLoggedInAppComponent.id;
    //     this.isLoggedIn = true;
    //   }
    this.loginService.currentUserForLogin.subscribe(user => {
      this.userLoggedInAppComponent = user;
      if (user != null) {
        this.user_balance = user.balance;
        this.user_displayName = user.firstName + " " + user.lastName;
        this.user_email = user.email;
        this.user_id = user.id;
        this.isLoggedIn = true;
      }

    })
  }

  printUser(user) {
    console.log(user);
  }

  logout(){
    this.authService.logout();
    this.isLoggedIn = false;
    this.loginService.changeUser(null);
    this.router.navigate(['loginGoogle']);
  }

  getCurrentCurrencyName(): string{
    return this.loginService.getCurrencyName();
  }

  getCurrentFactor(): number{
    return this.loginService.getCurrentFactor();
  }

  toggleMenu() {
    // 1-line if statement that toggles the value:
    this.menuState = this.menuState === 'out' ? 'in' : 'out';
  }

}
