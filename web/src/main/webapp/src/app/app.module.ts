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
import { PortfolioComponent } from './portfolio/portfolio.component';
import { AddRecordComponent } from './add-record/add-record.component';
import { MenuComponent } from './menu/menu.component';
import {BrowserAnimationsModule, NoopAnimationsModule} from "@angular/platform-browser/animations";
import {LoginService} from "./login-page/shared/login.service";

const routes: Routes=[
  {path: 'homePage', component: HomePageComponent},
  {path: '', component: PortfolioComponent},
  {path: 'loginGoogle', component: LoginPageComponent},
  {path: 'interrogate', component: StockInterogarionComponent},
  {path: 'portfolio', component: PortfolioComponent},
  {path: 'addRecord', component: AddRecordComponent}
];


@NgModule({
  declarations: [
    AppComponent,
    StockInterogarionComponent,
    LoginPageComponent,
    HomePageComponent,
    PortfolioComponent,
    AddRecordComponent,
    MenuComponent
  ],
  imports: [
    BrowserModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFireDatabaseModule,
    HttpModule,
    FormsModule,
    RouterModule.forRoot(routes),

    BrowserAnimationsModule,
    NoopAnimationsModule
  ],
  providers: [AuthService, AngularFireAuth, MainService, LoginService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
