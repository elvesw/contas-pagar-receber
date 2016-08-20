package br.com.pontek.util.email;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

@Service
public class EmailUtil {

	
	public static void sendEmail(String assunto, String corpoEmailHTML, String destinatario) {
		
		final String login = "pontekti@gmail.com";
		final String senha = "Um4M3rd4D3$3nh4";

		final String enviarDe = "pontekti@gmail.com";

		System.out.println("EmailUtil.sendEmail() - Iniciando envio email TLSGmail.");
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(login, senha);
			}
		};
		Session session = Session.getInstance(props, auth);

		try {
			MimeMessage msg = new MimeMessage(session);

			// Seta cabeçario
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			msg.setFrom(new InternetAddress(enviarDe,"Nao responder"));
			msg.setSubject(assunto, "UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(destinatario, false));
			System.out.println("EmailUtil.sendEmail() - Cabeçario montado com sucesso.");
			
			// Texto do email
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText("testando elves", "UTF-8");
			System.out.println("EmailUtil.sendEmail() - Texto do email carregado.");
			//Texto em HTML do email
			mbp1.setContent(corpoEmailHTML,"text/html");
			System.out.println("EmailUtil.sendEmail() - HTML do email carregado.");

			// Junta o Corpo email 
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);

			// Adiciona no email
			msg.setContent(mp);
			System.out.println("EmailUtil.sendEmail() - Enviando email, aguarde...");
			Transport.send(msg);
			System.out.println("EmailUtil.sendEmail() - Email enviado com sucesso.");
		} catch (Exception e) {
			System.err.println("EmailUtil.sendEmail() - DEU MERDA : "+e.getMessage());
			e.printStackTrace();
		}

	}
}