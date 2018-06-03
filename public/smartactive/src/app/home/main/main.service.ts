import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptions} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {Usage} from "../../../shared/models/usage";
import {Url} from "../../../shared/config/url";
import {Category} from "../../../shared/models/category";

@Injectable()
export class MainService {

  constructor(private _http: Http) {
  }


  researchCategory(text: string, price:string,catId:string): Observable<Usage[]> {
    let headers = new Headers();
    // headers.append('Access-Control-Allow-Headers', 'application/json');
    // headers.append('Access-Control-Allow-Methods', 'PUT');
    // headers.append('Access-Control-Allow-Origin', '*');
    let options = new RequestOptions({headers: headers});

    let body: FormData = new FormData();
    body.append("keywords", text);
    body.append("maxPrice", price);
    body.append("categoryId", catId);
    return this
      ._http
      .post(Url.url + "/services/multipleFilter",body, options).map(res => res.json())
      .catch((error) => Observable.throw(error));


  }

  findAllCategories():Observable<Category[]>{
    return this._http.get(Url.url + "/categories/findAll").map(res=>res.json());
  }

}
