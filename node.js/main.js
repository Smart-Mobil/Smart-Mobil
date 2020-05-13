var express = require('express') // 파일 로드를 위한 모듈
var fs = require('fs')   
var app = express()

//추가 
var SerialPort = require("serialport"); // 시리얼포트 라이브러리 호출 
var port =3000; // 포트번호 지정
var serialPort =new SerialPort("/dev/ttyACM0",{baudRate : 9600}); //  대역폭: 9600, 장치명 설정 
var bodyParser = require('body-parser'); // 바디 파서
app.use(bodyParser.json()); 
app.use(bodyParser.urlencoded({extended : true}));
//
app.locals.pretty = true
app.set('views', './view_file')
app.set('view engine', 'pug')
app.use(bodyParser.json()) // 바디 파싱

// func class 생성

var funcjs = require('./func')
var dbjs = require('./DB')
//const db = require('./models/findDB');


app.listen(3000, () => {  //node.js 서버 실행
  console.log("시작합니다.")
})

// wait함수 
function wait(msecs)
{
var start = new Date().getTime();
var cur = start;
while(cur - start < msecs)
{
cur = new Date().getTime();
}
}




var val; // 임시로 센서값을 저장할 변수 
 
serialPort.open(function () { //시리얼포트 열기
    serialPort.on('data',function(data){ //시리얼데이터 송신
    val += data;   // 변수에 데이터를 저장하며
    
    if(val.length >= 1000000){ // 특정 길이 이상이 되면 val 값을 줄여서 해결
      val = val.substring(1,200);  
    }
   // console.log('입력'); 
   // wait(2000);
    var d = new Date();

    if (d.getSeconds() === 1){ //정각일때만, 즉 1분당 하나만 저장하게끔
      console.log('안녕'); 
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
            h3 = val.substring(i,i+12);
          }
        }
        if( h1 && h2 && h3)
          break;
      }
      
      dbjs.adddata(String(d),String(h1),String(h3),String(h2));

      
   }
  });


// 최상위 라우트로 접속 시 /hello로 리다이렉트 
app.get("/", (req, res) => {  
  res.redirect('/hello')
  })

// /bye로 접근시   
app.get("/bye", (req, res) => { //바이
  fs.readFile('./view_file/bye.html', (error, data) => {  //data를
    if(error) {
      console.log('error :'+error)
    }
    else {
      res.writeHead(200, {'ContentType':'text/html'})
      res.end(data)   // 응답 프로세스 종료 
    }
  })
})

var audio // 오디오를 담당하는 변수 
var recvData //body 에서 data를 받아옵니다.
app.post("/data", function(req, res){
  recvData = req.body.data // input태그(name = data) 값을 받아옵니다. 
  console.log(recvData);
  funcjs.onLed(recvData);
  audio = funcjs.onSpeaker(recvData,audio);
  funcjs.onMotor(recvData,audio);

  res.render('finish', { title: './view_file/finish'});
});



serialPort.on('data',function(data){ //시리얼데이터 송신
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
        h3 = val.substring(i,i+12);
      }
    }
    if( h1 && h2 && h3)
      break;
  }
    //console.log(h1 +'@'+ h2+ '@'+ h3+'@');
    var d = new Date();
    res.render('hello', { title: './view_file/Hello',message1: h1, message2: h2, message3 : h3, message4: d  })
})
})





});


