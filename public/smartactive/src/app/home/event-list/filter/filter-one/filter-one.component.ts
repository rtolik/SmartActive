import {AfterViewInit, Component, ElementRef, Input, OnInit, ViewChild} from "@angular/core";
import {Category} from "../../../../../environments/models/category";
import {AppComponent} from "../../../../app.component";

@Component({
  selector: 'app-filter-one',
  templateUrl: './filter-one.component.html',
  styleUrls: ['./filter-one.component.css']
})
export class FilterOneComponent implements OnInit, AfterViewInit {

  @Input() category: Category;
  @ViewChild('check') checkVC: ElementRef;

  lang:string;


  constructor() {
    this.lang = AppComponent.langService.slang;
    AppComponent.langService._lang$.subscribe(next => {
      this.lang = next;
    });
  }

  ngOnInit() {
  }

  change() {
    // AppComponent.eventService.activateCategory(this.category,);
    AppComponent.eventService.activateCategory(this.category, this.checkVC.nativeElement.checked);
  }

  ngAfterViewInit(): void {
    AppComponent.eventService.activateCategory(this.category, this.checkVC.nativeElement.checked);
  }
}
