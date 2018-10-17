package hello.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BuildInfo {

  @Value("${build.commit}")
  private String commit;

  @Value("${build.branch}")
  private String branch;

  @Value("${build.tag}")
  private String tag;

  @Value("${build.time}")
  private String time;

  @Value("${build.version}")
  private String version;

  @Override
  public String toString() {
    return "commit=" + commit + ";branch=" + branch + ";tag=" + tag + ";time=" + time + ";version=" + version;
  }
}
