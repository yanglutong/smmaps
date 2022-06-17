package com.sm.smmap.smmap.lte_nr;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sm.smmap.smmap.R;

import java.util.ArrayList;

public class My4GAdapter extends RecyclerView.Adapter {
    private ArrayList<To4GBean> arrayList;
    private Context context;

    public My4GAdapter(ArrayList<To4GBean> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
       /* for (int i = 0; i <arrayList.size() ; i++) {
            if(i==1){
                arrayList.remove(1);
            }
        }*/
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.recy_item_4g, null);
        MyViewHolder holder = new MyViewHolder(inflate);
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.re_band.setText(arrayList.get(position).getLteBand());
        myViewHolder.re_nr_arfcn.setText(arrayList.get(position).getLteEarfac());
        myViewHolder.re_pci.setText(arrayList.get(position).getLtePci());
        myViewHolder.re_rsrp.setText(arrayList.get(position).getLteRsrp());
        myViewHolder.re_rsrq.setText(arrayList.get(position).getLteRsrq());
        myViewHolder.re_sinr.setText(arrayList.get(position).getLteRssi());

//        //动态添加textview线
//        if (position < arrayList.size() - 1) {
//            LinearLayout linearLayout = holder.itemView.findViewById(R.id.recycler_Line);
//            TextView textView = new TextView(context);
//            textView.setBackgroundResource(R.color.colorMyr);
//            textView.setHeight(1);
//            textView.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//            linearLayout.addView(textView);
//        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView re_band;
        private final TextView re_nr_arfcn;
        private final TextView re_pci;
        private final TextView re_rsrp;
        private final TextView re_rsrq;
        private final TextView re_sinr;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            re_band = itemView.findViewById(R.id.re_band);
            re_nr_arfcn = itemView.findViewById(R.id.re_NR_ARFCN);
            re_pci = itemView.findViewById(R.id.re_PCI);
            re_rsrp = itemView.findViewById(R.id.re_RSRP);
            re_rsrq = itemView.findViewById(R.id.re_RSRQ);
            re_sinr = itemView.findViewById(R.id.re_SINR);
        }
    }
}
