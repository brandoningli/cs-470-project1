## For exampleprogram
exampleServer: exampleprogram/Message.class exampleprogram/UDPServer.class
	java exampleprogram.UDPServer
exampleServerJar: exampleprogram/Message.class exampleprogram/UDPServer.class
	jar cfe jars/exampleServer.jar exampleprogram.UDPServer exampleprogram/UDPServer.class exampleprogram/Message.class
exampleClient: exampleprogram/Message.class exampleprogram/UDPClient.class
	java exampleprogram.UDPClient
exampleClientJar: exampleprogram/Message.class exampleprogram/UDPClient.class
	jar cfe jars/exampleClient.jar exampleprogram.UDPClient exampleprogram/UDPClient.class exampleprogram/Message.class
exampleprogram/Message.class: exampleprogram/Message.java
	javac exampleprogram/Message.java
exampleprogram/UDPServer.class: exampleprogram/UDPServer.java
	javac exampleprogram/UDPServer.java
exampleprogram/UDPClient.class: exampleprogram/UDPClient.java
	javac exampleprogram/UDPClient.java