
var express = require('express')
var app = express()

//추가 
var SerialPort = require("serialport");
var port =3000;
var serialPort =new SerialPort("/dev/ttyACM0",{baudRate : 9600});
//
app.locals.pretty = true
app.set('views', './view_file')
app.set('view engine', 'pug')
app.listen(3000, () => {
  console.log("Server has been started")
})
 
var value;

serialPort.open(function () {
    


// 최상위 라우트로 접속 시 /hello로 리다이렉트 
app.get("/", (req, res) => {
  res.redirect('/hello')
})
 
serialPort.on('data',function(data){
// Pug 파일 렌더링
app.get("/hello", (req, res) => {
    console.log(data);
    res.render('hello', { title: 'Hello', message: data})
})
})





});