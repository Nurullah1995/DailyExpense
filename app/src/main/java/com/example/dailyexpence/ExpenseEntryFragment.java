package com.example.dailyexpence;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExpenseEntryFragment extends Fragment {
    Bundle bundle;
    public  ExpenseEntryFragment(Bundle bundle)
    {
        this.bundle=bundle;
    }
    ImageView showDocument;
    Button addDocument;
    int id,typeId;String amount;String date,time,document;
    DatabaseHelper helper;
    EditText amountEt,dateEt,timeEt;

    Button insertBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_expense_entry, container, false);
        helper=new DatabaseHelper(view.getContext());
        Spinner spinner=view.findViewById(R.id.expenseTypeSpn);


        List<String> expenseTypes=new ArrayList<>();
        expenseTypes.add(0,"Select expense type");
        expenseTypes.add("Electricity Bill");
        expenseTypes.add("Transport Cost");
        expenseTypes.add("Lunch");

        ArrayAdapter<String> adapter=new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,expenseTypes);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //if(parent.getItemAtPosition(position).equals("Select expense type")) {
               // }
                    long itemId=parent.getItemIdAtPosition(position);
                    typeId=(int) itemId;
                  //  Toast.makeText(view.getContext(), ""+String.valueOf(itemId), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        amountEt=view.findViewById(R.id.amountEt);
        dateEt=view.findViewById(R.id.dateEt);
        timeEt=view.findViewById(R.id.timeEt);
        insertBtn=view.findViewById(R.id.insertBtn);
        showDocument=view.findViewById(R.id.documentIv);
        addDocument=view.findViewById(R.id.insertDoc);
        //Bundle bundle = getArguments();
        if(bundle !=null){
            id=bundle.getInt("id");
            //dateEt.setText(bundle.getString("date"));
            amountEt.setText(bundle.getString("amount"));
            dateEt.setText(bundle.getString("date"));
            timeEt.setText(bundle.getString("time"));

            int typeId=bundle.getInt("typeId");
            spinner.setSelection(typeId);
        Bitmap bmp=  decodeBase64(bundle.getString("document"));
            showDocument.setImageBitmap(bmp);
            Toast.makeText(getActivity(), String.valueOf(typeId), Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
        }
        dateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
openDatePicker();
            }
        });

timeEt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        openTimePicker();
    }
});
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = amountEt.getText().toString().trim() == "" ? "0.00" : amountEt.getText().toString().trim();
                date = dateEt.getText().toString().trim();
                time = timeEt.getText().toString().trim();
                // showDocument.invalidate();
                showDocument.buildDrawingCache();
                Bitmap bitmap = showDocument.getDrawingCache();
                document = encodeTobase64(bitmap);

                if(bundle==null) {

                    // Toast.makeText(view.getContext(), amount, Toast.LENGTH_SHORT).show();
                    Long lastRow = helper.insertData(typeId, amount, date, time, document);
                    Toast.makeText(view.getContext(), "Inserted row no " + lastRow, Toast.LENGTH_SHORT).show();
                }
                else
                {
                   // Toast.makeText(view.getContext(), "row no " +id, Toast.LENGTH_SHORT).show();
                    // Toast.makeText(view.getContext(), amount, Toast.LENGTH_SHORT).show();
                  int lastRow = helper.updateData(id,typeId, amount, date, time, document);
                    Toast.makeText(view.getContext(), "Updated row no " + lastRow, Toast.LENGTH_SHORT).show();
                }
            }
        });
        addDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });

        showDocument.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent,1);
                return false;
            }

        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == -1){
            if(requestCode == 0){
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                showDocument.setImageBitmap(bitmap);


            }else
            if(requestCode == 1){
                Uri uri= data.getData();
                showDocument.setImageURI(uri);
            }



        }
    }
    private void openTimePicker() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getLayoutInflater().inflate(R.layout.custom_time_picker,null);

        Button doneBtn = view.findViewById(R.id.doneBtn);
        final TimePicker timePicker = view.findViewById(R.id.timePicker);

        builder.setView(view);

        final Dialog dialog= builder.create();
        dialog.show();

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss aa");

                @SuppressLint({"NewApi", "LocalSuppress"}) int hour = timePicker.getHour();
                @SuppressLint({"NewApi", "LocalSuppress"}) int min = timePicker.getMinute();


                Time time = new Time(hour,min,0);

                timeEt.setText(timeFormat.format(time));
                dialog.dismiss();





            }
        });

    }

    private void openDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener =
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        month = month +1 ;

                        String currentDate = year+"-"+ month +"-"+ day +" 00:00:00";

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        Date date = null;

                        try {
                            date = dateFormat.parse(currentDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        dateEt.setText(dateFormat.format(date));
                        long milisec = date.getTime();
                    }
                };

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),dateSetListener,year,month,day);
        datePickerDialog.show();

    }
    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

        return imageEncoded;
    }
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

}
