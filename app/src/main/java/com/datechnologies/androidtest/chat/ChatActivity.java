package com.datechnologies.androidtest.chat;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;
import com.datechnologies.androidtest.api.ChatLogMessageModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Screen that displays a list of chats from a chat log.
 */
public class ChatActivity extends AppCompatActivity {
    // JSON is java script object notation
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatLogMessageModel> list;
    String jstring;
    JSONObject jobject;
    JSONArray jarray;
    public static void start(Context context) {
        Intent starter = new Intent(context, ChatActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.chat_button);
        setContentView(R.layout.activity_chat);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //chatAdapter = new ChatAdapter(this, R.layout.rowlayout);

        recyclerView.setAdapter(chatAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false));
        new BackGround().execute();

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.

        // TODO: Retrieve the chat data from http://dev.datechnologies.co/Tests/scripts/chat_log.php
        // TODO: Parse this chat data from JSON into ChatLogMessageModel and display it.
    }
    // need background thread and Async task is resulting a string
    class BackGround extends AsyncTask<Void,Void,String> {
        String jurl;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jurl="http://dev.datechnologies.co/Tests/scripts/chat_log.php";
        }
        //this takes the params the, first in async task generic types
        @Override
        // this changes the JSON object into a string format
        protected String doInBackground(Void... voids) {
            try {
                URL url= new URL(jurl);
                HttpURLConnection http= (HttpURLConnection) url.openConnection();
                InputStream input= http.getInputStream();
                BufferedReader buffer= new BufferedReader (new InputStreamReader(input));
                StringBuilder builder = new StringBuilder();
                while((jurl=buffer.readLine()) != null){
                    builder.append(jurl+"\n");
                }
                buffer.close();
                input.close();
                http.disconnect();
                return builder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        // this takes the progress the second in async task generic types
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        // this takes the result the third in async task generic types
        // answer has the json object in the form of a string
        @Override
        protected void onPostExecute(String answer) {
            jstring=answer;
            JSONMethod();
        }
    }

    private void JSONMethod() {
        list= new ArrayList<>();
        if(jstring==null){
            Toast.makeText(getApplication(),"get JSON", Toast.LENGTH_LONG).show();
        }else{
            try {
                jobject= new JSONObject(jstring);
                jarray= jobject.getJSONArray("data");
                int counter=0;
                String id,name, url, message;
                //you are traversing the JSON array and passing an object for the chat class
                while(counter<jarray.length()){
                    JSONObject object =  jarray.getJSONObject(counter);
                    id = object.getString("user_id");
                    int num= Integer.parseInt(id);
                    name = object.getString("name");
                    url = object.getString("avatar_url");
                    message = object.getString("message");
                    ChatLogMessageModel model = new ChatLogMessageModel(num, url, name, message);
                    counter++;
                    list.add(model);
                }
                chatAdapter = new ChatAdapter(list, getApplicationContext());
                recyclerView.setAdapter(chatAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
