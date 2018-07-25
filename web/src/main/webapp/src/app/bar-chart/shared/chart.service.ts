import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";

import {SharePrice} from "../../add-record/shared/SharePrice.model";
import {CompanyShare} from "../../add-record/shared/CompanyShare";


@Injectable({
  providedIn: 'root'
})
export class ChartService {

  private companyUrl = 'http://localhost:8080/api/iaopauza';
  private sharesUrl = 'http://localhost:8080/api/shareprice';

  constructor(private httpClient: HttpClient) { }

  getCompanyDetails(symbol: string): Observable<CompanyShare>{
    const companyUrl =`${this.companyUrl}/${symbol}`;
    return this.httpClient.get<CompanyShare>(companyUrl);
  }

  getAllShares(): Observable<SharePrice[]>{
    return this.httpClient.get<SharePrice[]>(this.sharesUrl);
  }



}
