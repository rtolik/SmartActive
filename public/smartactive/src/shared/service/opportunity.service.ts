import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Opportunity} from "../models/opportunity";
import "rxjs/add/operator/catch";
import "rxjs/add/observable/throw";

@Injectable()
export class OpportunityService {

  private controller = '/services';

  constructor(private _httpClient: HttpClient) {
  }

  add(form: FormData,id:number): Observable<Opportunity> {
    return this._httpClient.post(`${this.controller}/add/${id}`, form, {headers: new HttpHeaders().append("enctype", 'enctype')}).catch(err => Observable.throw(err));
  }

  findOne(id: number): Observable<Opportunity> {
    return this._httpClient.get(`${this.controller}/findOne/${id}`).catch(err => Observable.throw(err));
  }

  findAll(): Observable<Opportunity[]> {
    return this._httpClient.get(`${this.controller}/findAll`).catch(err => Observable.throw(err));
  }

  findAllActive(): Observable<Opportunity[]> {
    return this._httpClient.get(`${this.controller}/findAllActive`).catch(err => Observable.throw(err));
  }

  findByUser(): Observable<Opportunity[]> {
    return this._httpClient.get(`${this.controller}/findByUser`).catch(err => Observable.throw(err));
  }

  searchByKeywords(words: string): Observable<Opportunity[]> {
    return this._httpClient.get(`${this.controller}/searchByKeywords`, {params: new HttpParams().set('keywords', words)}).catch(err => Observable.throw(err));
  }

  saveToUser(id: number): Observable<any> {
    return this._httpClient.post(`${this.controller}/saveToUser/${id}`, null).catch(err => Observable.throw(err));
  }

  findAllInCategory(id: number): Observable<Opportunity[]> {
    return this._httpClient.get(`${this.controller}/findAllInCategory/${id}`).catch(err => Observable.throw(err));
  }

  setActive(id: number, active: boolean): Observable<any> {
    return this._httpClient.post(`${this.controller}/setActive/${id}`, null, {params: new HttpParams().set('activity', active + '')}).catch(err => Observable.throw(err));
  }

  findByPrice(price: number): Observable<Opportunity[]> {
    return this._httpClient.get(`${this.controller}/findByPrice`, {params: new HttpParams().set('price', price + '')}).catch(err => Observable.throw(err));
  }

  multipleFilter(idCategory: number, price: number, keywords: string): Observable<Opportunity[]> {
    return this._httpClient.get(`${this.controller}/multipleFilter`, {params: new HttpParams().set('categoryId', idCategory + '').set('maxPrice', price + '').set('keywords', keywords)}).catch(err => Observable.throw(err));
  }

  loadStatuses(): Observable<string[]> {
    return this._httpClient.get(`${this.controller}/loadStatuses`).catch(err => Observable.throw(err));
  }
}

