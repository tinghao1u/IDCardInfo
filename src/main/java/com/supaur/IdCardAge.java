package com.supaur;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.tukaani.xz.check.Check;

import java.util.Calendar;


public class IdCardAge extends UDF {

    public String evaluate(String idCard, String request) {
        String res = null;
        CheckIDCardValidate check = new CheckIDCardValidate();
        if (idCard == null) {
            return null;
        } else if (check.isValidatedAllIdcard(idCard)) {
            if (request.equals("age")) res = getAge(idCard);
            //else if (request.equals("province"))
        }
        return res;
    }

    public static String getAge(String idCard) {
        String res = null;
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayNow = cal.get(Calendar.DATE);

        int yearIdCard = Integer.valueOf(idCard.substring(6, 10));
        int monthIdCard = Integer.valueOf(idCard.substring(10, 12));
        int dayIdCard = Integer.valueOf(idCard.substring(12, 14));

        if (monthIdCard < monthNow || (monthIdCard == monthNow && dayIdCard < dayNow)) {
            res = String.valueOf(yearNow - yearIdCard);
        } else {
            res = String.valueOf(yearNow - yearIdCard - 1);
        }
        return res;
    }

}
