package booklistingapp.jd.com.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import booklistingapp.jd.com.booklistingapp.R;

/**
 * Created by chuondao on 11/6/16.
 */

public class NewsArrayAdapter extends ArrayAdapter {

    public NewsArrayAdapter(Context context, List<NewsResult> listBook) {
        super(context, 0, listBook);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // check if convert view is reusuable or not
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.news_item_layout,null);
        }

        // set the data
        NewsResult news = (NewsResult) getItem(position);
        setBookData(convertView,news);
        return convertView;
    }

    /**
     * helper to set all the correct news data
     * @param itemLayout
     */
    private void setBookData (View itemLayout, NewsResult news){
        TextView webTitleText = (TextView) itemLayout.findViewById(R.id.web_title_text);
        TextView sectionText = (TextView) itemLayout.findViewById(R.id.section_text);
        TextView publicationDateText = (TextView) itemLayout.findViewById(R.id.publication_date_text);
        TextView typeText = (TextView) itemLayout.findViewById(R.id.type_text);


        webTitleText.setText(news.getWebTitle());
        sectionText.setText(news.getSectionName());
        publicationDateText.setText(news.getWebPublicationDate());
        typeText.setText(news.getType());


    }
}
