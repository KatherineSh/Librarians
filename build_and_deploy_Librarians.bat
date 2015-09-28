@echo off

rem set "PROJECT_HOME = %D:\Env\workspace\Librarians%"
rem set "TOMCAT_HOME = %d:\Env\Tomcat\apache-tomcat-7.0.61%"
cd "D:\Env\workspace\Librarians"


RD /s /q "d:\Env\Tomcat\apache-tomcat-7.0.61\webapps\Librarians-1"
DEL /q "d:\Env\Tomcat\apache-tomcat-7.0.61\webapps\Librarians-1.war"
call mvn clean package

cd "d:\Env\workspace\Librarians\target"
copy "*.war" "d:\Env\Tomcat\apache-tomcat-7.0.61\webapps\"


PAUSE 
