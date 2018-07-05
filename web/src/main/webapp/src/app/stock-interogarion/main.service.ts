import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

@Injectable()
export class MainService {

  stocks: Array<String>;
  currentPrice: { name: string, price: number }[] = [];

  constructor(private _http: Http) { }

  getCurrentPrice(stock, cb) {
    this.stocks = stock.symbol.split(',');
    var idx = -1;
    for (var i = 0; i < this.stocks.length; i++) {
      var stockSymbol = this.stocks[i].trim();
      this._http.get('https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=' + stockSymbol + '&interval=1min&apikey=5G9AEN06TMIBRAAS').subscribe((res) => {
        idx++; // to get the right symbol because of asynchronous javascript
        if (!res.json()["Error Message"]) {
          var retrievedCurrentPrice = { name: res.json()["Meta Data"]["2. Symbol"], price: res.json()["Time Series (1min)"][Object.keys(res.json()["Time Series (1min)"])[0]]["4. close"] };
          this.currentPrice.push(retrievedCurrentPrice);
          cb(res.json()["Meta Data"]["2. Symbol"], true);
        }
        else {
          cb(this.stocks[idx].trim(), false);
        }
      });
    }
  };

  getPrice(stockSymbol, cb) {
    this._http.get('https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=' + stockSymbol + '&apikey=GT13K1EANRVG5KOS').subscribe((res) => {
      var symbol = res.json()["Meta Data"]["2. Symbol"];
      for (var i = 0; i < this.currentPrice.length; i++) {
        if (this.currentPrice[i].name === symbol) {
          var currentPr = this.currentPrice[i].price;
        }
      }
      cb(res.json()["Meta Data"]["2. Symbol"], currentPr, res.json()["Time Series (Daily)"][Object.keys(res.json()["Time Series (Daily)"])[1]]["5. adjusted close"], res.json()["Time Series (Daily)"][Object.keys(res.json()["Time Series (Daily)"])[0]]["6. volume"], res.json()["Time Series (Daily)"][Object.keys(res.json()["Time Series (Daily)"])[0]]["7. dividend amount"]);
    })
  }
};
