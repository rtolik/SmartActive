import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Http, Headers, RequestOptions} from "@angular/http";
import {Url} from "../../../../environments/url";
import {User} from "../../../../environments/models/user";

@Injectable()
export class OneMessageService {

  constructor(private _http: Http) {
  }

  report(userName: string): Observable<Response> {
    let headers = new Headers();
    // headers.append('Access-Control-Allow-Headers', 'application/json');
    // headers.append('Access-Control-Allow-Methods', 'PUT');
    // headers.append('Access-Control-Allow-Origin', '*');
    let options = new RequestOptions({headers: headers});

    let body: FormData = new FormData();
    body.append("name", userName);
    return this
      ._http
      .post(Url.url + "/user/ban", body, options).map(res => res)
      .catch((error) => Observable.throw(error));
  }

  findByName(userName: string): Observable<User> {
    let headers = new Headers();

    let options = new RequestOptions({headers: headers});

    let body: FormData = new FormData();
    body.append("name", userName);
    return this
      ._http
      .post(Url.url + "/user/findByName", body, options).map(res => res.json())
      .catch((error) => Observable.throw(error));
  }

}
