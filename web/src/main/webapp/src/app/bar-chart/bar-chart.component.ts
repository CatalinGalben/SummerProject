import { Component, OnInit, OnChanges, Input } from '@angular/core';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import {ChartService} from "./shared/chart.service";
import {Group} from "./shared/Group.model";
import {LoginService} from "../login-page/shared/login.service";
import {User} from "../login-page/shared/user.model";
import {HoldingRecord} from "../add-record/shared/HoldingRecord.model";
import {PortfolioService} from "../portfolio/shared/portfolio.service";
import {Company} from "../add-record/shared/Company.model";
import {AddRecordService} from "../add-record/shared/add-record.service";
import {CompanyGroup} from "./shared/CompanyGroup.model";
import {SharePrice} from "../add-record/shared/SharePrice.model";
import {Broker} from "../add-record/shared/Broker.model";

import { DatePipe } from '@angular/common';
import {Currency} from "../add-record/shared/Currency.model";
import {CurrencyExchange} from "../add-record/shared/CurrencyExchange.model";

@Component({
  selector: 'app-ngx-charts',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.css']
})
export class BarChartComponent implements OnInit {
  results: any;

  results2 = [];

  selectedRow: Group;

  userLoggedIn: User;
  companies: Company[] = [];
  companyGroups: CompanyGroup[] = [];

  holdingRecords: HoldingRecord[] = [];
  groups: Group[] = [];
  sharePrices: SharePrice[] = [];
  currentCompanyNamePortfolio: string;
  brokers: Broker[] = [];


  constructor(private groupService: ChartService,
              private loginService: LoginService,
              private portfolioService: PortfolioService,
              private recordService: AddRecordService,
              private datePipe: DatePipe) {

    this.populate();
  }

  ngOnInit() {
    this.selectedRow = {name:"", id:0, parentGroupID:0};

    this.results2.push({  // add new node
      "series": [
        {
          "name": "2018/07/11",
          "value": "6918"
        },
        {
          "name": "2018/01/31",
          "value": "3183"

        },
        {
          "name": "2018/08/23",
          "value": "6903"
        }
      ],
      "name": "Zero"

    });

  }

  populate() {
    if (this.loginService.getCurrentUser() != null) {
        // get user data
        this.userLoggedIn = this.loginService.getCurrentUser();
        console.log(this.userLoggedIn);
        console.log("POPULATE");

        // get all records for the user
        this.portfolioService.getRecords().subscribe(records => {
            //let auxholdingRecords = records;
            //this.holdingRecords = auxholdingRecords.filter(hr => {hr.userid === this.userLoggedIn.id; console.log(hr.userid + " " + this.userLoggedIn.id)});
            for (let i=0; i<records.length; i++) {
              if (records[i].userid == this.userLoggedIn.id) {
                this.holdingRecords.push(records[i]);
              }
            }

            // get all the companies of the user
            this.recordService.getAllCompanies().subscribe(records => {
                this.companies = records;
                this.companies.filter(comp => {
                  for (let i = 0; i < this.holdingRecords.length; i++)
                    if (this.holdingRecords[i].companyid == comp.id) {
                      return true;
                    }
                  return false;
                } );

              // get all CompanyGroups of the user
              this.groupService.getCompanyGroups().subscribe(records => {
                //this.companyGroups = records;
                /*this.companyGroups.filter(comp => {
                  for (let i = 0; i < this.holdingRecords.length; i++)
                    if (this.holdingRecords[i].companyid == comp.companyid)
                      return true;
                  return false;
                } );*/

                for (let i=0; i<records.length; i++) {
                  for (let j = 0; j < this.holdingRecords.length; j++)
                    if (this.holdingRecords[j].companyid == records[i].companyid) {
                        this.companyGroups.push(records[i]);
                        break;
                    }
                }

                // get all user's groups
                this.groupService.getGroups().subscribe( records => {
                   // this.groups = records;
                    /*this.groups.filter(gr => {
                      for (let i = 0; i < this.companyGroups.length; i++) {
                        if (this.companyGroups[i].groupid == gr.id)
                          return true;
                      }
                      return false;
                    });*/

                    for (let i=0; i<records.length; i++) {
                      for (let j = 0; j < this.companyGroups.length; j++) {
                        if (this.companyGroups[j].groupid == records[i].id) {
                            this.groups.push(records[i]);
                            break;
                        }
                      }
                    }
                });
              });
            });
        });
    }

    //get all sharePrices
    this.recordService.getAllSharePrices().subscribe(shareprices =>{
      this.sharePrices = shareprices.sort((s1, s2)=> s1.id-s2.id);
      this.loginService.changeSharePrices(this.sharePrices);});

    //get all brokers
    this.recordService.getAllBrokers().subscribe(brokers => {
      this.brokers = brokers;
      this.loginService.changeBrokers(this.brokers);
    });

    // get the currencies
    this.loginService.getAllCurrencies().subscribe(currencies=> {
      this.currencies = currencies;
      console.log(this.currencies);
    })

  }

