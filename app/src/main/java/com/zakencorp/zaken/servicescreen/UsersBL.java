package com.zakencorp.zaken.servicescreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Zaken on 30/05/2015.
 */
public class UsersBL extends Activity {

    UsersDAL task;
    public void getBusinessID(Activity activity,Context context,int userPassword,String userName)
    {
        task = new UsersDAL(this,context,userPassword,userName);
        task.execute();
    }

    //public void UUU(int branchId,String businessName,String branchName,String cityName,Context context)
    public void UUU(Context context,Object... values)
    {
        if (values[0]==0) {
            Log.d("No Business Were Found", " " +values[0]);
            task.cancel(true);
        }
        else
        {
            int branchId = (int) values[0];
            Intent i=new Intent(context ,MainScreen.class);
            i.putExtra("branchId",  branchId);
            context.startActivity(i);
            task.cancel(true);
        }

    }

}
