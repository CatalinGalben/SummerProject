import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {HoldingRecord} from "./HoldingRecord.model";
import {SharePrice} from "./SharePrice.model";
import {Broker} from "./Broker.model";
import {CompanyShare} from "./CompanyShare";
import {Trust} from "./Trust.model";
import {Company} from "./Company.model";

@Injectable({
  providedIn: 'root'
})
export class AddRecordService {

  private brokersUrl = 'http://localhost:8080/api/brokers';
  private companyUrl = 'http://localhost:8080/api/shareprice';
  private fillingUrl = 'http://localhost:8080/api/iaopauza';
  private addUrl = 'http://localhost:8080/api/holdingrecords';
  private companiesUrl = 'http://localhost:8080/api/companies';
  private sharePricesUrl = 'http://localhost:8080/api/shareprices';


  constructor(private httpClient: HttpClient ) {
  }

  sendCompleteDetails(companyShareDTO: CompanyShare): Observable<void>{
    return this.httpClient.post<void>(this.fillingUrl, companyShareDTO);
  }

  // this.companyFound.id, this.companyFound.currencyid, name, price, dividendYield, pe, this.shareFound.id, this.shareFound.date

  getAllBrokers(): Observable<Broker[]>{
    return this.httpClient.get<Broker[]>(this.brokersUrl);
  }

  getAllCompanies(): Observable<Company[]>{
    return this.httpClient.get<Company[]>(this.companiesUrl);
  }

  getAllSharePrices(): Observable<SharePrice[]>{
    return this.httpClient.get<SharePrice[]>(this.sharePricesUrl);
  }

  getBrokerDetails(name: string): Observable<Broker> {
    const brokerUrl = `${this.brokersUrl}/${name}`;
    return this.httpClient.get<Broker>(brokerUrl);
  }

  getCompanyDetails(symbol: string): Observable<CompanyShare>{
    const companyUrl =`${this.companyUrl}/${symbol}`+'.';
    console.log(companyUrl);
    return this.httpClient.get<CompanyShare>(companyUrl);
  }

  addNormalCompany(userid: number, brokerid: number, companyid: number, pricePaid: number, noShares: number): Observable<HoldingRecord>{
    //    id: number;
    //   userid: number;
    //   brokerid: number;
    //   companyid: number;
    //   pricePaid: number;
    //   noShares: number;
    const add = "add";
    let id = 0;
    let holdingRecord = {id, userid, brokerid, companyid, pricePaid, noShares};
    const addUrlLocal =`${this.addUrl}/${add}`;
    return this.httpClient.post<HoldingRecord>(addUrlLocal, holdingRecord);
  }

  addTrust(userid: number, brokerid: number, companyid: number, pricePaid: number, noShares: number, gearing: number, premiumDiscount: number, nav: number, ter: number): Observable<HoldingRecord>{
    const add = "addtrust";
    let id = 0;
    let holdingRecord = {id, userid, brokerid, companyid, pricePaid, noShares};
    let trust = {id, nav, ter, gearing, premiumDiscount};
    let trustWrapper = {holdingRecord, trust};
    const addUrlLocal =`${this.addUrl}/${add}`;
    return this.httpClient.post<HoldingRecord>(addUrlLocal, trustWrapper);
  }

  addETF(userid: number, brokerid: number, companyid: number, pricePaid: number, noShares: number, nav: number, ter: number, type: number): Observable<HoldingRecord>{
    const add = "addetf";
    let id = 0;
    let holdingRecord = {id, userid, brokerid, companyid, pricePaid, noShares};
    let etf = {id:id, nav: nav, ter: ter, type: type};
    let etfWrapper = {holdingRecord, etf};
    const addUrlLocal =`${this.addUrl}/${add}`;
    return this.httpClient.post<HoldingRecord>(addUrlLocal, etfWrapper);
  }
}
