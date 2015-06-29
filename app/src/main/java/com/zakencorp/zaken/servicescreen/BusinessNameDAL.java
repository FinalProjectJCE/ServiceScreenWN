
/**
 * Created by Zaken on 28/06/2015.
 */

package com.zakencorp.zaken.servicescreen;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import android.content.SharedPreferences;





public class BusinessNameDAL extends AsyncTask<String,Object,Integer> {
    private UsersBL ubl;
    private Context context;
    private TextView businessNameTV;
    private String query;
    private String idForDB,DB_URL,USER,PASS ;
    private BusinessNameBL bnbl;


    public BusinessNameDAL(BusinessNameBL bnbl,Context context, int businessID){
        this.context=context;
        this.bnbl=bnbl;
        businessNameTV = (TextView)((Activity)context).findViewById(R.id.businessNameTV);
        query ="SELECT BusinessName,City,Branch FROM Queue WHERE BusinessId = '" + businessID + "'";
        idForDB=DatabaseConstants.MAIN_ID;
        DB_URL = DatabaseConstants.DB_URL;
        PASS= DatabaseConstants.PASS;
        USER=DatabaseConstants.USER;
    }

    // doInBackground Starts The Connection To The DB And Sends The Data In The Process
    protected Integer doInBackground(String... params) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next())
            {
                String businessName=rs.getString("BusinessName");
                String city=rs.getString("City");
                String branch=rs.getString("Branch");
                publishProgress(businessName,branch,city);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    // onProgressUpdate Called In doInBackground
    protected void onProgressUpdate(Object...values) {
        super.onProgressUpdate(values);
        bnbl.setName(values); // Sends The Data To BL Class

    }




}


