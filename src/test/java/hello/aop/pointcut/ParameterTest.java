package hello.aop.pointcut;

import hello.aop.member.MemberService;
import hello.aop.member.annotation.ClassAop;
import hello.aop.member.annotation.MethodAop;
import hello.aop.pointcut.ParameterTest.ParameterAspect;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * Created by park on 2022/05/22.
 */
@Slf4j
@Import(ParameterAspect.class)
@SpringBootTest
public class ParameterTest {

  @Autowired
  MemberService memberService;

  @Test
  void success () {
    log.info("memberServiceProxy = {}", memberService.getClass());
    memberService.hello("helloA");
  }

  @Slf4j
  @Aspect
  static class ParameterAspect {

    @Pointcut("execution(* hello.aop.member..*.*(..))")
    private void allMatch() {}

    @Around("allMatch()")
    public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
      Object ars1 = joinPoint.getArgs()[0];
      log.info("[logArgs1]{}, args={}", joinPoint.getSignature(), ars1);
      return joinPoint.proceed();
    }



    @Around("allMatch() && args(arg,..)")
    public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
      log.info("[logArgs2]{}, args={}", joinPoint.getSignature(), arg);
      return joinPoint.proceed();
    }


    @Before("allMatch() && args(arg,..)")
    public void logArgs3(String arg) throws Throwable {
      log.info("[logArgs3], args={}", arg);
    }

    @Before("allMatch() && this(obj)")
    public void thisArgs(JoinPoint joinPoint, MemberService obj) throws Throwable {
      //[this]String hello.aop.member.MemberServiceImpl.hello(String), args=class hello.aop.member.MemberServiceImpl$$EnhancerBySpringCGLIB$$318875fe
      //스프링 컨테이너에 들어간 정보
      log.info("[this]{}, obj={}", joinPoint.getSignature(), obj.getClass());
    }


    @Before("allMatch() && target(obj)")
    public void targetArgs(JoinPoint joinPoint, MemberService obj) throws Throwable {
      //[target]String hello.aop.member.MemberServiceImpl.hello(String), args=class hello.aop.member.MemberServiceImpl
      //실제로 사용하는 클래스 정보
      log.info("[target]{}, obj={}", joinPoint.getSignature(), obj.getClass());
    }

    @Before("allMatch() && @target(annotation)")
    public void atTarget(JoinPoint joinPoint, ClassAop annotation) throws Throwable {
      log.info("[@target]{}, obj={}", joinPoint.getSignature(), annotation);
    }

    @Before("allMatch() && @within(annotation)")
    public void atWithin(JoinPoint joinPoint, ClassAop annotation) throws Throwable {
      log.info("[@within]{}, obj={}", joinPoint.getSignature(), annotation);
    }

    @Before("allMatch() && @annotation(annotation)")
    public void atWithin(JoinPoint joinPoint, MethodAop annotation) throws Throwable {
      log.info("[@annotation]{}, annotation.value={}", joinPoint.getSignature(), annotation.value());
    }
  }




}
