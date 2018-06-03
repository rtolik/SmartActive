import {Message} from "./message";

export class CategoryMessage{
  public id:string;
  public messages:Message[]=[];
  public name:string;
  public time:string;
  constructor(){}
}
