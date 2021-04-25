package com.sm.smmap.smmap.lte_nr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetWorkNrReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(onReceive!=null){
            onReceive.onReceive(context, intent);
        }
    }
    OnReceive onReceive;
    interface OnReceive{
        void onReceive(Context context,Intent intent);
    }
    public void setOnReceive(OnReceive onReceive) {
        this.onReceive = onReceive;
    }
}
