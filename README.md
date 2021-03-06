# IOT base
[![MIT Licensed](https://img.shields.io/badge/license-MIT-brightgreen.svg?style=flat-square)](LICENSE)

## Introduction
A TCP Socket application that is capable of communicating with Micro controllers (MCUs) e.g. Arduinos from the [iot-clients](https://github.com/RoyVoetman/iot-clients/) project.

## About this project
This application is part of my **IOT dashboard suite**. For more information about what this suite of projects entails a reference is made to [IOT dashboard suite](https://github.com/RoyVoetman/iot-base/blob/master/docs/IOT%20dashboard%20suite.md).

## Prerequisites
* [Java SDK >= 1.8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven >= 3.6](https://maven.apache.org/)

## Getting Started
### Maven
First make sure to install all Maven dependencies by running `mvn install`. Now that all dependencies are imported you can compile and run the Java application via your favorite IDE. Normally I use [IntelliJ](https://www.jetbrains.com/idea/) from Jetbrains.

### Config
This application also expects a `config.json` file in the root folder. An example of this file is shown in `example-config.json`.

#### Properties
| Property       | Description                                                  | Default                |
|----------------|--------------------------------------------------------------|------------------------|
| clients        | The local IP's of the `read units`                           |                        |
| api.url        | The url where the `iot-dashboard` project is hosted          |                        |
| api.token      | An api user token from the `iot-dashboard`                   |                        |
| pusher.app-key | The Pusher application key used in the `iot-dashboard`       |                        |
| pusher.cluster | The used Pusher cluster.                                     | eu                     |
| pusher.auth    | The broadcast authentication endpoint of the `iot-dashboard` | /api/broadcasting/auth |

## License
The MIT License (MIT). Please see [License File](LICENSE) for more information.

## Authors
* [Roy Voetman](https://www.royvoetman.nl)
