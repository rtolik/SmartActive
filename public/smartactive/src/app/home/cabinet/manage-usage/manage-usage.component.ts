import {Component, OnInit} from "@angular/core";
import {MenageUsageService} from "./manage-usage.service";
import {Router} from "@angular/router";
import {Opportunity} from "../../../../shared/models/opportunity";
import {AppComponent} from "../../../app.component";
import {OpportunityService} from "../../../../shared/service/opportunity.service";
import {Category} from "../../../../shared/models/category";
import {UserDetailsService} from "../../../../shared/service/user-details-service";

@Component({
  selector: 'app-manage-usage',
  templateUrl: './manage-usage.component.html',
  styleUrls: ['./manage-usage.component.css'],
  providers: [MenageUsageService, OpportunityService]
})
export class ManageUsageComponent implements OnInit {


  statuses;
  LoadedOpporByUser: Opportunity[] = [];
  opportunity: Opportunity = new Opportunity();

  dropList: string[] = [];
  lang: string;

  constructor(private _menageUsageService: MenageUsageService, private _opportunityService: OpportunityService,private _userDetails:UserDetailsService) {
    this.opportunity.category=new Category();
    this.lang = AppComponent.langService.slang;
    AppComponent.langService._lang$.subscribe(next => {
      this.lang = next;
      this.getStatuses();
    });
  }

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
    let data = new FormData(form);
    data.append('opportunity', JSON.stringify(this.opportunity));
    this._opportunityService.add(data,this._userDetails.user.id).subscribe((next) => console.log(next));

  }

  getStatuses() {
    this._opportunityService.loadStatuses().subscribe((data) => {
      this.statuses = data;
    });
  }

  loadOpporByUser() {
    this._opportunityService.findByUser().subscribe((data) => {
      this.LoadedOpporByUser = data;
    });
  }

}
