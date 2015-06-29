package com.zakencorp.zaken.servicescreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Zaken on 30/05/2015.
 */
public class UsersBL extends Activity {

    UsersDAL task;
    private SweetAlertDialog pDialog;
    private SweetAlertDialog wrongPassword;


    // Strats The AsyncTask And Display Alert
    public void getBusinessID(Activity activity,Context context,int userPassword,String userName)
    {
        pDialog=new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("אנא המתן...");
        pDialog.setCancelable(true);
        pDialog.show();

        task = new UsersDAL(this,context,userPassword,userName);
        task.execute();
    }

// Gets The Business Id From The AsyncTask And Starting The Main Activity
    public void startMain(Context context,Object... values)
    {
        pDialog.cancel();
        // Wrong Password Or User Name, Start Alert
        if (values[0]==0) {
            wrongPassword=new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
            wrongPassword.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            wrongPassword.setTitleText("בעיה בהתחברות");
            wrongPassword.setContentText("שם משתמש או ססמא לא נכונים");
            wrongPassword.setCancelable(true);
            wrongPassword.show();
            task.cancel(true);
        }
        else
        {
            // If The Password And UserName Is Correct, Start Main Activity
            int branchId = (int) values[0];
            Intent i=new Intent(context ,MainScreen.class);
            i.putExtra("branchId",  branchId);
            context.startActivity(i);
            task.cancel(true);
        }

    }


}
