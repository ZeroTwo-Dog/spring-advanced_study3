package hello.aop.member;

import hello.aop.member.annotation.ClassAop;
import hello.aop.member.annotation.MethodAop;
import org.springframework.stereotype.Component;

/**
 * Created by park on 2022/05/22.
 */
@ClassAop
@Component
public class MemberServiceImpl implements MemberService {

  @Override
  @MethodAop("test value ")
  public String hello(String param) {
    return "ok";
  }


  public String internal(String param) {
    return "ok";
  }
}
