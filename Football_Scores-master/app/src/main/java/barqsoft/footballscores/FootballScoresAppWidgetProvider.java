package barqsoft.footballscores;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import barqsoft.footballscores.service.FetchService;

/**

 */
public class FootballScoresAppWidgetProvider extends AppWidgetProvider {
    private static final String LOG_TAG = FootballScoresAppWidgetProvider.class.getSimpleName();
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intent service_start = new Intent(context, FetchService.class);
        service_start.putExtra("appWidgetIds", appWidgetIds);
        context.startService(service_start);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
       final int N = appWidgetIds.length;
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

//        CharSequence widgetText = FootballScoresAppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.football_scores_app_widget);

        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        // When intents are compared, the extras are ignored, so we need to embed the extras
        // into the data so that the extras will not be ignored.
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.widget_view, intent);
        views.setEmptyView(R.id.widget_view, R.id.empty_view);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(intent.getAction() != null && intent.getAction().equals("barqsoft.footballscores.REDRAW_WIDGET")) {
            int[] extraArray = intent.getIntArrayExtra("appWidgetIds");
            if (extraArray != null) {
                // There may be multiple widgets active, so update all of them
                final int N = extraArray.length;
                for (int i = 0; i < N; i++) {
                    updateAppWidget(context, AppWidgetManager.getInstance(context), extraArray[i]);
                }
            }
        }
    }
    private void requestData() {

}

}