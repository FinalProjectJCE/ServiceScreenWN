package com.zakencorp.zaken.servicescreen;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.TextView;

/**
 * Created by Zaken on 31/05/2015.
 */
public class InfoBL extends Activity
{
    public InfoDAL task;
    private TextView currentLineTV;
    private TextView waitingClientsTV;
    private Context context;


    public InfoBL(Context context)
    {
        this.context=context;
        currentLineTV = (TextView)((Activity)context).findViewById(R.id.currLineTV);
        waitingClientsTV=(TextView)((Activity)context).findViewById(R.id.waitingTV);
    }

    public void getCurrentQueueAndWaitingClients(Activity activity,Context context,int branchId)
    {
        task = new InfoDAL(this,context,branchId);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void displayData(int currentLine,int totalClients)
    {
        int waitingClients;
        if(totalClients==0)
            waitingClients=0;
        else
        waitingClients= totalClients-currentLine;

        currentLineTV.setText(""+currentLine);
        waitingClientsTV.setText(""+waitingClients);
    }
}
