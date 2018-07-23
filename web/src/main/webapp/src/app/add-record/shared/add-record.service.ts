import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {HoldingRecord} from "./HoldingRecord.model";
import {SharePrice} from "./SharePrice.model";
import {Broker} from "./Broker.model";
import {CompanyShare} from "./CompanyShare";

@Injectable({
  providedIn: 'root'
})
export class AddRecordService {

  private brokersUrl = 'http://localhost:8080/api/brokers';
  private companyUrl = 'http://localhost:8080/api/iaopauza';


  constructor(private httpClient: HttpClient ) {
  }

  getAllBrokers(): Observable<Broker[]>{
    return this.httpClient.get<Broker[]>(this.brokersUrl);
  }

  getCompanyDetails(symbol: string): Observable<CompanyShare>{
    const companyUrl =`${this.companyUrl}/${symbol}`;
    return this.httpClient.get<CompanyShare>(companyUrl);
  }
}
