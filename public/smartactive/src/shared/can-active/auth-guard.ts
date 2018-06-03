import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs";
import {ActiveGuardService} from "./auth-guard-service";
import {isNull} from "util";
/**
 * Created by Kishka on 08.10.2017.
 */


export class ActiveGuard implements CanActivate {

constructor(){}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean>| boolean {

  if(isNull(localStorage.getItem("login"))){}

  return;
  }
}
