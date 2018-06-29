package com.example.martin.github_repos;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.MenuRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.martin.github_repos.tasks.GithubTask;
import com.example.martin.github_repos.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchBoxEditText;

    private TextView mUrlDisplayTextView;

    private TextView mSearchResultsTextView;

    private TextView mErrorMessageTextView;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);
        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);
        mErrorMessageTextView = (TextView) findViewById(R.id.tv_error_message_display);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();
        if(menuItemSelected == R.id.action_search){
            makeGithubSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void makeGithubSearchQuery(){
        URL url = NetworkUtils.buildUrl(mSearchBoxEditText.getText().toString());
        mUrlDisplayTextView.setText(url.toString());
        String result = null;
        new GithubInnerTask().execute(url);
    }

    private void showJsonDataView(){
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mSearchResultsTextView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(){
        mErrorMessageTextView.setVisibility(View.VISIBLE);
        mSearchResultsTextView.setVisibility(View.INVISIBLE);
    }

    protected class GithubInnerTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String result = null;
            try {
                result = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            mProgressBar.setVisibility(View.INVISIBLE);
            if(null != s && !s.equals("")) {
                showJsonDataView();
                mSearchResultsTextView.setText(s);
            } else {
                showErrorMessage();
            }
        }
    }

}
