import {Component, OnInit} from "@angular/core";
import {RegistrationService} from "./registration.service";
import * as ts from "typescript/lib/tsserverlibrary";
import {Router} from "@angular/router";
import {User} from "../../environments/models/user";
import {AppComponent} from "../app.component";
import toSortedReadonlyArray = ts.server.toSortedReadonlyArray;

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
  providers: [RegistrationService]
})
export class RegistrationComponent implements OnInit {
  lang: string;

  constructor(private _registrationService: RegistrationService, private _router: Router) {
    this.lang = AppComponent.langService.slang;
    AppComponent.langService._lang$.subscribe(next => {
      this.lang = next;
    });
  }

  user: User = new User();
  pass: string;
  validName: boolean = false;
  validPass: boolean = false;
  validMail: boolean = false;

  ngOnInit() {
  }

  validateMail(soap: HTMLInputElement) {
    let mail = soap.value;
    this.validMail = mail.includes('@') && mail.includes('.') && (mail.lastIndexOf('.') > mail.indexOf('@'));
    if (this.validMail)
      this._registrationService.validateSoap(mail).subscribe(next => {
        this.validMail = next;
        if (next) {
          this.user.email = mail;
          console.log("validateMail");
        }
      }, error => {
        console.error(error);
      });
  }

  validatePassword(pass: HTMLInputElement) {
    this.validPass = pass.value.length > 7;
    if (this.validPass) {
      this.pass = pass.value;
      console.log("validatePass");
    }
  }

  validateName(name: HTMLInputElement) {
    this._registrationService.validateName(name.value).subscribe(next => {
      this.validName = next;
      if (next) {
        this.user.name = name.value;
        console.log("validateName");
      }
    }, error => {
      console.error(error);
    });
  }

  validatePhone(phoneInput:HTMLInputElement){

  }

  signUp(color: HTMLInputElement) {
    event.preventDefault();
    this.user.color = color.value;
    if (this.validMail && this.validPass && this.validName) {
      console.log("signing");
      this._registrationService.signUp(this.user, this.pass).subscribe(next => {
        this._router.navigateByUrl("/");
      }, error => {
        console.error(error);
      });
    }
  }

}
