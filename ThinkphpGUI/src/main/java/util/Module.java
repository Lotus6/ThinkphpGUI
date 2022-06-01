package util;

import com.github.kevinsawicki.http.HttpRequest;

import java.util.ArrayList;

/**
 * Author 莲花 2021/6/15
 */
public class Module {
    public String getModule(String url) {
        ArrayList<String> list = new ArrayList<String>() {{
            add("manage");
            add("admin");
            add("api");
        }};
        String mod = "index";
        for (int i = 0; i < list.size(); i++) {
            try {
                int code = HttpRequest.get(url + "/?s=/" + list.get(i)).code();
                if (code == 200) {
                    mod = list.get(i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mod;
    }
}
