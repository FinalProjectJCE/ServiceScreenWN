package com.zakencorp.zaken.servicescreen;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends ActionBarActivity { // Login Page Of The Application
    private Button loginButton;
    private EditText userNameET,passwordET;
    private UsersBL ubl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userNameET=(EditText)findViewById(R.id.userNameET);
        passwordET=(EditText)findViewById(R.id.passwordET);
    }

    public void loginClicked(View view)
    {
        String userName = userNameET.getText().toString();
        if(userName.equals("")
           || passwordET.getText().toString().equals(""))
        {
            Toast.makeText(this, "חובה להזין שם משתמש וסיסמא", Toast.LENGTH_SHORT).show();
        }
        else
        {
            int userPassword = Integer.parseInt(passwordET.getText().toString());
            ubl = new UsersBL();
            ubl.getBusinessID(this,this,userPassword,userName);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