  getCompanyForRecord(id: number, rec: HoldingRecord): string{
    this.currentCompanyNamePortfolio = this.companies.filter(company => company.id == id)[0].name;
    return this.currentCompanyNamePortfolio
  }

  getCurrentShareForCompany(companyid: number): number{
    return this.sharePrices.filter(shareprice => shareprice.companyid == companyid)[0].price;
  }

  getGain(paid: number, sumNow: number): number{
    let result = paid/sumNow;
    if (result >= 1){
      return (result - 1)*100;
    }
    else
      return (1 - result)*100;
  }

  getBrokerForRecord(brokerid: number): string {
    return this.brokers.filter(broker => broker.id == brokerid)[0].name;
  }

  clickRecord(group: Group) {
    console.log("NAME " + name);

    this.selectedRow = group;
    this.displayPrice();
  }

  displayPrice() {
    if (this.selectedRow.name == "") {
      alert("Please select a group");
    } else {

      this.groupService.getBenchmarks(this.selectedRow.id, this.selectedRow.name).subscribe(records => {
          this.results2 = [];

          let d = Object.values(records)[0];

          let arr = Object.values(d[0])[0];
          for (let i = 0; i < arr.length; i++) {
            this.results2.push(arr[i]);
          }

          // change the price value based on the selected currency
          for (let i = 0; i < this.results2.length; i++) {
            for (let k = 0; k < this.results2[i].series.length; k++) {
              this.results2[i].series[k].value = this.results2[i].series[k].value * this.factor;
            }
          }

          this.results2 = [...this.results2] // This will generate a new array and trigger the change detection to ngx chart
        }
      );
    }
  }

  displayPercentage() {
    if (this.selectedRow.name == "") {
      alert("Please select a group");
    } else {

      this.groupService.getBenchmarks(this.selectedRow.id, this.selectedRow.name).subscribe(records => {
          this.results2 = [];

          let d = Object.values(records)[0];
          let arr = [];
          arr.push(Object.values(d[0])[0]);
          for (let i = 0; i < arr[0].length; i++) {
            let sharename = arr[0][i].name;
            let companyid = this.companies.filter(c => c.name == sharename)[0].id;
            let init_price = arr[0][i].series[0].value;

            for (let k = 0; k < arr[0][i].series.length; k++) {
              arr[0][i].series[k].value = ((arr[0][i].series[k].value - init_price) / init_price) * 100;
            }
            this.results2.push(arr[0][i]);
          }
          this.results2 = [...this.results2] // This will generate a new array and trigger the change detection to ngx chart
        }
      );
    }
  }

