package io.crowdcode.tt2ss.utils;

public class StringUtil {

    public static String capitalizeString(String str) {
        String result="";
        String[] caps = str.split(" ");
        for (String cap : caps) {
            result+=capitalize(cap)+" ";

        }

        if (result != null && result.endsWith(" ")) {
            result = chomp(result);
        }

        return result;
    }

    public static String chomp(String s){
        return s.substring(0, s.length()-1);
    }

    public static String capitalize(String str) {
        if (str != null && str.length() > 1) {
            String cap = str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
            return cap;
        } else{
            return str;
        }

    }
}
