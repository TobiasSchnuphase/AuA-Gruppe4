package org.camunda.bpm.movie.movierecommend;

public class Movie {
	
	final String title;
	final String poster_path;
	final String overview;
	final double vote_average;
	final String release_date;
	
	Movie(String title, String poster_path, String overview, double vote_average, String release_date){
		this.title=title;
		this.poster_path=poster_path;
		this.overview=overview;
		this.vote_average=vote_average;
		this.release_date=release_date;
	}	
	
}
