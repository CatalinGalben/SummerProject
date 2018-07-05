import { Component } from '@angular/core';
import {AngularFireDatabase, AngularFireList} from "angularfire2/database";
import {Router} from '@angular/router';
import {AuthService} from "./providers/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  public user_displayName: String;
  public user_email: String;
  public isLoggedIn: Boolean;
  courses: any[];
  constructor(db: AngularFireDatabase, authService: AuthService, router: Router) {

    // testing the database

    // db.list('/courses')
    //   .valueChanges()
    //   .subscribe(co => {
    //     this.courses = co;
    //     console.log(this.courses);
    //   })

    authService.angularFireAuth.authState.subscribe(
      (auth)=> {
        if (auth == null) {
          this.isLoggedIn = false;
          router.navigate(['login']);
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



}
