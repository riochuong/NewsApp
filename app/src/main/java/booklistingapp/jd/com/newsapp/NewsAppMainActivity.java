package booklistingapp.jd.com.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import booklistingapp.jd.com.booklistingapp.R;

public class NewsAppMainActivity extends AppCompatActivity implements
        View.OnClickListener, LoaderCallbacks<LoaderResultsObj>, AdapterView.OnItemClickListener {

    /* contains search term */
    Button searchBtn = null;
    ListView newsReultsListView = null;
    View emptyView = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_listing);
        searchBtn = (Button) findViewById(R.id.refresh_btn);
        searchBtn.setOnClickListener(this);
        emptyView = getLayoutInflater().inflate(R.layout.empty_list_view,null,false);

        setEmptyViewText(getString(R.string.search_info_txt));
        newsReultsListView = (ListView) findViewById(R.id.news_list_view);
        ((ViewGroup) newsReultsListView.getParent()).addView(emptyView,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        newsReultsListView.setEmptyView(emptyView);
        newsReultsListView.setAdapter(
                new NewsArrayAdapter(this, new ArrayList<NewsResult>())
        );
        newsReultsListView.setOnItemClickListener(this);
        getSupportLoaderManager().initLoader(0,null,this);
    }

    /**
     * helper for dynamically set text on empty view
     * @param text
     */
    private void setEmptyViewText(String text)
    {
        if (emptyView != null){
            TextView emptyViewText = (TextView) emptyView.findViewById(R.id.empty_text_view);
            emptyViewText.setText(text);
        }

    }

    /* implements search btn on click*/
    @Override
    public void onClick(View view) {

        // implements refresh button to force reload
        getSupportLoaderManager().initLoader(0,null,this).forceLoad();

    }


    @Override
    public Loader<LoaderResultsObj> onCreateLoader(int id, Bundle args) {
            return new NewsAsyncTaskLoader(NewsAppMainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<LoaderResultsObj> loader, LoaderResultsObj loaderResult) {
        // set the data for adapter
        Log.d(AppConsts.TAG, "Load data finished  ");

        if (loaderResult == null){
            setEmptyViewText(getString(R.string.no_found));
            Toast.makeText(NewsAppMainActivity.this,
                    getString(R.string.no_found),
                    Toast.LENGTH_LONG).show();
            return;
        }

        // after receiving list book let's publish it
        // through array Adapter
        List<NewsResult> newsList = loaderResult.getNewsListResult();
        if (newsList != null && newsList.size() > 0) {
            newsReultsListView.setAdapter(
                    new NewsArrayAdapter(NewsAppMainActivity.this, newsList));
        } else {
            // show toast indicate we cannot find any book in the list
            newsReultsListView.setAdapter(new NewsArrayAdapter(
                    NewsAppMainActivity.this, new ArrayList<NewsResult>()
            ));
            if (loaderResult.isNetworkError()){
                setEmptyViewText(getString(R.string.network_connection_error));
                Toast.makeText(NewsAppMainActivity.this,
                        getString(R.string.network_connection_error),
                        Toast.LENGTH_LONG).show();
            }
            else{

                setEmptyViewText(getString(R.string.no_found));
                Toast.makeText(NewsAppMainActivity.this,
                        getString(R.string.no_found),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<LoaderResultsObj> loader) {
            Log.d(AppConsts.TAG, "Loader get reset !! ");
            newsReultsListView.setAdapter(new NewsArrayAdapter(this, new ArrayList<NewsResult>()));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        NewsResult selectedItem  = (NewsResult) newsReultsListView.getAdapter().getItem(i);
        Intent viewUrlAction = new Intent(Intent.ACTION_VIEW);
        viewUrlAction.setData(Uri.parse(selectedItem.getWebUrl()));
        startActivity(viewUrlAction);
    }
}
