import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import "rxjs/add/operator/catch";
import "rxjs/add/observable/throw";

@Injectable()
export class LoginService {

  constructor(private _http: HttpClient) {
  }

  login(name: string, password: string): Observable<any> {
    return this._http.post('/oauth/token', null, {
      params: new HttpParams()
        .set('username', name)
        .set('password', password)
        .set('grant_type', 'password')
    }).catch(error => Observable.throw(error));
  }
}
