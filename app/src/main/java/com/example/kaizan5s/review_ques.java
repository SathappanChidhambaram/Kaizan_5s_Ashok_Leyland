package com.example.kaizan5s;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class review_ques extends AppCompatActivity {

    TextView rtopic,rqn,rofi;

    ImageView img,addimg;

    Button next;

    RadioButton rb;

    final SimpleDateFormat time = new SimpleDateFormat("yyyy/MM/dd_hh:mm:ss", Locale.getDefault());

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    FirebaseStorage storage;
    StorageReference storageReference;


//    final SimpleDateFormat dateFormat= new SimpleDateFormat("MM", Locale.getDefault());
//    Date cdate= Calendar.getInstance().getTime();

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    int c,parentc;
    String key,ckey,gemba,bimgurl;

    int score;

    String year,month;
    String week;
    int itopic,iqn;

    Bitmap defbit,imgbit;

    String firstc,secondc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_ques);

        pref=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        editor=pref.edit();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        rtopic=findViewById(R.id.rtopic);
        rqn=findViewById(R.id.rqn);
        rofi=findViewById(R.id.rofi);

        img=findViewById(R.id.img);
        addimg=findViewById(R.id.addimg);

        rb=findViewById(R.id.rb);

        next=findViewById(R.id.next);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        defbit = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.event_image);
        imgbit=defbit;

        week=pref.getString("week","");
        year=pref.getString("ayear","");
        month=pref.getString("amonth","");


        gemba=pref.getString("zonenum","");
       // Log.e("key",year+month+week+gemba);
        myRef.child("audit").child(year).child(month).child(week).child(gemba).child("ans").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                itopic=pref.getInt("stopic",1);
                parentc=(int)dataSnapshot.getChildrenCount();
                Log.e("parentc",parentc+" ");
                Log.e("itopic",itopic+" ");
                for(DataSnapshot d1:dataSnapshot.getChildren())
                {
                   // Log.e("key",d1.getKey());


                   // Log.e("c",c+" ");
                    if(d1.getKey().contains(itopic+"s"))
                    {

                        //Log.e("keyt",d1.getKey());
                        firstc=d1.getKey();
                        c=(int)d1.getChildrenCount()-1;
                        rtopic.setText(d1.child("title").getValue(String.class));
                        iqn=pref.getInt("sqn",1);
                        Log.e("c",c+" ");
                        Log.e("iqn",iqn+" ");
                        for(DataSnapshot d2:d1.getChildren())
                        {
                            key=d2.getKey();

                            if(key.contains(itopic+"-"+iqn))
                            {
                                secondc=key;
                                ckey=key.replace("-",".");
                                ckey=ckey.replace("_","/");
                                if(d2.child("beforeimage").getValue(String.class)!=null) {
                                    bimgurl=d2.child("beforeimage").getValue(String.class);
                                    Glide.with(review_ques.this).load(bimgurl).into(img);
                                }

                                rqn.setText(ckey);
                                String sc=d2.child("score").getValue(String.class);
                                rb.setText(sc);
                                rofi.setText(d2.child("notes").getValue(String.class));
                                score=Integer.parseInt(sc);
                                break;

                            }
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isneed())
                {
                    Toast.makeText(review_ques.this,"After Image needed for this score",Toast.LENGTH_SHORT).show();
                }
                else
                    {
                        if(!defbit.equals(imgbit))
                        {

                            uploadImage();
                        }
                        else {
                            myRef = database.getReference();

                            if (itopic < parentc || iqn < c) {


                                if (iqn < c) {
                                    Log.e("ctrl",iqn+" "+c);
                                    iqn++;
                                } else {
                                    iqn = 1;
                                    itopic = itopic + 1;
                                    editor.putInt("stopic", itopic);
                                }
                                editor.putInt("sqn", iqn);
                                editor.apply();
                                Intent intent = new Intent(review_ques.this, review_ques.class);
                                startActivity(intent);
                                // finish();
                            } else {
                                Intent intent = new Intent(review_ques.this, home.class);
                                startActivity(intent);
                                //finish();
                            }

                        }
                }
            }
        });

    }

    private boolean isneed()
    {
        if(score<3 && defbit.equals(imgbit))
        {
            return true;
        }
        return false;
    }


    public void selectImage(View V) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(review_ques.this);
        builder.setTitle("Add Photo...");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    addimg.setImageBitmap(bitmap);
                    imgbit=bitmap;
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.e("img path from gallery", picturePath+"");
                imgbit=thumbnail;
                addimg.setImageBitmap(thumbnail);
            }

            //filePath=data.getData();
            //Log.e("path",filePath+" ");
        }
    }

    private void uploadImage()
    {
        Date date= Calendar.getInstance().getTime();
        String simg="afterimage_"+time.format(date);
        Log.e("img",simg);
        final StorageReference sRef = storageReference.child(simg);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imgbit.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = sRef.putBytes(data);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                progressDialog.dismiss();
                sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final Uri downloadUrl = uri;
                        Log.e("link",downloadUrl+"yes");
                        myRef=myRef.child("audit").child(year).child(month).child(week).child(gemba).child("ans").child(firstc).child(secondc);
                        myRef.child("afterimage").setValue(downloadUrl.toString());
                        myRef = database.getReference();

                    }
                });

                if (itopic < parentc || iqn < c)
                {



                    if (iqn < c) {
                        Log.e("ctrlimg",iqn+" "+c);
                        iqn++;
                    } else {
                        iqn = 1;
                        itopic = itopic + 1;
                        editor.putInt("stopic", itopic);
                    }
                    editor.putInt("sqn", iqn);
                    editor.apply();
                    Intent intent = new Intent(review_ques.this, review_ques.class);
                    startActivity(intent);
                    // finish();
                }
                else
                {
                    Intent intent = new Intent(review_ques.this, home.class);
                    startActivity(intent);
                    //finish();
                }


            }
        }).addOnProgressListener(
                new OnProgressListener<UploadTask.TaskSnapshot>() {

                    // Progress Listener for loading
                    // percentage on the dialog box
                    @Override
                    public void onProgress(
                            @NonNull UploadTask.TaskSnapshot taskSnapshot)
                    {
                        double progress
                                = (100.0
                                * taskSnapshot.getBytesTransferred()
                                / taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage(
                                "Uploaded "
                                        + (int)progress + "%");
                    }
                });


    }


}
