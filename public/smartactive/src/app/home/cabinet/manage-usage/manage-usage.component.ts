import {Component, OnInit} from "@angular/core";
import {MenageUsageService} from "./manage-usage.service";
import {Router} from "@angular/router";
import {Usage} from "../../../../shared/models/usage";
import {AppComponent} from "../../../app.component";

@Component({
  selector: 'app-manage-usage',
  templateUrl: './manage-usage.component.html',
  styleUrls: ['./manage-usage.component.css'],
  providers: [MenageUsageService]
})
export class ManageUsageComponent implements OnInit {


  Statuses;
  LoadedOpporByUser: Usage[]=[];

  dropList: string[] = [];

  constructor(private _menageUsageService: MenageUsageService, private _router: Router) {

    this.lang = AppComponent.langService.slang;
    AppComponent.langService._lang$.subscribe(next => {
      this.lang = next;
      this.getStatuses();
    });
  }


  lang: string;


  inputCategory(inp: HTMLInputElement) {
    this._menageUsageService.loadDropList(inp.value).subscribe(
      next => {
        this.dropList = next.json();
      }, error => {
        console.error(error);
      }
    )
  }

  ngOnInit() {
    this.loadOpporByUser();
  }

  addOpportunities(form: HTMLFormElement) {
    this._menageUsageService.addOpportunities(form).subscribe((data) => console.log(data.url + " http:[" + data.statusText + "]"));

  }

  getStatuses() {
    this._menageUsageService.getStatuses().subscribe((data) => {
      console.log(data.url + " http:[" + data.statusText + "]");
      this.Statuses = data.json()
    });
  }

  loadOpporByUser() {
    this._menageUsageService.loadOpporByUser().subscribe((data) => {
      this.LoadedOpporByUser = data.json()
    });
  }

}
