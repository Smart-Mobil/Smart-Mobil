package com.example.mymobil.record;

/**
 * Create by Jinyeob on 2020. 04. 23
 */
class item_list {
    private String recordname;


    private String recordDate;

    item_list(String recordname,String recordDate){
        this.recordname = recordname;
        this.recordDate=recordDate;
    }


    public String getRecordName()
    {
        return this.recordname;
    }

    public String getRecordDate() {
        return this.recordDate;
    }

}
