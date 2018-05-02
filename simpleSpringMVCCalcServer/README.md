# simpleSpringMVCCalcServer
Simple Spring based web server that provide some computing on user side with JavaScript or server-side by WebSocket STOMP protocol.

# Security
By default there is single user with root authority, who can change between server or JavaScript via button on main page(visible only if you logged in). Login through simple login form at /login, by default all password/login pairs are root/root. Also, password and login for root  hardcoded in Spring Security in memory database.

#Server
Asynchronous messaging performs by RabbitMQ with enabled STOMP plugin. User sends statement to server, and server computes result, then statement and result persist into database. After that, server sends result to user. It is only for server, with JavaScript, user does not send any to server. User gets his page, and never communicates with server.

#Enable plugin and set up RabbitMQ

1 Download and install RabbitMQ 3.6.6 (erlang is needed)

2 cd to ../RabbitMQ Server/rabbitmq_server-3.6.6/sbin

3 Run 
```
rabbitmq-server
```

4 Run 

```
rabbitmq-plugins enable rabbitmq_stomp 
```
Read more: https://www.rabbitmq.com/stomp.html

5 Restart RabbitMQ

=====================

If you are getting 'cant set short node name' or 'epmd error for host %yshorthostname% nxdomain (non-existing domain)'

You need change your hostname and host. For Win Seven users:

My Computer -> Properties -> and you will see "Full computer name:"

Make sure whether your host file at %WinDir%\System32\Drivers\Etc contains following entries: 

```
	127.0.0.1       localhost
  ::1             localhost
	127.0.0.1	%yshorthostname%
```

#How to deploy

1 Download Tomcat 8, download and install Maven on your computer.

2 Clone or download zip.

3 This project has Maven webapp archetype, so  

```
mvn archetype:create -DgroupId=com.mycompany.app -DartifactId=my-webapp 
      -DarchetypeArtifactId=maven-archetype-webapp
```

4 Unzip it in any folder

5 Go to this folder by cd command (if you use Win go to folder, hold shift and press right mouse button -> open command window)

6 Build

```
  mvn package - will compile project and package it in it distributable format
```

OR

```
 mvn install - will install the package into the local repository
 ```
 
 OR

You can enter 
```
 mvn clean install - will remove target folder, build and install project in a local repository
 ```

7 Add war file to ..apache-tomcat-8.0.38/webapps

8 Run Tomcat and RabbitMQ(if needed) and go to localhost /login

#Other

It runs on Tomcat v8.0 server and RabbitMQ Server 3.6.5

#Dependencies
For Java go to pom.xml

Other:

https://github.com/LeaVerou/css3patterns - Some CSS Backgrounds 

#Pics

<img src='http://i.imgur.com/rjXLVUy.png' width="200" height="200" >
<img src='http://i.imgur.com/9M8wAWk.png' width="200" height="200" >
<img src='http://i.imgur.com/9JOTmlW.jpg' width="200" height="200" >
<img src='http://i.imgur.com/ufdHAjh.png' width="200" height="200" >

