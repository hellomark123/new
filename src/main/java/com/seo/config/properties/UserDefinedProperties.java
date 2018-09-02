package com.seo.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "mark.seo")
public class UserDefinedProperties {

    public String url;

    public String deurl;

    public Map<String, String> referer = new HashMap<>();

    public Integer timer;

    public String domain;

    public Integer urlCount;

    public String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getUrlCount() {
        if (urlCount == null || urlCount == 0 || urlCount < 0) {
            urlCount = 40;
        }
//        else if (urlCount % 2 != 0) {
//            urlCount += 1;
//        }
        return urlCount;
    }

    public void setUrlCount(Integer urlCount) {
        this.urlCount = urlCount;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getTimer() {
        if (timer == null) {
            timer = 20;
        }
        return timer;
    }

    public void setTimer(Integer timer) {
        this.timer = timer;
    }

    public Map<String, String> getReferer() {
        if (referer.get("baidu") == null || "".equals(referer.get("baidu"))) {
            referer.put("baidu", ".baidu.com");
        }
        if (referer.get("sogou") == null || "".equals(referer.get("sogou"))) {
            referer.put("sogou", ".sogou.com");
        }
        if (referer.get("360") == null || "".equals(referer.get("360"))) {
            referer.put("360", ".so.com");
        }
        if (referer.get("bing") == null || "".equals(referer.get("bing"))) {
            referer.put("bing", ".bing.com");
        }
        return referer;
    }

    public void setReferer(Map<String, String> referer) {
        this.referer = referer;
    }

    public String getDeurl() {
        return deurl;
    }

    public void setDeurl(String deurl) {
        this.deurl = deurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
