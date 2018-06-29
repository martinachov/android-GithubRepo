package com.example.martin.github_repos.tasks;

import android.os.AsyncTask;

import com.example.martin.github_repos.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class GithubTask extends AsyncTask<URL, Void, String> {

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

}
