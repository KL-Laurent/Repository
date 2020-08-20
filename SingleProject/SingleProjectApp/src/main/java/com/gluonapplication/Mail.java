package com.gluonapplication;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

	/* L'adresse IP de votre serveur SMTP */
	String smtpHost = "smtp.gmail.com";
	String smtpPort = "smtp.gmail.com";

	//String smtpPort = "smtp.gmail.com";

	/* L'adresse de l'expéditeur */
	static String accountEmail = "";
	String pass = "";


	/* L'adresse du destinataire */
	static String to = "";

	static /* L'objet du message */
	String objet = "";

	/* Le corps du mail */
	static String texte = "";
	public Mail() {
		accountEmail = CONFIG.EMAIL;
		pass = CONFIG.EMAIL_PASS;
	}
	
	
	
	public void send(String mailDestinataire ,String header, String objet , String txt) throws SendFailedException , MessagingException {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		
		this.texte = txt ; 
		this.objet = objet ;
		this.to = mailDestinataire; 
		
		/* Création du message*/
		/* Session encapsule pour un client donné sa connexion avec le serveur de mails.*/
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return  new PasswordAuthentication(accountEmail, pass);
			}
		});

		Message msg = prepareMessage(session, accountEmail);
	}

	
	private static Message prepareMessage(Session session ,String  accountEmail) throws SendFailedException, MessagingException{
		  Message msg = new MimeMessage(session);
		  msg.setFrom(new InternetAddress(accountEmail));
		  msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to, false));
		  msg.setSubject(objet);
		  msg.setText(texte);
		  msg.setHeader("CLAIMS MANAGEMENT Mailer", "MICTSL-Email");
		  Transport.send(msg);
		return null;
	}
}
