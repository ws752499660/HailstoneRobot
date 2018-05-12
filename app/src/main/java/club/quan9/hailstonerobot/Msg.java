package club.quan9.hailstonerobot;

/**
 * Created by wily on 2018/5/12.
 */

public class Msg
{
    final static public int TYPE_RECEIVED=0;
    final static public int TYPE_SENT=1;

    private String content;

    private int type;

    public Msg(String content,int type)
    {
        this.content=content;
        this.type=type;
    }

    public String getContent()
    {
        return content;
    }

    public int getType()
    {
        return type;
    }
}
