package com.lawerance.scvision.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;

import com.lawerance.scvision.Models.gallery;

import java.util.ArrayList;


public class UpdateUIWidgetService extends IntentService {
    public static final String ACTION_UPDATE_FOOD = "android.appwidget.action.APPWIDGET_UPDATE";
public ArrayList<gallery> galleries;
    public UpdateUIWidgetService(String name) {
        super(name);
    }

    public UpdateUIWidgetService() {
        super("UpdateUIWidgetService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null && intent.getAction().equals(ACTION_UPDATE_FOOD))
        {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, TestsWidget.class));
            TestsWidget.updateAppWidget(this, appWidgetManager, appWidgetIds);
        }
    }
}
