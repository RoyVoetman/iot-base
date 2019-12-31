## About this Project
This application is part of my **IOT dashboard suite**. This suite consists of multiple projects which together provide a web dashboard via which IOT devices can be controlled remotely.
* [iot-dashboard](https://github.com/RoyVoetman/iot-dashboard) A Laravel Application which provides a GUI and a Database to store the current state of the IOT devices
* [iot-clients](https://github.com/RoyVoetman/iot-clients) C++ code that runs on the IOT devices. (e.g. MCUs such as the NodeMCU and the Wemos D1 mini)
* [iot-base](https://github.com/RoyVoetman/iot-base) The base unit which is responsible for the communication between the `iot-dashboard` and `iot-clients` and vice versa.

![](https://www.royvoetman.nl/iot/IOT-suite-diagram.png)

### Read unit vs Updatable unit
An iot-client can either be a `Read unit` or an `Updatable unit`. 

#### Read unit
A read unit normally is just a MCU with a specific type of sensor (e.g. A temperature sensor). 
As soon as a connection is made to a read unit it will immediately start sending measurements at a set interval.

#### Updatable unit
An Updatable unit is a MCU which fulfills a basis function and which waits until a specific command/request is made to change this functionality. 
A simple example of an updatable unit is a color changeable LED lamp. It will remain the same color until a request arrives to change the color.

> The iot-base always keeps an active connection with `read units` but only establishes a connection with an `updatable unit` if a request has to be sent.

### Communication

#### Dashboard with Base unit
The Dashboard communicates with the base unit via a service called [Pusher](https://pusher.com/). Pusher is the category leader in robust APIs for app developers building scalable realtime communication features.

#### Base unit with Dashboard
The base unit communicates with the Dashboard via calling sertain webhooks (`https` required). It has been decided not to do this via Pusher because the base unit would otherwise have to act as a Pusher client and Pusher server at the same item.

#### Base unit with Clients
The base unit communicates with the clients over a TCP socket connection on port `8888`. The clients are capable of maintaining only one connection at the time. 

So the purpose of the base unit is the forward the high level Pusher messages to low level TCP sockets and vice versa. Furthermore, the base unit ensures that only 1 connection to a particular unit is made at the time.

### Security
#### Encryption 
To communication between the Dashboard and Base unit needs to be secure because it goes through the Internet. Pusher ensures that all messages are sent over a secure SSL connection which made it the perfect service to use. If necessary, Pusher also supports E2E encryption.

#### Authentication
A Pusher client listens for messages and a Pusher Server dispatches messages into so-called `channels`. Pusher has a feature called `Private channels` joining these channels requires the clients to authorize themselves before they can listen for messages from the server.
