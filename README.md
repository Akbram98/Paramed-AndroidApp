# Paramed - Health Care Android Application


## About the Project

Paramed is an emergency-focused application designed to provide paramedics with real-time access to critical patient vital signs while en route to an emergency scene. The application enables paramedics to review essential patient information before arriving at the location, enhancing their preparedness and efficiency in delivering care. By equipping paramedics with advance knowledge, Paramed aims to improve response times, increase operational effectiveness, and boost patient satisfaction.

### Built With
 - **Android Studio**: Developed an authentication service and dashboard for paramedics to access and display patient vital information.
 - **WampServer and MySQL**: Implemented paramedic authentication using WampServer with a MySQL database.
 - **Firebase Real-Time Database**: Enabled real-time communication between the IoT device and the Android application over the internet.
 - **IoT Device**: Utilized a Raspberry Pi microcontroller and a MAX30102 Pulse Oximetry Sensor to capture vital signs.
 - **Python Script ([maxRun.py](https://github.com/Akbram98/Paramed-AndroidApp/blob/master/app/maxRun.py))**: Processed raw blood oxygen readings from the MAX30102 sensor into human-readable data and sent it to the Firebase database for retrieval.

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
 If you are interested in watching a demo of this project then here is the [link](https://drive.google.com/file/d/1hZVW2Cp3u23Hb1TLTvJWHOBIBMfpa7CH/view?usp=sharing).

