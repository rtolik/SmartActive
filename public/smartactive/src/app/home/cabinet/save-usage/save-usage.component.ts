import { Component, OnInit } from '@angular/core';
import {AppComponent} from "../../../app.component";
import {Opportunity} from "../../../../shared/models/opportunity";

@Component({
  selector: 'app-save-usage',
  templateUrl: './save-usage.component.html',
  styleUrls: ['./save-usage.component.css']
})
export class SaveUsageComponent implements OnInit {

  lang:string;
  savedUsages:Opportunity[]=[];

  constructor() {
    this.lang = AppComponent.langService.slang;
    AppComponent.langService._lang$.subscribe(next => {
      this.lang = next;
    });
    this.savedUsages = AppComponent.eventService.sSavedEvents;
    AppComponent.eventService._savedEvents$.subscribe(next=>{
      this.savedUsages = next;
    });
  }

  ngOnInit() {
  }

}
