import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {HoldingRecord} from "./HoldingRecord.model";
import {SharePrice} from "./SharePrice.model";

@Injectable({
  providedIn: 'root'
})
export class AddRecordService {

  private hrUrl = 'http://localhost:8080/api/holdingrecords';
  private spUrl = 'http://localhost:8080/api/shareprice';

  constructor(private httpClient: HttpClient ) {
  }

  // getHoldingRecords(): Observable<HoldingRecord[]> {
  //   const getHoldingRecords = "getAllHoldingRecords";
  //   const url = `${this.hrUrl}/${getHoldingRecords}`;
  //
  //   return this.httpClient
  //     .get<Array<HoldingRecord>>(url);
  // }
  //
  // getSharePrice(name: string): Observable<SharePrice> {
  //
  //   const getSharePrice = 'getSharePrice';
  //   const url = `${this.spUrl}/${getSharePrice}/${name}`;
  //   return this.httpClient
  //     .get<SharePrice>(url);
  // }
}
