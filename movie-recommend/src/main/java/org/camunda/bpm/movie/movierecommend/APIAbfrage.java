package org.camunda.bpm.movie.movierecommend;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;



public class APIAbfrage implements JavaDelegate {

	
	public static Movie[] api (String [] genres) throws IOException{
	
		return API.apiFragen(genres);
	}
	
	public static void schreibe(Movie [] tmp){
    	
    	SchreibeDatei.schreibeFormular( (Movie []) tmp);	
	}  
	
	
	  private final static Logger LOGGER = Logger.getLogger(APIAbfrage.class.getName()); 
	  
	  public void execute(DelegateExecution execution) throws Exception {
		
		   
		List<Integer> liste = (List<Integer>) execution.getVariable("apigenre");
		  
		Object array [] = liste.toArray();
		
		String [] genres= {};
		switch(array.length){
			case 1:
				String [] tmp1 = {array[0].toString()};
				genres = tmp1;
				break;
			case 2:
				String [] tmp2 = {array[0].toString(),array[1].toString()};
				genres=tmp2;
				break;
		}
		
		// movieArray holen		
		Movie[] movieArray = api(genres);
		
		//Formular schreiben
		schreibe(movieArray);
		
		
		
		/*
		//Log Ausgabe
		String output = "";
		
		for (int i = 0; i < movieArray.length && movieArray[i] != null; i++) {
			
			Movie movie = movieArray[i];
			
			output+=movie.title+"\n";
			output+=movie.poster_path+"\n";
			output+=movie.overview+"\n";
			output+=movie.vote_average+"\n";
			output+=movie.release_date+"\n";
			output+="\n\n";
		}
		
		String logoutput = output;
		
	    LOGGER.info("**** API-Ausgabe: "+ logoutput );
	    */
	    
	    //execution.setVariable("gewichtetePunkte", new StringBuffer(tmp).reverse().toString());
	    //execution.setVariable("gewichtetePunkte", "100");
	    //execution.setVariable("verantwortlicher", "John Doe");
		
		
		
		
		
		// Falls movieArray Moviews enthält setze Prozessvariable für Email
						
		if(movieArray[0]!=null) {
			
			Movie movie=movieArray[0];
			
			String email= "";
			
			email+=
				"Sehr geehrter Kunde, \n \n"
				+ "das Hofgarten-Team empfiehlt Ihnen folgen Film: \n \n"
				+ "- Titel: " + movie.title + "\n"
				+ "- Durchschnittsbewertung: " + movie.vote_average +"\n"
				+ "- im Kino seit: " + movie.release_date +"\n"
				+ "- Handlung: " + movie.overview +"\n \n"
				+ "Mit freundlichen Gruessen \n \n"
				+ "Ihr Service-Team";
			
			execution.setVariable("y", email);
		}
		else {//Falls nicht keine E-Mail-Abfrage
			execution.setVariable("x", false);
		}
		
		
		
	  } 
	  
}

