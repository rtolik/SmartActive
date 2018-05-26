import { Component, OnInit } from '@angular/core';
import {AppComponent} from "../../../app.component";
import {Usage} from "../../../../environments/models/usage";

@Component({
  selector: 'app-save-usage',
  templateUrl: './save-usage.component.html',
  styleUrls: ['./save-usage.component.css']
})
export class SaveUsageComponent implements OnInit {

  lang:string;
  savedUsages:Usage[]=[];

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
