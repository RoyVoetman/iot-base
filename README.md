# IOT base
[![MIT Licensed](https://img.shields.io/badge/license-MIT-brightgreen.svg?style=flat-square)](LICENSE)

## Introduction
A TCP Socket application that is capable of communicating with Micro controllers (MCU) e.g. Arduinos from the [iot-clients](https://github.com/RoyVoetman/iot-clients/) project.

## About this Project
This application is part of my **IOT dashboard suite**. This suite consists of multiple projects which together provide a web dashboard via which IOT devices can be controlled remotely.
* [iot-dashboard](https://github.com/RoyVoetman/iot-dashboard) A Laravel Application which provides a GUI and a Database to store the current state of the IOT devices
* [iot-clients](https://github.com/RoyVoetman/iot-clients) C++ code that runs on the IOT devices. (e.g. MCUs such as the NodeMCU and the Wemos D1 mini)
* [iot-base](https://github.com/RoyVoetman/iot-base) The base unit which is responsible for the communication between the `iot-dashboard` and `iot-clients` and vice versa.

### Communication

#### Dashboard with Base unit
The Dashboard communicates with the base unit via a service called [Pusher](https://pusher.com/). Pusher is the category leader in robust APIs for app developers building scalable realtime communication features.

#### Base units with Client
The base unit communicates with the clients over a TCP socket connection.

So the purpose of the base unit is the forward the high level Pusher events to low level TCP sockets and vice versa.

### Read unit vs Updatable unit
An iot-client can either be a `Read unit` or an `Updatable unit`. 

#### Read unit
A read unit normally is just a MCU with a specific type of sensor (e.g. A temperature sensor). 
As soon as a connection is made to a read unit it will immediately start sending measurements at a set interval.

#### Updatable unit
An Updatable unit is a MCU which fulfills a basis function and which waits until a specific command/request is made to change this functionality. 
A simple example of an updatable unit is a color changeable LED lamp. It will remain the same color until a request arrives to change the color.

> The iot-base always keeps an active connection with `read units` but only establishes a connection with an `updatable unit` if a request has to be sent.

## Prerequisites
* [Java SDK >= 1.8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven >= 3.6](https://maven.apache.org/)

## Getting Started
### Maven
First make sure to install all Maven dependencies by running `mvn install`. Now that all dependencies are imported you can compile and run the Java application via your favourite IDE. Normally I use [IntelliJ](https://www.jetbrains.com/idea/) from Jetbrains.

### Config
This application also expects a `config.json` file in the root folder. The structure of this json is shown in `example-config.json`.

#### Properties
| Property       | Description                                                  | Default                |
|----------------|--------------------------------------------------------------|------------------------|
| clients        | The local IP's of the `read units`                           |                        |
| api.url        | The url where the `iot-dashboard` project is hosted          |                        |
| api.token      | The api token for the `iot-dashboard`                        |                        |
| pusher.app-key | The Pusher application key used in the `iot-dashboard`       |                        |
| pusher.cluster | The used Pusher cluster.                                     | eu                     |
| pusher.auth    | The broadcast authentication endpoint of the `iot-dashboard` | /api/broadcasting/auth |

## License
The MIT License (MIT). Please see [License File](LICENSE) for more information.
