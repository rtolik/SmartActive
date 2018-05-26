import {Component, ElementRef, Input, OnInit, ViewChild} from "@angular/core";
import {Usage} from "../../../../environments/models/usage";
import {AppComponent} from "../../../app.component";
import {Url} from "../../../../environments/url";


@Component({
  selector: 'app-event-list-one-element',
  templateUrl: './event-list-one-element.component.html',
  styleUrls: ['./event-list-one-element.component.css']
})
export class EventListOneElementComponent implements OnInit {

  @ViewChild('star_1') star_1: ElementRef;
  @ViewChild('star_2') star_2: ElementRef;
  @ViewChild('star_3') star_3: ElementRef;
  @ViewChild('star_4') star_4: ElementRef;
  @ViewChild('star_5') star_5: ElementRef;

  @Input() usage: Usage;
  lang: string;
  numberRating: number;
  countStar: number;
  url:string=Url.url;

  constructor() {
    this.lang = AppComponent.langService.slang;
    AppComponent.langService._lang$.subscribe(next => {
      this.lang = next;
    });
    let num = Math.random() * 5;
    this.countStar = Math.floor(num);
    this.numberRating = num - this.countStar;
  }

  addUsage() {
    AppComponent.eventService.saveUsage(this.usage);
  }

  ngOnInit() {
    this.changeStar();
  }

  changeStar() {
    if (this.countStar == 1) {
      this.star_1.nativeElement.style.color = "yellow";
    }
    if (this.countStar == 2) {
      this.star_1.nativeElement.style.color = "yellow";
      this.star_2.nativeElement.style.color = "yellow";
    }
    if (this.countStar == 3) {
      this.star_1.nativeElement.style.color = "yellow";
      this.star_2.nativeElement.style.color = "yellow";
      this.star_3.nativeElement.style.color = "yellow";
    }
    if (this.countStar == 4) {
      this.star_1.nativeElement.style.color = "yellow";
      this.star_2.nativeElement.style.color = "yellow";
      this.star_3.nativeElement.style.color = "yellow";
      this.star_4.nativeElement.style.color = "yellow";
    }
    if (this.countStar == 5) {
      this.star_1.nativeElement.style.color = "yellow";
      this.star_2.nativeElement.style.color = "yellow";
      this.star_3.nativeElement.style.color = "yellow";
      this.star_4.nativeElement.style.color = "yellow";
      this.star_5.nativeElement.style.color = "yellow";
    }
  }


}
