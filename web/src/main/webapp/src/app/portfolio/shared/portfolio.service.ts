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
}
