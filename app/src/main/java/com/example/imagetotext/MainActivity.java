package com.example.imagetotext;

import static java.lang.Class.forName;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class MainActivity extends AppCompatActivity {

    EditText mResultEt;
    ImageView mPreviewIv;

    //mysql database
    TextView text,errorText;
    Button show;
    Button import_data;

    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    String[] cameraPermission;
    String[] storagePermission;

    Connection connect;
    String ConnectionResult="";


    StringBuilder student_name=new StringBuilder();
    //SparseArray<TextBlock> it=recognizer.detect(frame);
    StringBuilder register_no=new StringBuilder();
    //  SparseArray<TextBlock> it=recognizer.detect(frame);
    StringBuilder project_title=new StringBuilder();
    //SparseArray<TextBlock> it=recognizer.detect(frame);
    StringBuilder project_guide=new StringBuilder();



    Uri image_uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setSubtitle("Click + button to insert image");

        mResultEt = findViewById(R.id.resultEt);
        mPreviewIv=findViewById(R.id.imageIv);

        cameraPermission=new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        TextView s_name=(TextView)findViewById(R.id.editStudentName);

        TextView r_no=(TextView)findViewById(R.id.editRegisterNumber);

        TextView p_title=(TextView)findViewById(R.id.editProjectTitle);

        TextView p_guide=(TextView)findViewById(R.id.editProjectGuide);


        Button insert=(Button)findViewById(R.id.button);
        Button viewDb=(Button)findViewById(R.id.button2);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connection connection = connectionclass();
                try {
                    if (connection != null) {
//                        String sqlinsert = "Insert into test values ('" + s_name.getText().toString() + "','" + r_no.getText().toString() + "','" + p_title.getText().toString() + "','" + p_guide.getText().toString() + "')";
                        String sqlinsert = "Insert into test values ('" + student_name.toString() + "','" + register_no.toString() + "','" + project_title.toString() + "','" + project_guide.toString() + "')";

                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlinsert);
                    }
                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }

            }
        });

        viewDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tx1=(TextView)findViewById(R.id.textView);
                TextView tx3=(TextView)findViewById(R.id.textView3);
                TextView tx4=(TextView)findViewById(R.id.textView4);
                TextView tx5=(TextView)findViewById(R.id.textView5);

                Connection connect=connectionclass();

                try{
                    String txt_S=new String();
                    String txt_S2=new String();
                    String txt_S3=new String();
                    String txt_S4=new String();
                    txt_S="";
                    txt_S2="";
                    txt_S3="";
                    txt_S4="";
                    if(connect!=null)
                    {
                        String query="Select * from test";
                        Statement str=connect.createStatement();
                        ResultSet res=str.executeQuery(query);
                        int i=1;



                        while(res.next()) {
                            if(i==1) {
                                txt_S = txt_S.concat(res.getString(1));
                                txt_S=txt_S.concat(" ");
                                txt_S = txt_S.concat(res.getString(2));
                                txt_S=txt_S.concat(" ");
                                txt_S = txt_S.concat(res.getString(3));
                                txt_S=txt_S.concat(" ");
                                txt_S = txt_S.concat(res.getString(4));
                                txt_S=txt_S.concat("\n");
                                i++;

                            }


                         else if(i==2){
                            txt_S2=txt_S2.concat(res.getString(1));
                                txt_S2=txt_S2.concat(" ");
                            txt_S2=txt_S2.concat(res.getString(2));
                                txt_S2=txt_S2.concat(" ");
                            txt_S2=txt_S2.concat(res.getString(3));
                                txt_S2=txt_S2.concat(" ");
                            txt_S2=txt_S2.concat(res.getString(4));
                                txt_S2=txt_S2.concat("\n");
                                i++;

                          }
                         else if(i==3){
                                txt_S3=txt_S3.concat(res.getString(1));
                                txt_S3=txt_S3.concat(" ");
                                txt_S3=txt_S3.concat(res.getString(2));
                                txt_S3=txt_S3.concat(" ");
                                txt_S3=txt_S3.concat(res.getString(3));
                                txt_S3=txt_S3.concat(" ");
                                txt_S3=txt_S3.concat(res.getString(4));
                                txt_S3=txt_S3.concat("\n");
                                i++;

                            }
                         else if(i==4){
                                txt_S4=txt_S4.concat(res.getString(1));
                                txt_S4=txt_S4.concat(" ");
                                txt_S4=txt_S4.concat(res.getString(2));
                                txt_S4=txt_S4.concat(" ");
                                txt_S4=txt_S4.concat(res.getString(3));
                                txt_S4=txt_S4.concat(" ");
                                txt_S4=txt_S4.concat(res.getString(4));
                                txt_S4=txt_S4.concat("\n");
                                i++;
                            }


                        }
                    }
                    else
                    {
                        ConnectionResult="Check Connection";
                    }
                    tx1.setText(txt_S);
                    tx3.setText(txt_S2);
                    tx4.setText(txt_S3);
                    tx5.setText(txt_S4);
                }catch(Exception ex)
                {
                    Log.e("Error", ex.getMessage());
                }
            }
        });

    }


    @SuppressLint("NewApi")
    public Connection connectionclass() {
        Connection con = null;
        String ip = "192.168.70.36", port = "1433", username = "muthu", password = "akileshhselika", databasename = "testDatabase";
        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String connectionUrl = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databasename=" + databasename + ";User=" + username + ";password=" + password + ";";
            con = DriverManager.getConnection(connectionUrl);
        } catch (Exception exception) {
            Log.e("Error", exception.getMessage());
        }
        return con;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.addImage){
            showImageImportDialog();
        }
        if(id==R.id.settings){
            Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showImageImportDialog() {
        String[] items={"Camera","Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Select Image");
        dialog.setItems(items, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else {
                        pickCamera();
                    }
                }
                if(which==1){
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else {
                        pickGallery();
                    }
                }
            }
        });
        dialog.create().show();
    }

    private void pickGallery() {
        Intent intent=new Intent(Intent.ACTION_PICK);

        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private void pickCamera() {
        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"NewPic");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image To Text");
        image_uri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE);

    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result= ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted) {
                        pickCamera();
                    } else {
                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted) {
                        pickGallery();
                    } else {
                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);

            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                CropImage.activity(image_uri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);

            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                mPreviewIv.setImageURI(resultUri);

                BitmapDrawable bitmapDrawable = (BitmapDrawable) mPreviewIv.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();

                if (!recognizer.isOperational()) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();
                    int c=0;
                    for (int i = 0; i < items.size(); i++) {
                        TextBlock myItem = items.valueAt(i);
                        sb.append(myItem.getValue());
                        sb.append(c++);
                        sb.append("\n");
                    }
                    //new modifications
                  SparseArray<TextBlock> it=recognizer.detect(frame);


