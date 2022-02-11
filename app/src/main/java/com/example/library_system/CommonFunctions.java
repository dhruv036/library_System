package com.example.library_system;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CommonFunctions {

    public static String givedate(String mili) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy");
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTimeInMillis(Long.parseLong(mili));
        String d = simpleDateFormat.format(calendar1.getTime());
        String dd = String.valueOf(calendar2.get(Calendar.YEAR));  //take current day YEAR
        dd= dd.substring(2,dd.length());
        int now = Integer.parseInt(dd);
        int pre = Integer.parseInt(d);

        if (now == pre) { // CHECKING YEAR
            simpleDateFormat = new SimpleDateFormat("MM");
            dd = String.valueOf(calendar2.get(Calendar.MONTH));
            d = simpleDateFormat.format(calendar1.getTime());
            pre = Integer.parseInt(d);
            now = Integer.parseInt(dd);

            if ((now+1) == pre) { // CHECKING MONTH
                simpleDateFormat = new SimpleDateFormat("dd");
                dd = String.valueOf(calendar2.get(Calendar.DATE));
                d = simpleDateFormat.format(calendar1.getTime());
                pre = Integer.parseInt(d);
                now = Integer.parseInt(dd);
                Log.e("TAG1"," "+now);
                Log.e("TAG2"," "+pre);
                if (now == pre) {  // CHECKING DATE
                    return "Today";
                } else {
                    if (now == (pre + 1)) {
                        return "Yesterday";
                    }else {
                        simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
                        return simpleDateFormat.format(calendar1.getTime());
                    }
                }

            } else {
                simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
                return simpleDateFormat.format(calendar1.getTime());
            }
        }else {
            simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
            return simpleDateFormat.format(calendar1.getTime());
        }
    }




}
