package com.seo.utils;

import com.seo.config.properties.UserDefinedProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;


@Slf4j
public class FileUtils {

    @Autowired
    UserDefinedProperties userDefinedProperties;

    public static List<StringBuffer> LIST_KEYWORDS;
    public static List<StringBuffer> LIST_COUNTS;
    public static List<StringBuffer> LIST_TITLES;

//    private static final String FILE_PRFIX = "classpath:static/file/";
    private static final String FILE_PRFIX = "static/file/";

    public static void put() {
        LIST_KEYWORDS = readLineEnd("keywords.txt", "utf-8");
        LIST_COUNTS = readLineEnd("content.txt", "utf-8");
        LIST_TITLES = readLineEnd("title.txt", "utf-8");
    }

    public void ss() {

    }


    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     *
     * @param fileName 文件名
     */
    public static void readFileByChars(String fileName) {
        Reader reader = null;
        try {
            File file = ResourceUtils.getFile(FILE_PRFIX + fileName);
            System.out.println("以字符为单位读取文件内容，一次读一个字节：");
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                //对于windows下，/r/n这两个字符在一起时，表示一个换行。
                //但如果这两个字符分开显示时，会换两次行。
                //因此，屏蔽掉/r，或者屏蔽/n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r') {
                    System.out.print((char) tempchar);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("以字符为单位读取文件内容，一次读多个字节：");
            //一次读多个字符
            char[] tempchars = new char[30];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(fileName));
            //读入多个字符到字符数组中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                //同样屏蔽掉/r不显示
                if ((charread == tempchars.length) && (tempchars[tempchars.length - 1] != '\r')) {
                    System.out.print(tempchars);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            System.out.print(tempchars[i]);
                        }
                    }
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }



    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     *
     * @param fileName 文件名
     */
    public static void readFileByLines(String fileName) {
        BufferedReader reader = null;
        try {
            File file = ResourceUtils.getFile(FILE_PRFIX + fileName);

            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            //一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                //显示行号
                System.out.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }


    /**
     * 使用LineNumberReader读取文件，1000w行比RandomAccessFile效率高，无法处理1亿条数据
     *
     * @param fileName 源文件名
     * @param encoding 文件编码
     * @param index    开始位置
     * @param num      读取量
     * @return pins文件内容
     */
    public static List<StringBuffer> readLine(String fileName, String encoding, int index, int num) {
        List<StringBuffer> pins = Lists.newArrayList();
        LineNumberReader reader = null;
        try {
            File file = ResourceUtils.getFile(FILE_PRFIX + fileName);
            System.out.println(file.length());

            reader = new LineNumberReader(new InputStreamReader(new FileInputStream(file), encoding));
            int lines = 0;
            while (true) {
                StringBuffer pin = new StringBuffer(reader.readLine());
                if (StringUtils.isBlank(pin)) {
                    break;
                }
                if (lines >= index) {
                    if (StringUtils.isNotBlank(pin)) {
                        pins.add(pin);
                    }
                }
                if (num == pins.size()) {
                    break;
                }
                lines++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(reader);
        }
        return pins;
    }


    /**
     * 使用LineNumberReader读取文件，1000w行比RandomAccessFile效率高，无法处理1亿条数据
     *
     * @param fileName 源文件名
     * @param encoding 文件编码
     * param index    开始位置
     * param num      读取量
     * @return pins文件内容
     */
    public static List<StringBuffer> readLineEnd(String fileName, String encoding) {
        List<StringBuffer> pins = Lists.newArrayList();
        LineNumberReader reader = null;
        int index = 0;
        String line = null;
        try {

            Resource resource = new ClassPathResource(FILE_PRFIX + fileName);

//            File file = ResourceUtils.getFile(FILE_PRFIX + fileName);
//            reader = new LineNumberReader(new InputStreamReader(new FileInputStream(file), encoding));

            reader = new LineNumberReader(new InputStreamReader(resource.getInputStream(), encoding));
            int lines = 0;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                StringBuffer pin = new StringBuffer(line);
                if (StringUtils.isBlank(pin)) {
                    break;
                }
                if (lines >= index) {
                    if (StringUtils.isNotBlank(pin)) {
                        pins.add(pin);
                    }
                }
                lines++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(reader);
        }
        return pins;
    }


    public static String getHostIp(){
        try{
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()){
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()){
                    InetAddress ip = (InetAddress) addresses.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                            && ip.getHostAddress().indexOf(":")==-1){
                        return ip.getHostAddress();
                    }
                }
            }
        }catch(Exception e){
            log.info("errorp");
            return null;
        }
        return null;
    }
}
