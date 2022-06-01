package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Author 莲花 2021/6/15
 */
public class Exp_list {
    public static List get_exp() {
        List<String> list = new ArrayList();
        list.add("ThinkPHP 5.0 RCE");
        list.add("ThinkPHP 5.0.10 RCE");
        list.add("ThinkPHP 5.0.22/5.1.29 RCE");
        list.add("ThinkPHP 5.0.23 RCE");
        list.add("ThinkPHP 5.0.24-5.1.30 RCE");
        list.add("ThinkPHP 5.x 数据库信息泄露");
        list.add("ThinkPHP 5.x 日志泄露");
        list.add("ThinkPHP 3.x RCE");
        list.add("ThinkPHP 3.x 日志泄露");
        list.add("ThinkPHP 3.x Log RCE");
        list.add("ThinkPHP 6.x 日志泄露");
        return list;
    }
}
