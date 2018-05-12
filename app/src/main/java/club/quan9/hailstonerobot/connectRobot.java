package club.quan9.hailstonerobot;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wily on 2018/5/12.
 * 用来与图灵机器人服务器通讯的类
 */

public class connectRobot
{
    /*public static String connect(final JSONObject sent)
    {
        String get = null;
        try
        {
            String content = String.valueOf(sent);
            String url = "http://openapi.tuling123.com/openapi/api/v2";
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "json");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Length", String.valueOf(content.length()));
            OutputStream os = connection.getOutputStream();
            os.write(content.getBytes());
            os.close();

            connection.setDoInput(true);
            InputStream is = connection.getInputStream();
            try
            {
                byte[] data = new byte[is.available()];
                is.read(data);
                get = new String(data, "UTF-8");
            }catch (Exception e)
            {

            }
        } catch (Exception e)
        {

        }
        return get;
    }*/
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<String>();
    private static Handler mhandler;


    public static String getRe(JSONObject jsonObject)
    {
        final String[] temp=new String[1];
        mhandler=new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                if(msg.what==0x2333)
                {
                    String get=msg.getData().getString("get");
                    //这里肯定可以get到get
                }
            }
        };
        connectRobot.connectRobot(jsonObject);
        if(threadLocal.get()!=null)
            return (String)threadLocal.get();
        else
            return "lalaal";
    }

    public static void connectRobot(final JSONObject jsonObject)
    {
        new Thread()
        {
            @Override
            public void run()
            {
                String get = "";
                String url = "http://openapi.tuling123.com/openapi/api/v2";
                final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                final String json = String.valueOf(jsonObject);
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                try

                {
                    Response response = client.newCall(request).execute();
                    get = response.body().string();
                    Bundle bundle=new Bundle();
                    bundle.putString("get",get);
                    Message msg=new Message();
                    msg.what=0x2333;
                    msg.setData(bundle);
                    mhandler.sendMessage(msg);
                    threadLocal.set(get);
                }catch(
                        Exception e)

                {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
