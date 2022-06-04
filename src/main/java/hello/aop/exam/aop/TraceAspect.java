package hello.aop.exam.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Created by park on 2022/06/04.
 */
@Slf4j
@Aspect
public class TraceAspect {

  @Before("@annotation(hello.aop.exam.annotation.Trace)")
  public void doTrace(JoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    log.info("[trace] {} agrs {}",joinPoint.getSignature(), args );
  }

}
