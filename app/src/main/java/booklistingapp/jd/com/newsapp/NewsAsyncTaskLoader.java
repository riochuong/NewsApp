package booklistingapp.jd.com.newsapp;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import booklistingapp.jd.com.booklistingapp.R;

/**
 * Asynctask loader helps to load the data
 */

public class NewsAsyncTaskLoader extends AsyncTaskLoader<LoaderResultsObj> {

    public NewsAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public LoaderResultsObj loadInBackground() {
        String response = null;
        List<NewsResult> listBook = null;
        LoaderResultsObj resultObj = null;
        URL url;
        boolean isNetworkDisconnected = false;
        try {

            if (!HttpConnectionHelper.isConnectToInternet(getContext())) {
                resultObj = new LoaderResultsObj(true, new ArrayList<NewsResult>());
                Log.e(AppConsts.TAG, "Device is not connected to the Internet");
                return resultObj;
            }

            url = GuardiansNewsHelper.buildSearhCmd(GuardiansNewsHelper.NFL_SEARCH_TERM);
            response =
                        HttpConnectionHelper.makeHttpRequest(url, AppConsts.GET_REQUEST_TYPE);

            // call parsing method to help parse this ...
            listBook =
                    GuardiansNewsHelper.parseJsonResponeFromgGoogleBooks(response);

            resultObj = new LoaderResultsObj(false,listBook);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),
                    getContext().getString(R.string.ioexception_error_toast),
                    Toast.LENGTH_LONG)
                    .show();
            Log.e(AppConsts.TAG, "IO exception please check network connection");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(AppConsts.TAG, "Failed to parse the JSON response from server");
        }
        return resultObj;
    }
}
