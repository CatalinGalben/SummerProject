import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {HoldingRecord} from "../../add-record/shared/HoldingRecord.model";
import {Observable} from "rxjs/internal/Observable";
import {DatePipe} from "@angular/common";
import {MetricYear} from "../../mockmodels";
import {Metrics} from "../../add-record/shared/Metrics.model";

@Injectable({
  providedIn: 'root'
})
export class PortfolioService {

  constructor(private httpClient: HttpClient,
              private datePipe: DatePipe) { }

  private allRecordsUrl = 'http://localhost:8080/api/records';
  private allMetricsUrl = 'http://localhost:8080/api/metrics';

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

  getNewSharePrice(companyid: number, newBalanceValue: number): Observable<void> {
    const updateUrl = `${this.allRecordsUrl}/${companyid}`;
    return this.httpClient.put<void>(updateUrl, {newBalanceValue});
  }


  getCurrentDate(): string{
    return this.datePipe.transform(Date.now(), 'yyyy/MM/dd');
  }

  getAllMetricsService(holdingrecordId: number): Observable<Metrics[]> {
    const updateUrl = `${this.allMetricsUrl}/${holdingrecordId}`;
    return this.httpClient.get<Metrics[]>(updateUrl);
  }

}
