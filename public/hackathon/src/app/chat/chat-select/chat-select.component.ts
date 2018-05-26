import { Component, OnInit } from '@angular/core';
import {Category} from "../../../environments/models/category";

@Component({
  selector: 'app-chat-select',
  templateUrl: './chat-select.component.html',
  styleUrls: ['./chat-select.component.css']
})
export class ChatSelectComponent implements OnInit {

  categoriesList: Category[] = [];

  constructor() { }

  ngOnInit() {
  }

}
