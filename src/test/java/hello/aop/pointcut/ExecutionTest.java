package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

/**
 * Created by park on 2022/05/22.
 */
@Slf4j
public class ExecutionTest {

  AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
  Method helloMethod;

  @BeforeEach
  public void init() throws NoSuchMethodException {
    helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
  }


  @Test
  void printMethod () {
    //helloMethod = public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
    log.info("helloMethod = {}", helloMethod);
  }


  @Test
  void exactMatch() {
    //helloMethod = public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
    //execution 표현식
    //? 생략가능
    //접근제어자? 반환타입 선언타입? 메소드이름 파라미터
    //접근제어자 : public
    //반환타입: String
    //선업타입?: hello.aop.member.MemberServiceImpl
    //메소드이름: hello
    //파라미터타입: (String)
    pointcut.setExpression("execution(public String hello.aop.member.MemberServiceImpl.hello(String))");

    Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }


  @Test
  void allMatch() {
    //접근제어자 : 생략
    //반환타입: * 전체
    //선업타입?: 생략
    //메소드이름: 전체
    //파라미터타입: (..) 무관
    pointcut.setExpression("execution(* *(..))");


    Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();

  }


  @Test
  void nameMatch() {
    pointcut.setExpression("execution(* hello(..))");
    Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();

  }

  @Test
  void nameMatchStar1() {
    pointcut.setExpression("execution(* hel*(..))");
    Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();

  }

  @Test
  void nameMatchStar2() {
    pointcut.setExpression("execution(* *el*(..))");
    Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();

  }


  @Test
  void nameMatchStar3() {
    pointcut.setExpression("execution(* *abc*(..))");
    Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();

  }


  @Test
  void packageMatch1() {
    pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");
    Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();

  }

  @Test
  void packageMatch2() {
    pointcut.setExpression("execution(* hello.aop.member.*.*(..))");
    Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();

  }

  @Test
  void packageMatchIsFalse() {
    pointcut.setExpression("execution(* hello.aop.*.*(..))");
    Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();

  }


  @Test
  void packageMatchSubPackage() {
    pointcut.setExpression("execution(* hello.aop..*.*(..))");
    Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();

  }
}
