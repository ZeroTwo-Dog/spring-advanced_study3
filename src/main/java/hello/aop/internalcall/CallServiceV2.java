package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

/**
 * Created by park on 2022/06/04.
 */
@Slf4j
@Component
public class CallServiceV2 {

  private final ObjectProvider<CallServiceV2> callServiceProvider;

  public CallServiceV2(
      ObjectProvider<CallServiceV2> callServiceProvider) {
    this.callServiceProvider = callServiceProvider;

  }


  public void external () {
    log.info("call external");
    //싱글톤으로 컨테이너에 적재된 값을 꺼내 쓰는것
    CallServiceV2 callServiceV2 = callServiceProvider.getObject();
    callServiceV2.internal(); //내부 메소드 호출
  }

  public void internal () {
    log.info("call internal");
  }
}