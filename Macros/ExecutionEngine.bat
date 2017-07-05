@echo off
SET DEVELOPMENT_HOME= 
cd %DEVELOPMENT_HOME%
call  mvn package
call  mvn exec:java
PAUSE
