package com.lawerance.scvision.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.lawerance.scvision.Custom_Camera_Activity;
import com.lawerance.scvision.R;


public class TestsWidget extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int []appWidgetIds) {


        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, Custom_Camera_Activity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.tests_widget);
            views.setOnClickPendingIntent(R.id.start_scanning, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }




    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);


    }


    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

