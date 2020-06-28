
var Gpio = require('pigpio').Gpio, //include pigpio to interact with the GPIO
ledRed = new Gpio(16, {mode: Gpio.OUTPUT}), //use GPIO pin 4 as output for RED
ledGreen = new Gpio(20, {mode: Gpio.OUTPUT}), //use GPIO pin 17 as output for GREEN
ledBlue = new Gpio(21, {mode: Gpio.OUTPUT}), //use GPIO pin 27 as output for BLUE
redRGB = 255, //set starting value of RED variable to off (255 for common anode)
greenRGB = 255, //set starting value of GREEN variable to off (255 for common anode)
blueRGB = 255; //set starting value of BLUE variable to off (255 for common anode)
//RESET RGB LED
ledRed.pwmWrite(redRGB); // Turn RED LED off
ledGreen.pwmWrite(greenRGB); // Turn GREEN LED off
ledBlue.pwmWrite(blueRGB); // Turn BLUE LED off

// 
const motor_onoff = new Gpio(13, {mode: Gpio.OUTPUT});
const motor_left = new Gpio(19, {mode: Gpio.OUTPUT});
const motor_right = new Gpio(26, {mode: Gpio.OUTPUT});


// 스피커기능
var player = require('play-sound')(opts = {})
var sound = require('node-aplay')

// 주요 기능들에 따른 함수를 호출할 예정입니다. 
exports.onLed = function main( argument1 ) {

  // led
    // Do Something
    if( argument1 === 'on'){ //스피커 ON
      /* a~g까지 알파벳들을 입력받아서  */
      console.log("mainfunc");
  
      
      ledRed.pwmWrite(0); //set RED LED to specified value
      ledGreen.pwmWrite(0); //set GREEN LED to specified value
      ledBlue.pwmWrite(0); //set BLUE LED to specified value
  
      return 1;
  
    }
    if( argument1 === 'off'){ //스피커 ON
      /* a~g까지 알파벳들을 입력받아서  */
      console.log("mainfunc");
  
      
      ledRed.pwmWrite(255); // Turn RED LED off
      ledGreen.pwmWrite(255); // Turn GREEN LED off
      ledBlue.pwmWrite(255); // Turn BLUE LED off
  
      return 1;
  
    }
  
}
 
var audio
exports.onSpeaker = function main2 (argument1,audio){

  if( argument1 === 'stop'){ //스피커 OFF

    audio.kill()
  }
  else if( argument1 === 'start1'){
    audio = player.play('./sound_file/a.mp3', function(err){
      if (err) throw err
    })
    return audio
  }

  else if( argument1 === 'start2'){
    audio = player.play('./sound_file/b.mp3', function(err){
      if (err) throw err
    })
    return audio
  }

  else if( argument1 === 'start3'){
    audio = player.play('./sound_file/c.mp3', function(err){
      if (err) throw err
    })
    return audio
  }

  else if( argument1 === 'start4'){
    audio = player.play('./sound_file/d.mp3', function(err){
      if (err) throw err
    })
    return audio
  }

  else if( argument1 === 'start5'){
    audio = player.play('./sound_file/e.mp3', function(err){
      if (err) throw err
    })
    return audio
  }

  else if( argument1 === 'start6'){
    audio = player.play('./sound_file/f.mp3', function(err){
      if (err) throw err
    })
    return audio
  }

  else if( argument1 === 'start7'){
    audio = player.play('./sound_file/g.mp3', function(err){
      if (err) throw err
    })
    return audio
  }

  else if( argument1 === 'start8'){
    audio = player.play('./sound_file/h.mp3', function(err){
      if (err) throw err
    })
    return audio
  }

  else if( argument1 === 'start9'){
    audio = player.play('./sound_file/i.mp3', function(err){
      if (err) throw err
    })
    return audio
  }

  else if( argument1 === 'start10'){
    audio = player.play('./uploads/1.mp3', function(err){
      if (err) throw err
    })
    return audio
  }
}







exports.onMotor = function main3 (argument1, audio){
  if( argument1 === 'motoroff'){ //모터 OFF
    motor_onoff.pwmWrite(0);
    motor_right.pwmWrite(0);
    motor_left.pwmWrite(0);
    if(audio != null)
      audio.kill()
  }
  else if( argument1 === 'right'){
    motor_onoff.pwmWrite(255);
    motor_right.pwmWrite(255);
    motor_left.pwmWrite(0);

   
    audio = player.play('./sound_file/b.mp3', function(err){
      if (err) throw err
    })
    return audio
    
  }
  else if( argument1 === 'left'){
    motor_onoff.pwmWrite(255);
    motor_right.pwmWrite(0);
    motor_left.pwmWrite(255);

    if(audio != null){
      audio.kill()
    }
    audio = player.play('./sound_file/c.mp3', function(err){
      if (err) throw err
    })
    return audio
  }

}






var voice 
exports.onVoice = function main4 (argument1,audio){

  if( argument1 === 'voicestop'){ 
    
    
    setTimeout(function () {
      voice = audio
      voice.pause(); // pause the music after five seconds
    }, 100);
  }
  else if( argument1 === 'voice'){
    voice = new sound('./uploads/1.wav')
    voice.play()
    return voice
  }

  
}
