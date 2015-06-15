package com.zakencorp.zaken.servicescreen;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Zaken on 05/06/2015.
 */
public class QueueDAL extends AsyncTask<String,Integer,Integer>
{
    private final QueueBL qbl;
    private int branchId;
    String DB_URL =DatabaseConstants.DB_URL;
    String USER = DatabaseConstants.USER;
    String PASS = DatabaseConstants.PASS;
    Context context;

    public QueueDAL(QueueBL qbl,int branchId,Context context) {
        this.branchId=branchId;
        this.qbl=qbl;
        this.context=context;
        Log.d("### OnQuwue DAL Cons()", "" );

    }

    @Override
    protected Integer doInBackground(String... sqlQ) {
        int response = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            String result = "\nDatabase connection success\n";
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            String query = "SELECT CurrentQueue FROM Queue WHERE BusinessId = '" + branchId + "'";
            String query2 = "SELECT TotalQueue FROM Queue WHERE BusinessId = '" + branchId + "'";
            String query3 ="UPDATE Queue SET TotalQueue = TotalQueue + 1 WHERE BusinessId = '" + branchId + "'";
            ResultSet rs = st.executeQuery(query);
            ResultSet rs2 = st2.executeQuery(query2);
            int rs3 = st3.executeUpdate(query3);
            while (rs.next()&& rs2.next()) {
                int currentQueue = rs.getInt("CurrentQueue");
                int totalQueue= rs2.getInt("TotalQueue");
                response = currentQueue;
                //System.out.println("\nSQLQ{1}"+sqlQ[1]+"\n");
                publishProgress(currentQueue,totalQueue);
                Log.d("QueueDAL Queue Is", ""+currentQueue );
                Log.d("QueueDAL Total Is", ""+totalQueue );


                Log.d("### DoInBackrou Cons()", "" );
            }

            Log.d(result, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    protected void onProgressUpdate(Integer... progress) {
        Log.d("onProgres CurrentQueue",""+progress[0] );
        Log.d("onProgressUpdate Total",""+progress[1] );

        qbl.setUserQueue(progress[0] + (progress[1] - progress[0]) + 1,context);
        //currentQueueDisplay_in_queue.setText(Integer.toString(progress[0]));
    }
}