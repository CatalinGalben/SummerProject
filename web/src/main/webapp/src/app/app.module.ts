import {BrowserModule} from "@angular/platform-browser";

import { NgModule } from '@angular/core';
import {AngularFireModule } from 'angularfire2';
import {AngularFireDatabaseModule } from 'angularfire2/database';

import { AppComponent } from './app.component';
import {environment} from "../environments/environment";
import {FormsModule} from "@angular/forms";
import {MainService} from "./stock-interogarion/main.service";
import {HttpModule} from "@angular/http";
import { StockInterogarionComponent } from './stock-interogarion/stock-interogarion.component';
import {RouterModule, Routes} from "@angular/router";



  const routes: Routes = [
    {path:'interogate', component:StockInterogarionComponent}
  ];
  @NgModule({
  declarations: [
    AppComponent,
    StockInterogarionComponent,

  ],
  imports: [
    [RouterModule.forRoot(routes)],
    BrowserModule,

    AngularFireModule.initializeApp(environment.firebase),
    AngularFireDatabaseModule,
    FormsModule,
    HttpModule
  ],
  providers: [MainService],
  bootstrap: [AppComponent]
})
export class AppModule { }
