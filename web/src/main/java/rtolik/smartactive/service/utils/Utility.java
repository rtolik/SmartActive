package rtolik.smartactive.service.utils;

import rtolik.smartactive.config.Constants;

import java.time.LocalDate;

/**
 * Created by Anatoliy on 04.06.2018.
 */
public class Utility {

    public static String getUnbanDate(String dateOfStart){
        String[] date=dateOfStart.split("-");
        Integer year=Integer.parseInt(date[0]);
        Integer mounth=Integer.parseInt(date[1]);
        Integer day = Integer.parseInt(date[2]);
        day+= Constants.BANNING_DAYS;
        if(mounth==2&&year%4==0&&day>29){
            day-=29;
            mounth++;
            return createResultDate(year,mounth,day);
        }
        if(mounth==2&&year%4!=0&&day>28){
            day-=28;
            mounth++;
            return createResultDate(year,mounth,day);
        }
        if((mounth==4||mounth==6||mounth==9||mounth==11)&&day>30){
            day-=30;
            mounth++;
            return createResultDate(year,mounth,day);
        }
        if(day>31){
            day-=31;
            mounth++;
            if(mounth>12) {
                mounth-=12;
                year++;
            }
            return createResultDate(year,mounth,day);
        }
        return createResultDate(year,mounth,day);
    }

    private static String createResultDate(Integer year,Integer mounth, Integer day){
        return year+"-"+(mounth<10?"0"+mounth:mounth)+"-"+(day<10?"0"+day:day);
    }

//    public static Boolean dataComparer(String leftDate, String rightDate) {
//        String[] leftSplit = leftDate.split("-");
//        String[] rightSplit = rightDate.split("-");
//        Integer leftYear = Integer.parseInt(leftSplit[0]);
//        Integer rightYear = Integer.parseInt(rightSplit[0]);
//        if (leftYear != rightYear)
//            return false;
//        Integer leftMounth = Integer.parseInt(leftSplit[1]);
//        Integer rightMounth = Integer.parseInt(rightSplit[1]);
//        if (leftMounth != rightMounth)
//            return false;
//        Integer leftDay = Integer.parseInt(rightSplit[2]);
//        Integer rightDay = Integer.parseInt(rightSplit[2]);
//        if (leftDay != rightDay)
//            return false;
//        return false;
//    }
}
