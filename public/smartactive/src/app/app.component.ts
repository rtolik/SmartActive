import {Component} from "@angular/core";
import {EventService} from "../shared/rx-service/event-rx-service";
import {UserDetailsService} from "../shared/service/user-details-service";
import {LangService} from "../shared/rx-service/lang-rx-service";
import {LoginService} from "./login/login.service";
import {isNull} from "util";
import {ActiveGuardService} from "../shared/can-active/auth-guard-service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [LoginService,ActiveGuardService]

})
export class AppComponent {

  static eventService = new EventService();
  static langService = new LangService();

  constructor(private _loginService: LoginService, private _activeGuardService: ActiveGuardService,private _userDetails:UserDetailsService) {
    this.checkLogin();
  }

  checkLogin() {
    this._activeGuardService.checkAuth().subscribe(next => {
      if (next) {
        this.getPrincipal();
      }
      else {
        this._userDetails.logout();
        localStorage.clear();
      }
    }, error => {
      this._userDetails.logout();
      localStorage.clear();
    });
  }

  getPrincipal() {
    if (!isNull(localStorage.getItem("login")) && localStorage.getItem("login")) {
      this._loginService.getPrincipal().subscribe(next => {
        this._userDetails.login(next);
      });
    }
  }

  title = 'app';
}
