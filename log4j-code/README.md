## Non-existent Configuration File Specified with an URL
`java -Dlog4j.configuration=file:/build.gradle -cp "lib/log4j-1.2.17.jar:build/classes/java/main" Main` 
produces:

```
log4j:ERROR Could not read configuration file from URL [file:/build.gradle].
java.io.FileNotFoundException: /build.gradle (No such file or directory)
	at java.io.FileInputStream.open0(Native Method)
	at java.io.FileInputStream.open(FileInputStream.java:195)
	at java.io.FileInputStream.<init>(FileInputStream.java:138)
	at java.io.FileInputStream.<init>(FileInputStream.java:93)
	at sun.net.www.protocol.file.FileURLConnection.connect(FileURLConnection.java:90)
	at sun.net.www.protocol.file.FileURLConnection.getInputStream(FileURLConnection.java:188)
	at org.apache.log4j.PropertyConfigurator.doConfigure(PropertyConfigurator.java:524)
	at org.apache.log4j.helpers.OptionConverter.selectAndConfigure(OptionConverter.java:483)
	at org.apache.log4j.LogManager.<clinit>(LogManager.java:127)
	at org.apache.log4j.Logger.getLogger(Logger.java:117)
	at Main.<clinit>(Main.java:4)
log4j:ERROR Ignoring configuration file [file:/build.gradle].
main start
log4j:WARN No appenders could be found for logger (Main).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
main end
```

## Non-existent Configuration File 
`java '-Dlog4j.configuration=~/build.gradle' -cp "lib/log4j-1.2.17.jar:build/classes/java/main" Main`
produces:
```
main start
log4j:WARN No appenders could be found for logger (Main).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
main end
```

## Invalid Configuration 
`java -Dlog4j.configuration=build.gradle -cp "lib/log4j-1.2.17.jar:build/classes/java/main" Main`
produces:

```
main start
log4j:WARN No appenders could be found for logger (Main).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
main end
```

## No Configuration File Specified
`java -cp "lib/log4j-1.2.17.jar:build/classes/java/main" Main` produces:

```
main start
log4j:WARN No appenders could be found for logger (Main).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
main end
```

## With a Log4j2 Configuration File
```
java -cp "lib/log4j-1.2.17.jar:build/classes/java/main" \
            -Dlog4j.configuration=file:/Users/jing/code/github/my/java-projects/log4j-code/log4j-conf/log4j2.xml \
            Main
```
produces:
```
log4j:WARN Continuable parsing error 2 and column 30
log4j:WARN Document root element "Configuration", must match DOCTYPE root "null".
log4j:WARN Continuable parsing error 2 and column 30
log4j:WARN Document is invalid: no grammar found.
log4j:ERROR DOM element is - not a <log4j:configuration> element.
main start
log4j:WARN No appenders could be found for logger (Main).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
main end
```

# How to Specify Configuration File
```bash
java -cp "lib/log4j-1.2.17.jar:build/classes/java/main:log4j-conf/valid-xml" Main

# Don't work
java -cp "lib/log4j-1.2.17.jar:build/classes/java/main" \
            -Dlog4j.configuration=/Users/jing/code/github/my/java-projects/log4j-code/log4j-conf/valid-xml/log4j.xml \
            Main

# Work
java -cp "lib/log4j-1.2.17.jar:build/classes/java/main" \
            -Dlog4j.configuration=file:///Users/jing/code/github/my/java-projects/log4j-code/log4j-conf/valid-xml/log4j.xml \
            Main

# Work
java -cp "lib/log4j-1.2.17.jar:build/classes/java/main" \
            -Dlog4j.configuration=file:/Users/jing/code/github/my/java-projects/log4j-code/log4j-conf/valid-xml/log4j.xml \
            Main
```

http://logging.apache.org/log4j/1.2/faq.html#noconfig says:
```
log4j.configuration
  URL for default initialization configuration file.
```


java -cp "lib/log4j-1.2.17.jar:build/classes/java/main" \
                            -Dlog4j.configuration=file:/Users/jing/code/github/my/java-projects/log4j-code/log4j-conf/log4j2.xml \
                            Main
                            
myid example
MDC