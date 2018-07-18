import { Component } from '@angular/core';
import {AngularFireDatabase, AngularFireList} from "angularfire2/database";
import {Router} from '@angular/router';
import {AuthService} from "./providers/auth.service";
import {trigger, state, style, transition, animate} from  '@angular/animations';


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

export class AppComponent {
  public user_displayName: String;
  public user_email: String;
  public isLoggedIn: Boolean;
  courses: any[];
  constructor(db: AngularFireDatabase, private authService: AuthService, private router: Router) {

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


  logout(){
    this.authService.logout();
    this.router.navigate(['loginGoogle']);
  }


  menuState:string = 'out';

  toggleMenu() {
    // 1-line if statement that toggles the value:
    this.menuState = this.menuState === 'out' ? 'in' : 'out';
  }

}
