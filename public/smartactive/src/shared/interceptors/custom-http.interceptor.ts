import {Inject, Injectable, PLATFORM_ID} from '@angular/core';
import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Router} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {isNull, isNullOrUndefined} from 'util';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import {isPlatformBrowser} from '@angular/common';
import {UserDetailsService} from "../service/user-details-service";
import {Url} from "../config/url";
import {basicKey} from "../config/constant";

// import {AppComponent} from "../../app/app.component";

@Injectable()
export class CustomHttpInterceptor implements HttpInterceptor {

  constructor(private _router: Router, private _userDetailsService: UserDetailsService, @Inject(PLATFORM_ID) private platformId: Object) {
  }

  intercept<T>(req: HttpRequest<T>, next: HttpHandler): Observable<HttpEvent<T>> {
    // AppComponent._loadRxListner.startLoading();

    let headers;
    if (!isPlatformBrowser(this.platformId)) {
      headers = new HttpHeaders();
    } else {
      headers = this.getHeaders(req);
    }
    req = req.clone({headers, url: Url.url + req.url});
    return next.handle(req).catch(err => {
      if (err.status === 401) {
        this._userDetailsService.logout();
      }
      if (err.status === 403) {
        return Observable.throw(err);
      } else {
        return Observable.throw(err);
      }
    });
  }

  getHeaders(req: HttpRequest<any>): HttpHeaders {
    let authKey = '';
    let headers = new HttpHeaders();
    let temp: HttpRequest<any>;
    if (isNull(req.headers)) {
      temp = req.clone({headers});
    } else {
      temp = req.clone();
    }
    if (temp.headers.keys().indexOf('multipart') != -1 || temp.headers.keys().indexOf('enctype') != -1) {
      headers = headers.append('enctype', 'form-data/multipart');
    } else {
      if (!isNullOrUndefined(localStorage.getItem('access_token')) && localStorage.getItem('access_token') != '') {
        authKey = 'Bearer ' + localStorage.getItem('access_token');
      } else if (req.params.get('grant_type') != null) {
        authKey = `Basic  ${basicKey}`;
        if (temp.headers.keys().indexOf('Content-Type') != -1) {
          if (temp.headers.get('Content-Type').indexOf('application/x-www-form-urlencoded') == -1) {
            headers = headers.set('Content-Type', temp.headers.get('Content-Type') + ';application/x-www-form-urlencoded');
          }
          if (temp.headers.get('Content-Type').indexOf('application/json') == -1) {
            headers = headers.set('Content-Type', temp.headers.get('Content-Type') + ';application/json');
          }
        } else {
          headers = headers.append('Content-Type', 'application/x-www-form-urlencoded;application/json');
        }
      }
      if (headers.keys().indexOf('Content-Type') != -1) {
        if (headers.get('Content-Type').indexOf('application/json') == -1) {
          headers = headers.set('Content-Type', temp.headers.get('Content-Type') + ';application/json');
        }
      } else {
        headers = headers.append('Content-Type', 'application/json');
      }
      headers = headers.append('Authorization', authKey);
    }
    headers = headers.append('Accept', 'application/json');
    return headers;
  }
}
