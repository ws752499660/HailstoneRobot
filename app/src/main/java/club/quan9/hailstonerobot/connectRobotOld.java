package club.quan9.hailstonerobot;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wily on 2018/5/12.
 * 用来与图灵机器人服务器通讯的类
 */

public class connectRobotOld
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
    private static Handler mhandler;
    public static String str;

    private static void chuan(String text)
    {
        str = text;
    }

    public static void getRe(JSONObject jsonObject)
    {
        final String[] temp = new String[1];
        connectRobotOld.connect(jsonObject);
        mhandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                if (msg.what == 0x2333)
                {
                    String get = msg.getData().getString("get");
                    Log.d("test-1", get);
                }
            }
        };
    }

    public static void connect(final JSONObject jsonObject)
    {
        String url = "http://openapi.tuling123.com/openapi/api/v2";
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        final String json = String.valueOf(jsonObject);
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call=client.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                try
                {
                    String get = "";
                    get = response.body().string();
                    Bundle bundle = new Bundle();
                    bundle.putString("get", get);
                    Message msg = new Message();
                    msg.what = 0x2333;
                    msg.setData(bundle);
                    mhandler.sendMessage(msg);
                } catch (
                        Exception e)

                {
                    e.printStackTrace();
                }
            }
        });
    }
}
