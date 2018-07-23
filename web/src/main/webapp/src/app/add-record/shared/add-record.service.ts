import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {HoldingRecord} from "./HoldingRecord.model";
import {SharePrice} from "./SharePrice.model";
import {Broker} from "./Broker.model";

@Injectable({
  providedIn: 'root'
})
export class AddRecordService {

  private hrUrl = 'http://localhost:8080/api/holdingrecords';
  private brokersUrl = 'http://localhost:8080/api/brokers';

  constructor(private httpClient: HttpClient ) {
  }

  getAllBrokers(): Observable<Broker[]>{
    return this.httpClient.get<Broker[]>(this.brokersUrl);
  }
}
