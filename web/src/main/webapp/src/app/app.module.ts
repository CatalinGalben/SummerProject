import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {AngularFireModule } from 'angularfire2';
import {AngularFireDatabaseModule } from 'angularfire2/database';
import { AuthService} from "./providers/auth.service";

import { AppComponent } from './app.component';
import {environment} from "../environments/environment";
import { LoginPageComponent } from './login-page/login-page.component';
import { HomePageComponent } from './home-page/home-page.component';

import {RouterModule, Routes} from '@angular/router';
import {AngularFireAuth} from "angularfire2/auth";

const routes: Routes=[
  {path: '', component: HomePageComponent},
  {path: 'login', component: LoginPageComponent}
];


@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    HomePageComponent
  ],
  imports: [
    BrowserModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFireDatabaseModule,
    RouterModule.forRoot(routes)
  ],
  providers: [AuthService, AngularFireAuth],
  bootstrap: [AppComponent]
})
export class AppModule {
}
