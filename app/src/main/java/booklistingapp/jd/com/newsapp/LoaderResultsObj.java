package booklistingapp.jd.com.newsapp;

import java.util.List;

/**
 * will use this compound obj to reported the results
 */

public class LoaderResultsObj {

    List<NewsResult> newsListResult;
    boolean isNetworkError;

    public boolean isNetworkError() {
        return isNetworkError;
    }

    public List<NewsResult> getNewsListResult() {
        return newsListResult;
    }

    public LoaderResultsObj(boolean isNetworkError, List<NewsResult> newsListResult) {

        this.isNetworkError = isNetworkError;
        this.newsListResult = newsListResult;
    }
}
