import {Component} from "@angular/core";
import {EventService} from "../shared/rx-service/event-rx-service";
import {UserDetailsService} from "../shared/service/user-details-service";
import {LangService} from "../shared/rx-service/lang-rx-service";
import {LoginService} from "./login/login.service";
import {UserService} from "../shared/service/user.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [LoginService, UserService]
})
export class AppComponent {

  static eventService = new EventService();
  static langService = new LangService();

  constructor(private _loginService: LoginService, private _userService: UserService, private _userDetails: UserDetailsService) {
    this.checkLogin();
  }

  checkLogin() {
    this.getPrincipal();
  }

  getPrincipal() {
    if (this._userDetails.checkAuth()) {
      this._userService.getUser().subscribe(next => {
        this._userDetails.login(next);
        console.log(next);
      });
    } else {
      this._userDetails.logout();
    }
  }
}
