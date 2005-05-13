@echo off
echo %JBOSS_HOME%
cd C:\Program Files\eclipse\workspace\wandux\bin
rem java -cp .;%JBOSS_HOME%\client\jbossall-client.jar pfe.migration.client.pre.ClientMain %1
java -cp .;%JBOSS_HOME%\client\jbossall-client.jar pfe.migration.client.pre.ClientPreinstall

pause

rem java -cp .;%JBOSS_HOME%\client\jbossall-client.jar -Djava.security.manager -Djava.security.policy=..\utils\rmi.policy pfe.migration.client.pre.ClientMain 127.0.0.1
rem java -cp .;c:\java\jboss-4.0.0RC1\client\jbossall-client.jar -Djava.security.manager -Djava.security.policy=..\rmi.policy pfe.migration.client.pre.ClientMain 127.0.0.1