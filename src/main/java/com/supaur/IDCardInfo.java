package com.supaur;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class IDCardInfo extends UDF {

    public String evaluate(String idCard, String request) {
        String res = null;
        CheckIDCardValidate check = new CheckIDCardValidate();
        if (idCard == null) {
            return null;
        } else if (check.isValidatedAllIdcard(idCard)) {
            if (request.equals("age")) res = getAge(idCard);
            else if (request.equals("province")) res = getProvince(idCard);
            else if (request.equals("gender"))  res = getGender(idCard);
            else if (request.equals("birthday")) res = getBirthday(idCard);
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

    public static String getProvince(String idCard) {
        Map<String, String> cityCodeMap = new HashMap<String, String>() {
            {
                this.put("11", "北京");
                this.put("12", "天津");
                this.put("13", "河北");
                this.put("14", "山西");
                this.put("15", "内蒙古");
                this.put("21", "辽宁");
                this.put("22", "吉林");
                this.put("23", "黑龙江");
                this.put("31", "上海");
                this.put("32", "江苏");
                this.put("33", "浙江");
                this.put("34", "安徽");
                this.put("35", "福建");
                this.put("36", "江西");
                this.put("37", "山东");
                this.put("41", "河南");
                this.put("42", "湖北");
                this.put("43", "湖南");
                this.put("44", "广东");
                this.put("45", "广西");
                this.put("46", "海南");
                this.put("50", "重庆");
                this.put("51", "四川");
                this.put("52", "贵州");
                this.put("53", "云南");
                this.put("54", "西藏");
                this.put("61", "陕西");
                this.put("62", "甘肃");
                this.put("63", "青海");
                this.put("64", "宁夏");
                this.put("65", "新疆");
                this.put("71", "台湾");
                this.put("81", "香港");
                this.put("82", "澳门");
                this.put("91", "国外");
            }
        };
        String province = null;
        String provinceId = idCard.substring(0, 2);
        Set<String> key = cityCodeMap.keySet();
        for (String id : key) {
            if (id.equals(provinceId)) {
                province = cityCodeMap.get(id);
                break;
            }
        }
        return province;
    }

    public static String getGender(String idCard){
        String gender = null;
        String id17 = idCard.substring(16, 17);
        if (Integer.parseInt(id17) % 2 != 0) {
            gender = "男";
        } else {
            gender = "女";
        }
        return gender;
    }

    public static String getBirthday(String idCard){
        return idCard.substring(6, 14);
    }
}