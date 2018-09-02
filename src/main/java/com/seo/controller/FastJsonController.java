package com.seo.controller;

import com.seo.common.BaseController;
import com.seo.entity.FastJsonTest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
@RequestMapping("/fast")
public class FastJsonController extends BaseController {

    @RequestMapping("")
    public String aa() {
        return "index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getFastJson(ModelMap map) {
        FastJsonTest fastjsonTest = new FastJsonTest();
        fastjsonTest.setId(1);
        fastjsonTest.setString("fastjson test");
        fastjsonTest.setIgnore("ignore field");
        fastjsonTest.setDate(new Date());
        map.addAttribute("fastjsonTest", fastjsonTest);
        map.addAttribute("host", "http://blog.didispace.com");
        map.addAttribute("ads", "ss");
        return "index";
    }
}
