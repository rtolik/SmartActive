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

  findOne(id: number): Observable<Category> {
    return this._httpClient.get(`${this.controller}/findOne/${id}`).catch(err => Observable.throw(err));
  }

  findAll(): Observable<Category[]> {
    return this._httpClient.get(`${this.controller}/findAll`).catch(err => Observable.throw(err));
  }

  findByWord(words: string): Observable<string[]> {
    return this._httpClient.post(`${this.controller}/filterByWord`, null, {params: new HttpParams().set('word', words)}).catch(err => Observable.throw(err));
  }

  findAllByIds(ids: number[]): Observable<Category[]> {
    return this._httpClient.get(`${this.controller}/findAllByIds`, {params: new HttpParams().set('ids', JSON.stringify(ids))}).catch(err => Observable.throw(err));
  }

  delete(id: number): Observable<any> {
    return this._httpClient.delete(`${this.controller}/delete/${id}`).catch(err => Observable.throw(err));
  }
}
