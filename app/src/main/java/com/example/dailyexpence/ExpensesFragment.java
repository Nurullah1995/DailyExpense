package com.example.dailyexpence;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExpensesFragment extends Fragment {
private  RecyclerView expenseRV;
private List<Expense> expenseList=new ArrayList<>();
private ExpenseAdapter expenseAdapter;
    List<String> expenseTypes=new ArrayList<>();
    DatabaseHelper helper;
    TextView fromDateTv,toDateTv;
    public ExpensesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_expenses, container, false);
        helper=new DatabaseHelper(view.getContext());
        expenseRV=view.findViewById(R.id.expenseRV);
        expenseRV.setLayoutManager(new LinearLayoutManager(view.getContext()));
        expenseAdapter=new ExpenseAdapter(expenseList);
        expenseRV.setAdapter(expenseAdapter);
fromDateTv=view.findViewById(R.id.fromDateTv);
toDateTv=view.findViewById(R.id.toDateTv);

        expenseTypes.add(0,"Select expense type");
        expenseTypes.add("Electricity Bill");
        expenseTypes.add("Transport Cost");
        expenseTypes.add("Lunch");
        expenseTypes.add("Dinner");
       // expenseList.add(new Expense(1,"Electricity Bill",500.00,"18 Feb 2019","",R.drawable.ic_document));
       // expenseList.add(new Expense(2,"Transport Cost",50.00,"18 Feb 2019","",R.drawable.ic_document));
       // expenseList.add(new Expense(3,"Lunch",80.00,"18 Feb 2019","",R.drawable.ic_document));
       // expenseAdapter.notifyDataSetChanged();
        loadData();

        FloatingActionButton expEntryFABtn=view.findViewById(R.id.expEntryFABtn);

        expEntryFABtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                getActivity().setTitle("Add Expense");
                ft.replace(getActivity().findViewById(R.id.mainFrame).getId(),new ExpenseEntryFragment(null));
                ft.commit();
            }
        });

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
        Spinner spinner=view.findViewById(R.id.expenseTypeSpn);



        ArrayAdapter<String> adapter=new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,expenseTypes);

        //adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //if(parent.getItemAtPosition(position).equals("Select expense type")) {
                // }
                long itemId=parent.getItemIdAtPosition(position);
                Toast.makeText(view.getContext(), ""+String.valueOf(itemId), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return view;
    }
    private void loadData() {
        Cursor cursor=helper.viewData();

        while (cursor.moveToNext())
        {
            Double amount=Double.valueOf(cursor.getString(cursor.getColumnIndex("Amount")));
            String date=cursor.getString(cursor.getColumnIndex("Date"));
            String time=cursor.getString(cursor.getColumnIndex("Time"));
            String document=cursor.getString(cursor.getColumnIndex("Document"));
            int typeId=cursor.getInt(cursor.getColumnIndex("TypeId"));
            int id=cursor.getInt(cursor.getColumnIndex("Id"));
           // Toast.makeText(getActivity(), String.valueOf(typeId), Toast.LENGTH_SHORT).show();
           String type=  expenseTypes.get(typeId);
            expenseList.add(new Expense(id,typeId,type,amount,date,time,document));
            expenseAdapter.notifyDataSetChanged();
        }

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
}
