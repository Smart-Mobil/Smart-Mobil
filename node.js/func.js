



// led
var Gpio = require('pigpio').Gpio, //include pigpio to interact with the GPIO
ledRed = new Gpio(4, {mode: Gpio.OUTPUT}), //use GPIO pin 4 as output for RED
ledGreen = new Gpio(17, {mode: Gpio.OUTPUT}), //use GPIO pin 17 as output for GREEN
ledBlue = new Gpio(27, {mode: Gpio.OUTPUT}), //use GPIO pin 27 as output for BLUE
redRGB = 255, //set starting value of RED variable to off (255 for common anode)
greenRGB = 255, //set starting value of GREEN variable to off (255 for common anode)
blueRGB = 255; //set starting value of BLUE variable to off (255 for common anode)
//RESET RGB LED
ledRed.digitalWrite(1); // Turn RED LED off
ledGreen.digitalWrite(1); // Turn GREEN LED off
ledBlue.digitalWrite(1); // Turn BLUE LED off

// 주요 기능들에 따른 함수를 호출할 예정입니다. 
function main( argument1 ) {
    // Do Something
    if( argument1 === 'on'){ //스피커 ON
      /* a~g까지 알파벳들을 입력받아서  */
      console.log("mainfunc");
  
      
      ledRed.pwmWrite(redRGB); //set RED LED to specified value
      ledGreen.pwmWrite(greenRGB); //set GREEN LED to specified value
      ledBlue.pwmWrite(blueRGB); //set BLUE LED to specified value
  
      return 1;
  
    }
    if( argument1 === 'off'){ //스피커 ON
      /* a~g까지 알파벳들을 입력받아서  */
      console.log("mainfunc");
  
      
      ledRed.digitalWrite(1); // Turn RED LED off
      ledGreen.digitalWrite(1); // Turn GREEN LED off
      ledBlue.digitalWrite(1); // Turn BLUE LED off
  
      return 1;
  
    }
  
  }

