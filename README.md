yolo-octo-android-sensors
=========================

android client server application with sensor data
Client app(client side) requests for location and sensor data from the server(androidserver) using a simple TCP connection. 
Server, upon receiving connection request, computes the appropriate sensor data locally using hardware sensors, depending on the request message.
Finally, the server sends back the data again, using a TCP socket.
