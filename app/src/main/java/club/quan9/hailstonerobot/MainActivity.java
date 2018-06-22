package club.quan9.hailstonerobot;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.os.PatternMatcher;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private List<Msg> msgList = new ArrayList<>();
    EditText inputText;
    RecyclerView msgRecyclerView;
    MsgAdapter msgAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.about:
            {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
            }
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //用于Android 6.0后申请动态权限的检查和申请
        checkPermission();

        Button voiceBtn = (Button) findViewById(R.id.voice_btn);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "= 5af68519");
        voiceBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                voice();
            }
        });


        initText();
        inputText = (EditText) findViewById(R.id.input_text);
        Button send = (Button) findViewById(R.id.send);
        msgRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        msgAdapter = new MsgAdapter(msgList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        msgRecyclerView.setAdapter(msgAdapter);
        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String content = inputText.getText().toString();
                Log.d("alphaTest", content);
                if (!content.equals(""))
                {
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg);
                    msgAdapter.notifyItemInserted(msgList.size() - 1);
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    inputText.setText("");
                    JSONObject jsonObject = robotJson.createJson(content);
                    connectRobot connect = new connectRobot(handler, jsonObject);
                    connect.connect();
                }
            }
        });
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message getMsg)
        {
            if (getMsg.what == 0x2333)
            {
                String get = getMsg.getData().getString("get");
                Log.d("alphaTest", get);
                get = robotJson.getTextFromJson(get);
                Log.d("alphaTest", get);
                Msg msg = new Msg(get, Msg.TYPE_RECEIVED);
                msgList.add(msg);
                msgAdapter.notifyItemInserted(msgList.size() - 1);
                msgRecyclerView.scrollToPosition(msgList.size() - 1);
            }
        }
    };

    private void initText()
    {
        Msg msg1 = new Msg("欢迎和wily开发的大冰雹聊天━(*｀∀´*)ノ", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        /*Msg msg2=new Msg("听不懂人话还是怎么着？",Msg.TYPE_RECEIVED);
        msgList.add(msg2);
        Msg msg3=new Msg("傻逼，滚",Msg.TYPE_RECEIVED);
        msgList.add(msg3);*/
    }

    private void voice()
    {
        RecognizerDialog dialog = new RecognizerDialog(this, null);
        dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin");

        dialog.setListener(new RecognizerDialogListener()
        {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b)
            {
                String get=voiceJson.getResult(recognizerResult);
                Log.d("voice", recognizerResult.getResultString());
                String orginText=inputText.getText().toString();
                inputText.setText(orginText+get);
                if(voiceJson.getLs(recognizerResult))
                {
                    String content = inputText.getText().toString();
                    if (!content.equals(""))
                    {
                        Msg msg = new Msg(content, Msg.TYPE_SENT);
                        msgList.add(msg);
                        msgAdapter.notifyItemInserted(msgList.size() - 1);
                        msgRecyclerView.scrollToPosition(msgList.size() - 1);
                        inputText.setText("");
                        JSONObject jsonObject = robotJson.createJson(content);
                        connectRobot connect = new connectRobot(handler, jsonObject);
                        connect.connect();
                    }
                }
            }

            @Override
            public void onError(SpeechError speechError)
            {

            }
        });
        dialog.show();
        Toast.makeText(this, "请开始说话", Toast.LENGTH_SHORT).show();
    }

    //用于Android 6.0后申请动态权限的检查和申请
    private void checkPermission()
    {
        //检查是否已经赋予录音权限
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)!=
                PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECORD_AUDIO))
            {
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},1);
            }
        }
    }

}
