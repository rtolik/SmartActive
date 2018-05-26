import {Component, Input, OnInit} from '@angular/core';
import {Usage} from "../../../../../environments/models/usage";

@Component({
  selector: 'app-manage-usage-one',
  templateUrl: './manage-usage-one.component.html',
  styleUrls: ['./manage-usage-one.component.css']
})
export class ManageUsageOneComponent implements OnInit {

  @Input() usage:Usage;

  constructor() { }

  ngOnInit() {
  }

}
