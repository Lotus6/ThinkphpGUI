/*
 * Created by JFormDesigner on Tue Mar 23 11:02:35 CST 2021
 */

package view;

import util.BasePayload;
import util.Result;
import util.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class tp_view extends JFrame {

    public void loginfo(String info) {
        this.infores_txt.append(info + "\r\n");
        infores_txt.paintImmediately(infores_txt.getBounds());
    }

    public void logcmd(String info) {
        this.cmdres_txt.append(info);
        cmdres_txt.paintImmediately(cmdres_txt.getBounds());
    }

    private void clear(ActionEvent e) {
        infores_txt.setText("");
        cmdres_txt.setText("");
    }

    private void check_url(ActionEvent e) throws Exception {
        boolean standard = Tools.checkTheURL(this.url_txt.getText());
        if (standard) {
            check_vul(e);
        } else {
            JOptionPane.showMessageDialog(null, "URL格式不符合要求，示例：http://127.0.0.1:7001", "URL检查", JOptionPane.WARNING_MESSAGE);
        }
    }


    public void check_vul(ActionEvent e) throws Exception {
        this.test_btn.setEnabled(false);
        String url = this.url_txt.getText();
        String version = this.comboBox.getSelectedItem().toString();

        if (version.startsWith("ALL")) {
            loginfo("检测所有漏洞中......");
            ArrayList<String> versions = new ArrayList<String>() {{
                add("ThinkPHP 5.0 RCE");
                add("ThinkPHP 5.0.10 RCE");
                add("ThinkPHP 5.0.22/5.1.29 RCE");
                add("ThinkPHP 5.0.23 RCE");
                add("ThinkPHP 5.0.24-5.1.30 RCE");
                add("ThinkPHP 3.x RCE");
                add("ThinkPHP 5.x 数据库信息泄露");
                add("ThinkPHP 3.x Log RCE");
                add("ThinkPHP 5.x 日志泄露");
                add("ThinkPHP 3.x 日志泄露");
                add("ThinkPHP 6.x 日志泄露");
            }};
            for (String v : versions) {
                BasePayload bp = Tools.getPayload(v);
                Result vul = bp.checkVUL(url);
                if (vul.isRes()) {
                    loginfo("[+] 存在" + vul.getVuln());
                    loginfo("Payload: " + vul.getPayload());
                } else {
                    loginfo("[-] 不存在" + vul.getVuln());
                }
            }
        } else {
            loginfo("检测漏洞 " + version + "中......");
            BasePayload bp = Tools.getPayload(version);
            Result vul = bp.checkVUL(url);
            if (vul.isRes()) {
                loginfo("[+] 存在" + vul.getVuln());
                loginfo("Payload: " + vul.getPayload());
            } else {
                loginfo("[-] 不存在" + vul.getVuln());
            }
        }
        this.test_btn.setEnabled(true);
    }

    public void exe_vul(ActionEvent e) throws Exception {
        this.fun_btn.setEnabled(false);
        String url = this.url_txt.getText();
        String version = this.comboBox.getSelectedItem().toString();
        String cmd = this.cmd_txt.getText();
        String res = null;

        if (version.startsWith("ALL")) {
            JOptionPane.showMessageDialog(null, "请选择漏洞对应版本!", "信息", JOptionPane.WARNING_MESSAGE);
        } else {
            BasePayload bp = Tools.getPayload(version);
            Result vul = bp.exeVUL(url, cmd);
            if (vul.isRes()) {
                res = vul.getPayload();
                logcmd("[+] " + res);
            } else {
                JOptionPane.showMessageDialog(null, "命令执行失败!", "信息", JOptionPane.WARNING_MESSAGE);
            }
        }
        this.fun_btn.setEnabled(true);
    }


    public void getShell(ActionEvent e) throws Exception {
        this.getshell_btn.setEnabled(false);
        String url = this.url_txt.getText();
        String version = this.comboBox.getSelectedItem().toString();
        String res = null;

        if (version.startsWith("ALL")) {
            JOptionPane.showMessageDialog(null, "请选择漏洞对应版本!", "信息", JOptionPane.WARNING_MESSAGE);
        } else {
            BasePayload bp = Tools.getPayload(version);
            Result vul = bp.getShell(url);
            if (vul.isRes()) {
                res = vul.getPayload();
                loginfo("[+] " + res);
            } else {
                JOptionPane.showMessageDialog(null, "Getshell失败", "信息", JOptionPane.WARNING_MESSAGE);
            }
        }
        this.getshell_btn.setEnabled(true);
    }

    List<String> list_url = new ArrayList();

    public void batch_import_url(ActionEvent e) throws Exception {
        JFileChooser jf = new JFileChooser();
        jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jf.showDialog(new JLabel(), "选择");
        File file = jf.getSelectedFile();
        if (file != null) {
            this.file_txt.setText(file.getAbsolutePath());
            list_url = Tools.read_file(file.getAbsolutePath());
            JOptionPane.showMessageDialog(null, "成功导入" + list_url.size() + "个URL！", "导入URL", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void batch_check_url(ActionEvent e) throws Exception {
        this.test_btn.setEnabled(false);
        String version = this.comboBox.getSelectedItem().toString();
        if (list_url.size() > 0) {
            if (version.startsWith("ALL")) {
                ArrayList<String> versions = new ArrayList<String>() {{
                    add("ThinkPHP 5.0 RCE");
                    add("ThinkPHP 5.0.10 RCE");
                    add("ThinkPHP 5.0.22/5.1.29 RCE");
                    add("ThinkPHP 5.0.23 RCE");
                    add("ThinkPHP 5.0.24-5.1.30 RCE");
                    add("ThinkPHP 5.x 数据库信息泄露");
                    add("ThinkPHP 5.x 日志泄露");
                    add("ThinkPHP 3.x RCE");
                    add("ThinkPHP 3.x 日志泄露");
                    add("ThinkPHP 3.x Log RCE");
                    add("ThinkPHP 6.x 日志泄露");
                }};
                for (int j = 0; j < list_url.size(); j++) {
                    loginfo("检测URL " + list_url.get(j) + "中......");
                    for (String v : versions) {
                        BasePayload bp = Tools.getPayload(v);
                        Result vul = bp.checkVUL(list_url.get(j));
                        if (vul.isRes()) {
                            loginfo("[+] 存在" + vul.getVuln());
                            loginfo("Payload: " + vul.getPayload());
                        } else {
                            loginfo("[-] 不存在" + vul.getVuln());
                        }
                    }
                }
            } else {
                BasePayload bp = Tools.getPayload(version);
                for (int j = 0; j < list_url.size(); j++) {
                    if (j!=0) {
                        loginfo("");
                    }
                    loginfo("检测URL " + list_url.get(j) + "中......");
                    Result isvul = bp.checkVUL(list_url.get(j));
                    if (isvul.isRes()) {
                        loginfo("[+] 存在" + isvul.getVuln());
                        loginfo("Payload: " + isvul.getPayload());
                    } else {
                        loginfo("[-] 不存在" + isvul.getVuln());
                    }
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "请先导入URL！", "URL批量检查", JOptionPane.WARNING_MESSAGE);
        }
        this.test_btn.setEnabled(true);
    }

    public tp_view() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        url_txt = new JTextField();
        label2 = new JLabel();
        test_btn = new JButton();
        comboBox = new JComboBox<>();
        textArea1 = new JTextArea();
        clear_btn = new JButton();
        tabbedPane1 = new JTabbedPane();
        panel1 = new JPanel();
        scrollPane1 = new JScrollPane();
        infores_txt = new JTextArea();
        panel2 = new JPanel();
        label4 = new JLabel();
        cmd_txt = new JTextField();
        fun_btn = new JButton();
        scrollPane2 = new JScrollPane();
        cmdres_txt = new JTextArea();
        label3 = new JLabel();
        file_txt = new JTextField();
        import_btn = new JButton();
        batch_test_btn = new JButton();
        getshell_btn = new JButton();

        //======== this ========
        setTitle("ThinkphpGUI By \u83b2\u82b1");
        setMinimumSize(new Dimension(0, 22));
        Container contentPane = getContentPane();

        //---- label1 ----
        label1.setText("URL:");

        //---- url_txt ----
        url_txt.setText("http://127.0.0.1");

        //---- label2 ----
        label2.setText("\u7248\u672c:");

        //---- test_btn ----
        test_btn.setText("\u68c0\u6d4b");
        test_btn.addActionListener(e -> {
            try {
                check_url(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //---- comboBox ----
        comboBox.setModel(new DefaultComboBoxModel<>(new String[] {
            "ThinkPHP 5.0 RCE",
            "ThinkPHP 5.0.10 RCE",
            "ThinkPHP 5.0.22/5.1.29 RCE",
            "ThinkPHP 5.0.23 RCE",
            "ThinkPHP 5.0.24-5.1.30 RCE",
            "ThinkPHP 5.x \u6570\u636e\u5e93\u4fe1\u606f\u6cc4\u9732",
            "ThinkPHP 5.x \u65e5\u5fd7\u6cc4\u9732",
            "ThinkPHP 3.x RCE",
            "ThinkPHP 3.x \u65e5\u5fd7\u6cc4\u9732",
            "ThinkPHP 3.x Log RCE",
            "ThinkPHP 6.x \u65e5\u5fd7\u6cc4\u9732",
            "ALL"
        }));

        //---- clear_btn ----
        clear_btn.setText("Clear");
        clear_btn.addActionListener(e -> clear(e));

        //======== tabbedPane1 ========
        {

            //======== panel1 ========
            {

                //======== scrollPane1 ========
                {
                    scrollPane1.setViewportView(infores_txt);
                }

                GroupLayout panel1Layout = new GroupLayout(panel1);
                panel1.setLayout(panel1Layout);
                panel1Layout.setHorizontalGroup(
                    panel1Layout.createParallelGroup()
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)
                );
                panel1Layout.setVerticalGroup(
                    panel1Layout.createParallelGroup()
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                );
            }
            tabbedPane1.addTab("\u4fe1\u606f", panel1);

            //======== panel2 ========
            {

                //---- label4 ----
                label4.setText("CMD:");

                //---- cmd_txt ----
                cmd_txt.setText("whoami");

                //---- fun_btn ----
                fun_btn.setText("\u6267\u884c");
                fun_btn.addActionListener(e -> {
                    try {
                        exe_vul(e);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                //======== scrollPane2 ========
                {
                    scrollPane2.setViewportView(cmdres_txt);
                }

                GroupLayout panel2Layout = new GroupLayout(panel2);
                panel2.setLayout(panel2Layout);
                panel2Layout.setHorizontalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(label4, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cmd_txt, GroupLayout.PREFERRED_SIZE, 537, GroupLayout.PREFERRED_SIZE)
                            .addGap(34, 34, 34)
                            .addComponent(fun_btn)
                            .addContainerGap(105, Short.MAX_VALUE))
                        .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)
                );
                panel2Layout.setVerticalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label4, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmd_txt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(fun_btn))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE))
                );
            }
            tabbedPane1.addTab("\u547d\u4ee4\u6267\u884c", panel2);
        }

        //---- label3 ----
        label3.setText("\u8def\u5f84\uff1a");

        //---- import_btn ----
        import_btn.setText("\u5bfc\u5165");
        import_btn.addActionListener(e -> {
            try {
                batch_import_url(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //---- batch_test_btn ----
        batch_test_btn.setText("\u6279\u91cf\u68c0\u6d4b");
        batch_test_btn.addActionListener(e -> {
            try {
                batch_check_url(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //---- getshell_btn ----
        getshell_btn.setText("GetSehll");
        getshell_btn.addActionListener(e -> {
            try {
                getShell(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(label1, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label3))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGap(57, 57, 57)
                                    .addComponent(textArea1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addGap(91, 733, Short.MAX_VALUE))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                            .addComponent(url_txt, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(label2, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                            .addComponent(file_txt, GroupLayout.PREFERRED_SIZE, 266, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(import_btn, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                            .addGap(16, 16, 16)
                                            .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                            .addGap(18, 18, 18)
                                            .addComponent(batch_test_btn)))
                                    .addGap(32, 32, 32)
                                    .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                            .addComponent(test_btn, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(clear_btn, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                            .addGap(6, 6, 6)
                                            .addComponent(getshell_btn, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)))
                                    .addGap(0, 35, Short.MAX_VALUE))))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(tabbedPane1, GroupLayout.PREFERRED_SIZE, 801, GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 35, Short.MAX_VALUE))))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(12, 12, 12)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label1, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                        .addComponent(url_txt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(clear_btn)
                        .addComponent(test_btn))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label3, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                            .addComponent(file_txt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(import_btn)
                            .addComponent(batch_test_btn))
                        .addComponent(getshell_btn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tabbedPane1, GroupLayout.PREFERRED_SIZE, 513, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(textArea1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(9, 9, 9))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JTextField url_txt;
    private JLabel label2;
    private JButton test_btn;
    private JComboBox<String> comboBox;
    private JTextArea textArea1;
    private JButton clear_btn;
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JScrollPane scrollPane1;
    private JTextArea infores_txt;
    private JPanel panel2;
    private JLabel label4;
    private JTextField cmd_txt;
    private JButton fun_btn;
    private JScrollPane scrollPane2;
    private JTextArea cmdres_txt;
    private JLabel label3;
    private JTextField file_txt;
    private JButton import_btn;
    private JButton batch_test_btn;
    private JButton getshell_btn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
