package com.mummyding.app.funnychat.Tookit;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mummyding.app.funnychat.Application.App;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

/**
 * Created by mummyding on 15-8-31.
 */
public class IntentHelper {
    private static UrlEncodedFormEntity entity;
    private static HttpClient httpClient = new DefaultHttpClient();
    private static HttpPost httpPost;
    private static HttpResponse httpResponse;
    private static String res;

    public static String getData(final String url, final List<NameValuePair> valuePairs){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                httpPost = new HttpPost(url);
                try {
                    entity = new UrlEncodedFormEntity(valuePairs,"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                httpPost.setEntity(entity);
                try {
                    httpResponse = httpClient.execute(httpPost);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("err",e.toString());
                }
                if(httpResponse.getStatusLine().getStatusCode() == 200){
                    HttpEntity httpEntity = httpResponse.getEntity();
                    try {
                        res = EntityUtils.toString(httpEntity,"utf-8");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }

}