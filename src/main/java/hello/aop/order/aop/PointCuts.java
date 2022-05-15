package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by park on 2022/05/15.
 */
public class PointCuts {

  @Pointcut("execution(* hello.aop.order..*(..))")
  public void allOrders(){} // pointcut signature


  //클래스 이름 패턴이 *Service
  @Pointcut("execution(* *..*Service.*(..))")
  public void allService(){} // pointcut signature


  @Pointcut("allOrders() && allService()")
  public void orderAndService() {}

}
