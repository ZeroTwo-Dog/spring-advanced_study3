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


  @Test
  void typeExactMatch() {
    pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
    Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();

  }


  @Test
  void typeMatchSuperType() {
    //부모타입으로 해도 매칭이됨
    pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
    Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();

  }

  @Test
  void typeMatchInternal() throws NoSuchMethodException {
    //MemberServiceImpl 하위 내부 메소드는 부모에 없는 메소드는 불가능하다
    pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
    Method internal = MemberServiceImpl.class.getMethod("internal", String.class);

    Assertions.assertThat(pointcut.matches(internal, MemberServiceImpl.class)).isFalse();

  }


  //String 타입의 파라미터 허용
  // (String)
  @Test
  void argsMatch() {
    pointcut.setExpression("execution(* *(String))");
    Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();


  }


  // 파라미터 없음
  // ()
  @Test
  void argsMatchNoArgs() {
    pointcut.setExpression("execution(* *())");
    Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();


  }


  // 정확히 하나의 파라미터 허요으, 모든 타입 허용
  // (Xxx)
  @Test
  void argsMatchStar() {
    pointcut.setExpression("execution(* *(*))");
    Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  // 파라미터 갯수와 무관하게 모든 파라미터 허용
  // (Xxx), (Xxx, xxX), ()
  @Test
  void argsMatchAll() {
    pointcut.setExpression("execution(* *(..))");
    Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  // String 타입으로 시작, 파라미터 갯수와 무관하게 모든 파라미터 허용
  // (String), (String, xxX() ...
  @Test
  void argsMatchComplex() {
    pointcut.setExpression("execution(* *(String, ..))");
    Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }
}
