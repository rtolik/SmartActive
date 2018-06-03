import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {LoginService} from "./login.service";
import {AppComponent} from "../app.component";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [LoginService]
})
export class LoginComponent implements OnInit {

  lang: string;

  constructor(private _router: Router, private _loginService: LoginService) {
    this.lang = AppComponent.langService.slang;
    AppComponent.langService._lang$.subscribe(next => {
      this.lang = next;
    });
  }

  ngOnInit() {
  }

  login(login: HTMLInputElement, password: HTMLInputElement) {
    event.preventDefault();
    console.log(login.value+"  "+ password.value);
    this._loginService.login(login.value, password.value).subscribe(next => {
      console.log(JSON.stringify(next));
      this.getPrincipal();
    }, error => {
      console.error(error)
    })
  }

  getPrincipal() {
    this._loginService.getPrincipal().subscribe(next => {
      AppComponent.userDetailsService.login(next);
      this._router.navigateByUrl("/");
    });
  }

}
