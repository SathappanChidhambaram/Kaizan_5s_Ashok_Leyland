package com.example.kaizan5s;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.opencsv.CSVWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class question extends AppCompatActivity {



    TextView ttopic,tqn;
    EditText erem;
    ImageView img;

    RadioGroup rg;
    RadioButton rb1,rb2,rb3,rb4,rbid;

    int selectedId;
    Bitmap imgbit;

    String sbit,obit;

    Button go;

   final SimpleDateFormat time = new SimpleDateFormat("yyyy/MM/dd_hh:mm:ss", Locale.getDefault());

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    FirebaseStorage storage;
    StorageReference storageReference;

    private Uri filePath;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public ArrayList<String> atopic;

    int n,m,c,parentc;
    String key,rbval,sqn,title;
    String syear,smonth,sweek,ofi;
    String getzone;

    Bitmap defbit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        pref = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        editor = pref.edit();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        img=findViewById(R.id.img);
        //Resources res = getContext().getResources();
        //img.setImageResource(R.drawable.event_image);
        defbit = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.event_image);
       // img.setImageBitmap(defbit);
        imgbit=defbit;
       // defbit = ((BitmapDrawable)img.getDrawable()).getBitmap();

        atopic=new ArrayList<>();


        ttopic=findViewById(R.id.ttopic);
        tqn=findViewById(R.id.tqn);

        erem=findViewById(R.id.erem);

        go=findViewById(R.id.go);

        rg=findViewById(R.id.rg);
        rb1=findViewById(R.id.rb1);
        rb2=findViewById(R.id.rb2);
        rb3=findViewById(R.id.rb3);
        rb4=findViewById(R.id.rb4);

        myRef.child("questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //long value = dataSnapshot.getChildrenCount();
               // atopic=new ArrayList<>((int)dataSnapshot.getChildrenCount());
                parentc=(int)dataSnapshot.getChildrenCount();
                for(DataSnapshot d1:dataSnapshot.getChildren())
                {
                    n=pref.getInt("qn",1);
                    m=pref.getInt("subqn",1);
                    if(d1.getKey().contains(n+"s"))
                    {
                        key=d1.getKey();
                        title=d1.child("title").getValue(String.class);
                        ttopic.setText(title);
                        Log.e("subqn", "yes" + d1.child("subqn").child(m + "q").getValue(String.class));
                        c=(int)d1.child("subqn").getChildrenCount();
                        tqn.setText(d1.child("subqn").child(m + "q").getValue(String.class));
                        break;
                    }
                }
                //Log.e("size is",atopic.size()+"yes");
                //ttopic.setText(atopic.get(1));


            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("error is", "Failed to read value.", error.toException());
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getzone = pref.getString("zonenum", "");
                ofi=erem.getText().toString();
                selectedId = rg.getCheckedRadioButtonId();
                if (nonnull()) {





                    rbid = (RadioButton) findViewById(selectedId);
                    // if(selectedId!=-1)
                    rbval = rbid.getText().toString();
                    if (constraint()) {
//                else
//                    rbval="0";
//                        SimpleDateFormat year = new SimpleDateFormat("yyyy", Locale.getDefault());
//                        SimpleDateFormat month = new SimpleDateFormat("MM", Locale.getDefault());
//                        SimpleDateFormat date = new SimpleDateFormat("dd", Locale.getDefault());


                        syear = pref.getString("ayear","");
                        smonth = pref.getString("amonth","");
                        sweek=pref.getString("week","");
                      //  int idate = Integer.parseInt(date.format(new Date()));
//                        if (idate < 15) {
//                            sweek = "Week1";
//                        } else {
//                            sweek = "Week3";
//                        }

                        sqn = tqn.getText().toString().replace(".", "-");
                        sqn = sqn.replace("/", "_");

                        myRef = myRef.child("audit").child(syear).child(smonth).child(sweek).child(getzone).child("ans").child(key);
                        myRef.child("title").setValue(title);
                        myRef=myRef.child(sqn);
                        myRef.child("score").setValue(rbval);
                        myRef.child("notes").setValue(ofi);

                        if(!imgbit.equals(defbit))
                        {
                            uploadImage();
                        }

                      else
                        {
                            myRef = database.getReference();

                            if (n < parentc || m < c) {


                                if (m < c) {
                                    m++;
                                } else {
                                    m = 1;
                                    n = n + 1;
                                    editor.putInt("qn", n);
                                }
                                Log.e("need",n+" "+m);
                                editor.putInt("subqn", m);
                                editor.apply();
                                Intent intent = new Intent(question.this, question.class);
                                startActivity(intent);
                                // finish();
                            }
                            else {
                                String date=pref.getString("date","");
                                myRef=myRef.child("audit").child(syear).child(smonth).child(sweek).child(getzone);
                                myRef.child("date").setValue(date);

                                goreport();

                            }

//                                Intent intent = new Intent(question.this, home.class);
//                                startActivity(intent);
                                //finish();
                            }
                        }
                    }
                }
        });




    }

    public Boolean nonnull()
    {


        if(selectedId==-1)
        {
            Toast.makeText(question.this,"Evaluation Score Needed",Toast.LENGTH_SHORT).show();
            return false;

        }

        return true;
    }

    public Boolean constraint()
    {
        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
        sbit=bitmap.toString();
        obit=defbit.toString();
        if(Integer.parseInt(rbval)<3 && ofi.isEmpty())
        {
            erem.setError("OFI required for this score");
            return false;
        }
        if(Integer.parseInt(rbval)<3 && imgbit.equals(defbit))
        {
            Toast.makeText(question.this,"Image required for this score",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds options to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
    public void selectImage(View V) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(question.this);
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
                    img.setImageBitmap(bitmap);
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
                img.setImageBitmap(thumbnail);
            }

            filePath=data.getData();
            Log.e("path",filePath+" ");
        }
    }

    private void uploadImage()
    {
        final Date date= Calendar.getInstance().getTime();
        String img="beforeimage_"+time.format(date);
        Log.e("img",img);
        final StorageReference sRef = storageReference.child(img);

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
                        myRef.child("beforeimage").setValue(downloadUrl.toString());
                        myRef = database.getReference();

                    }
                });





                if (n < parentc || m < c) {


                    if (m < c) {
                        m++;
                    } else {
                        m = 1;
                        n = n + 1;
                        editor.putInt("qn", n);
                    }
                    editor.putInt("subqn", m);
                    editor.apply();
                    Intent intent = new Intent(question.this, question.class);
                    startActivity(intent);
                    // finish();
                } else {
                    pref.getString("date","");
                    myRef=myRef.child("audit").child(syear).child(smonth).child(sweek).child(getzone);
                    myRef.child("date").setValue(date);

                    goreport();
//                    Intent intent = new Intent(question.this, home.class);
//                    startActivity(intent);
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

    public void goreport()
    {

        myRef = database.getReference();
        AlertDialog.Builder builderrep=new AlertDialog.Builder(question.this);
        builderrep.setMessage("Rep") .setTitle("REPORT");
        builderrep.setMessage("Do you need the report for this audit?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myRef.child("audit").child(syear).child(smonth).child(sweek).child(getzone).addValueEventListener(new ValueEventListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                try {
                                    //String name1=name.getText().toString();
                                    //String no1=no.getText().toString();
                                    /**
                                     * Values to be added from the firebase to the following variables
                                     */
                                    String GembaNo=dataSnapshot.getKey();
                                    String auditeeName=dataSnapshot.child("auditee").getValue(String.class);
                                    String auditorName=dataSnapshot.child("auditor").getValue(String.class);
                                    String date=dataSnapshot.child("date").getValue(String.class);

                                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                                    String time = sdf.format(new Date());

                                    ArrayList<String> score=new ArrayList<>();
                                    ArrayList<String> ofi=new ArrayList<>();
                                    dataSnapshot=dataSnapshot.child("ans");
                                    for(DataSnapshot d1:dataSnapshot.getChildren())
                                    {
                                        for(DataSnapshot d2:d1.getChildren())
                                        {
                                            score.add(d2.child("score").getValue(String.class));
                                            String tofi=d2.child("notes").getValue(String.class);
                                            if(tofi!=null)
                                                ofi.add(tofi);
                                            else
                                                ofi.add("NULL");
                                        }
                                    }

                                    String Titlerow1="Assembly Internal Audit CHeck List";
                                    String Gembarow1="GEMBA NO:"+GembaNo;
                                    String auditeeRow2="Auditee:"+auditeeName;
                                    String auditorRow2="Audit Conducted By:"+auditorName;
                                    String daterow3="Date:"+date;
                                    String timerow3="Time:"+time;
                                    String Evaluationrow4="Evaluation Criteria";
                                    String Scorerow4="Evaluation Score";
                                    String ofirow4="Opportunity  for improvement";
                                    String Steprow4="Step";

                                    File file=new File("/sdcard/audit");
                                    file.mkdir();
                                    String timeStamp = new android.icu.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                                    String csv="/sdcard/audit/audit_"+timeStamp+".csv";
                                    CSVWriter csvWriter=new CSVWriter(new FileWriter(csv));

                                    //String row[]=new String[]{name1,no1};
                                    String row1[]=new String[]{Titlerow1,Gembarow1};
                                    csvWriter.writeNext(row1);
                                    String row2[]=new String[]{auditeeRow2,auditorRow2};
                                    csvWriter.writeNext(row2);
                                    String row3[]=new String[]{daterow3,timerow3};
                                    csvWriter.writeNext(row3);
                                    String row4[]=new String[]{Steprow4,Evaluationrow4,Scorerow4,ofirow4};
                                    csvWriter.writeNext(row4);
                                    String row5[]=new String[]{"Sort-","1.1 adherence of Red tag system as per Red tag process flow",score.get(0),ofi.get(0)};
                                    csvWriter.writeNext(row5);
                                    String row6[]=new String[]{"Sort-","1.2 Elimination of Non value added Activities : wrong fitment /Excess Issues",score.get(1),ofi.get(1)};
                                    csvWriter.writeNext(row6);
                                    String row7[]=new String[]{"Sort-","1.3 Preventive methods for generation of Rework /parts spillage",score.get(2),ofi.get(2)};
                                    csvWriter.writeNext(row7);
                                    String row8[]=new String[]{"Set in order-","2.1 Reserved Seat methods : Production parts ,non production parts ,equipments ",score.get(3),ofi.get(3)};
                                    csvWriter.writeNext(row8);
                                    String row9[]=new String[]{"Set in order-","2.2  Visual Communication : process Signages /Information signages",score.get(4),ofi.get(4)};
                                    csvWriter.writeNext(row9);
                                    String row10[]=new String[]{"Set in order-","2.3 Search time reduction :Evidence  Pokeyoke /Innovative Visual control /Agility,EEI  implemenation",score.get(5),ofi.get(5)};
                                    csvWriter.writeNext(row10);
                                    String row11[]=new String[]{"Shine-","3.1 Dirt free manchines & Equipments, surrounding area, walk ways, toilets, periphery",score.get(6),ofi.get(6)};
                                    csvWriter.writeNext(row11);
                                    String row12[]=new String[]{"Shine-","3.2 Abnormality identification : Fitment missing ,wrong fitment ,orientation mismatch,sequence mismatch ",score.get(7),ofi.get(7)};
                                    csvWriter.writeNext(row12);
                                    String row13[]=new String[]{"Shine-","3.3 Aggregate Rework system/oil wastage /storage system",score.get(8),ofi.get(8)};
                                    csvWriter.writeNext(row13);
                                    String row14[]=new String[]{"Standardization-","4.1 New models Assy process,Improvements  incorporated and documented.(Red tag reg.,SOP,WIS,OCP)",score.get(9),ofi.get(9)};
                                    csvWriter.writeNext(row14);
                                    String row15[]=new String[]{"Standardization-","4.2 standard for visual displays & controls are  local language and simple to understand .",score.get(10),ofi.get(10)};
                                    csvWriter.writeNext(row15);
                                    String row16[]=new String[]{"Standardization-","4.3 Evidence of Standards are tried ,tested,trained ,clearely understood by users",score.get(11),ofi.get(11)};
                                    csvWriter.writeNext(row16);
                                    String row17[]=new String[]{"Standardization-","4.4 Evidence of Operators contribution to development of SOP's",score.get(12),ofi.get(12)};
                                    csvWriter.writeNext(row17);
                                    String row18[]=new String[]{"Standardization-","4.5 Evidence of review focus only on revision of standards - Number, reasons, benefits etc",score.get(13),ofi.get(13)};
                                    csvWriter.writeNext(row18);
                                    String row19[]=new String[]{"Standardization-","4.6 Evidence of Visual Display OPL and  FPP adherence ",score.get(14),ofi.get(14)};
                                    csvWriter.writeNext(row19);
                                    String row20[]=new String[]{"Sustain-","5.1 Reqular /Refresh Training Consistency-Every body trained & follow 5S standards. Make it as habit. ",score.get(15),ofi.get(15)};
                                    csvWriter.writeNext(row20);
                                    String row21[]=new String[]{"Sustain-","5.2 Evidence of linking 5S with Daily work activities. ",score.get(17),ofi.get(17)};
                                    csvWriter.writeNext(row21);
                                    String row22[]=new String[]{"Sustain-","5.3 Evidence of self audit practices ,5S Internal Audits and OFI identified status",score.get(18),ofi.get(18)};
                                    csvWriter.writeNext(row22);
                                    String row23[]=new String[]{"Sustain-","5.4 Evidence of regular internal Dept. reviews and remedial meausres to improve /sustain 5S improvements",score.get(19),ofi.get(19)};
                                    csvWriter.writeNext(row23);
                                    String row24[]=new String[]{"Sustain-","5.5 Evidence of Document control system exists and effective",score.get(20),ofi.get(20)};
                                    csvWriter.writeNext(row24);
                                    String row25[]=new String[]{"Sustain-","5.6 Users/operators are clear & able to explain their SOP's and link  5s process parameters with quality of work",score.get(21),ofi.get(21)};
                                    csvWriter.writeNext(row25);
                                    String row26[]=new String[]{"Sustain-","5.7 Involvement in Kaizen,JDI,APS,BPS,SGA  and Suggestion schemes on 5S (Indi./group Participation)",score.get(22),ofi.get(22)};
                                    csvWriter.writeNext(row26);
                                    String row27[]=new String[]{"Sustain-","5.8 Evidence of increase in number of  5S projects in the areas of PQCDSME",score.get(23),ofi.get(23)};
                                    csvWriter.writeNext(row27);
                                    String row28[]=new String[]{"Sustain-","5.9 Evidence of recognition in Internal / external competitions",score.get(24),ofi.get(24)};
                                    csvWriter.writeNext(row28);
                                    String row29[]=new String[]{"Sustain-","5.10 Benefits of the above activity in - QCC, Kaizen, terms of PQCDSME.( Year wise )",score.get(16),ofi.get(16)};
                                    csvWriter.writeNext(row29);






                                    csvWriter.close();
                                    Toast.makeText(question.this,"File SuccessFully Created",Toast.LENGTH_LONG).show();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }



                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Intent intent = new Intent(question.this, home.class);
                        startActivity(intent);
                    }
                });
        //Creating dialog box
        AlertDialog alert = builderrep.create();
        //Setting the title manually
        alert.setTitle("REPORT");
        alert.show();
    }

}
