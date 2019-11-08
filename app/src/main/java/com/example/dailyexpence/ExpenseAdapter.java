package com.example.dailyexpence;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.List;
import androidx.fragment.app.Fragment;
public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    List<Expense> expenseList;

    public ExpenseAdapter(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_model_item_design,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
    final Expense expense=expenseList.get(position);
holder.typeTV.setText(expense.getType());
holder.dateTV.setText(expense.getDate());
holder.amountTV.setText(expense.getAmount().toString());

        holder.textViewOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(view.getContext(), holder.textViewOptions);
                //inflating menu from xml resource
                popup.inflate(R.menu.action_options);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                               // FragmentTransaction ft=view.getContext().getSupportFragmentManager().beginTransaction();
                                Bundle bundle = new Bundle();
                                bundle.putInt("id",expense.getId());
                                bundle.putInt("typeId",expense.getTypeId());
                                bundle.putString("type",holder.typeTV.getText().toString());
                                bundle.putString("date",holder.dateTV.getText().toString());
                                bundle.putString("time",expense.getTime());
                                bundle.putString("amount",holder.amountTV.getText().toString());
                                bundle.putString("document",expense.getDocument());
                                //ExpenseEntryFragment expenseEntryFragment = new ExpenseEntryFragment();
                              //  expenseEntryFragment.setArguments(bundle);
                              //  Toast.makeText(view.getContext(), bundle.get("type").toString(), Toast.LENGTH_SHORT).show();
                                ((MainActivity)view.getContext()).setTitle("Update Expense");
                                ((MainActivity)view.getContext()).getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.mainFrame, new ExpenseEntryFragment(bundle))
                                        .commit();
                                return true;
                            case R.id.menu2:
                                //handle menu2 click
                                DatabaseHelper helper=new DatabaseHelper(view.getContext());
                             int deletedRow=   helper.deleteData(expense.getId());
                                Toast.makeText(view.getContext(), ""+deletedRow, Toast.LENGTH_SHORT).show();
                                expenseList.remove(position);
                                notifyDataSetChanged();

                                return true;

                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putInt("id",expense.getId());
                bundle.putInt("typeId",expense.getTypeId());
                bundle.putString("type",holder.typeTV.getText().toString());
                bundle.putString("date",holder.dateTV.getText().toString());
                bundle.putString("time",expense.getTime());
                bundle.putString("amount",holder.amountTV.getText().toString());
                bundle.putString("document",expense.getDocument());

BottomSheetExpense bottomSheetExpense=new BottomSheetExpense(bundle);

bottomSheetExpense.show(((MainActivity)v.getContext()).getSupportFragmentManager(),"expense");
            }
        });

    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView typeTV,dateTV,amountTV,textViewOptions;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            typeTV=itemView.findViewById(R.id.typeTV);
            dateTV=itemView.findViewById(R.id.dateTV);
            amountTV=itemView.findViewById(R.id.amountTV);
            textViewOptions=itemView.findViewById(R.id.textViewOptions);
        }
    }
}
