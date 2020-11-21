package com.example.mymobil.setting;

/**
 * Create by Jinyeob on 2020. 04. 23
 */
class item_list {
    private int settingIcon;
    private String settingName;
    private String settingExplain;

    item_list(int settingIcon, String settingName, String settingExplain){
        this.settingIcon = settingIcon;
        this.settingName = settingName;
        this.settingExplain = settingExplain;
    }

    int getSettingIcon()
    {
        return this.settingIcon;
    }

    String getSettingName()
    {
        return this.settingName;
    }

    String getSettingExplain()
    {
        return this.settingExplain;
    }

}
