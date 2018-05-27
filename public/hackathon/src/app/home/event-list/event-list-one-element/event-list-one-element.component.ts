import {Component, ElementRef, Input, OnInit, ViewChild} from "@angular/core";
import {Usage} from "../../../../environments/models/usage";
import {AppComponent} from "../../../app.component";
import {Url} from "../../../../environments/url";
import {RateService} from "../../../../shared/services/rate.service";
import {EventOneService} from "../../event-one/event-one.service";


@Component({
  selector: 'app-event-list-one-element',
  templateUrl: './event-list-one-element.component.html',
  styleUrls: ['./event-list-one-element.component.css'],
  providers: [RateService,EventOneService]
})
export class EventListOneElementComponent implements OnInit {

  @ViewChild('star_1') star_1: ElementRef;
  @ViewChild('star_2') star_2: ElementRef;
  @ViewChild('star_3') star_3: ElementRef;
  @ViewChild('star_4') star_4: ElementRef;
  @ViewChild('star_5') star_5: ElementRef;

  @Input() usage: Usage;
  lang: string;
  countStar: number;
  url:string=Url.url;

  constructor(private rateService : RateService,private  eventOneService :EventOneService) {
    this.lang = AppComponent.langService.slang;
    AppComponent.langService._lang$.subscribe(next => {
      this.lang = next;
    });
  }

  addUsage() {
    AppComponent.eventService.saveUsage(this.usage);
  }

  ngOnInit() {
    this.rateService.loadRate(this.usage.id)
      .subscribe(next=>{
        this.countStar=next;
        this.changeStar();
        },
        error2 => console.error(error2));
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

  incrementVoices(value:number, id:number){
    this.eventOneService.incrementVoices(value,id)
      .subscribe(
        next=>console.log(next),
        error2 => console.error(error2)
      );
  }


}
