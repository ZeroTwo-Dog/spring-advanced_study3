package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

/**
 * Created by park on 2022/05/15.
 */
@Slf4j
public class AspectV5Order {

  // AOP 순서정리

  @Aspect
  @Order(2)
  public static class LogAspect {
    @Around("PointCuts.allOrders()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
      log.info("[logV3] {}", joinPoint.getSignature());
      return joinPoint.proceed();
    }
  }


  @Aspect
  @Order(1)
  public static class TxAspect {

    //hello. aop.order 패키지 안에 있으면서 클래스 패턴이 Service 인 것
    @Around("PointCuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
      try {
        log.info("[트랙잭션 시작] {}", joinPoint.getSignature());
        Object result = joinPoint.proceed();
        log.info("[트랙잭션 커밋] {}", joinPoint.getSignature());
        return result;
      } catch (Exception e) {
        log.info("[트랙잭션 롤백] {}", joinPoint.getSignature());
        throw e;
      } finally {
        log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
      }
    }
  }
}
