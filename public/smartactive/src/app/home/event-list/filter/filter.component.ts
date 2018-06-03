import {Component, ElementRef, OnInit, ViewChild} from "@angular/core";
import {Category} from "../../../../shared/models/category";
import {AppComponent} from "../../../app.component";

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css']
})
export class FilterComponent implements OnInit {

  @ViewChild('filter') filter: ElementRef;
  @ViewChild('btnFilter') btnFilter: ElementRef;

  category: Category[] = [];
  lang:string;

  constructor() {
    this.lang = AppComponent.langService.slang;
    AppComponent.langService._lang$.subscribe(next => {
      this.lang = next;
    });
    this.category = AppComponent.eventService.sCategoryList;
    AppComponent.eventService._categoryList$.subscribe(next => {
      this.category = next;
    });
  }

  showFilter() {
    this.filter.nativeElement.style.animation = "showFilt .5s";
    this.filter.nativeElement.style.left = "0";
    this.btnFilter.nativeElement.style.display = "none";
  }

  hideFilter() {
    this.filter.nativeElement.style.animation = "hideFilt .5s";
    this.filter.nativeElement.style.left = "-100vw";
    this.btnFilter.nativeElement.style.display = "flex";
  }

  ngOnInit() {
  }

}
