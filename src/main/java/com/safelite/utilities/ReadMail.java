package com.safelite.utilities;

import org.jsoup.Jsoup;

import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.Properties;

public class ReadMail {
	public static Properties properties = null;
	public static String resetURL = "";
	public static Session session = null;
	public static Store store = null;
	public static Folder inbox = null;
	public static String userName = "cigniti30031984@gmail.com";
	public static String password = "user@cigniti";

	public static String readQuikFlixResetLink() {

		properties = new Properties();

		properties.setProperty("mail.host", "imap.gmail.com");

		properties.setProperty("mail.port", "995");

		properties.setProperty("mail.transport.protocol", "imaps");

		session = Session.getInstance(properties,

				new javax.mail.Authenticator() {

					protected PasswordAuthentication getPasswordAuthentication() {

						return new PasswordAuthentication(userName, password);
					}
				});

		try {

			Thread.sleep(8000L);
			store = session.getStore("imaps");

			store.connect();

			inbox = store.getFolder("INBOX");

			inbox.open(Folder.READ_WRITE);

			Message messages[] = inbox.search(new FlagTerm(
					new Flags(Flag.SEEN), false));

			System.out.println("Number of mails = " + messages.length);

			for (int i = 0; i < messages.length; i++) {

				Message message = messages[i];

				if (message.getSubject().equalsIgnoreCase(
						"Quickflix Password Reset")) {
					Object content;
					try {
						content = message.getContent();
						if (content instanceof String) {

							message.setFlag(Flags.Flag.DELETED, true);
							resetURL = Jsoup.parse((String) content)
									.select("a").first().text();
							return resetURL;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			inbox.close(true);

			store.close();
		} catch (NoSuchProviderException e) {

			e.printStackTrace();
		} catch (MessagingException e) {

			e.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "No Unread Mails from QuickFlix";
	}
}
