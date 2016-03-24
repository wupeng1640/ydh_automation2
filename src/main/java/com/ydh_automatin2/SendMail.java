package com.ydh_automatin2;
/**
 * Created by Administrator on 2016/3/21.
 */

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.*;
import javax.activation.*;

public class SendMail {

    private String host = "";  //smtp服务器
    private String from = "";  //发件人地址
    private String to = "";    //收件人地址
    private String affix = ".//"; //附件地址
    private String affixName = ""; //附件名称
    private String user = "";  //用户名
    private String pwd = "";   //密码
    private String subject = ""; //邮件标题




    public void setAddress(String from, String to, String subject) {
        this.from = from;
        this.to = to;
        this.subject = subject;
    }

    public void setAffix(String affix, String affixName) {
        this.affix = affix;
        this.affixName = affixName;
    }

    public void send(String host, String user, String pwd)  throws IOException {
        this.host = host;
        this.user = user;
        this.pwd = pwd;

        Properties props = new Properties();

        //设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
        props.put("mail.smtp.host", host);
        //需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
        props.put("mail.smtp.auth", "true");

        //用刚刚设置好的props对象构建一个session
        Session session = Session.getDefaultInstance(props);

        //有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
        //用（你可以在控制台（console)上看到发送邮件的过程）
        session.setDebug(true);

        //用session为参数定义消息对象
        MimeMessage message = new MimeMessage(session);
        try {
            //加载发件人地址
            message.setFrom(new InternetAddress(from));
            //加载收件人地址
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //加载标题
            message.setSubject(subject);

            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();

            String filecontent=null;
            FileUtils fileUtils=new FileUtils();
            filecontent=fileUtils.readFile("./ydh_automation2_test.log");

            //   设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            //contentPart.setText("邮件的具体内容在此");
            contentPart.setText(filecontent);
            multipart.addBodyPart(contentPart);
            //添加附件
            BodyPart messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(affix);
            //添加附件的内容
            messageBodyPart.setDataHandler(new DataHandler(source));
            //添加附件的标题
            //这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
            sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
            messageBodyPart.setFileName("=?GBK?B?" + enc.encode(affixName.getBytes()) + "?=");
            multipart.addBodyPart(messageBodyPart);


            //将multipart对象放到message中
            message.setContent(multipart);
            //保存邮件
            message.saveChanges();
            //   发送邮件
            Transport transport = session.getTransport("smtp");
            //连接服务器的邮箱
            transport.connect(host, user, pwd);
            //把邮件发送出去
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)  throws IOException  {
        SendMail cn = new SendMail();
        //设置发件人地址、收件人地址和邮件标题
        cn.setAddress("wupeng164@163.com", "164553538@qq.com", "一个带附件的JavaMail邮件");
        //设置要发送附件的位置和标题
        cn.setAffix("./ydh_automation2_test.log", "ydh_automation2_test.log");
        //cn.setAffix("./readme.txt","readme.txt");
        //设置smtp服务器以及邮箱的帐号和密码
        cn.send("smtp.163.com", "wupeng164", "wu164553538");

    }
}