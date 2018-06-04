import {Component, Inject, OnInit} from "@angular/core";
import {Opportunity} from "../../../shared/models/opportunity";
import {AppComponent} from "../../app.component";
import {Category} from "../../../shared/models/category";

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.css']
})
export class EventListComponent implements OnInit {

    usagesList: Opportunity[] = [];
  lang:string;

  constructor() {
    this.lang = AppComponent.langService.slang;
    AppComponent.langService._lang$.subscribe(next => {
      this.lang = next;
    });
    this.usagesList = AppComponent.eventService.sEventList;
    AppComponent.eventService._eventList$.subscribe(next => {
      this.usagesList = [];
      for (let i = 0; i < next.length; i++)
        if (next[i].active)
          this.usagesList.push(next[i]);
      if (this.usagesList.length == 0)
        this.usagesList = next;
    });
    // this.test(20);
  }

  ngOnInit() {
  }





}