  displayTotal() {
    if (this.selectedRow.name == "") {
      alert("Please select a group");
    } else {

      this.groupService.getBenchmarks(this.selectedRow.id, this.selectedRow.name).subscribe(records => {
        this.results2 = [];

        let d = Object.values(records)[0];
        let arr = [];
        arr.push(Object.values(d[0])[0]);
        console.log(arr);
        let series = [];

        // for each share
        for (let i = 0; i < arr[0].length; i++) {
          let sharename = arr[0][i].name;
          let companyid = this.companies.filter(c => c.name == sharename)[0].id;

          let date = arr[0][0].series[0].name; // the date from where to start the graph  arr[0][0].series[0].name;
          let w = new DatePipe('en-US').transform(new Date(), 'yyyy/MM/dd');

          // go until the present day
          while (date <= w) {
            // find the values of the date
            let flag = false;
            for (let k = 0; k < arr[0][i].series.length && flag == false; k++) {
              let share_date = arr[0][i].series[k].name;

              if (date == share_date) {
                flag = true;

                // get the price
                arr[0][i].series[k].value = arr[0][i].series[k].value * this.holdingRecords.filter(r => r.companyid == companyid)[0].noShares * this.factor;

                // find the index of the position where the date is in series
                let index = series.length;
                let found = false;
                for (var j = 0; j < series.length; j++) {
                  if (series[j].name == date) {
                    index = j;
                    found = true;
                    break;
                  }
                }
                // if new entry, create a new entry
                if (found == false) {
                  series.push({value: 0, name: ""});
                }

                // add it to the total value
                series[index].value += arr[0][i].series[k].value * this.holdingRecords.filter(r => r.companyid == companyid)[0].noShares;
                series[index].name = date;
              }
              // if the share doesn't have this date, add the value of the previous date, or 0 if it doesn't have one
              else if (date < share_date) {
                // find the index of the position where the date is in series
                let index = series.length;
                let found = false;
                for (var j = 0; j < series.length; j++) {
                  if (series[j].name == date) {
                    index = j;
                    found = true;
                    break;
                  }
                }
                if (found == false) {
                  series.push({value: 0, name: ""});
                }

                if (k > 0) {
                  series[index].value += arr[0][i].series[k - 1].value * this.holdingRecords.filter(r => r.companyid == companyid)[0].noShares;
                } else {
                  series[index].value += 0;
                }
                series[index].name = date;
                flag = true;
              }
            }

            var date_aux = new Date(date);
            date_aux.setDate(date_aux.getDate() + 1).toLocaleString();
            date = new DatePipe('en-US').transform(date_aux, 'yyyy/MM/dd').toString();
          }
        }

        // display just the year and month if there are too many data

        let series2 = [];
        for (let k = 0; k < series.length; k++) {
          series2.push(series[k]);
        }

        let arr2 = [];
        arr2.push({name: "TOTAL", series: series2});
        this.results2.push(arr2[0]);
        this.results2 = [...this.results2] // This will generate a new array and trigger the change detection to ngx chart
      });
    }
  }

  getHoldingRecordsOfGroup(groupName: string) {
    let id = this.groups.filter(x => x.name == groupName)[0].id;

    let companiesFromGroup = this.companyGroups.filter(x => x.groupid == id)
    return this.holdingRecords.filter(x => {for(let i=0; i<companiesFromGroup.length; i++)
                                                          if (x.companyid == companiesFromGroup[i].companyid)
                                                              return true;
                                                          return false;});
  }

  currencies: Currency[];
  selectedCurrencyId: number;
  currencyExchangeChanged: CurrencyExchange;
  factor = 1;
  symbol = 'EUR';

  setNewCurrency(args){
    this.selectedCurrencyId = args.target.value;
  }

  changeCurrency(){
    this.currencyExchangeChanged = this.loginService.getNewCurrencyExchange(this.selectedCurrencyId);
    if (this.currencyExchangeChanged == null) {
      alert("The data for this currency couldn't be found");
    } else {
      this.factor = this.currencyExchangeChanged.factor;
      this.symbol = this.currencies.filter(currency => currency.id == this.currencyExchangeChanged.currencyid2)[0].symbol;
    }

    this.displayPrice();
  }


}
