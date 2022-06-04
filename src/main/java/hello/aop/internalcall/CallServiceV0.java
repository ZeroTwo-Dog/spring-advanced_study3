package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by park on 2022/06/04.
 */
@Slf4j
@Component
public class CallServiceV0 {

  public void external () {
    log.info("call external");
    internal(); //내부 메소드 호출 (this)
  }

  public void internal () {
    log.info("call internal");
  }
}