import {Component, Input, OnInit} from '@angular/core';
import {Usage} from "../../../../../shared/models/usage";
import {MenageUsageService} from "../manage-usage.service";

@Component({
  selector: 'app-manage-usage-one',
  templateUrl: './manage-usage-one.component.html',
  styleUrls: ['./manage-usage-one.component.css'],
  providers: [MenageUsageService]
})
export class ManageUsageOneComponent implements OnInit {

  @Input() usage:Usage;

  constructor(private menageUsageService:MenageUsageService) { }

  ngOnInit() {
  }

  setActivity(){
    this.menageUsageService.setOpporActive(!this.usage.active,this.usage.id)
      .subscribe(
        next=>console.log(this.usage.active),
        error2 => console.error(error2)
      );
  }

}
