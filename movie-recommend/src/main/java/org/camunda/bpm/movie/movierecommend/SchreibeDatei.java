package org.camunda.bpm.movie.movierecommend;

	import java.io.BufferedWriter;
	import java.io.File;
	import java.io.FileWriter;
	import java.io.IOException;

	public class SchreibeDatei {
	   public static void schreibeFormular(Movie[] movies) {
	      BufferedWriter bw = null;
	      try {
	    	 
	    	  String mycontent = "";
	    	  
	    	  mycontent += "<form name='wertbestaetigenformular'>";
	    	  
	    	  //Falls Abfrage leer
	    	  if(movies[0]==null){
	    	  
	    		  mycontent +=
	    				  	"<div class='form-group'> \n"
	    				  + "<h4>Zu diesem Genre momentan keine Filme vorhanden.</h4>"
	    				  + "</div> \n";
	    	  }
	    	  else { //Falls Abfrage Moviews enthält
	    	  
		    	  for (int i = 0; i < movies.length && movies[i]!=null; i++) {
		    		  
		    		  //Falls kein overview vorhanden
			    	  final String overview = (movies[i].overview != "" ? movies[i].overview: "k.A.");
			    	  
			    	  mycontent +=
			    			  	"<div class='form-group'> \n"
			    			  + "<h4>"+movies[i].title+"</h4>"
			    			  + "<img src=\""+movies[i].poster_path+"\"/><br />"
			    			  + "<table>"
			    			  + "<tr valign=top><td>im Kino seit: </td><td>"+movies[i].release_date+"</td></tr>"
			    			  + "<tr valign=top><td>Durchschnittsbewertung: </td><td>"+movies[i].vote_average+"</td></tr>"
			    			  + "<tr valign=top><td>Handlung: </td><td>"+overview+"</td></tr>"
			    			  + "</table>"
			    			  + "</div> \n";
		    	  }
		    	  
		    	  	mycontent +=
		    			  "<div class=\"form-group\">"
		    			  + "Informationen per E-Mail zusenden "
		    			  + "<input type=\"checkbox\" cam-variable-name=\"x\" cam-variable-type=\"Boolean\" />"
		    			  + " . Falls ja an folgende E-Mail Adresse: "
		    			  + "<input type=\"email\" cam-variable-name=\"email\" cam-variable-type=\"String\" /><br />"  
		    			  +"</div>";
		    	   	   
	    	  }
	    	  
	    	  mycontent += "</form>";
	    	  
	    	  
	         //Specify the file name and path here
		 File file = new File("../webapps/movie-recommend-0.1.0-SNAPSHOT/forms/filmeEinsehen.html");
		  
		 /* This logic will make sure that the file 
		  * gets created if it is not present at the
		  * specified location*/
		  if (!file.exists()) {
		     file.createNewFile();
		  }

		  FileWriter fw = new FileWriter(file);
		  bw = new BufferedWriter(fw);
		  bw.write(mycontent);
	          System.out.println("File written Successfully");

	      } catch (IOException ioe) {
		   ioe.printStackTrace();
		}
		finally
		{ 
		   try{
		      if(bw!=null)
			 bw.close();
		   }catch(Exception ex){
		       System.out.println("Error in closing the BufferedWriter"+ex);
		    }
		}
 }
} // public class SchreibeDatei 