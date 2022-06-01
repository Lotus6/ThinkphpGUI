package util;

/**
 * Author 莲花 2021/6/15
 */
public interface BasePayload {
    Result checkVUL(String url) throws Exception;
    Result exeVUL(String url,String cmd) throws Exception;
    Result getShell(String url) throws Exception;
}
