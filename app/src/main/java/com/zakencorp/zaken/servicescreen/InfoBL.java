package com.zakencorp.zaken.servicescreen;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.TextView;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * Created by Zaken on 31/05/2015.
 */
public class InfoBL extends Activity // Business Logic Class
{
    public InfoDAL task;
    private TextView currentLineTV;
    private TextView waitingClientsTV;
    private TextView timeTextTV;
    private Context context;


    public InfoBL(Context context) //Constructor Thats Get The Activity Contex And Business Id
    {
        this.context=context;
        currentLineTV = (TextView)((Activity)context).findViewById(R.id.currLineTV);
        waitingClientsTV=(TextView)((Activity)context).findViewById(R.id.waitingTV);
        timeTextTV=(TextView)((Activity)context).findViewById(R.id.timeTextTV);
    }

    // Stars The AsyncTask
    public void getCurrentQueueAndWaitingClients(Activity activity,Context context,int branchId)
    {
        task = new InfoDAL(this,context,branchId);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void displayData(Object...progress) // Processing The Data And Displaying It.
    {
        // Using The Current Line, Number Of Clerks In The Business, The Total Clients, Average Time For A Queue,
        int currentLine= (int) progress[0];
        int numOfClerks=(int) progress[3];
        int totalClients=(int)progress[4];
        int numOfPeopleForAverage=(int) progress[2];
        Time t = (Time) progress[1];
        int waitingClients;

        if(totalClients==0)
            waitingClients=0;
        else
        waitingClients= totalClients-currentLine+1;

        currentLineTV.setText(""+currentLine);
        waitingClientsTV.setText(""+waitingClients);
        timeTextTV.setText(setAverage(t.getHours(),t.getMinutes(),t.getSeconds(),numOfPeopleForAverage,numOfClerks));
    }

    // Sets The Average Time For A Queue((Average Time For Queue * Waiting Clients )/The Number Of Clerks)
    public String setAverage(int receivedHours, int receivedMinutes, int receivedSeconds,int queueNum,int numOfClerks) {
        Long secondsRR,newHours,newMinutes,newSeconds;
        String toReturn;
        secondsRR = TimeUnit.HOURS.toSeconds(receivedHours)+
                TimeUnit.MINUTES.toSeconds(receivedMinutes)+
                receivedSeconds;
        secondsRR = (secondsRR*queueNum)/numOfClerks;
        newHours=TimeUnit.SECONDS.toHours(secondsRR);
        secondsRR=secondsRR-(newHours*3600);
        newMinutes=TimeUnit.SECONDS.toMinutes(secondsRR);
        secondsRR=secondsRR-(newMinutes*60);
        newSeconds=secondsRR;
// Adds Zero`s The Time Display
        if(newHours<10)
            toReturn="0"+newHours;
        else
            toReturn=""+newHours;
        if(newMinutes<10)
            toReturn+=":0"+newMinutes;
        else
            toReturn+=":"+newMinutes;
        if(newSeconds<10)
            toReturn+=":0"+newSeconds;
        else
            toReturn+=":"+newSeconds;

        return toReturn;
    }
}
