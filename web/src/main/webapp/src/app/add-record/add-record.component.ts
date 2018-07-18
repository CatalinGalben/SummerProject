import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-record',
  templateUrl: './add-record.component.html',
  styleUrls: ['./add-record.component.css']
})
export class AddRecordComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  saveDetails(name: string, price: number, noShares: number) {
    console.log("Price: " + price);

    if (Number.isInteger(noShares) == false)
    {
      alert("The number of shares has to be an integer");
      return;
    }
  }

}
