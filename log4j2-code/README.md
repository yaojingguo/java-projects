```shell
mvn -Dtest=AsyncLogTest \
  -D log4j2.configurationFile=./src/main/resources/log4j2-async-root.xml \
  test
mvn -Dtest=MixedLogTest \
  -D log4j2.configurationFile=./src/main/resources/log4j2-mixed.xml \
  test
 
mvn -Dtest=LogTest test
```

Sample JSON template log message:
```JSON
{
  "@version": 1,
  "source_host": "jing-mbp.local",
  "message": "an info message",
  "thread_name": "main",
  "@timestamp": "2021-03-26T20:28:46.708+08:00",
  "level": "INFO",
  "logger_name": "json.template.logger"
}
```