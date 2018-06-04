import {Component, OnInit} from '@angular/core';
import {AppComponent} from "../../app.component";
import {Opportunity} from "../../../shared/models/opportunity";
import {ActivatedRoute, RouterLinkActive} from "@angular/router";
import {EventOneService} from "./event-one.service";
import {Url} from "../../../shared/config/url";

@Component({
  selector: 'app-event-one',
  templateUrl: './event-one.component.html',
  styleUrls: ['./event-one.component.css'],
  providers: [EventOneService]
})
export class EventOneComponent implements OnInit {

  lang: string;
  usage: Opportunity;
  isReady = false;
  url: string = Url.url;

  constructor(private _route: ActivatedRoute, private  _service: EventOneService) {

  }

  ngOnInit() {
    this.lang = AppComponent.langService.slang;
    AppComponent.langService._lang$.subscribe(next => {
      this.lang = next;
    });
    this._route.params.subscribe(param => {
      this.isReady = false;
      this._service.loadUsage(param["id"]).subscribe(next => {
        this.usage = next;
        this.isReady = this.usage.active;
      });
    });

  }

}
