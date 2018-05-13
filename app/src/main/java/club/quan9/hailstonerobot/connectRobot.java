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
 * Created by wily on 2018/5/13.
 */

public class connectRobot
{
    private Handler handler;
    private static String url="http://openapi.tuling123.com/openapi/api/v2";
    private JSONObject jsonObject;

    public connectRobot(Handler handler, JSONObject jsonObject)
    {
        this.handler=handler;
        this.jsonObject=jsonObject;
    }

    public void connect()
    {
        OkHttpClient client=new OkHttpClient();
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        final String json = String.valueOf(jsonObject);
        final RequestBody body = RequestBody.create(JSON, json);
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
                String get=response.body().string();
                Bundle bundle=new Bundle();
                bundle.putString("get",get);
                Message message=Message.obtain();
                message.what=0x2333;
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }
}
