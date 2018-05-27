import { Component, OnInit } from '@angular/core';
import {Category} from "../../../environments/models/category";
import {MainService} from "../../home/main/main.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-chat-select',
  templateUrl: './chat-select.component.html',
  styleUrls: ['./chat-select.component.css'],
  providers: [MainService]
})
export class ChatSelectComponent implements OnInit {

  categoriesList: Category[] = [];

  constructor(private mainService:MainService,private _router:Router) {
    this.mainService.findAllCategories().subscribe(next => this.categoriesList=next,error2 => console.error(error2));
  }
goTo(select:HTMLSelectElement){
    if(select.value!="")
  this._router.navigateByUrl("/chat/"+select.value);
}
  ngOnInit() {
  }

}
