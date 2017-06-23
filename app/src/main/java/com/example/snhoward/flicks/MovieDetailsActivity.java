package com.example.snhoward.flicks;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.snhoward.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

import static com.example.snhoward.flicks.MovieListActivity.API_BASE_URL;
import static com.example.snhoward.flicks.MovieListActivity.API_KEY_PARAM;

public class MovieDetailsActivity extends AppCompatActivity {

    // instance fields
    AsyncHttpClient client;
    String videoKey;

    // the movie to display
    Movie movie;

    // the view objects
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvOverview) TextView tvOverview;
    @BindView(R.id.ivBackdrop) ImageView ivBackdrop;
    @BindView(R.id.rbRating) RatingBar rbRating;
    @BindView(R.id.tvReleaseDate) TextView tvReleaseDate;
    @BindView(R.id.ivLogo) ImageView ivLogo;
    @BindView(R.id.ivPlay) ImageView ivPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        client = new AsyncHttpClient();
        // set the title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvReleaseDate.setText(" Released " + movie.getReleaseDate());

        Glide.with(ivBackdrop.getContext())
                .load(movie.getBackdropURL())
                .into(ivBackdrop);

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbRating.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);
    }

    @OnClick(R.id.ivBackdrop)
    public void displayToast(ImageView ivBackdrop) {
        ivLogo.setColorFilter(Color.WHITE);
        Toast.makeText(MovieDetailsActivity.this,
                "Playing trailer...",
                Toast.LENGTH_LONG).show();
        getVideoKey();
    }

    // get the list of currently playng movies from the API
    private void getVideoKey() {
        // create the url
        String url = API_BASE_URL + "/movie/" + movie.getId().toString() + "/videos";
        // set the request parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key)); // API key, always required
        // execute a GET request expecting a JSON object response
        client.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    videoKey = results.getJSONObject(0).getString("key");
                    Intent intent = new Intent(MovieDetailsActivity.this , MovieTrailerActivity.class);
                    intent.putExtra("key", videoKey);
                    MovieDetailsActivity.this.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
