package booklistingapp.jd.com.newsapp;

import java.util.HashMap;
import java.util.List;

/**
 * this class represent news from the guardians
 */

public class NewsResult {
    private String id;
    private String webTitle;
    private String webUrl;
    private String webPublicationDate;
    private String type;
    private String sectionName;
    private String sectionId;


    public NewsResult(String id,
                      String sectionName,
                      String type,
                      String webPublicationDate,
                      String webTitle,
                      String webUrl,
                      String secId) {
        this.id = id;
        this.sectionName = sectionName;
        this.type = type;
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.sectionId = secId;
    }

    public String getId() {
        return id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getType() {
        return type;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }
}
