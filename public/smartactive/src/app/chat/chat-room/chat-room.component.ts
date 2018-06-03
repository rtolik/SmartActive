import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {CategoryMessage} from "../../../environments/models/category-message";
import {Message} from "../../../environments/models/message";
import {User} from "../../../environments/models/user";
import {AppComponent} from "../../app.component";
import {$WebSocket} from "angular2-websocket/angular2-websocket";
import {Url} from "../../../environments/url";

@Component({
  selector: 'app-chat-room',
  templateUrl: './chat-room.component.html',
  styleUrls: ['./chat-room.component.css']
})
export class ChatRoomComponent implements OnInit {

  ws: $WebSocket;
  categoryMessage: CategoryMessage = new CategoryMessage();

  user: User;
  lang: string;


  constructor(private route: ActivatedRoute) {
    this.lang = AppComponent.langService.slang;
    AppComponent.langService._lang$.subscribe(next => {
      this.lang = next;
    });
    this.ws = new $WebSocket(Url.ws +"/websocket-chat");
    this.user = AppComponent.userDetailsService.user;
    AppComponent.userDetailsService._user$.subscribe(next => {
      this.user = next;
    });

    this.route.params.subscribe(params => {
      this.categoryMessage.id = params["id"];
      // this.test();
    });
    // this.test();


  }


  test() {
    let user = new User();
    user.name = "admin";
    AppComponent.userDetailsService.login(user);
    for (let i = 0; i < 20; i++) {
      let m = new Message();
      m.date = "date" + i;
      m.message = "message message message message message message message messagemessage message message message message message message messagemessage message message message message message message messagemessage message message message message message message message" + i;
      m.userName = "admin";
      this.categoryMessage.messages.push(m);
    }
  }

  public send(text: HTMLTextAreaElement) {
    event.preventDefault();
    let mes = new Message();
    mes.categoryMessage = Object.assign({}, this.categoryMessage);
    mes.message = text.value;
    mes.userName = this.user.name;
    mes.date = null;
    mes.categoryMessage.messages = null;
    this.ws.send(JSON.stringify(mes)).subscribe(
      (msg) => {
        console.log("next", msg.data);
      },
      (msg) => {
        console.log("error", msg);
      },
      () => {
        console.log("complete");
        text.value = "";
      }
    );
  }

  ngOnInit() {

    this.ws.getDataStream().subscribe(res => {
        console.log(JSON.parse(res.data));
        let obj: Message = JSON.parse(res.data);
        this.categoryMessage.name = obj.categoryMessage.name;
        this.categoryMessage.time = obj.categoryMessage.time;
        this.categoryMessage.messages.push(obj);
        console.log(JSON.parse(res.data));
      }, function (e) {
        console.log('Error: ' + e.message);
      },
      function () {
        console.log('Completed');
      });

    let fMes = new Message();
    fMes.categoryMessage = this.categoryMessage;
    fMes.message = null;
    this.ws.send(JSON.stringify(fMes)).subscribe(
      (msg) => {
        console.log("next", msg.data);
      },
      (msg) => {
        console.log("error", msg);
      },
      () => {
        console.log("complete");
      }
    );


  }

}
