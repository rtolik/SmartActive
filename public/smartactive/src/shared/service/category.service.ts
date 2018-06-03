import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Category} from "../models/category";
import "rxjs/add/operator/catch";
import "rxjs/add/observable/throw";

@Injectable()
export class CategoryService {
  private controller = '/categories';

  constructor(private _httpClient: HttpClient) {
  }

  add(name: string): Observable<Category> {
    return this._httpClient.post(`${this.controller}/add`, null, {params: new HttpParams().set('name', name)}).catch(err => Observable.throw(err));
  }
}
