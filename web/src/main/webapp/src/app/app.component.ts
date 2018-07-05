import {Component, OnInit} from '@angular/core';
import {AngularFireDatabase, AngularFireList} from "angularfire2/database";
import {MainService} from "./stock-interogarion/main.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent{
  courses: any[];

 constructor(db: AngularFireDatabase, ) {
    db.list('/courses')
      .valueChanges()
      .subscribe(co => {
        this.courses = co;
        console.log(this.courses);
      });


  }

}




