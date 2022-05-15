package hello.aop;

import hello.aop.order.OrderRepository;
import hello.aop.order.OrderService;
import hello.aop.order.aop.AspectV1;
import hello.aop.order.aop.AspectV2;
import hello.aop.order.aop.AspectV3;
import hello.aop.order.aop.AspectV4;
import hello.aop.order.aop.AspectV5Order.LogAspect;
import hello.aop.order.aop.AspectV5Order.TxAspect;
import hello.aop.order.aop.AspectV6Advice;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * Created by park on 2022/05/15.
 */
@Slf4j
@SpringBootTest
//@Import(AspectV2.class )
//@Import(AspectV4.class )
@Import(AspectV6Advice.class )

// AOP 순서정리
//@Import({LogAspect.class, TxAspect.class})
public class AopTest {


  @Autowired
  OrderRepository orderRepository;

  @Autowired
  OrderService orderService;

  @Test
  public void aopInfo() throws Exception {
    log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
    log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));
  }
  
  @Test
  public void success() throws Exception {
    orderService.orderItem("itemA");
  }


  @Test
  public void exception() throws Exception {

    Assertions.assertThatThrownBy(() -> orderService.orderItem("ex"))
        .isInstanceOf(IllegalStateException.class);
  }
}
