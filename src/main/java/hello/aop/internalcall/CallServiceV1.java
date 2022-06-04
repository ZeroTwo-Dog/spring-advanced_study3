package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by park on 2022/06/04.
 */
@Slf4j
@Component
public class CallServiceV1 {

  private CallServiceV1 callServiceV1;

  /*
  생성자로 자기 자신을 주입하면 내부순환참조빈주입 에러가 발생한다.
  - 자기 자신이 아직 생성되지도 않은 상태로 자기 자신을 줄수없다.
  세터 주입을 하면 내부순환참조주입 에러가 발생하지 않는다.
   */
  @Autowired
  public void setCallServiceV1(CallServiceV1 callServiceV1) {
    log.info("callServiceV1 = {}", callServiceV1.getClass());
    this.callServiceV1 = callServiceV1;
  }


  public void external () {
    log.info("call external");
    callServiceV1.internal(); //내부 메소드 호출
  }

  public void internal () {
    log.info("call internal");
  }
}