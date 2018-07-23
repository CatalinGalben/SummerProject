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

import { BuyComponent } from './buy/buy.component';
import {TransferService} from "./providers/transfer.service";
import {LoginService} from "./login-page/shared/login.service";
import {HttpClientModule} from "@angular/common/http";
import { BarChartComponent } from './shared/bar-chart/bar-chart.component';
import {NgxChartsModule} from "@swimlane/ngx-charts";

const routes: Routes=[
  {path: 'homePage', component: HomePageComponent},
  {path: '', component: PortfolioComponent},
  {path: 'loginGoogle', component: LoginPageComponent},
  {path: 'interrogate', component: StockInterogarionComponent},
  {path: 'portfolio', component: PortfolioComponent},
  {path: 'addRecord', component: AddRecordComponent},
  {path: 'buy', component: BuyComponent},
  {path: 'barChart', component: BarChartComponent}
];


@NgModule({
  declarations: [
    AppComponent,
    StockInterogarionComponent,
    LoginPageComponent,
    HomePageComponent,
    PortfolioComponent,
    AddRecordComponent,
    MenuComponent,
    BuyComponent,
    BarChartComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFireDatabaseModule,
    FormsModule,
    RouterModule.forRoot(routes),

    BrowserAnimationsModule,
    NgxChartsModule
  ],
  providers: [AuthService, AngularFireAuth, MainService, TransferService, LoginService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
