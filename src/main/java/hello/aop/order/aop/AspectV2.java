package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by park on 2022/05/15.
 */
@Aspect
@Slf4j
public class AspectV2 {

  @Pointcut("execution(* hello.aop.order..*(..))")
  private void allOrders(){} // pointcut signature

  @Around("allOrders()")
  public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("[logV2] {}", joinPoint.getSignature());
    return joinPoint.proceed();
  }



}
