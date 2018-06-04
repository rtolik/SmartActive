import {Component, Input, OnInit} from '@angular/core';
import {Opportunity} from "../../../../../shared/models/opportunity";

@Component({
  selector: 'app-save-usage-one',
  templateUrl: './save-usage-one.component.html',
  styleUrls: ['./save-usage-one.component.css']
})
export class SaveUsageOneComponent implements OnInit {

  @Input() usage:Opportunity;

  constructor() { }

  ngOnInit() {
  }

}
