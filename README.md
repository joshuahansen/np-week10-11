# np-week10-11
Buffers and SocketChannels

## Aims
To learn the use of buffers and socket channels
## Tasks

**1.** In this lab exercise you will create an echo server, similar to Lab 4-5, but this time you
will use socket channels for both client and server. You can use any method for
console input-output.

##### The client
At start, it prints out its local address and the port number it is using, connects to
the server and then it enters the following loop.
  * It takes one line of input from the console, sends it to the server, and prints
the server’s response to the screen.
  * If the input line is a single ‘x’ character, the client exits after receiving it
back from the server.

***Blocking mode***

The client should use buffers and a non-blocking socket channel.

##### The server
At start, it prints out its own socket address to the screen. When it receives a
connection, it prints the peer’s socket address to the screen (so you can check who
connected to the server), then enters the following loop.
  * It receives a line of message from the client, prints the received message to
the screen, converts every lower-case letter in the message to upper case,
and then sends the converted message back to the client.
  * If the received line is a single ‘x’ character, the server exits after sending it
back to the client.
The server is running on the first port assigned to you.

***Blocking mode***

The server should use buffers and a blocking socket channel.

**2.** In this task both client and server use non-blocking input-output.

##### The client
Same as in task 1.

##### The server
It implements the same functionality as task 1.

***Blocking mode***

The server should use buffers and a non-blocking socket channel.

It uses a Selector to detect when an operation has finished. After opening the
Selector, you need to register your operations of interest, check if the operation
is available and perform the operation. Finally, you need to remove the key, and
this will prepare the Selector for the next operation of the same type.
