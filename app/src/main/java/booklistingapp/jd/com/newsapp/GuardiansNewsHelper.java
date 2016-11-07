package booklistingapp.jd.com.newsapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Googlebooks Api helpers go construct
 */

public class GuardiansNewsHelper {

    private static final String SEARCH_QUERY_PRE_FIX
            = "http://content.guardianapis.com/search";

    private static final String API_KEY = "5463b4ff-8c6f-4f81-a636-b1d583ad7834";

    public static final String NFL_SEARCH_TERM = "NFL";

    private static final String STATUS_OK_KEY = "ok";
    private static final String STATUS_BAD_KEY = "bad";
    private static final String STATUS_KEY = "status";
    private static final String RESULTS_KEY = "results";

    private static final String ID_KEY = "id";
    private static final String TYPE_KEY = "type";
    private static final String SECTION_ID = "sectionId";
    private static final String SECTION_NAME = "sectionName";
    private static final String WEB_PUBLICATION_DATE = "webPublicationDate";
    private static final String WEB_TITLE_KEY = "webTitle";
    private static final String WEB_URL_KEY = "webUrl";
    private static final String RESPONSE_KEY = "response";
    private static final String NA_KEY = "N/A";


    public static URL buildSearhCmd(String term) throws MalformedURLException {
        Uri baseUri = Uri.parse(SEARCH_QUERY_PRE_FIX);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("q",term.trim());
        uriBuilder.appendQueryParameter("api-key",API_KEY);
        return new URL(uriBuilder.toString());
    }

    public static List<NewsResult> parseJsonResponeFromgGoogleBooks(String response)
            throws JSONException {

        List<NewsResult> resultList = new ArrayList<>();

        if (response == null){
            return resultList;
        }


        JSONObject jsonResponse = new JSONObject(response);
        // get the array of book items;
        JSONObject jsonResponseItem = jsonResponse.getJSONObject(RESPONSE_KEY);

        // check response status
        String status = jsonResponseItem.has(STATUS_KEY)
                            ? jsonResponseItem.getString(STATUS_KEY) : STATUS_BAD_KEY;

        // if status is not OK also return empty list
        if (!status.equals(STATUS_OK_KEY)){
            Log.e(AppConsts.TAG, "Status is  NOT OK  "+status);
            return resultList;
        }

        // get JSON results here
        JSONArray jsonResults = jsonResponseItem.getJSONArray(RESULTS_KEY);

        // go through each book and parse it
        for (int i = 0; i < jsonResults.length(); i++) {
            NewsResult resBook = parseResults(jsonResults.getJSONObject(i));
            if (resBook != null) {
                Log.d(AppConsts.TAG, "Add news Results to list " + resBook.getWebTitle());
                resultList.add(resBook);
            }
        }

        return resultList;
    }


    public static NewsResult parseResults(JSONObject resultItem) throws JSONException {
        
        String idStr = resultItem.has(ID_KEY) ? resultItem.getString(ID_KEY) : NA_KEY;
        String typeStr = resultItem.has(TYPE_KEY) ? resultItem.getString(TYPE_KEY) : NA_KEY;
        String sectionIdStr = resultItem.has(SECTION_ID) ? resultItem.getString(SECTION_ID) : NA_KEY;
        String sectionNameStr = resultItem.has(SECTION_NAME) ?
                                        resultItem.getString(SECTION_NAME) : NA_KEY;
        String webPublicDateStr = resultItem.has(WEB_PUBLICATION_DATE)
                                    ? resultItem.getString(WEB_PUBLICATION_DATE) : NA_KEY;
        String webTitleStr = resultItem.has(WEB_TITLE_KEY) ?
                                        resultItem.getString(WEB_TITLE_KEY) : NA_KEY;
        String webUrlStr = resultItem.has(WEB_URL_KEY) ? resultItem.getString(WEB_URL_KEY) : NA_KEY;

        return new NewsResult(
            idStr,sectionNameStr,typeStr,webPublicDateStr,webTitleStr,webUrlStr, sectionIdStr
        );

    }



}
