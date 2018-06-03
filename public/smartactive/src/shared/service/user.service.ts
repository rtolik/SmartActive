import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {User} from "../models/user";
import {HttpClient} from "@angular/common/http";
import "rxjs/add/operator/catch";
import "rxjs/add/observable/throw";

@Injectable()
export class UserService {
  private controller = '/user';

  constructor(private _httpClient: HttpClient) {
  }

  getUser(): Observable<User> {
    return this._httpClient.get(this.controller).catch(err => Observable.throw(err));
  }
}
