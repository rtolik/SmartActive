import {Opportunity} from "./opportunity";
/**
 * Created by Kishka on 07.10.2017.
 */

export class Category{
  id:number;
  name:string;
  active:boolean;
  usages:Opportunity[];
}
