import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {User} from "../models/user";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import "rxjs/add/operator/catch";
import "rxjs/add/observable/throw";

@Injectable()
export class UserService {
  private controller = '/user';

  constructor(private _httpClient: HttpClient) {
  }

  getUser(): Observable<User> {
    return this._httpClient.get(`${this.controller}/getUser`).catch(err => Observable.throw(err));
  }

  add(form: FormData):Observable<User>{
    return this._httpClient.post(`${this.controller}/add`,form,{headers: new HttpHeaders().append("enctype",'enctype')})
      .catch(err=>Observable.throw(err));
  }

  findOne(id:number):Observable<User>{
    return this._httpClient.get(`${this.controller}/findOne/${id}`).catch(err=>Observable.throw(err));
  }

  getUserByPrincipal():Observable<User>{//todo zajve
    return this._httpClient.get(`${this.controller}/getUserByPrincipal`).catch(err=>Observable.throw(err));
  }

  findAll():Observable<User[]>{
    return this._httpClient.get(`${this.controller}/findAll`).catch(err=>Observable.throw(err));
  }

  sendEmail(email: string):Observable<any>{
      return this._httpClient.get(`${this.controller}/sendEmail`,{params: new HttpParams().set('email',email)})
        .catch(err=>Observable.throw(err));
  }

  confirm(uuid:string):Observable<any>{
    return this._httpClient.get(`${this.controller}/confirm/${uuid}`).catch(err=> Observable.throw(err));
  }

  validateName(name:string):Observable<boolean>{
    return this._httpClient.get(`${this.controller}/validateName`,{params: new HttpParams().set('name',name)})
      .catch(err=>Observable.throw(err));
  }

  validateEmail(email:string):Observable<boolean>{
    return this._httpClient.get(`${this.controller}/validateEmail`,{params: new HttpParams().set('email',email)})
      .catch(err=>Observable.throw(err));
  }

  deleteUser():Observable<any>{
    return this._httpClient.get(`${this.controller}/deleteUser`).catch(err=>Observable.throw(err));
  }

  ban(name:string):Observable<any>{
    return this._httpClient.post(`${this.controller}/ban`,{params: new HttpParams().set('name',name)})
      .catch(err=>Observable.throw(err));
  }

  findByName(name:string):Observable<User>{
    return this._httpClient.get(`${this.controller}/findByName`,{params:new HttpParams().set('name',name)})
      .catch(err=>Observable.throw(err));
  }
}
