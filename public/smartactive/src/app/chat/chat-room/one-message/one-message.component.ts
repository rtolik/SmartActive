import {Component, Input, OnInit} from '@angular/core';
import {Message} from "../../../../shared/models/message";
import {User} from "../../../../shared/models/user";
import {AppComponent} from "../../../app.component";
import {OneMessageService} from "./one-message.service";

@Component({
  selector: 'app-one-message',
  templateUrl: './one-message.component.html',
  styleUrls: ['./one-message.component.css'],
  providers: [OneMessageService]
})
export class OneMessageComponent implements OnInit {

  user: User;
  @Input() message: Message;
  lang: string;

  color: string;

  constructor(private _oneMessageService: OneMessageService) {



    this.lang = AppComponent.langService.slang;
    AppComponent.langService._lang$.subscribe(next => {
      this.lang = next;
    });
    this.user = AppComponent.userDetailsService.user;
    AppComponent.userDetailsService._user$.subscribe(next => {
      this.user = next;
    });
  }

  retort() {
    event.preventDefault();
    this._oneMessageService.report(this.message.userName).subscribe(
      next => {
        console.log(next.status);
      }, error => {
        console.error(error);
      }
    )
  }

  ngOnInit() {
    this._oneMessageService.findByName(this.message.userName).subscribe(next => {
      this.color = next.color;
    }, error => {
      console.log(error)
    });
  }

}
