const Gpio = require('pigpio').Gpio;


const led = new Gpio(18, {mode: Gpio.OUTPUT});
const but1 = new Gpio(19, {mode: Gpio.OUTPUT});
const but2 = new Gpio(26, {mode: Gpio.OUTPUT});
let dutyCycle = 255;
 
led.pwmWrite(255);
but1.pwmWrite(255);
but2.pwmWrite(0);
 




