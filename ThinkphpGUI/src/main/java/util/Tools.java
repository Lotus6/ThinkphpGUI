package util;

import exploit.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author 莲花 2021/6/15
 */
public class Tools {

    public static boolean checkTheURL(String weburl) {
        if (!weburl.startsWith("http")) {
            return false;
        }
        return true;
    }


    public static BasePayload getPayload(String select) {
        BasePayload bp = null;
        Exp_list list = new Exp_list();
        List exp_list = list.get_exp();
        if (select.startsWith((String) exp_list.get(0))) {
            bp = new tp50();
        }
        if (select.startsWith((String) exp_list.get(1))) {
            bp = new tp5010();
        }
        if (select.startsWith((String) exp_list.get(2))) {
            bp = new tp5022_5129();
        }
        if (select.startsWith((String) exp_list.get(3))) {
            bp = new tp5023();
        }
        if (select.startsWith((String) exp_list.get(4))) {
            bp = new tp5024_5130();
        }
        if (select.startsWith((String) exp_list.get(5))) {
            bp = new tp5_db();
        }
        if (select.startsWith((String) exp_list.get(6))) {
            bp = new tp5_log();
        }
        if (select.startsWith((String) exp_list.get(7))) {
            bp = new tp3();
        }
        if (select.startsWith((String) exp_list.get(8))) {
            bp = new tp3_log();
        }
        if (select.startsWith((String) exp_list.get(9))) {
            bp = new tp3_log_rce();
        }if (select.startsWith((String) exp_list.get(10))) {
            bp = new tp6_log();
        }
        return bp;
    }

    public static String addTheURL(String weburl) {
        if (!weburl.startsWith("http")) {
            weburl = "http://" + weburl;
        }
        return weburl;
    }

    public static List read_file(String file) throws IOException {
        List<String> list = new ArrayList();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String url;
            while ((url = br.readLine()) != null) {
                url = addTheURL(url);
                list.add(url);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
