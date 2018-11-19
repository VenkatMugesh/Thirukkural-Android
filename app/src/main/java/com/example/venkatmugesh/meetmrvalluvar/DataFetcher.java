package com.example.venkatmugesh.meetmrvalluvar;

import android.util.Log;

public class DataFetcher {
    public static String _kuralNo = new String();
    public static String _Line1 = new String();
    public static String _Line2 = new String();
    public static String _trans = new String();
    public static int count = 0;

    public void put_KuralNo(String no){
        Log.i("no" , no);
        if(_kuralNo != null) {
            this._kuralNo = no;
        }
    }
    public String get_kuralno(){
        Log.i("kueralno" , this._kuralNo);
        return this._kuralNo;
    }
    public void put_Line1(String Line1){
        this._Line1 = Line1;
    }
    public String get_Line1(){
        return this._Line1;
    }
    public void put_Line2(String Line2){
        this._Line2 = Line2;
    }
    public String get_Line2(){
        return this._Line2;
    }
    public void put_trans(String trans){
        this._trans = trans;
    }
    public String get_trans(){
        return this._trans;
    }

    public void setCount(int cnt){
        this.count = cnt;
    }public int putCount(){
        return this.count;
    }


}
