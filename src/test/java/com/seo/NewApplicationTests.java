package com.seo;


//@RunWith(SpringRunner.class)
//@SpringBootTest
public class NewApplicationTests {

//    @Autowired
//    private ZhuaTitlesMapper zhuaTitlesMapper;
//
//    @Test
//    public void contextLoads() {
//        ZhuaTitles zhuaTitles = new ZhuaTitles();
//        String url = "http://www.83133.com/der";
//        for (int i = 1; i < 100; i++) {
//            String newUrl = url + i;
//            String title = null;
//            try {
//                zhuaTitles.setUrl(newUrl);
//                Document document = Jsoup.connect(newUrl).get();
//                title = document.title();
//                if (title == null) {
//                    continue;
//                }
//                String newTitle = title.substring(0, title.indexOf("-中国科学院"));
//                zhuaTitles.setTitles(newTitle);
//                zhuaTitlesMapper.insert(zhuaTitles);
//                System.out.println("+++++++++++++++++++++++位置：" + i + "===========" + newTitle);
////                System.out.println(title);
//            } catch (Exception e) {
////                e.printStackTrace();
//                System.out.println("==============================进入异常==========================");
//                continue;
//            }
//        }
//    }
}
