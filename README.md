# FlightGear-Android
link to github - https://github.com/shovalharel/FlightGear-Android.git

link to an explain video - https://youtu.be/UZrsobLt4-E

Summary:
This is a Android App which is used as a remote control joystick for a FlightGear flight simulator. From the app there is the ability to control the rudder, throttle, aileron and elevator of the plane.

Prerequisites:
'Android Studio' environment to run the code.

Installing:
Download and install the simulator to your computer by the following URL: https://www.flightgear.org/download/
As you open the FlightGear App, choose the 'Settings' tab and add the following text:
--telnet=socket,in,10,127.0.0.1,6400,tcp – change the IP(127.0.0.1) to the computer intern IP.
This will open communication socket - 'in' where you send commands to the simulator.

Running:
Download by git clone from -https://github.com/shovalharel/FlightGear-Android.git. and it to Android Studio.
Open the FlightGear App, click on 'FLY', and then click on 'Cessna C172P' and choose 'Autostart'.
Run the program from Android Studio.
After inserting the Port and IP, you will be able to control the plane by moving the joystick and slides.

Documentation and general explanation of the structure of the folders and main files in the project:
The project is designed by the “MVVM” architecture, which means it is divided to 3 main layers:
Model – 'FGPlayer' , responsible of all the logic of the app.
View – responsible of the 'MainActivity' and the 'Joystick' view.
ViewModel – responsible to connect between the Model and the View . The ViewModel has data-binding with each of them.
The main suppose of this architecture is to divide between the View layer and the Model layer. Due to the ViewModel layer, any change in the View will be updated in the Model and vice versa.

Collaborators
This App was developed by Sapir Vered and Shoval Harel, CS students at Bar-Ilan university, Israel.
