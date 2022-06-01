import view.tp_view;

import javax.swing.*;

/**
 * Author 莲花 2021/6/15
 */
public class App {
    public static void main(String[] args) throws Exception {
        tp_view f = new tp_view();
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
