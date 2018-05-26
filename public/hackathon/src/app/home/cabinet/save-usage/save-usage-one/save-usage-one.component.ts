import {Component, Input, OnInit} from '@angular/core';
import {Usage} from "../../../../../environments/models/usage";

@Component({
  selector: 'app-save-usage-one',
  templateUrl: './save-usage-one.component.html',
  styleUrls: ['./save-usage-one.component.css']
})
export class SaveUsageOneComponent implements OnInit {

  @Input() usage:Usage;

  constructor() { }

  ngOnInit() {
  }

}
