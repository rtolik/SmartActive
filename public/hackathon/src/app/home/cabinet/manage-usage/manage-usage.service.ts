import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptions, Response} from "@angular/http";
import {Observable} from "rxjs";
import {Url} from "../../../../environments/url";
import {Usage} from "../../../../environments/models/usage";


@Injectable()
export class MenageUsageService {
  constructor(public _http: Http) {
  }

  addOpportunities(form: HTMLFormElement): Observable<Response> {
    event.preventDefault();
    let userUrl = Url.url + "/services/add";
    let headers = new Headers();
    let formData = new FormData(form);

    let options = new RequestOptions({headers: headers});
    return this._http.post(userUrl, formData, options);
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

  getStatuses(): Observable<Response> {
    let userUrl = Url.url + "/services/loadStatuses";
    let headers = new Headers();
    let options = new RequestOptions({headers: headers});
    return this._http.get(userUrl, options);
  }


  loadOpporByUser(): Observable<Response> {
    let userUrl = Url.url + "/services/findByUser";
    let headers = new Headers();
    let options = new RequestOptions({headers: headers});
    return this._http.get(userUrl, options);
  }

  setOpporActive(active:boolean,id:number) : Observable<any>{
    return this._http.get(`${Url.url}/services/setActive?activity=${active}&id=${id}`)
      .map(res => res.json())
      .catch(err=>Observable.throw(err))
      ;
  }

}
