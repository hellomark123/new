package com.seo.config.filter;

import com.seo.NewApplication;
import com.seo.common.BaseController;
import com.seo.config.properties.UserDefinedProperties;
import com.seo.utils.FileUtils;
import com.seo.utils.security.Base64Util;
import com.seo.utils.security.MD5Util;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@WebFilter(filterName = "filterFirst", urlPatterns = "/mean/*")
public class FilterFirst extends BaseController implements Filter {

    private static String AAA;

    @Autowired
    private UserDefinedProperties userDefinedProperties;

    @Override
    public void init(FilterConfig filterConfig) {
        FileUtils.put();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (RandomUtils.nextInt(0, 50) == 2) {
            if (AAA == null) {
                AAA = userDefinedProperties.getKey();
            }
            if (MD5Util.encode(Base64Util.encode(FileUtils.getHostIp().getBytes())).equals(AAA)) {
                System.out.println("通过授权");
            } else {
                NewApplication.context.close();
            }
        }

        HttpServletRequest httpServletRequest = getHttpServletRequest();

        String domain = userDefinedProperties.getDomain();
        request.setAttribute("domain", domain);

        Map<String, String> map = userDefinedProperties.getReferer();
        String baidu = map.get("baidu");
        String sougou = map.get("sogou");
        String so = map.get("360");
        String bing = map.get("bing");
        /**
         * 判断用户referer，从什么页面跳转过来
         */
        String referer = httpServletRequest.getHeader("referer");
        if(null != referer && baidu != null && !"".equals(baidu) && referer.trim().contains(baidu)){
            System.out.println("+++++++正常页面请求+++++++");
            request.setAttribute("referer", "baidu");
            chain.doFilter(request, response);
        }if(null != referer && sougou != null && !"".equals(sougou) && referer.trim().contains(sougou)){
            System.out.println("+++++++正常页面请求+++++++");
            request.setAttribute("referer", "sougou");
            chain.doFilter(request, response);
        }if(null != referer && so != null && !"".equals(so) && referer.trim().contains(so)){
            System.out.println("+++++++正常页面请求+++++++");
            request.setAttribute("referer", "so");
            chain.doFilter(request, response);
        }if(null != referer && bing != null && !"".equals(bing) && referer.trim().contains(bing)){
            System.out.println("+++++++正常页面请求+++++++");
            request.setAttribute("referer", "bing");
            chain.doFilter(request, response);
        }else{
            System.out.println("+++++++盗链+++++++");
            request.setAttribute("referer", "other");
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
