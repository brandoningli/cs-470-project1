## for lb4theartbeat
clientServer: lb4theartbeat/HeartbeatDriverClientServer.class lb4theartbeat/HeartbeatSharedData.class lb4theartbeat/Heartbeat.class lb4theartbeat/HeartbeatPacket.class lb4theartbeat/HeartbeatReceive.class lb4theartbeat/HeartbeatSend.class lb4theartbeat/HeartbeatStatusPrinter.class lb4theartbeat/HeartbeatSummarySend.class lb4theartbeat/NetIdentity.class
	java lb4theartbeat.HeartbeatDriverClientServer lb4theartbeat/IPs.txt 150.243

clientServerJar: lb4theartbeat/HeartbeatDriverClientServer.class lb4theartbeat/HeartbeatSharedData.class lb4theartbeat/Heartbeat.class lb4theartbeat/HeartbeatPacket.class lb4theartbeat/HeartbeatReceive.class lb4theartbeat/HeartbeatSend.class lb4theartbeat/HeartbeatStatusPrinter.class lb4theartbeat/HeartbeatSummarySend.class lb4theartbeat/NetIdentity.class
	mkdir -p jars
	jar cfe jars/lb4theartbeatClientServer.jar lb4theartbeat.HeartbeatDriverClientServer lb4theartbeat/HeartbeatDriverClientServer.class lb4theartbeat/Heartbeat.class lb4theartbeat/HeartbeatPacket.class lb4theartbeat/HeartbeatReceive.class lb4theartbeat/HeartbeatSend.class lb4theartbeat/HeartbeatStatusPrinter.class lb4theartbeat/HeartbeatSummarySend.class lb4theartbeat/NetIdentity.class lb4theartbeat/HeartbeatSharedData.class

peerToPeer: lb4theartbeat/HeartbeatDriverP2P.class lb4theartbeat/HeartbeatSharedData.class lb4theartbeat/Heartbeat.class lb4theartbeat/HeartbeatPacket.class lb4theartbeat/HeartbeatReceive.class lb4theartbeat/HeartbeatSend.class lb4theartbeat/HeartbeatStatusPrinter.class lb4theartbeat/HeartbeatSummarySend.class lb4theartbeat/NetIdentity.class
	java lb4theartbeat.HeartbeatDriverP2P lb4theartbeat/IPs.txt 150.243

peerToPeerJar: lb4theartbeat/HeartbeatDriverP2P.class lb4theartbeat/HeartbeatSharedData.class lb4theartbeat/Heartbeat.class lb4theartbeat/HeartbeatPacket.class lb4theartbeat/HeartbeatReceive.class lb4theartbeat/HeartbeatSend.class lb4theartbeat/HeartbeatStatusPrinter.class lb4theartbeat/HeartbeatSummarySend.class lb4theartbeat/NetIdentity.class
	mkdir -p jars
	jar cfe jars/lb4theartbeatP2P.jar lb4theartbeat.HeartbeatDriverP2P lb4theartbeat/HeartbeatDriverP2P.class lb4theartbeat/Heartbeat.class lb4theartbeat/HeartbeatPacket.class lb4theartbeat/HeartbeatReceive.class lb4theartbeat/HeartbeatSend.class lb4theartbeat/HeartbeatStatusPrinter.class lb4theartbeat/HeartbeatSummarySend.class lb4theartbeat/NetIdentity.class lb4theartbeat/HeartbeatSharedData.class

## For exampleprogram
exampleServer: exampleprogram/Message.class exampleprogram/UDPServer.class
	java exampleprogram.UDPServer
exampleServerJar: exampleprogram/Message.class exampleprogram/UDPServer.class
	mkdir -p jars
	jar cfe jars/exampleServer.jar exampleprogram.UDPServer exampleprogram/UDPServer.class exampleprogram/Message.class
exampleClient: exampleprogram/Message.class exampleprogram/UDPClient.class
	java exampleprogram.UDPClient
exampleClientJar: exampleprogram/Message.class exampleprogram/UDPClient.class
	mkdir -p jars
	jar cfe jars/exampleClient.jar exampleprogram.UDPClient exampleprogram/UDPClient.class exampleprogram/Message.class

## Generic Pattern Rules
%.class: %.java
	javac $<