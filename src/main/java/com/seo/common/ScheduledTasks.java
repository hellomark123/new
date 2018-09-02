package com.seo.common;

import com.seo.NewApplication;
import com.seo.config.properties.UserDefinedProperties;
import com.seo.entity.Menus;
import com.seo.mongo.MenuRepository;
import com.seo.service.MenusService;
import com.seo.utils.FileUtils;
import com.seo.utils.security.Base64Util;
import com.seo.utils.security.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTasks {

    private static String hsh;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenusService menusService;

    @Autowired
    private UserDefinedProperties userDefinedProperties;

    /***
     * http://cron.qqe2.com/ 需删减
     * 每日早晨7点执行
     * 只能指定6位,不指定年份,否则异常
     */
    @Scheduled(cron = "0 0 7 * * ?")
    public void task1() {
        Integer timer = userDefinedProperties.getTimer();
        List<Menus> menusList = new ArrayList<>();
        for (int i = 0; i < timer; i++) {
            menusList.add(menusService.collect(null));
        }
        menuRepository.saveAll(menusList);
    }

    @Scheduled(cron = "0 0 0/2 * * ?")
    public void task2() {
        setReserved();
    }

    @CacheEvict(allEntries = true)
    public void setReserved() {
        System.out.println("-----------------delete cache-------------------");
    }

    @Scheduled(cron = "0 0 0/2 * * ?")
    public void task3() {
        if (hsh == null) {
            hsh = userDefinedProperties.getKey();
        }else if (!MD5Util.encode(Base64Util.encode(FileUtils.getHostIp().getBytes())).equals(hsh)) {
            NewApplication.context.close();
        }
    }
}
