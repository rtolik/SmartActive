import { Injectable } from '@angular/core';
import {Headers, Http, RequestOptions} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {Usage} from "../../../environments/models/usage";
import {Url} from "../../../environments/url";

@Injectable()
export class EventOneService {

  constructor(private _http:Http) { }

  loadUsage(id:number):Observable<Usage>{
    let headers = new Headers({});

    let options = new RequestOptions({headers: headers});
    return this
      ._http
      .get(Url.url + "/services/findOne?id="+id, options).map(res=>res.json())
      .catch((error) => Observable.throw(error));
  }

  incrementVoices(value:number, id:number):Observable<any> {
    return this._http.get(`${Url.url}/rate/incrementVoices?opportunityId=${id}&val=${value}`)
      .catch(error=> Observable.throw(error));
  }
}
