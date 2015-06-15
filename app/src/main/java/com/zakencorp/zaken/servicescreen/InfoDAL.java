package com.zakencorp.zaken.servicescreen;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Zaken on 31/05/2015.
 */
public class InfoDAL extends AsyncTask<String,Integer,Integer>
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
        query= "SELECT CurrentQueue FROM Queue WHERE BusinessId = '" + branchId + "'";
        query2 = "SELECT TotalQueue FROM Queue WHERE BusinessId = '" + branchId + "'";
    }

    @Override
    protected Integer doInBackground(String... params) {
        int response = 0;
        try {
            boolean running = true;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            String result = "\nDatabase connection success\n";
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            while(running) {
                ResultSet rs = st.executeQuery(query);
                ResultSet rs2 = st2.executeQuery(query2);
                while (rs.next()&& rs2.next()) {
                    int currentQueue = rs.getInt("CurrentQueue");
                    int totalQueue = rs2.getInt("TotalQueue");
                    publishProgress(currentQueue,totalQueue);
                }
                if(isCancelled())
                    running=false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    protected void onProgressUpdate(Integer...progress)
    {
        ibl.displayData(progress[0],progress[1]);
    }

}
