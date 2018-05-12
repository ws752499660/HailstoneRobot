package club.quan9.hailstonerobot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private List<Msg> msgList=new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.about:
                Toast.makeText(this,"关于页面正在施工中",Toast.LENGTH_SHORT).show();
                break;
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
        initText();
        final EditText inputText=(EditText) findViewById(R.id.input_text);
        Button send=(Button) findViewById(R.id.send);
        final RecyclerView msgRecyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        final MsgAdapter msgAdapter=new MsgAdapter(msgList);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        msgRecyclerView.setAdapter(msgAdapter);
        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String content=inputText.getText().toString();
                if(!content.equals(""))
                {
                    Msg msg=new Msg(content,Msg.TYPE_SENT);
                    msgList.add(msg);
                    msgAdapter.notifyItemInserted(msgList.size()-1);
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    inputText.setText("");
                }
            }
        });
    }

    private void initText()
    {
        Msg msg1=new Msg("我好。",Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2=new Msg("听不懂人话还是怎么着？",Msg.TYPE_RECEIVED);
        msgList.add(msg2);
        Msg msg3=new Msg("傻逼，滚",Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }
}
