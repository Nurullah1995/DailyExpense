package com.example.dailyexpence;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetExpense extends BottomSheetDialogFragment {
    Bundle bundle;
ImageView documentIv;
TextView amountTV,dateTV,timeTV,typeTV;
    public BottomSheetExpense(Bundle bundle) {
        this.bundle = bundle;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.bottom_sheet_expense,container,false);
        documentIv=view.findViewById(R.id.documentIv);
        amountTV=view.findViewById(R.id.amountTV);
        dateTV=view.findViewById(R.id.dateTV);
        timeTV=view.findViewById(R.id.timeTV);
        typeTV=view.findViewById(R.id.typeTV);

if(bundle!=null)
{
    amountTV.setText(bundle.getString("amount"));
    dateTV.setText(bundle.getString("date"));
    timeTV.setText(bundle.getString("time"));
    typeTV.setText(bundle.getString("type"));

    Bitmap bmp=  decodeBase64(bundle.getString("document"));
    documentIv.setImageBitmap(bmp);

}
        return view;
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

}
