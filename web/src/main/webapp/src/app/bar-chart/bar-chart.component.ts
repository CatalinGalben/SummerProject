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

@Component({
  selector: 'app-ngx-charts',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.css']
})
export class BarChartComponent implements OnInit {
  @Input() commodity: any;
  results: any;

  results2: any;

  selectedRow: any;

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
              private recordService: AddRecordService) {

    this.populate();
  }

  ngOnInit() {
    this.results2 = [];
    this.results = [

      {
        "name": "G1",
        "series": [

          {
            "name": "Isle of Man",
            "series": [
              {
                "value": 5990,
                "name": "2016-09-16T16:58:17.749Z"
              },
              {
                "value": 5308,
                "name": "2016-09-23T03:15:22.044Z"
              },
              {
                "value": 5888,
                "name": "2016-09-21T18:51:48.246Z"
              },
              {
                "value": 2709,
                "name": "2016-09-18T20:29:50.869Z"
              },
              {
                "value": 4882,
                "name": "2016-09-14T04:06:26.043Z"
              }
            ]
          },
          {
            "name": "Kenya",
            "series": [
              {
                "value": 4924,
                "name": "2016-09-16T16:58:17.749Z"
              },
              {
                "value": 2403,
                "name": "2016-09-23T03:15:22.044Z"
              },
              {
                "value": 5914,
                "name": "2016-09-21T18:51:48.246Z"
              },
              {
                "value": 6151,
                "name": "2016-09-18T20:29:50.869Z"
              },
              {
                "value": 5569,
                "name": "2016-09-14T04:06:26.043Z"
              }
            ]
          }
        ]
      },
      {
        "name": "G2",
        "series": [
          {
            "name": "Sint Maarten (Dutch Part)",
            "series": [
              {
                "value": 3144,
                "name": "2016-09-16T16:58:17.749Z"
              },
              {
                "value": 5066,
                "name": "2016-09-23T03:15:22.044Z"
              },
              {
                "value": 5510,
                "name": "2016-09-21T18:51:48.246Z"
              },
              {
                "value": 2621,
                "name": "2016-09-18T20:29:50.869Z"
              },
              {
                "value": 5945,
                "name": "2016-09-14T04:06:26.043Z"
              }
            ]
          },
          {
            "name": "Suriname",
            "series": [
              {
                "value": 6556,
                "name": "2016-09-16T16:58:17.749Z"
              },
              {
                "value": 5149,
                "name": "2016-09-23T03:15:22.044Z"
              },
              {
                "value": 2439,
                "name": "2016-09-21T18:51:48.246Z"
              },
              {
                "value": 5988,
                "name": "2016-09-18T20:29:50.869Z"
              },
              {
                "value": 5525,
                "name": "2016-09-14T04:06:26.043Z"
              }
            ]
          },
          {
            "name": "Tunisia",
            "series": [
              {
                "value": 6918,
                "name": "2016-09-16T16:58:17.749Z"
              },
              {
                "value": 3183,
                "name": "2016-09-23T03:15:22.044Z"
              },
              {
                "value": 5903,
                "name": "2016-09-21T18:51:48.246Z"
              },
              {
                "value": 6019,
                "name": "2016-09-18T20:29:50.869Z"
              },
              {
                "value": 4940,
                "name": "2016-09-14T04:06:26.043Z"
              }
            ]
          }
        ]
      }
    ]

    /*
    console.log("BC: " + this.results[0].name);
    console.log("BC: " + this.results[0].series[0].value);
    console.log("BC: " + this.results[0].series[0].name);

    for(let i=0; i<this.results.length; i++) {
      console.log(this.results[i].name);
      for(let j=0; j<this.results.length; j++) {
        console.log(this.results[i].series[j].value);
        console.log(this.results[i].series[j].name);
      }
    }
*/

    /*
    const index = this.checkinTemp.indexOf(item);
    this.checkinTemp.splice(index, 1);
     */
    this.results.splice(3,1); // delete Suriname

    this.results.push( {  // add new node
      "name": "Trrrrrr",
      "series": [
        {
          "value": 6918,
          "name": "2016-09-16T16:58:17.749Z"
        },
        {
          "value": 3183,
          "name": "2016-09-23T03:15:22.044Z"
        },
        {
          "value": 5903,
          "name": "2016-09-21T18:51:48.246Z"
        },
        {
          "value": 6019,
          "name": "2016-09-18T20:29:50.869Z"
        },
        {
          "value": 4940,
          "name": "2016-09-14T04:06:26.043Z"
        }
      ]
    });

    this.results2.push({  // add new node
      "name": "Trrrrrr",
      "series": [
        {
          "value": 6918,
          "name": "2016-09-16T16:58:17.749Z"
        },
        {
          "value": 3183,
          "name": "2016-09-23T03:15:22.044Z"
        },
        {
          "value": 5903,
          "name": "2016-09-21T18:51:48.246Z"
        },
        {
          "value": 6019,
          "name": "2016-09-18T20:29:50.869Z"
        },
        {
          "value": 4940,
          "name": "2016-09-14T04:06:26.043Z"
        }
      ]
    });

  }

  populate() {
    if (this.loginService.getCurrentUser() != null) {
        // get user data
        this.userLoggedIn = this.loginService.getCurrentUser();


        // get all records for the user
        this.portfolioService.getRecords().subscribe(records => {
          console.log(records);
          this.holdingRecords = records;
          this.holdingRecords.filter(hr => hr.userid == this.userLoggedIn.id)
        });
        console.log(this.holdingRecords);

        // get all the companies groups of the user
        this.recordService.getAllCompanies().subscribe(records => {
            this.companies = records;
            this.companies.filter(comp => {
                 for (let i = 0; i < this.holdingRecords.length; i++)
                   if (this.holdingRecords[i].companyid == comp.id)
                      return false;
                 return true;
               } );
         });

        // get all CompanyGroups of the user
        this.groupService.getCompanyGroups().subscribe(records => {
            this.companyGroups = records;
            this.companyGroups.filter(comp => {
              for (let i = 0; i < this.holdingRecords.length; i++)
                if (this.holdingRecords[i].companyid == comp.companyid)
                  return false;
              return true;
            } );
        });


      // get all user's groups
      this.groupService.getGroups().subscribe( records => {
        this.groups = records;
        this.groups.filter(gr => {
            for (let i = 0; i < this.companyGroups.length; i++) {
              if (this.companyGroups[i].groupid == gr.id)
                return false;
            }
            return true;
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

  clickRecord(name: string) {
    console.log("NAME " + name);

    this.selectedRow = name;
    this.addNode(name);

    //console.log("NN " + this.selectedRow);

    // console.log(this.userLoggedIn);
    // console.log("HR: ");
    // console.log(this.holdingRecords);
    // console.log("Companies: ");
    // console.log(this.companies);
     console.log("Company Groups: ");
     console.log(this.companyGroups)
     console.log("Groups: ");
     console.log(this.groups)
  }

  addNode(name: string) {
    let node = this.results.find(x => x.name == name).series;

    console.log(node);
    for(let i=0; i<node.length; i++)
      this.results2.push(node[i]);
    console.log("RES2");
    console.log(this.results2);
  }


  getHoldingRecordsOfGroup(groupName: string) {
    let id = this.groups.filter(x => x.name == groupName)[0].id;

    let companiesFromGroup = this.companyGroups.filter(x => x.groupid == id)
    return this.holdingRecords.filter(x => {for(let i=0; i<companiesFromGroup.length; i++)
                                                          if (x.companyid == companiesFromGroup[i].companyid)
                                                              return true;
                                                          return false;});
  }

}
