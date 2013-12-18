package com.cwat.util;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Created by alexthornburg on 12/9/13.
 */
public class UniversalHTTP extends AsyncTask<String,Void,UserDAO>{

    UserDAO response;
    public UniversalHTTP(){

    }

    @Override
    protected UserDAO doInBackground(String... server) {
        String result = "";

        try{
            final String url = server[0];
            RestTemplate restTemplate = new RestTemplate();
            MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
            Log.d("Alex-nades","Mapped");
            messageConverter.setSupportedMediaTypes(Collections.singletonList(new MediaType("text", "javascript")));
            restTemplate.getMessageConverters().add(messageConverter);
            Log.d("Alex-nades","Message");
            response = restTemplate.getForObject(url, UserDAO.class);
            Log.d("Alex-nades","Message");
        }catch(Exception e){
            Log.i("Error", "Error Recieving DAO");
        }


        return response;
    }


    protected void onPostExecute(){
        super.onPostExecute(response);
    }


}