package com.example.martin.github_repos.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 *
 */
public class NetworkUtils {

    final static String GITHUB_BASE_URL =
            "https://api.github.com/search/repositories";

    final static String PARAM_QUERY = "q";

    /*
     * The sort field. One of stars, forks, or updated.
     * Default: results are sorted by best match if no field is specified.
     */
    final static String PARAM_SORT = "sort";
    final static String sortBy = "stars";

    /**
     * Builds the URL used to query Github.
     *
     * @param githubSearchQuery The keyword that will be queried for.
     * @return The URL to use to query the weather server.
     */
    public static URL buildUrl(String githubSearchQuery) {
        URL url = null;
        try {
            Uri builtUri = Uri.parse(GITHUB_BASE_URL)
                    .buildUpon()
                    .appendQueryParameter(PARAM_QUERY, githubSearchQuery)
                    .appendQueryParameter(PARAM_SORT, sortBy)
                    .build();

            url = new URL(builtUri.toString());

        } catch(MalformedURLException ex){
           ex.printStackTrace();
        }

        return url;

    }

    /**
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if(scanner.hasNext()) {
                return scanner.next();
            } else {
                return null;
            }

        } finally {
            urlConnection.disconnect();
        }
    }



 }
