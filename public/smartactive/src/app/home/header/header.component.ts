import {Component, ElementRef, OnInit, ViewChild} from "@angular/core";
import {AppComponent} from "../../app.component";
import {UserDetailsService} from "../../../shared/service/user-details-service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  @ViewChild('ruID') ru: ElementRef;
  @ViewChild('uaID') ua: ElementRef;

  lang: string;

  auth: boolean;

  constructor(private _userDetails:UserDetailsService) {

    this.auth = this._userDetails.isAuth;
    this._userDetails.isAuth$.subscribe(next => {
      this.auth = next;
    });


    this.lang = AppComponent.langService.slang;
    AppComponent.langService._lang$.subscribe(next => {
      this.lang = next;
      if (next == "ua") {
        this.ru.nativeElement.style.color = "#959595";
        this.ru.nativeElement.style.borderBottom = "none";
        this.ua.nativeElement.style.color = "#fff";
        this.ua.nativeElement.style.borderBottom = "2px solid #fff";
      } else {
        this.ru.nativeElement.style.color = "#fff";
        this.ru.nativeElement.style.borderBottom = "2px solid #fff";
        this.ua.nativeElement.style.color = "#959595";
        this.ua.nativeElement.style.borderBottom = "none";
      }
    });
  }

  changeLang(lang: string) {
    if (lang == "ua") {
      this.ru.nativeElement.style.color = "#959595";
      this.ru.nativeElement.style.borderBottom = "none";
      this.ua.nativeElement.style.color = "#fff";
      this.ua.nativeElement.style.borderBottom = "2px solid #fff";
    } else {
      this.ru.nativeElement.style.color = "#fff";
      this.ru.nativeElement.style.borderBottom = "2px solid #fff";
      this.ua.nativeElement.style.color = "#959595";
      this.ua.nativeElement.style.borderBottom = "none";
    }

    AppComponent.langService.next(lang);
  }

  ngOnInit() {
    if (this.lang == "ua") {
      this.ru.nativeElement.style.color = "#959595";
      this.ru.nativeElement.style.borderBottom = "none";
      this.ua.nativeElement.style.color = "#fff";
      this.ua.nativeElement.style.borderBottom = "2px solid #fff";
    } else {
      this.ru.nativeElement.style.color = "#fff";
      this.ru.nativeElement.style.borderBottom = "2px solid #fff";
      this.ua.nativeElement.style.color = "#959595";
      this.ua.nativeElement.style.borderBottom = "none";
    }
  }

}
