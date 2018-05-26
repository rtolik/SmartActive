import {Subject} from "rxjs/Subject";
import {isNull} from "util";
/**
 * Created by danul on 08.10.2017.
 */


export class LangService {

  public slang: string;
  private _lang = new Subject<string>();
  _lang$ = this._lang.asObservable();


  constructor() {
    if (!isNull(localStorage.getItem("lang"))) {
      this.slang = localStorage.getItem("lang");
    } else {
      this.next("ua");
    }
  }

  next(lang: string) {
    this.slang = lang;
    this._lang.next(this.slang);
    localStorage.setItem("lang", lang);
  }
}
