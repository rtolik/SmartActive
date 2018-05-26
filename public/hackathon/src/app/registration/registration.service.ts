import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Url} from "../../environments/url";
import {Headers, Http, RequestOptions} from "@angular/http";
import {User} from "../../environments/models/user";

@Injectable()
export class RegistrationService {

  constructor(private _http: Http) {
  }

  validateName(nickName: string): Observable<boolean> {
    let headers = new Headers({});

    let options = new RequestOptions({headers: headers});
    let body = new FormData();
    body.append("name", nickName);
    return this
      ._http
      .post(Url.url + "/user/validateName", body, options)
      .catch((error) => Observable.throw(error));
  }

  validateSoap(soap: string): Observable<boolean> {
    let headers = new Headers({});

    let options = new RequestOptions({headers: headers});
    let body = new FormData();
    body.append("email", soap);
    return this
      ._http
      .post(Url.url + "/user/validateEmail", body, options)
      .catch((error) => Observable.throw(error));
  }

  signUp(user: User, pass: string): Observable<Response> {
    let headers = new Headers({});

    let options = new RequestOptions({headers: headers});
    let body = new FormData();
    body.append("email", user.email);
    body.append("name", user.name);
    body.append("password", pass);
    body.append("color", user.color);
    body.append("phone", "1234567890");
    return this
      ._http
      .post(Url.url + "/user/add", body, options)
      .catch((error) => Observable.throw(error));
  }

}
