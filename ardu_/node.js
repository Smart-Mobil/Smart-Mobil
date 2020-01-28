
var express = require('express') // 파일 로드를 위한 모듈
var fs = require('fs')   
var app = express()

//추가 
var SerialPort = require("serialport");
var port =3000;
var serialPort =new SerialPort("/dev/ttyACM0",{baudRate : 9600});
var bodyParser = require('body-parser'); // 바디 파싱
//
app.locals.pretty = true
app.set('views', './view_file')
app.set('view engine', 'pug')
app.use(bodyParser.json()) // 바디 파싱
app.listen(3000, () => {
  console.log("Server has been started")
})
 
var val; // 임시로 센서값을 저장할 변수 

serialPort.open(function () {
    serialPort.on('data',function(data){
    val += data;  
    if(val.length >= 1000000000 ){
      val = val.substring(1,1);  
    }
});


// 최상위 라우트로 접속 시 /hello로 리다이렉트 
app.get("/", (req, res) => {
  res.redirect('/hello')
})
 
app.get("/bye", (req, res) => { //바이
  fs.readFile('bye.html', (error, data) => {
    if(error) {
      console.log('error :'+error)
    }
    else {
      res.writeHead(200, {'ContentType':'text/html'})
      res.end(data)   // 응답 프로세스 종료 
    }
  })

})


 
app.post("/data", (req, res) => {
  var recvData = req.body.data
  serialPort.wirte(recvData,function(data){
    if(error) {

			return console.log("Error on write : ", error.message);

		} else {

			console.log("메세지가 정상적으로 입력되었습니다.");

    }

  });
})




serialPort.on('data',function(data){
// Pug 파일 렌더링
app.get("/hello", (req, res) => {
  var h1;
  var h2;
  var h3;
  for(var i = val.length - 1; i>0; i--){
    if(val[i] === '1'){
      if(val[i+1] === ')'){
         h1 = val.substring(i,i+8);
      }
    }
    else if(val[i] === '2'){
      if(val[i+1] === ')'){
        h2 = val.substring(i,i+17);
      }
    }
    else if(val[i] === '3'){
      if(val[i+1] === ')'){
        h3 = val.substring(i,i+10);
      }
    }
    if( h1 && h2 && h3)
      break;
  }
    console.log(h1 +'@'+ h2+ '@'+ h3+'@');
    var d = new Date();
    res.render('hello', { title: 'Hello',message1: h1, message2: h2, message3 : h3, message4: d  })
})
})

});