/**
 * Created by Kishka on 08.10.2017.
 */

import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptions} from "@angular/http";
import {Observable} from "rxjs";
import {Url} from "../../url";

@Injectable()
export class ActiveGuardService{

  constructor(private _http:Http){}

  checkAuth():Observable<boolean>{
    return this._http.get(Url.url+"/user/getPrincipal").map(res=>res.json());
  }

}
