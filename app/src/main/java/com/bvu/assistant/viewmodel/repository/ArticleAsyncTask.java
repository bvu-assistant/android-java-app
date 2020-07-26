package com.bvu.assistant.viewmodel.repository;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bvu.assistant.view.activities.NewsDetailActivity;
import com.bvu.assistant.viewmodel.repository.ArticleDetail;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ArticleAsyncTask extends AsyncTask<String, String, String> {
    private final String TAG = "ArticleAsyncTask";
    NewsDetailActivity activity;
    ArticleDetail result;

    public ArticleAsyncTask(NewsDetailActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();
        activity.refresher.setRefreshing(true);
    }


    @Override
    protected String doInBackground(String... params) {
        String result = null;

        try {
            Log.i(TAG, "doInBackground: " + params[0].split("=")[1]);
            URL url = new URL("https://bvu-news-getter.herokuapp.com/details/" + params[0].split("=")[1]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String temp;

                while ((temp = reader.readLine()) != null) {
                    stringBuilder.append(temp);
                }
                result = stringBuilder.toString();
            }
            else {
                result = "error";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "doInBackground: ", e);
        }
        return result;
    }

    @Override
    public void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            Log.d(TAG, "onPostExecute() returned: " + s);
            JSONArray array = new JSONArray(s);
//            Log.i(TAG, "AsyncTask completed: Length = " + array.length());
//            Log.i(TAG, "AsyncTask completed: Length = " + array.get(0));
//            Log.i(TAG, "AsyncTask completed: Length = " + array.get(1));

            result = new ArticleDetail();
            result.setFullMessage(array.getJSONObject(0).getString("fullmessage"));

            for (int i = 1; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                Iterator<?> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String currKey = (String) keys.next();
                    result.links.put(currKey, jsonObject.getString(currKey));
                }
            }

            activity.refresher.setRefreshing(false);
//            activity.setData(result);
        }
        catch (Exception e)
        {
            activity.refresher.setRefreshing(false);
            Toast.makeText(activity, "Có lỗi trong quá trình xử lý thông tin", Toast.LENGTH_LONG).show();

            Log.i("AsyncTask", "AsyncTask failed" + e.getMessage());
            e.printStackTrace();

            Log.e(TAG, "onPostExecute: ", e);
        }
    }
}