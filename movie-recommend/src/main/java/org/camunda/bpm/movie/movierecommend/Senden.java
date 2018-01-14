package org.camunda.bpm.movie.movierecommend;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


public class Senden implements JavaDelegate {

	  private final static Logger LOGGER = Logger.getLogger(Senden.class.getName()); 
	  
	  
	  public static void sendMail(String subject, String content, String email) throws MessagingException {
	    	
	    	GoogleMail.sendMail(subject, content, email);
	    	
	  }   
	  
	  
	  public void execute(DelegateExecution execution) throws Exception {
		
		//Hole Datum im lokalen Format
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		String nowstring = now.format(formatter);
		
		//Hole Email Inhalt
		String content = (String) execution.getVariable("y");  
		
		//Hole Email Adresse
		String email = (String) execution.getVariable("email");
	  
		//wenn email Adresse vorhanden wird E-Mail gesendet
		
		if(email!="") {
			sendMail("Ihr Filmempfehlung vom "+nowstring , content, email);
		}
		
		
		//LOGGER.info("Processing request by Senden Movie " + z);
	    
	    //execution.setVariable("gewichtetePunkte", new StringBuffer(tmp).reverse().toString());
	    
	    //execution.setVariable("gewichtetePunkte", a);
	    
	    //execution.setVariable("verantwortlicher", "John Doe");
	    
	    //execution.setVariable("x", true);
	    
	    //main(new String[1]);
	    
	    //* sendMail(subject,content.toString());
	       
	  }
	  
}

