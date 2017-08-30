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
