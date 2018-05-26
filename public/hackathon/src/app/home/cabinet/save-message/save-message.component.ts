import { Component, OnInit } from '@angular/core';
import {AppComponent} from "../../../app.component";

@Component({
  selector: 'app-save-message',
  templateUrl: './save-message.component.html',
  styleUrls: ['./save-message.component.css']
})
export class SaveMessageComponent implements OnInit {

  lang: string;

  constructor() {
    this.lang = AppComponent.langService.slang;
    AppComponent.langService._lang$.subscribe(next => {
      this.lang = next;
    });
  }

  ngOnInit() {
  }

}
