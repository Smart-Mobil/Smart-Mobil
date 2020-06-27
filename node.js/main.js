var express = require('express') 
var multer = require('multer')
const fs = require('fs');
const path = '~/Smart-Mobil/node.js/uploads';

var app = express();
var done = false;


var SerialPort = require("serialport"); 
var port =3000; 
var serialPort =new SerialPort("/dev/ttyACM0",{baudRate : 9600}); 
var bodyParser = require('body-parser'); 


// add record func 1. 
app.use(multer({
  dest: './uploads/',

  
  rename: function (fieldname, filename) {
      return 1;
  },
  onFileUploadStart: function (file) {
      console.log(file.originalname + ' is starting ...')
  },
  onFileUploadComplete: function (file) {
      console.log(file.fieldname + ' uploaded to  ' + file.path)
      done = true;
  }
}));


app.use(bodyParser.json()); 
app.use(bodyParser.urlencoded({extended : true}));

app.locals.pretty = true
app.set('views', './view_file')
app.set('view engine', 'pug')
app.use(bodyParser.json()) 







var funcjs = require('./func')
var dbjs = require('./DB')
//const db = require('./models/findDB');


app.listen(3000, () => {  
  console.log("start!")
})


function wait(msecs)
{
var start = new Date().getTime();
var cur = start;
while(cur - start < msecs)
{
cur = new Date().getTime();
}
}




var val; 
 
serialPort.open(function () { 
    serialPort.on('data',function(data){ 
    val += data;   
    
    if(val.length >= 1000000){ 
      val = val.substring(1,200);  
    }
  
    
    var d = new Date();

    if (d.getSeconds() === 1){ 
      console.log('hello'); 
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



app.get("/", (req, res) => {  
  res.redirect('/hello')
  })

 
app.get("/bye", (req, res) => { 
  fs.readFile('./view_file/bye.html', (error, data) => {  
    if(error) {
      console.log('error :'+error)
    }
    else {
      res.writeHead(200, {'ContentType':'text/html'})
      res.end(data)  
    }
  })
})


app.get('/record', function (req, res) {
  res.sendfile('./view_file/record.html');
});


app.post('/api/photo', function (req, res) {
 
  if (done == true) {
      console.log(req.files);
      res.end("File uploaded.\n" + JSON.stringify(req.files));
  }

 
});

var audio 
var audio2
var audio3
var recvData 
app.post("/data", function(req, res){
  recvData = req.body.data 
  console.log(recvData);
  funcjs.onLed(recvData);
  audio = funcjs.onSpeaker(recvData,audio);
  funcjs.onMotor(recvData,audio2);
  audio3 = funcjs.onVoice(recvData,audio3);

  res.render('finish', { title: './view_file/finish'});
});



serialPort.on('data',function(data){ 
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


