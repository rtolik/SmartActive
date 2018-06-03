import {Subject} from 'rxjs/Subject';
import {isNullOrUndefined} from 'util';
import {Inject, Injectable, PLATFORM_ID} from '@angular/core';
import {Router} from '@angular/router';
import {isPlatformBrowser} from '@angular/common';
import {User} from "../models/user";

@Injectable()
export class UserDetailsService {


  public user: User = new User();
  public isAuth: boolean = false;
  private _user = new Subject<User>();
  user$ = this._user.asObservable();
  private _isAuth = new Subject<boolean>();
  isAuth$ = this._isAuth.asObservable();

  constructor(private _router: Router, @Inject(PLATFORM_ID) private platformId: Object) {
  }

  public login(user: User) {
    if (this.isAuth)
      return;
    this.user = user;
    this._user.next(this.user);
    this.isAuth = true;
    this._isAuth.next(this.isAuth);

  }

  public logout() {
    this.clearStorage();
    this.user = new User();
    this.user.role = 'no_access_token';
    this.isAuth = false;
    this._user.next(this.user);
    this._isAuth.next(this.isAuth);
    this._router.navigateByUrl('/login');
  }

  checkAuth(): boolean {
    if (!isPlatformBrowser(this.platformId))
      return false;
    return (!isNullOrUndefined(localStorage.getItem('access_token')));
  }

  tokenParseInLocalStorage(data: any) {
    if (!isPlatformBrowser(this.platformId))
      return;
    this.clearStorage();
    localStorage.setItem('access_token', data.access_token);
    localStorage.setItem('token_type', data.token_type);
    localStorage.setItem('expires_in', new Date().setSeconds(data.expires_in) + '');
    localStorage.setItem('scope', data.scope);
    localStorage.setItem('jti', data.jti);
    localStorage.setItem('refresh_token', data.refresh_token);
  }

  clearStorage() {
    if (!isPlatformBrowser(this.platformId))
      return;
    localStorage.removeItem('access_token');
    localStorage.removeItem('token_type');
    localStorage.removeItem('expires_in');
    localStorage.removeItem('scope');
    localStorage.removeItem('jti');
    localStorage.removeItem('refresh_token');
  }

}
