package com.zakencorp.zaken.servicescreen;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;



    public class UsersDAL extends AsyncTask<String,Object,Integer> {
        private  UsersBL ubl;
        private  Context context;

        private String query;
        private String idForDB,cityForDB,businessForDB,branchForDB,DB_URL,USER,PASS ,latitudeForDB,longitudeForDB,distanceForDB ;
        private String userName;
        private int userPassword;

        public UsersDAL(UsersBL activity,Context context,int userPassword,String userName){


            this.ubl=activity;
            this.context=context;
            this.userName=userName;
            this.userPassword=userPassword;
            query = "SELECT "+DatabaseConstants.MAIN_ID+" FROM "+DatabaseConstants.USERS+
                    " WHERE "+DatabaseConstants.USER_NAME+" = '" + userName + "' and " +DatabaseConstants.USER_PASSWORD+" = '" + userPassword + "'";
            idForDB=DatabaseConstants.MAIN_ID;
            DB_URL = DatabaseConstants.DB_URL;
            PASS= DatabaseConstants.PASS;
            USER=DatabaseConstants.USER;
        }

        protected Integer doInBackground(String... sqlQ) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                Log.d("After Execute", " " );
                Log.d("After Execute", " " );
                if (!rs.next())
                    publishProgress(0);
                else {
                    int businessID = rs.getInt(idForDB);

                    publishProgress(businessID);
                    Log.d("Business ID Is", " " + businessID);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return 1;
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);

            ubl.UUU(context,values);
        }




    }


