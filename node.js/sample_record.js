var Gpio = require('pigpio').Gpio, //include pigpio to interact with the GPIO
ledRed = new Gpio(16, {mode: Gpio.OUTPUT}), //use GPIO pin 4 as output for RED
ledGreen = new Gpio(20, {mode: Gpio.OUTPUT}), //use GPIO pin 17 as output for GREEN
ledBlue = new Gpio(21, {mode: Gpio.OUTPUT}), //use GPIO pin 27 as output for BLUE
redRGB = 255, //set starting value of RED variable to off (255 for common anode)
greenRGB = 255, //set starting value of GREEN variable to off (255 for common anode)
blueRGB = 255; //set starting value of BLUE variable to off (255 for common anode)

//RESET RGB LED
ledRed.digitalWrite(1); // Turn RED LED off
ledGreen.digitalWrite(1); // Turn GREEN LED off
ledBlue.digitalWrite(1); // Turn BLUE LED off