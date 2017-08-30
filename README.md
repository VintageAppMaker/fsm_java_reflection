# java에서 fsm구현을 위해 reflection 사용
> FSM(Finite State Machine)을 구현하려면 Function TABLE을 만드는 것은 거의 필수요소이다. 

그러나 자바에서는 펑션포인터를 쓸 수 없다. 그래서 함수를 동적처리하기 위해 자주 사용하는 방법이 inner Class를 이용하는 방법이다. 문제는 코드가 지저분해지고 가독성이 떨어진다는 것이다.

[원본블로그](http://blog.naver.com/adsloader/50139711121)


~~~java
import java.util.HashMap;

class Event{
    public int STATE = 0; 
    public Event (int i ){STATE = i;}
}

public class EventTest1 {
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
    
    public void Process(Event e)
    {
        FSM f = (FSM)m.get(e.STATE);
        f.Execute(e);
    }
    
    public EventTest1()
    {
        initProcess(); 
    }
    
    public void initProcess()
    {
        m.put(START, 
            new FSM(){
                public void Execute(Event e){
                    OnStart(e);
                };    
            }
        );
     
        m.put(STOP, 
            new FSM(){
                public void Execute(Event e){
                    OnStop(e);
                };    
            }
        );
     
     
        m.put(PAUSE, 
            new FSM(){
                public void Execute(Event e){
                    OnPause(e);
                };    
             }
        );
    }
    
    class FSM
    {
        public void Execute(Event e){};
    }
    //-----------------------------------------------------
 
   static public void  main(String[] args)
    {
        EventTest1 o = new EventTest1();
        o.Process(new Event(EventTest1.START));
        o.Process(new Event(EventTest1.STOP));
        o.Process(new Event(EventTest1.PAUSE));
     
    }
}

~~~

그러나 reflection을 사용하면 innerClass를 사용할 필요없이 코딩이 깔끔하게 처리된다.

~~~java
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

~~~
