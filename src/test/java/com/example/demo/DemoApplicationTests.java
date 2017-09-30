package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
	}
	@Autowired
	JavaMailSenderImpl javaMailSender;

	@Test
	public void sendTxtMail(){
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(new String[]{"18701781651@163.com"});
		simpleMailMessage.setFrom("18701781651@163.com");
		simpleMailMessage.setSubject("邮件");
		simpleMailMessage.setText("你好，这是一封邮件。");
		javaMailSender.send(simpleMailMessage);
		System.out.println("邮件已发送。");
	}
	@Test
	public void sendHtmlMail() throws  Exception{
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
		mimeMessageHelper.setTo("18701781651@163.com");
		mimeMessageHelper.setFrom("18701781651@163.com");
		mimeMessageHelper.setSubject("Html邮件");
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head></head>");
		sb.append("<body><h1>spring 邮件测试</h1><p>hello!this is spring mail test。</p></body>");
		sb.append("</html>");
		mimeMessageHelper.setText(sb.toString(),true);
		javaMailSender.send(mimeMessage);
		System.out.println("邮件已发送");
	}
	@Test
	public void sendAttachedImageMail() throws  Exception{
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
		mimeMessageHelper.setTo("18701781651@163.com");
		mimeMessageHelper.setFrom("18701781651@163.com");
		mimeMessageHelper.setSubject("Spring Boot Mail 邮件测试【图片】");

		StringBuilder sb = new StringBuilder();
		sb.append("<html><head></head>");
		sb.append("<body><h1>spring 邮件测试</h1><p>hello!this is spring mail test。</p>");
		// cid为固定写法，imageId指定一个标识
		sb.append("<img src=\"cid:imageId\"/></body>");
		sb.append("</html>");

		// 启用html
		mimeMessageHelper.setText(sb.toString(), true);

		// 设置imageId
		FileSystemResource img = new FileSystemResource(new File("E:/a.png"));
		mimeMessageHelper.addInline("imageId", img);

		// 发送邮件
		javaMailSender.send(mimeMessage);
		System.out.println("邮件已发送");
	}
	@Test
	public void sendAttachedFileMail() throws  Exception{
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"utf-8");
		mimeMessageHelper.setTo("18701781651@163.com");
		mimeMessageHelper.setFrom("18701781651@163.com");
		mimeMessageHelper.setSubject("Spring Boot Mail 邮件测试【附件】");
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head></head>");
		sb.append("<body><hl>spring 邮件测试</hl><p>Hello,this is a spring mail test.");
		sb.append("</p></body></html>");
		mimeMessageHelper.setText(sb.toString(),true);
		FileSystemResource file = new FileSystemResource(new File("E:/config.ini"));
		mimeMessageHelper.addAttachment("config.ini",file	);
		javaMailSender.send(mimeMessage);
	}
}
