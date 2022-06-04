package hello.aop.internalcall;

import static org.junit.jupiter.api.Assertions.*;

import hello.aop.internalcall.aop.CallLogAspect;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


@Import(CallLogAspect.class)
@SpringBootTest
@Slf4j
class CallServiceV0Test {

  @Autowired
   CallServiceV0 callServiceV0;

  @Test
  public void external() {
    callServiceV0.external();
  }

  @Test
  public void internal() {
    callServiceV0.internal();
  }
}