package club.quan9.hailstonerobot;

import android.os.Message;
import android.util.Log;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by wily on 2018/5/12.
 */

public class testConnect
{
    private String get;

    public static void run()
    {
        String test="华侨大学计算机学院";
        Log.d("test",String.valueOf(robotJson.createJson(test)));
        String get=connectRobot.getRe(robotJson.createJson(test));
        if(get!=null)
            Log.d("test", get);
        String getText=robotJson.getTextFromJson(get);
        Log.d("test", getText);
    }
}
