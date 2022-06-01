package util;

/**
 * Author 莲花 2021/6/15
 */
public class Result {
    boolean res;
    String payload;
    String vuln;

    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getVuln() {
        return vuln;
    }

    public void setVuln(String vuln) {
        this.vuln = vuln;
    }

    public Result(boolean res,  String vuln,String payload) {
        this.res = res;
        this.payload = payload;
        this.vuln = vuln;
    }

}
