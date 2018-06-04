import {Http} from "@angular/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Url} from "../config/url";
import {HttpParams} from "@angular/common/http";
/**
 * Created by Anatoliy on 26.05.2018.
 */

@Injectable()
export class RateService{
  constructor( private http:Http ){
  }

  loadRate( opportId : number):Observable<number>{
    return this.http.get(`${Url.url}/rate/countAvg?opportId=${opportId}`)
      .map(res => res.json())
      .catch(err=>Observable.throw(err));
  }

  incrementVoices(opportId:number,val:number):Observable<any>{
    return this.http.get(`${Url.url}/rate/incrementVoices`,{params: new HttpParams().set('opportunityId',opportId+"")
      .set('val',val+"")}).catch(err=> Observable.throw(err));
  }
}
