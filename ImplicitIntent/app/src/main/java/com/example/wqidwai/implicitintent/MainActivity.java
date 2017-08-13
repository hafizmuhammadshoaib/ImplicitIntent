package com.example.wqidwai.implicitintent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    final int Photo_Intent_Simple = 1000;
    final int Photo_Intent_With_FileName = 1001;
    final int Video_Intent = 1002;
    Uri _photoFileUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

    public void sendSimpleText(View v)
    {
        Intent sTextIntent = new Intent(Intent.ACTION_SEND);
        sTextIntent.putExtra("sms_body", "This is Test Text");
        sTextIntent.setType("text/plain");

        Intent chooser = Intent.createChooser(sTextIntent, "Please Select");

        if (sTextIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);

        }
    }

    public void makeCall(View v){
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:03478085761"));
        startActivity(callIntent);
    }

    public void showWebPage(View view){
        //indicating the nature of the intent (to display something to the user) using the ACTION_VIEW option
        //indicating to the Android intent resolution system that the activity is
        //requesting that a web page be displayed

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://cnn.com"));
        startActivity(intent);
    }


    public void showTimeAction(View view){

        Intent oIntent = new Intent("com.example.wqidwai.action.LOG_TIME");
        startActivity(oIntent);
    }

    public void showDateAction(View view){

        Intent oIntent = new Intent("com.example.wqidwai.action.LOG_DATE");
        startActivity(oIntent);
    }

    public void ImageCapture(View v)
    {
     //   Intent oIntent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      //  startActivityForResult(oIntent, Photo_Intent_Simple);

        _photoFileUri = generateTimeStampPhotoFileUri();
        if (_photoFileUri != null) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, _photoFileUri);
            startActivityForResult(intent, Photo_Intent_With_FileName);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras = null;
        Bitmap oBitmap = null;
        ImageView img = (ImageView)findViewById(R.id.imgView);
        switch (requestCode){
            case Photo_Intent_Simple:
                extras = data.getExtras();
                oBitmap = (Bitmap)extras.get("data");
                break;
            case Photo_Intent_With_FileName:
                oBitmap = BitmapFactory.decodeFile(_photoFileUri.getPath());
                break;
        }

if (oBitmap != null)
{
    img.setImageBitmap(oBitmap);
}
    }


    File getPhotoDirectory() {
        File outputDir = null;
        String externalStorageState = Environment.getExternalStorageState();
        if(externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
            File pictureDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            outputDir = new File(pictureDir, "cttc");
            if (!outputDir.exists()) {
                if(!outputDir.mkdirs()) {
                    Toast.makeText(this, "Failed to create directory: " + outputDir.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    outputDir = null;
                }
            }
        }

        return outputDir;
    }

    Uri generateTimeStampPhotoFileUri() {
        Uri photoFileUri = null;
        File outputDir = getPhotoDirectory();

        if(outputDir != null) {
            String timeStamp = new SimpleDateFormat("yyyyMMDD_HHmmss").format(new Date());
            String photoFileName = "IMG_" + timeStamp + ".jpg";

            File photoFile = new File(outputDir, photoFileName);
            photoFileUri = Uri.fromFile(photoFile);
        }


        return photoFileUri;
    }
//changes from gedit

//changes to check
    //changes in to shoaib branch


}
