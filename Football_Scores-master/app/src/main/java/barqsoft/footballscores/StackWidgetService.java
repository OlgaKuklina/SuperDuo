package barqsoft.footballscores;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StackWidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    private static class StackRemoteViewsFactory implements RemoteViewsFactory {
        private static final String TAG = StackRemoteViewsFactory.class.getSimpleName();
        private Context mContext;
        private ArrayList<WidgetMatchData> widgetMatchDatas;

        public StackRemoteViewsFactory(Context applicationContext, Intent intent) {
            this.mContext = applicationContext;
        }


        @Override
        public void onCreate() {
            widgetMatchDatas = getMatchData();
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return widgetMatchDatas.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            // Construct a remote views item based on the app widget item XML file,
            // and set the text based on the position.
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.football_scores_app_widget_item);
            rv.setTextViewText(R.id.home_name, widgetMatchDatas.get(position).getHomeTeamName());
            rv.setTextViewText(R.id.score_textview, widgetMatchDatas.get(position).getScore());
            rv.setTextViewText(R.id.data_textview, widgetMatchDatas.get(position).getDate());
            rv.setTextViewText(R.id.away_name, widgetMatchDatas.get(position).getAwayTeamName());
            rv.setImageViewResource(R.id.away_crest, Utilities.getTeamCrestByTeamName(widgetMatchDatas.get(position).getAwayTeamName()));
            rv.setImageViewResource(R.id.home_crest, Utilities.getTeamCrestByTeamName(widgetMatchDatas.get(position).getHomeTeamName()));

            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        private ArrayList<WidgetMatchData> getMatchData() {
            Uri uri = DatabaseContract.scores_table.buildScoreWithDate();
            ContentResolver resolver = mContext.getContentResolver();
            Date fragmentdate = new Date(System.currentTimeMillis());
            SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
            String dateFormatted = mformat.format(fragmentdate);

            Cursor cursor = resolver.query(uri, null, null, new String[]{dateFormatted}, null);

            ArrayList<WidgetMatchData> data = new ArrayList<>();
            if (cursor == null) {
                return data;
            }
            while (cursor.moveToNext()) {
                WidgetMatchData widgetMatchData = new WidgetMatchData(cursor.getString(ScoresAdapter.COL_HOME), cursor.getString(ScoresAdapter.COL_AWAY), cursor.getString(ScoresAdapter.COL_HOME_GOALS) + "-" + cursor.getString(ScoresAdapter.COL_AWAY_GOALS), cursor.getString(ScoresAdapter.COL_DATE));
                if (widgetMatchData.getScore() != null && !widgetMatchData.getScore().equals("-1--1")) {
                    data.add(widgetMatchData);
                }
            }
            cursor.close();
            return data;
        }

    }
}
