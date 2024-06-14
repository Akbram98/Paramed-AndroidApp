# Paramed - Health Care Android Application


## About the Project

Paramed is an emergency centered application service which provides a platform for paramedics where they can view common patient vital readings while heading towards the location of the emergency. The rationale behind this is that paramedics, equipped with patient information even before arrival of the scene, would be better prepared to handle the care of the patient upon arrival. This would not only improve paramedic efficiency and responsiveness during an emergency but also improves their reliability and patient satisfaction.

### Built With
 - Android studio to create an authentication service and dashboard for paramedic authentication and patient vital information display
 - Wampserver service with MySQL for paramedic authentication
 - Firebase real-time database for real-time communication of IoT device and android application from the internet
 - An IoT device by leveraging the interface and functional utilities of a Raspberry Pi Microcontrolller and a MAX30100 Pulse Oxymetry Sensor
 - Python([maxRun.py](https://github.com/Akbram98/Paramed-AndroidApp/blob/master/app/maxRun.py)) script to process MAX30102 blood oxygen raw readings into human-readable approximations and send to the firebase database for retrieval

## Getting Started

### Hardware Setup
  - [Raspberry Pi 3 Microcontroller](https://www.raspberrypi.com/products/raspberry-pi-3-model-b/)
  - [Raspberry Pi 3 Setup Tutorial](https://www.raspberrypi.com/documentation/computers/getting-started.html)
  - [MAX30102 Pulse Oxymetry Sensor Setup Tutorial](https://github.com/vrano714/max30102-tutorial-raspberrypi)

### Software Setup
 - [WampServer Setup with MySQL](https://blog.containerize.com/how-to-install-and-configure-wamp-server-on-windows/)
 - [Firebase Real-Time Database Setup](https://firebase.google.com/docs/database/)

### Prerequisites
- Download google_services.json file upon creation of real-time database and add to project
- [Additional configuration Info](https://firebase.google.com/docs/android/setup)
- Firebase doesn't seem to work with latest version of google-services dependency. Use older version:
  ```sh
  id 'com.google.gms.google-services' version '4.3.8' apply false

 ## Project Demo
 If you are interested in watching a demo of this project then here is the [link](https://drive.google.com/file/d/1EM7xl7EOJXe2wgTQs4uB0Ksur4w_-jwy/view?usp=sharing). WARNING: This video has no sound!


