import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Url} from "../../shared/config/url";
import {Headers, Http, RequestOptions} from "@angular/http";
import {User} from "../../shared/models/user";
import {HttpParams} from "@angular/common/http";

@Injectable()
export class RegistrationService {

  constructor(private _http: Http) {
  }

  validateName(nickName: string): Observable<boolean> {
    return this
      ._http
      .get(Url.url + "/user/validateName", {params:new HttpParams().set('name',nickName)})
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
    body.append("phone", user.phoneNumber );
    return this
      ._http
      .post(Url.url + "/user/add", body, options)
      .catch((error) => Observable.throw(error));
  }

}
