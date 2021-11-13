package org.yao;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;
import java.util.concurrent.Executor;

@RequestMapping("/api/config")
@RestController
public class ApiController {
  private static String serverAddr = "localhost";
  private static String dataId = "example.properties";
  private static String group = "DEFAULT_GROUP";

  private Listener listener;
  private ConfigService configService;

  @GetMapping("index")
  @ResponseBody
  public String index() {
    return "OK";
  }

  public ApiController() throws NacosException {
    Properties properties = new Properties();
    properties.put("serverAddr", serverAddr);
    configService = NacosFactory.createConfigService(properties);
  }

  @DeleteMapping("listener")
  @ResponseBody
  public String deleteConfigListener() {
    configService.removeListener(dataId, group, listener);
    System.out.printf("listener deleted\n");
    return "OK";
  }

  @PostMapping("listener")
  @ResponseBody
  public void createConfigListener() throws NacosException {
    String content = configService.getConfig(dataId, group, 5000);
    System.out.printf("initial config: %s\n", content);

    listener =
        new Listener() {
          @Override
          public void receiveConfigInfo(String configInfo) {
            System.out.printf("updated config part: %s\n", configInfo);
          }

          @Override
          public Executor getExecutor() {
            return null;
          }
        };

    configService.addListener(dataId, group, listener);
  }
}
