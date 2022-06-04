package hello.aop.exam;

import hello.aop.exam.annotation.Trace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by park on 2022/06/04.
 */
@Service
@RequiredArgsConstructor
public class ExamService {

  private final ExamRepository examRepository;

  @Trace
  public void request (String itemId) {
    examRepository.save(itemId);
  }

  @Trace
  public void request (String itemId, String item2) {
    examRepository.save(itemId);
  }
}
