import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {LoginService} from "./login.service";
import {AppComponent} from "../app.component";
import {UserDetailsService} from "../../shared/service/user-details-service";
import {UserService} from "../../shared/service/user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [LoginService, UserService]
})
export class LoginComponent implements OnInit {

  lang: string;

  constructor(private _router: Router, private _loginService: LoginService, private _userService: UserService, private _userDetails: UserDetailsService) {
    this.lang = AppComponent.langService.slang;
    AppComponent.langService._lang$.subscribe(next => {
      this.lang = next;
    });
  }

  ngOnInit() {
  }

  login(login: HTMLInputElement, password: HTMLInputElement) {
    event.preventDefault();
    console.log(login.value + "  " + password.value);
    this._loginService.login(login.value, password.value).subscribe(next => {
      this._userDetails.tokenParseInLocalStorage(next);
      this.getPrincipal();
    }, error => {
      console.error(error)
    })
  }

  getPrincipal() {
    this._userService.getUser().subscribe(next => {
      this._userDetails.login(next);
      this._router.navigateByUrl("/");
    });
  }

}
