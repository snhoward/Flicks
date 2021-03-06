package com.example.snhoward.flicks.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by snhoward on 6/21/17.
 */

@Parcel // annotation indicates class is Parcelable
public class Movie {

    // values from API
    // fields must be public for parceler
    String title;
    String overview;
    String posterPath; // only the path
    String backdropPath;
    Double voteAverage;
    Integer id;
    String backdropUrl;
    String releaseDate;

    // no-arg, empty constructor required for Parceler
    public Movie() {}

    // initialize from JSON data
    public Movie(JSONObject object) throws JSONException {
        title = object.getString("title");
        overview = object.getString("overview");
        posterPath = object.getString("poster_path");
        backdropPath = object.getString("backdrop_path");
        voteAverage = object.getDouble("vote_average");
        id = object.getInt("id");
        releaseDate = object.getString("release_date");
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public Double getVoteAverage() { return voteAverage; }

    public Integer getId() { return id; }

    public String getBackdropURL() { return backdropUrl; }

    public String getReleaseDate() { return releaseDate; }

    public void setBackdropUrl(String backdropUrl) {
        this.backdropUrl = backdropUrl;
    }


}
