import { Component, OnInit, OnChanges, Input } from '@angular/core';
import { NgxChartsModule } from '@swimlane/ngx-charts';

@Component({
  selector: 'app-ngx-charts',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.css']
})
export class BarChartComponent implements OnInit, OnChanges {
  @Input() commodity: any;
  results: any;
  constructor() { }

  ngOnInit() {
    this.results = [
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
      },
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

  ngOnChanges() {
  }

}
