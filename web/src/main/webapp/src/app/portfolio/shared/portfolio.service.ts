import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {HoldingRecord} from "../../add-record/shared/HoldingRecord.model";
import {Observable} from "rxjs/internal/Observable";

@Injectable({
  providedIn: 'root'
})
export class PortfolioService {

  constructor(private httpClient: HttpClient) { }

  private allRecordsUrl = 'http://localhost:8080/api/records';

  getRecords(): Observable<HoldingRecord[]> {
    return this.httpClient.get<HoldingRecord[]>(this.allRecordsUrl);
  }

  getUpdatedRecords(brokerKey: number, userKey: number, shareKey: number, recordKey: number, pricePaid: number, noShares: number): Observable<HoldingRecord[]> {
    const addShares = "addshares";
    const urlToUpdate = `${this.allRecordsUrl}/${addShares}/${brokerKey}/${userKey}/${shareKey}/${recordKey}`;
    return this.httpClient.put<HoldingRecord[]>(urlToUpdate, {pricePaid, noShares});
  }

  liquidateRecord(symbol: string, noShares: number): Observable<void> {
    const deleteString = "liquidate";
    const urlToDelete = `${this.allRecordsUrl}/${deleteString}/${symbol}`+'.';
    return this.httpClient.put<void>(urlToDelete, {noShares});
  }

}
