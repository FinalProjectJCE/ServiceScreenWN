package com.zakencorp.zaken.servicescreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainScreen extends ActionBarActivity {

    private InfoBL ibl;
    private int branchId;
    private ImageView qrCodeIV;
    private SharedPreferences.Editor editor;
    SharedPreferences sharedPrefQueue;
    private int userQueue;
    private TextView headerTV;
    QueueBL qbl;
    BusinessNameBL businessNameBL;
    private SweetAlertDialog pDialog;
    private String businessName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        headerTV=(TextView)findViewById(R.id.businessNameTV);
        Intent intent = getIntent();
        branchId = intent.getIntExtra("branchId",0);
        businessName=intent.getStringExtra("businessName");
        headerTV.setText(businessName);
        ibl=new InfoBL(this);
        ibl.getCurrentQueueAndWaitingClients(this, this, branchId);
        setQrCode(branchId);
        qbl=new QueueBL(this,branchId);
        businessNameBL=new BusinessNameBL(this,branchId);
        businessNameBL.startAsync();
        sharedPrefQueue = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        editor = sharedPrefQueue.edit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void start(View view)
    {
        qbl.startAsync();
    } // Get Queue Button Clicked, Start Calculating A Qeueu In Async

    @Override
    protected void onStop() {
        super.onStop();
        //ibl.task.cancel(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ibl=new InfoBL(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //ibl.task.cancel(true);
    }

    public void setQrCode(int branchId) // Sets The QR Code To Return The Business Id, And Displaying It On The Screen
    {
        qrCodeIV = (ImageView) findViewById(R.id.qrCodeIV);
        String qrData = ""+branchId;
        int qrCodeDimention = 300;

        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrData, null,
                Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeDimention);

        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            qrCodeIV.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }


    }



}
