# java에서 fsm구현을 위해 reflection 사용
> FSM(Finite State Machine)을 구현하려면 Function TABLE을 만드는 것은 거의 필수요소이다. 

그러나 자바에서는 펑션포인터를 쓸 수 없다. 그래서 함수를 동적처리하기 위해 자주 사용하는 방법이 inner Class를 이용하는 방법이다. 문제는 코드가 지저분해지고 가독성이 떨어진다는 것이다.

[원본블로그](http://blog.naver.com/adsloader/50139711121)
