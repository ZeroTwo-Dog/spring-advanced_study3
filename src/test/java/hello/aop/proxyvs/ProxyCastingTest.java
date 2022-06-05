package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

/**
 * Created by park on 2022/06/05.
 */
@Slf4j
public class ProxyCastingTest {


  @Test
  public void jdkProxy() {
    MemberServiceImpl target = new MemberServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.setProxyTargetClass(false); //JDK 동적 프록

    //프록시를 인터페이스로 캐스팅 성공
    MemberService serviceProxy = (MemberService) proxyFactory.getProxy();

    //구체타입으로 캐스팅이 불가
    //JDK 동적 프록시는 구체클래스로 만들어도 바라보는게 인터페이스 이기때문에 구체클래스를 알지 못한다.
    Assertions.assertThrows(ClassCastException.class, () -> {
      MemberServiceImpl proxy = (MemberServiceImpl) proxyFactory.getProxy();


    });

  }

  @Test
  public void cglibProxy() {
    MemberServiceImpl target = new MemberServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.setProxyTargetClass(true); //CLIB 동적 프록

    //프록시를 인터페이스로 캐스팅 성공
    MemberService serviceProxy = (MemberService) proxyFactory.getProxy();

    //구체클래스 기반으로 프록시를 만들기때문에 캐스팅 성공
    MemberServiceImpl proxy = (MemberServiceImpl) proxyFactory.getProxy();

  }

}
