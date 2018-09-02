package com.seo.controller;

import com.seo.common.BaseController;
import com.seo.entity.Menus;
import com.seo.mongo.MenuRepository;
import com.seo.service.MenusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/mean")
public class MenuController extends BaseController {

    @Autowired
    private MenuRepository menuRepository;

//    @Autowired
//    private MenusMapper menusMapper;

    @Autowired
    private MenusService menusService;



    @RequestMapping("${mark.seo.url}")
    public String aaa(ModelMap modelMap) {

        HttpServletRequest httpServletRequest = getHttpServletRequest();
//        HttpServletResponse httpServletResponse = getHttpServletResponse();

        System.out.println("getRemoteAddr()-----------" + httpServletRequest.getRemoteAddr());
        String url = httpServletRequest.getRequestURI();

        List<Menus> menusList = menusService.selectAllWithLimitAndSort();
        modelMap.addAttribute("menusList", menusList);

        modelMap.addAttribute("domain", httpServletRequest.getAttribute("domain"));
//        httpServletResponse.encodeURL((String) httpServletRequest.getAttribute("domain"));

        modelMap.addAttribute("referer", httpServletRequest.getAttribute("referer"));

        Menus menus = menuRepository.findByUrl(url);
        if (null == menus) {
            Menus installMenus = menusService.collect(url);
            menuRepository.save(installMenus);
            modelMap.addAttribute("menus", installMenus);
            System.out.println("-----------------未发现");
        } else {
            modelMap.addAttribute("menus", menus);
            System.out.println("-----------------发现");
        }
        return "rxyj.html";
    }


    /**
     * 测试
     */
    @RequestMapping("/text")
    @ResponseBody
    public void text() {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
//        List list1 = FileUtils.readLine("content.txt", "utf-8", 0, 20);
//        List<StringBuffer> list = FileUtils.LIST_KEYWORDS;
//        System.out.println("---"+list.get(0).toString());
//        System.out.println(list.size() + list.get(1).toString().trim());
//        System.out.println(list.size() + list.get(2).toString());
//        System.out.println(list.size() + list.get(3).toString());
//        System.out.println(list.size() + list.get(4).toString());
//        System.out.println(list.size() + list.get(68301).toString());
//        System.out.println(list.size() + list.get(68300).toString());
//        Map<String, String> map = userDefinedProperties.getReferer();
//        System.out.println(map.toString() + map.size());
//        final UserAgentParser parser = new UserAgentService().loadParser(); // handle IOException and ParseException
//        String userAgent = httpServletRequest.getHeader("user-agent");
//        System.out.println(userAgent);
        menusService.selectAllWithLimitAndSort();
    }

}
