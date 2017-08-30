/*
 * 제목:reflection을 이용한 동적함수처리
 * 작성자: 박성완(adsloader@naver.com)
 * 작성일: 2012.04
 * 목적  : 예제
 * */
import java.util.HashMap;
import java.lang.reflect.Method;
class Event{
    public int STATE = 0; 
    public Event (int i ){STATE = i;}
}

public class EventTest2 {
    public static int START = 0;
    public static int STOP  = 1;
    public static int PAUSE = 2;
 
    //----------------------------------------------------
    public void OnStart(Event e)
    {
        System.out.println("Start");
    }
    
    public void OnStop(Event e)
    {
        System.out.println("Stop");
    }
    
    public void OnPause(Event e)
    {
        System.out.println("Pause");
    }
    
    //----------------------------------------------------
    HashMap m = new HashMap();
    
    public void Process(Event e) throws Exception
    {
        Class<?> c = Class.forName("EventTest2");
     
        String sFunction = (String)m.get(e.STATE);
        Method m = c.getDeclaredMethod(sFunction, Event.class);
        m.invoke(this, e);           
    }
    
    public EventTest2()
    {
        initProcess(); 
    }
    
    public void initProcess()
    {
        m.put(START, "OnStart");
        m.put(STOP,  "OnStop");
        m.put(PAUSE, "OnPause");
    }
    
    //-----------------------------------------------------
    
    static public void  main(String[] args)
    {
        EventTest2 o = new EventTest2();
        try {
            o.Process(new Event(EventTest2.START));
            o.Process(new Event(EventTest2.STOP));
            o.Process(new Event(EventTest2.PAUSE));
        } catch (Exception e) {
            e.printStackTrace();
        }
     
    }
}
