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
import {StockInterogarionComponent} from "./stock-interogarion/stock-interogarion.component";
import {FormsModule} from "@angular/forms";
import {MainService} from "./stock-interogarion/main.service";
import {HttpModule} from "@angular/http";

const routes: Routes=[
  {path: '', component: HomePageComponent},
  {path: 'login', component: LoginPageComponent},
  {path: 'interrogate', component: StockInterogarionComponent}
];


@NgModule({
  declarations: [
    AppComponent,
    StockInterogarionComponent,
    LoginPageComponent,
    HomePageComponent
  ],
  imports: [
    BrowserModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFireDatabaseModule,
    HttpModule,
    FormsModule,
    RouterModule.forRoot(routes)
  ],
  providers: [AuthService, AngularFireAuth, MainService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
