[Log4j 2 Compatibility with Log4j 1 API Compatibility](https://logging.apache.org/log4j/2.x/manual/compatibility.html)

```
java -cp 'build/classes/java/main:lib/log4j-api-2.13.3.jar:lib/log4j-core-2.13.3.jar' \
    -Dlog4j.configurationFile=log4j-conf/log4j.xml \
    Main 
```
produces:
```
2020-08-25 23:46:29,693 main ERROR Unknown object "root" of type org.apache.logging.log4j.core.config.LoggerConfig is ignored: try nesting it inside one of: ["Appenders", "Loggers", "Properties", "Scripts", "CustomLevels"].
```