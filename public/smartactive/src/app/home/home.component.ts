import { Component, OnInit } from '@angular/core';
import {Category} from "../../shared/models/category";
import {EventService} from "../../shared/rx-service/event-rx-service";
import {AppComponent} from "../app.component";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  lang:string;

  constructor() {
    this.lang = AppComponent.langService.slang;
    AppComponent.langService._lang$.subscribe(next => {
      this.lang = next;
    });
  }

  ngOnInit() {
  }
}
