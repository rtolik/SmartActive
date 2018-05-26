import {User} from "../models/user";
import {Subject} from "rxjs";
/**
 * Created by Kishka on 07.10.2017.
 */

export class UserDetailsService{

  public user:User;
  private _user = new Subject<User>();
  _user$ = this._user.asObservable();

  public isAuthenticated:boolean;
  private _isAuthenticated = new Subject<boolean>();
  _isAuthenticated$ = this._isAuthenticated.asObservable();

  login(user:User){
    this.user = user;
    this._user.next(this.user);
    this.isAuthenticated = true;
    this._isAuthenticated.next(this.isAuthenticated);
    localStorage.setItem("login", JSON.stringify(true));
  }

  logout(){
    this.user = new User;
    this._user.next(this.user);
    this.isAuthenticated = false;
    this._isAuthenticated.next(this.isAuthenticated);
    localStorage.setItem("login", JSON.stringify(false));

  }

}
