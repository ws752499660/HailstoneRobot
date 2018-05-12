package club.quan9.hailstonerobot;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wily on 2018/5/12.
 * 本类是为了创建与图灵机器人对接需要json文件的类(发送与接收)
 * 图灵机器人api文档地址：https://www.kancloud.cn/turing/web_api/522992
 */

public class robotJson
{
    final static int GET=2333;

    public static JSONObject createJson(String text)
    {
        JSONObject jsonObject=new JSONObject();
        if(text.length()>128 && text.length()<1)
        {
            Log.e("robotJson", "createJson: 输入的文本超过128字节或小于1字节");
            return null;
        }
        try
        {
            jsonObject.put("reqType", 0);
            JSONObject perception=new JSONObject();
            JSONObject perceptionItem=new JSONObject();
            JSONObject userInfo=new JSONObject();
            perceptionItem.put("text",text);
            perception.put("inputText",perceptionItem);
            jsonObject.put("perception",perception);
            userInfo.put("apiKey","ea045080a882490e924c2ea7e224d019");
            userInfo.put("userId","test0");
            jsonObject.put("userInfo",userInfo);

        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static String getTextFromJson(String response)
    {
        String text="";
        try
        {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray results=jsonObject.getJSONArray("results");
            int i=0;
            for(;i<results.length();i++)
            {
                JSONObject result=results.getJSONObject(i);
                JSONObject values=result.getJSONObject("values");
                text=values.getString("text")+text;
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return text;
    }
}
