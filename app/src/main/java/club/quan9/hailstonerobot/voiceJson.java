package club.quan9.hailstonerobot;

import com.iflytek.cloud.RecognizerResult;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by wily on 2018/5/14.
 */

public class voiceJson
{
    public static String getResult(RecognizerResult result)
    {
        String json=result.getResultString();
        String get="";
        try
        {
            JSONObject jsonObject=new JSONObject(json);
            JSONArray textArray=jsonObject.getJSONArray("ws");
            for(int i=0;i<textArray.length();i++)
            {
                JSONArray textJson=textArray.getJSONObject(i).getJSONArray("cw");
                get=get+textJson.getJSONObject(0).getString("w");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return get;
    }

    public static boolean getLs(RecognizerResult result)
    {
        String json=result.getResultString();
        boolean ls=false;
        try
        {
            JSONObject jsonObject=new JSONObject(json);
            ls=jsonObject.getBoolean("ls");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return ls;
    }
}
