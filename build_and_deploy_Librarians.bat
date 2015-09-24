@echo off

set "PROJECT_HOME = %D:\Env\workspace\Librarians%"
set "TOMCAT_HOME = %d:\Env\Tomcat\apache-tomcat-7.0.61%"
cd "D:\Env\workspace\Librarians"


call mvn clean package
cd "d:\Env\workspace\Librarians\target"
copy "*.war" "d:\Env\Tomcat\apache-tomcat-7.0.61\webapps\"


PAUSE 