//                    TextBlock my_tr=it.valueAt(5);
//                    student_name.append(my_tr.getValue());

//                    student_name.append("\n");
//                    student_name.append(student_name.toString());
//                    String nope="Student Name";
//                    String yusop=student_name.toString();

//                    if(nope.equals(yusop))
//                        student_name.append("\nWorks");
//                    else
//                        student_name.append("\nNOt works");
//                    mResultEt.setText(student_name.toString());

                   for(int i=0;i<it.size();i++)
                    {
                        TextBlock my_it=it.valueAt(i);
                        StringBuilder test_str=new StringBuilder();
                        test_str.append(my_it.getValue());
                        if(test_str.toString().equals("Student Name"))
                        {
                            TextBlock my_it1=it.valueAt(i+1);
                            student_name.append(my_it1.getValue());
                        }
                        else if(test_str.toString().equals("Register Number"))
                        {
                            TextBlock my_it2=it.valueAt(i+1);
                            register_no.append(my_it2.getValue());
                        }
                        else if(test_str.toString().equals("Project Title"))
                        {
                            TextBlock my_it3=it.valueAt(i+1);
                            project_title.append(my_it3.getValue());
                        }
                        else if(test_str.toString().equals("Project Guide"))
                        {
                            TextBlock my_it4=it.valueAt(i+1);
                            project_guide.append(my_it4.getValue());
                        }
                    }
//                    sb.append("\n");
//                    sb.append("Name:");
//                    sb.append(student_name);
//                    sb.append("\n");
//                    sb.append("NO:");
//                    sb.append(register_no);
//                    sb.append("\n");
//                    sb.append("project_title:");
//                    sb.append(project_title);
//                    sb.append("\n");
//                    sb.append("Guide_Name:");
//                    sb.append(project_guide);
                    mResultEt.setText(sb.toString());
                    TextView tx6=(TextView)findViewById(R.id.textView6);
                    TextView tx7=(TextView)findViewById(R.id.textView7);
                    TextView tx10=(TextView)findViewById(R.id.textView10);
                    TextView tx11=(TextView)findViewById(R.id.textView11);
                    tx6.setText(student_name);
                    tx7.setText(register_no);
                    tx10.setText(project_title);
                    tx11.setText(project_guide);

                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();

            }
        }
    }
}