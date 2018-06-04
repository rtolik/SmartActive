import {Component, OnInit} from "@angular/core";
import {Category} from "../../../shared/models/category";
import {Router} from "@angular/router";
import {Opportunity} from "../../../shared/models/opportunity";
import {MainService} from "./main.service";
import {AppComponent} from "../../app.component";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css'],
  providers: [MainService]
})
export class MainComponent implements OnInit {

  categoriesList: Category[] = [];
  usagesList: Opportunity[] = [];
  lang: string;

  constructor(private _router: Router, private _mainService: MainService) {
    this.lang = AppComponent.langService.slang;
    AppComponent.langService._lang$.subscribe(next => {
      this.lang = next;
    });
    // this.test(7);
    this._mainService.findAllCategories().subscribe(next => {
      this.categoriesList = next;
    }, error => {
      console.error(error);
    });
    AppComponent.eventService.addUsages(this.usagesList);
    AppComponent.eventService.addCategories(this.categoriesList);
    AppComponent.eventService._eventList$.subscribe(next => {
      this.usagesList = next;
    });
    AppComponent.eventService._categoryList$.subscribe(next => {
      this.categoriesList = next;
    });
  }

  ngOnInit() {
  }

  research(res: HTMLInputElement, price: HTMLInputElement/*, id: HTMLSelectElement*/) {
    event.preventDefault();
    AppComponent.eventService.remAll();
    this._mainService.researchCategory(res.value, price.value, "").subscribe(
      next => {
        let categories: Category[] = [];
        for (let i = 0; i < next.length; i++) {
          categories.push(next[i].category);
        }
        AppComponent.eventService.addUsages(next);
        AppComponent.eventService.addCategories(categories);
        this._router.navigateByUrl("/list");
      }, error => {
        console.error(error);
      }
    )
  }


}
