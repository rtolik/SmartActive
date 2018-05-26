import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Url} from "../../environments/url";
import {Headers, Http, RequestOptions} from "@angular/http";
import {User} from "../../environments/models/user";

@Injectable()
export class LoginService {

  constructor(private _http: Http) {
  }

  login(name: string, password: string): Observable<Response> {
    let headers = new Headers({
      'authorization': 'Basic ' + btoa(name + ':' + password),
      'X-Requested-With': 'XMLHttpRequest'
    });

    let options = new RequestOptions({headers: headers});

    return this
      ._http
      .options(Url.url + "/", options)
      .catch((error) => Observable.throw(error));
  }

  getPrincipal():Observable<User>{
    let headers = new Headers({
    });

    let options = new RequestOptions({headers: headers});

    return this
      ._http
      .get(Url.url + "/user/getUserByPrincipal", options).map(res=>res.json())
      .catch((error) => Observable.throw(error));

  }

}
