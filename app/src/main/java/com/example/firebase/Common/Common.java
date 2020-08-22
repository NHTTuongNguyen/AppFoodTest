package com.example.firebase.Common;

import android.content.Context;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;

        import com.example.firebase.Model.Request;
        import com.example.firebase.Model.User;

import java.util.Calendar;
import java.util.Locale;

public class Common {
    public  static User currentUser;
    public  static Request currentRequest;
    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";
    public static final String USER_KEY = "User";
    public static final String PWD_KEY = "Password";

    public static final String INTENT_FOOD_ID = "FoodId";

    public static  boolean isConnectedtoInternet(Context context)
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo[] info= connectivityManager.getAllNetworkInfo();
            if (info != null)
            {
                for (int i=0;i<info.length;i++)
                {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }

    public static String convertCodeToStatus(String status) {
        if (status.equals("0"))
            return "Placed";
        else if (status.equals("1"))
            return "On my way";
        else
            return "Shipped";
    }

    public static String getDate(long time)
    {
        Calendar calendar  = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        StringBuilder date  = new StringBuilder(
                android.text.format.DateFormat.format("MM/dd/yyyy HH:mm"
                ,calendar)
                .toString());
        return date.toString();
    }

}

