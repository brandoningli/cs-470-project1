# Requirements for `lb4theartbeat`

**NOTE:** Include a `toString()` for every class.

## HeartbeatPacket
+ Contains the following fields
    + version number (can use `serialVersionUID` that `Serializable` requires)
    + Peer-to-Peer (false) or Client-Server (true) flag
    + Summary (true) or Individual (false) flag
    + Sender IP Address (String)
    + ArrayList of Heartbeat objects
+ Accessors and mutators as appropriate
+ Constructor that accepts all fields

## Heartbeat
+ Contains the following fields
    + IP (String)
    + Beat number (int is fine; starts at 1)
    + Timestamp (`java.util.Date`)
    + TTL (int, seconds)
    + Time until next beat expected (random, [1,29] seconds)
+ Accessors and mutators as appropriate
+ Method that computes if this Heartbeat is dead. Assume time synchronized servers
+ Constructor that accepts IP, Beat #, TTL, Time until next beat
    + Timestamp should be automatically created
+ Constructor that accepts IP, Beat #, TTL, Time until next beat, Timestamp

## HeartbeatSharedData
+ Contains the following fields
    + `DatagramSocket`
    + `ArrayList<String>` for the general list of IPs
    + `String` IP of this machine
    + `ArrayList<String>` List of the servers in priority order (including local machine)
        + In Peer-to-Peer, this will be initialized to be only the local machine by the Driver
    + `Hashtable<String, Heartbeat>` for the local cache
    + `int` TTL in seconds
    + `int` TTL multiplier for server
        + What the TTL gets multiplied by if the server in C-S
    + `boolean` is currently the server
+ Accessors and mutators as appropriate for all fields
    + For TTL, write two accessors
        + `getTTL()` should return the TTL value, or the TTL * TTL multiplier if this is currently the server
        + `getMaxWait()` should return TTL-1
+ Constructor to accept all these fields

## HeartbeatReceive
+ Constructor accepts the `HeartbeatSharedData`
+ Is `Runnable`
+ General Procedure
    + Get the incoming packet off the socket
    + For each new `Heartbeat` received, update the local cache if the received beat is newer
    + If client server, and an individual packet received, and `HeartbeatSummarySend` thread is not running, start one.
    + If client server, and a summary packet received, and `HeartbeatSummarySend` thread is running, interrupt it.

## HeartbeatSend
+ Constructor accepts the `HeartbeatSharedData`
+ Is `Runnable`
+ Runs on all nodes, all the time
+ General Procedure
    + On first run, wait `sharedData.getMaxWait()` seconds so a summary might be received by the `HeartbeatReceive` thread with accurate server information.
    + Loop:
    + Generate a wait time [1,`sharedData.getMaxWait()`] seconds
    + Generate a `Heartbeat` and `HeartbeatPacket` with just this machine's heartbeat
    + Check the list of servers, in order, for the highest priority server that's alive and send the `HeartbeatPacket` to it
        + This could be itself
        + Peer-to-Peer: Always itself; server list will be populated differently by the driver
        + Note that `Hashtable.get()` will return `null` if the key doesn't exist.
        + **Always** attempt sending to the last server on the list, even if it's declared dead
    + Wait the random time
    + End Loop
+ TTL may change dynamically in the shared memory

## HeartbeatSummarySend
+ Constructor accepts the `HeartbeatSharedData`
+ Is `Runnable`
+ Client-Server: only runs on the designated server
+ Peer-to-Peer: Runs on all machines
+ General Procedure
    + Build an ArrayList of `Heartbeat`s for the packet from the local cache
        + See [this in the JDK](https://docs.oracle.com/javase/8/docs/api/java/util/Hashtable.html#values--) and [this solution](https://www.javacodeexamples.com/convert-hashtable-to-arraylist-in-java-example/3181) for inspiration; `ArrayList` is a `Collection`
        + Only values are needed; Keys are the IP address, which is also in the `Heartbeat`
    + Send to all IP addresses but this machine
    + Wait [1,`sharedData.getMaxWait()/2`] random seconds
+ Should return (die) whenever `Thread.interrupt()` is called.

## HeartbeatDriverClientServer
+ Sets up shared memory and threads for a client server operation
    + Load the list of IP addresses from a file
    + Initialize fields in shared memory as appropriate
    + Initialize the cache for this machine with a current Heartbeat
+ Should start up as a client, triggering server if needed via `HeartbeatReceive`
+ For this project, also start up a `HeartbeatStatusPrinter`

## HeartbeatDriverP2P
+ Sets up shared memory and threads for a client server operation
    + Load the list of IP addresses from a file
    + Initialize fields in shared memory as appropriate
+ Should start up as a client, triggering server if needed via `HeartbeatReceive`
+ For this project, also start up a `HeartbeatStatusPrinter`

## HeartbeatStatusPrinter
+ Constructor accepts the `HeartbeatSharedData`
+ Is `Runnable`
+ Iterates through the cache to print out all the data it knows
+ Waits `sharedData.getMaxWait()` and repeats for the life of the program
