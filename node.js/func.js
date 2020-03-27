



class func{
    constructor() { this.config = [] }

  

// 주요 기능들에 따른 함수를 호출할 예정입니다. 
function mainFunction( argument1 ) {
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

}