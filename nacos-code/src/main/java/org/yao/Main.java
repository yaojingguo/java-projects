package org.yao;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;

import java.util.Properties;

public class Main {
  private static String serverAddr = "localhost";
  private static String dataId = "example.properties";
  private static String group = "DEFAULT_GROUP";

  public static void main(String[] args) {
    try {
      Properties properties = new Properties();
      properties.put("serverAddr", serverAddr);
      ConfigService configService = NacosFactory.createConfigService(properties);
      String content = configService.getConfig(dataId, group, 5000);
      System.out.println(content);
    } catch (NacosException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
