import {Component, OnInit, ViewChild, ElementRef} from '@angular/core';
import {AppComponent} from "../../../app.component";
import {User} from "../../../../environments/models/user";

@Component({
  selector: 'app-left-side',
  templateUrl: './left-side.component.html',
  styleUrls: ['./left-side.component.css']
})
export class LeftSideComponent implements OnInit {

  @ViewChild('menu') menu: ElementRef;
  @ViewChild('close') close: ElementRef;
  @ViewChild('show') show: ElementRef;
  lang: string;
  user: User;

  constructor() {
    this.lang = AppComponent.langService.slang;
    AppComponent.langService._lang$.subscribe(next => {
      this.lang = next;
    });
    this.user = AppComponent.userDetailsService.user;
    AppComponent.userDetailsService._user$.subscribe(next => {
      this.user = next;
      console.log(next.name);
    });
  }


  ngOnInit() {
  }

  showComponent() {
    this.menu.nativeElement.style.animation = "showCom .7s";
    this.menu.nativeElement.style.left = "0";
    this.show.nativeElement.style.display = "none";
  }

  hideComponent() {
    this.menu.nativeElement.style.animation = "hideCom .7s";
    this.menu.nativeElement.style.left = "-102vw";
    this.show.nativeElement.style.display = "block";

  }

}
