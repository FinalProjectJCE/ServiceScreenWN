package com.zakencorp.zaken.servicescreen;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Zaken on 28/06/2015.
 */
public class BusinessNameBL // Business Logic For Display The Business Name
{
    private Context context;
    private TextView businessNameTV;
    int branchId;
    BusinessNameDAL bndal;

    public BusinessNameBL(Context context,int branchId)// Constructor Thats Get The Activity Contex And Business Id
    {
        this.context=context;
        this.branchId=branchId;
        businessNameTV = (TextView)((Activity)context).findViewById(R.id.businessNameTV);
    }

    public void startAsync() // Start The Async Task To Get The Name
    {
        bndal=new BusinessNameDAL(this,context,branchId);
        bndal.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    public void setName(Object... values){ // Sets The Business Name When Called
        businessNameTV.setText(""+values[0]+" "+values[1]+" "+values[2]);
    }
}
