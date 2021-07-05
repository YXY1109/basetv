package com.kjsc.basetv;

import android.app.Application;

import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //屏幕适配
        AutoSizeConfig.getInstance().getUnitsManager().setSupportDP(false).setSupportSubunits(Subunits.PT);
    }
}
