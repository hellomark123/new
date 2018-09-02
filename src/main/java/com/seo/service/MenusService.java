package com.seo.service;

import com.seo.config.properties.UserDefinedProperties;
import com.seo.entity.Menus;
import com.seo.mongo.MenuRepository;
import com.seo.utils.DateUtils;
import com.seo.utils.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MenusService {

    @Autowired
    private MenuRepository menuRepository;

//    @Autowired
//    private MenusMapper menusMapper;

    @Autowired
    private UserDefinedProperties userDefinedProperties;


    /**
     * 数据采集并处理
     */
    public Menus collect(String url) {

        Integer size;

        Menus menus = new Menus();

        List<StringBuffer> contentList = FileUtils.LIST_COUNTS;
        List<StringBuffer> titleList = FileUtils.LIST_TITLES;
        List<StringBuffer> keywordList = FileUtils.LIST_KEYWORDS;

        /**
         * id
         */
//        menus.setId(System.currentTimeMillis());

        /**
         * 日期,null默认两天
         */
        menus.setDate(DateUtils.getBeforRandomDays(null));

        /**
         * 标题
         */
        StringBuffer stringBuffer = new StringBuffer();
        size = titleList.size() - 1;
        StringBuffer title1 = titleList.get(RandomUtils.nextInt(0, size));
        StringBuffer title2 = titleList.get(RandomUtils.nextInt(0, size));
        stringBuffer.append(title1.substring(RandomUtils.nextInt(0, title1.length() - 1)));
        stringBuffer.append(title2.substring(RandomUtils.nextInt(0, title2.length() - 1)));
        menus.setTitles(stringBuffer.toString());

        /**
         * 内容content
         */
        List<StringBuffer> conentsBuffer = new ArrayList();
        List<String> conents = new ArrayList();
        size = titleList.size() - 1;
        Integer titlesSize = menus.getTitles().length() - 1;
        conentsBuffer.add(contentList.get(RandomUtils.nextInt(0, size - 1)));
        conentsBuffer.add(contentList.get(RandomUtils.nextInt(0, size - 1)));
        conentsBuffer.add(contentList.get(RandomUtils.nextInt(0, size - 1)));
        conentsBuffer.add(contentList.get(RandomUtils.nextInt(0, size - 1)));
        conentsBuffer.add(contentList.get(RandomUtils.nextInt(0, size - 1)));
        for (StringBuffer s : conentsBuffer) {
            if (s.indexOf("我") != -1) {
                s.insert(s.indexOf("我"), menus.getTitles().substring(RandomUtils.nextInt(0, titlesSize)));
                conents.add(s.toString());
                continue;
            } else if (s.indexOf("他") != -1) {
                s.insert(s.indexOf("他"), menus.getTitles().substring(RandomUtils.nextInt(0, titlesSize)));
                conents.add(s.toString());
                continue;
            } else if (s.indexOf(" ") != -1) {
                s.insert(s.indexOf(" "), menus.getTitles().substring(RandomUtils.nextInt(0, titlesSize)));
                conents.add(s.toString());
                continue;
            } else if (s.indexOf("的") != -1) {
                s.insert(s.indexOf("的"), menus.getTitles().substring(RandomUtils.nextInt(0, titlesSize)));
                conents.add(s.toString());
                continue;
            } else {
                conents.add(s.toString());
                continue;
            }
        }
        menus.setContent(conents);

        /**
         * KeyWord
         */
        size = keywordList.size() - 1;
        String keyWord = keywordList.get(RandomUtils.nextInt(0, size)).toString();
        menus.setKeyword(keyWord);

        /**
         * url
         */
        if (null == url || "".equals(url)) {
            String deurl = "/mean" + userDefinedProperties.getDeurl() + System.currentTimeMillis();
            menus.setUrl(deurl);
        } else {
            menus.setUrl(url);
        }

        return menus;

    }


    public List<Menus> selectAllWithLimitAndSort() {
        Pageable pageable = PageRequest.of(0, 200, new Sort(Sort.Direction.DESC, "date"));
        Page<Menus> menusPage = menuRepository.findAll(pageable);
        List<Menus> menusList = menusPage.getContent();
        menusList = getRandomList(menusList, userDefinedProperties.getUrlCount());
        return menusList;
    }


    public List getRandomList(List paramList,int count){
        if(paramList.size()<count){
            return paramList;
        }
        Random random=new Random();
        List<Integer> tempList=new ArrayList();
        List<Object> newList=new ArrayList();
        int temp=0;
        for(int i=0;i<count;i++){
            temp=random.nextInt(paramList.size());//将产生的随机数作为被抽list的索引
            if(!tempList.contains(temp)){
                tempList.add(temp);
                newList.add(paramList.get(temp));
            }
            else{
                i--;
            }
        }
        return newList;
    }
}
