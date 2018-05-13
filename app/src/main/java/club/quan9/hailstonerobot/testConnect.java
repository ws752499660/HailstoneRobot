package club.quan9.hailstonerobot;

import android.os.Message;
import android.util.Log;

import android.os.Handler;

import org.json.JSONObject;

/**
 * Created by wily on 2018/5/12.
 */

public class testConnect
{
    private String get;
    private static Handler handler;

    public static void run()
    {
        String test="华侨大学计算机学院";
        JSONObject jsonObject=robotJson.createJson(test);
        Log.d("test",String.valueOf(jsonObject));
        //connectRobotOld.getRe(robotJson.createJson(test));
        handler=new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                if(msg.what==0x2333)
                {
                    String get = msg.getData().getString("get");
                    Log.d("test-2", get);
                }
            }
        };
        connectRobot connect=new connectRobot(handler,jsonObject);
        connect.connect();
        Log.d("test", "run: run so fast");
    }
}
