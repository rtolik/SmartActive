import {Opportunity} from "../models/opportunity";
import {Subject} from "rxjs";
import {Category} from "../models/category";
/**
 * Created by Kishka on 07.10.2017.
 */


export class EventService {

  public sSavedEvents:Opportunity[]=[];
  private _savedEvents = new Subject<Opportunity[]>();
  _savedEvents$ = this._savedEvents.asObservable();

  public sEventList: Opportunity[] = [];
  private _eventList = new Subject<Opportunity[]>();
  _eventList$ = this._eventList.asObservable();


  public sCategoryList: Category[] = [];
  private _categoryList = new Subject<Category[]>();
  _categoryList$ = this._categoryList.asObservable();

  constructor() {
  }


  contains(list: any[], element: any): boolean {
    for (let one of list) {
      if (one.id == element.id)
        return true;
    }
    return false;
  }

  saveUsage(category: Opportunity) {
    if (!this.contains(this.sSavedEvents, category)) {
      this.sSavedEvents.push(category);
      this._savedEvents.next(this.sSavedEvents);
    }
  }

  addCategory(category: Category) {
    console.log(category);
    if (!this.contains(this.sCategoryList, category)) {
      this.sCategoryList.push(category);
      this._categoryList.next(this.sCategoryList);
    }
  }

  addCategories(categories: Category[]) {
    for (let category of categories) {
      this.addCategory(category);
    }
  }

  removeCategory(category: Category) {
    this.sCategoryList.slice(this.indexOf(this.sCategoryList, category), 1);
    this._categoryList.next(this.sCategoryList);
  }

  removeCategories(categories: Category[]) {
    for (let category of categories) {
      this.removeCategory(category);
    }
  }

  addUsage(usage: Opportunity) {
    console.log(usage);
    if (!this.contains(this.sEventList, usage)) {
      this.sEventList.push(usage);
      this._eventList.next(this.sEventList);
    }
  }

  addUsages(usages: Opportunity[]) {
    for (let usage of usages) {
      this.addUsage(usage);
    }
  }

  removeUsage(usage: Opportunity) {
    this.sEventList.slice(this.indexOf(this.sEventList, usage), 1);
    this._eventList.next(this.sEventList);
  }

  remAll() {
    this.sCategoryList = [];
    this.sEventList = [];
  }

  removeUsages(usages: Opportunity[]) {
    for (let usage of usages) {
      this.removeUsage(usage);
    }
  }

  activateUsage(usage: Opportunity, value: boolean) {
    this.sEventList[this.indexOf(this.sEventList, usage)].active = value;
  }

  activateCategory(category: Category, value: boolean) {
    this.sCategoryList[this.indexOf(this.sCategoryList, category)].active = value;
    for (let i = 0; i < this.sEventList.length; i++) {
      if (this.sEventList[i].category.id == category.id) {
        this.sEventList[i].active = value;
        // alert();
      }
    }
    this._eventList.next(this.sEventList);

    // for (let i = 0; i < this.sEventList.length; i++) {
    //   console.log("active:" + this.sEventList[i].category.active + "id:" + this.sEventList[i].id);
    // }
  }

  indexOf(arr: any[], element): number {
    for (let i = 0; i < arr.length; i++) {
      if (arr[i].id == element.id) {
        return i;
      }
    }
    return -1;
  }

}
