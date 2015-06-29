package com.zakencorp.zaken.servicescreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Zaken on 05/06/2015.
 */
public class QueueBL
{
    QueueDAL task;
    public SharedPreferences.Editor editor;
    SharedPreferences sharedPrefQueue;
    private Context context;
    private int branchId;
    private SweetAlertDialog pDialog;

    public QueueBL(Context context,int branchId)
    {
        this.context=context;
        this.branchId=branchId;
        sharedPrefQueue = context.getSharedPreferences("MyPrefsFile", 0);
        editor = sharedPrefQueue.edit();
    }

    public void startAsync() // Starts The Async Task
    {
        // Starts An Alert While Calculating The Queueu
        pDialog=new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("אנא המתן לקבלת התור");
        pDialog.setCancelable(false);
        pDialog.show();
        task= new QueueDAL(this,branchId,context);
        task.execute();
        Log.d("### startAsync()", "" );
    }

    public void setUserQueue(int lineNum,Context mainScreenContext) // Displaying The Queue To The User! And Setting The Message
    {
        editor.putInt("THE_LINE",lineNum).apply();
        Log.d("On BL setUserQueue", "" + lineNum);
        pDialog.cancel();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mainScreenContext);
        // set title
        alertDialogBuilder.setTitle("קבלת תור!");
        // set dialog message
        alertDialogBuilder
                .setMessage("מספרך בתור הוא : " + lineNum )
                .setCancelable(false)
                .setPositiveButton("אשר",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        lp.height = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        TextView message=(TextView) alertDialog.findViewById(android.R.id.message);
        message.setTextSize(40);
        alertDialog.getWindow().getAttributes().height = WindowManager.LayoutParams.MATCH_PARENT;
        alertDialog.show();
    }



}