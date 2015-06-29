package com.zakencorp.zaken.servicescreen;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;

/**
 * Created by Zaken on 31/05/2015.
 */
public class InfoDAL extends AsyncTask<String,Object,Integer>
{
    private String DB_URL,USER,PASS;
    private InfoBL ibl;
    private final Context context;
    private int branchId;
    private String query,query2;


    public InfoDAL(InfoBL ibl,Context context,int branchId) {
        this.ibl = ibl;
        this.context=context;
        this.branchId=branchId;
        DB_URL = DatabaseConstants.DB_URL;
        PASS= DatabaseConstants.PASS;
        USER=DatabaseConstants.USER;
        query= "SELECT CurrentQueue,TotalQueue,NumberOfClerks,AverageTime FROM Queue WHERE BusinessId = '" + branchId + "'";
    }

    // doInBackground Starts The Connection To The DB And Sends The Data In The Process.
    @Override
    protected Integer doInBackground(String... params) {
        int response = 0;
        try {
            boolean running = true;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            String result = "\nDatabase connection success\n";
            Statement st = con.createStatement();
            while(running) {
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    int currentQueue = rs.getInt("CurrentQueue");
                    int totalQueue = rs.getInt("TotalQueue");
                    int numOfClerks = rs.getInt("NumberOfClerks");
                    Time time=rs.getTime("AverageTime");
                    int numOfPeopleForAverage = totalQueue-currentQueue;
                    if (numOfPeopleForAverage<1)
                        numOfPeopleForAverage=0;

                    publishProgress(currentQueue,time,numOfPeopleForAverage,numOfClerks,totalQueue);
                }
                if(isCancelled())
                    running=false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
    protected void onProgressUpdate(Object...progress)
    {
        ibl.displayData(progress);
    }
}
