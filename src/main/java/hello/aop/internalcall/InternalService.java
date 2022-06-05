package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 대안3 구조를 변경
 * Created by park on 2022/06/04.
 */
@Slf4j
@Component
public class InternalService {


  public void internal () {
    log.info("call internal");
  }
}