import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptions} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {Opportunity} from "../../../shared/models/opportunity";
import {Url} from "../../../shared/config/url";
import {Category} from "../../../shared/models/category";
import {HttpParams} from "@angular/common/http";

@Injectable()
export class MainService {

  constructor(private _http: Http) {
  }

  findAllCategories():Observable<Category[]>{
    return this._http.get(Url.url + "/categories/findAll").map(res=>res.json());
  }

}
