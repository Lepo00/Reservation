package it.anoki.spring.service;

public interface EmailService {

	public void sendSimpleMessage(String name, String subject, String text) throws Exception;

	public void sendMessageWithAttachment(String name, String subject, String text, String pathToAttachment) throws Exception;

}