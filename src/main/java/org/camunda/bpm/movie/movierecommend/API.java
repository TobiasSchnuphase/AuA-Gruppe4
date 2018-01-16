package org.camunda.bpm.movie.movierecommend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class API {

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      return json;
    } finally {
      is.close();
    }
  }

  
  public static Movie[] apiFragen(String genres[]) throws IOException{
		
		//heutiges Datum
		LocalDate now = LocalDate.now();
		
		//Datum vor 60 Tagen
		LocalDate before60d = now.minusDays(60);
		
		//genres[] in apistring umwandeln
		
		String apistring ="";
		
		for (int i = 0; i < genres.length; i++) {
			
			if(i>1){ throw new RuntimeException("genres-Arraylaenge nicht vorgesehen");}
			if(i==1){apistring+="|";}
			apistring += genres[i];	
		}
		
		//url für API-Abfrage zusammensetzen
		
		String url = "https://api.themoviedb.org/3/discover/movie?api_key=5d461c6b843e3fc436cef40c0f3774b8&language=de-DE&region=de&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&release_date.gte="+before60d+"&release_date.lte="+now+"&with_release_type=2|3&with_original_language=en|de&with_genres="+apistring;
		
		// Result als JSONObject erhalten
		
		JSONObject json = readJsonFromUrl(url);
		
		//results[] erhalten
		
		JSONArray results = json.getJSONArray("results");
        
		//JSONArray in movieArray umwandeln

		Movie movieArray [] = new Movie[10]; 
		
		int mz=0; //  movieArray Zeiger
				  // i JSONArray Zeiger
		for(int i=0; i<results.length() && mz<movieArray.length; i++){
	    	
			JSONObject obj = results.getJSONObject(i);
			
			//API Fehler bezüglich Datum beheben
			//Prüfe ob Datum nicht vor dem genanten Datum liegt
			int comparision = before60d.compareTo(LocalDate.parse(obj.getString("release_date")));
			if(comparision>0){continue;}
						
			//neues Movie Object erzeugen und in movieArray eintragen
			
			String poster_path = "";
			if (!obj.isNull("poster_path")){poster_path="https://image.tmdb.org/t/p/w185"+obj.getString("poster_path");};
			
			movieArray[mz++] = new Movie(
				obj.getString("title"),
				poster_path,
				obj.getString("overview"),
				obj.getDouble("vote_average"),
				obj.getString("release_date")		
		    );
	    }
		
		/*------------*/
		/*
		JSONObject json = new JSONObject();
		*/
		/*------------*/
		
		return movieArray;
	}
 
  
  
  
  
  
  
  
  
  
 /* 
  
  public static void main(String[] args) throws IOException, JSONException {
     
	String [] zeichen = {"18","878"};
    
	//API Result auslesen und Ausgabe Formular vorbereiten
	
	System.out.println(LocalDate.now().minusDays(60));  
	  	  
	Movie[] movieArray = apiFragen(zeichen);
		
	for (int i = 0; i < movieArray.length && movieArray[i] != null; i++) {
		
		Movie movie = movieArray[i];
		
		System.out.println(movie.title);
		System.out.println(movie.poster_path);
		System.out.println(movie.overview);
		System.out.println(movie.vote_average);
		System.out.println(movie.release_date);
		System.out.println("\n\n");
	}
	

  }
  */
  
}