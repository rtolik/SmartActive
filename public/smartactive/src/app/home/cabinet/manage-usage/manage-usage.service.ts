import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptions, Response} from "@angular/http";
import {Observable} from "rxjs";
import {Url} from "../../../../shared/config/url";
import {Opportunity} from "../../../../shared/models/opportunity";


@Injectable()
export class MenageUsageService {
  constructor(public _http: Http) {
  }

  loadDropList(cat: string): Observable<Response> {
    event.preventDefault();
    let userUrl = Url.url + "/categories/filterByWord";
    let headers = new Headers();
    let formData = new FormData();
    formData.append("word", cat);

    let options = new RequestOptions({headers: headers});
    return this._http.post(userUrl, formData, options);
  }
}
