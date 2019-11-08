package com.example.dailyexpence;


import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    TextView fromDateTv,toDateTv;
    int itemId;
    long milisecFrom;
    long milisecTo;
    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dashboard, container, false);
        Spinner spinner=view.findViewById(R.id.expenseTypeSpn);
        List<String> expenseTypes=new ArrayList<>();
        expenseTypes.add(0,"Select expense type");
        expenseTypes.add("Electricity Bill");
        expenseTypes.add("Transport Cost");
        expenseTypes.add("Lunch");

        ArrayAdapter<String> adapter=new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,expenseTypes);

        //adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //if(parent.getItemAtPosition(position).equals("Select expense type")) {
                // }
               itemId=(int)parent.getItemIdAtPosition(position);
              //  Toast.makeText(view.getContext(), ""+String.valueOf(itemId), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fromDateTv=view.findViewById(R.id.fromDateTv);
        toDateTv=view.findViewById(R.id.toDateTv);
        fromDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerFrom();


            }
        });
        toDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerTo();
            }
        });

        TextView summaryTv=view.findViewById(R.id.summaryTv);
        summaryTv.setText(loadSummary(view));
        return view;
    }


    private void openDatePickerFrom() {

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

                        fromDateTv.setText(dateFormat.format(date));
                         milisecFrom = date.getTime();


                    }
                };

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),dateSetListener,year,month,day);
        datePickerDialog.show();

    }

    private void openDatePickerTo() {

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

                        toDateTv.setText(dateFormat.format(date));
                         milisecTo = date.getTime();
                    }
                };

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),dateSetListener,year,month,day);
        datePickerDialog.show();

    }

    private String loadSummary(View view) {
        DatabaseHelper helper=new DatabaseHelper(view.getContext());
        Cursor cursor=helper.viewData();
        Double amount=0.00;


        while (cursor.moveToNext())
        {
          ////int typeid=  Integer.valueOf(cursor.getString(cursor.getColumnIndex("TypeId")));

          //  String expsenseDate = cursor.getString(cursor.getColumnIndex("Date"))+" 00:00:00";

           // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

           // Date date = null;

          //  try {
           //     date = dateFormat.parse(expsenseDate);
           // } catch (ParseException e) {
           //     e.printStackTrace();
           // }


        //  Long  milisecExp = date.getTime();

         // if(typeid==itemId)
        //  {
              amount+=Double.valueOf(cursor.getString(cursor.getColumnIndex("Amount")));
        //  }

        }
       // Toast.makeText(getActivity(), String.valueOf(amount), Toast.LENGTH_SHORT).show();
return String.valueOf(amount);
    }


}
