package hello.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

/**
 * 대안3 구조를 변경
 * Created by park on 2022/06/04.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV3 {

  private final InternalService internalService;



  public void external () {
    log.info("call external");
    internalService.internal(); //내부 메소드 호출
  }

}