# SmartMobil
Korea Polytechnic University  
Computer Engineering Senier Project  

### :book: 프로젝트명 
#### 원격 제어가 가능한 신생아 건강 스마트 모빌  
Remotely Controllable Smart Mobil for newborn baby  
Korea Polytechnic University  
Professor 공기석 Student 김진엽 박현욱 신용원  

### :pencil2: 개요  
----------------------------------------------------------
주변 환경에 민감한 신생아의 건강  
Health of newborns sensitive to the surrounding environment  
동시에 여러가지 환경 데이터를 수집하는데 어려움을 겪는 부모  
Parents who have difficulty collecting various environmental data at the same time  
신생아와 밀접한 장난감일 모빌을 이용하여 환경 데이터 수집  
Collect environmental data using mobile, a toy closely related to newborn  
모빌을 이용한 스트리밍  
Streaming with Mobile  
애플리케이션과 웹을 이용한 육아 데이터 및 통계치 제공  
Providing parenting data and statistics using applications and the web  

### :pencil2: 목적
----------------------------------------------------------
모빌에 부착된 다양한 모듈을 통해 환경 데이터 수집 및 영상 스트리밍  
Collect environmental data and stream video through various modules attached to mobiles  
애플리케이션과 웹을 통해 다양한 환경 데이터 전달, 모빌 원격 제어  
Various environmental data delivery through mobile and application, mobile remote control  

### :pencil2: 기능 
----------------------------------------------------------
신생아 모니터링  
Newborn monitoring  
환경, 체온 데이터 수집/제공  
Collect and provide environmental and temperature data  
원격 동작 제어  
Remote motion control  
음악 재생  
Play music  
수유등/야간 무드등  
Feeding light / night mood light  
육아정보  
Child care information  
(추가 개발 예정)  
(To be developed further)  


### :pencil2: 역할
----------------------------------------------------------
* 김진엽 : 온습도/미세먼지/비접촉식 모듈, 앱 제작/데이터베이스 구축  
* 신용원 : UV4L/LED/서보모터/애플리케이션 프론트앤드/프레임제작  
* 박현욱 : nodeJS 웹서버/웹 프론트앤드/SoC보드 통신/보드-애플리케이션 통신  


### :pencil2: 개발환경
----------------------------------------------------------
* SoC Board : Arduino Uno / Raspberry Pi 4 B+  
* Device : Galaxy S8  
* Module : DHT22 / SG90 / PM2008M / MLX90614  
* OS : Raspbian  
* DB : MySQL  
* Server : nodeJS  
* Streaming Server : UV4L  


### :pencil2: 시연
----------------------------------------------------------
#### Application
* adobe tool (https://xd.adobe.com/view/4d7cd635-81fe-4a37-79d2-4c57d108293a-3b4d/)
<img src="https://user-images.githubusercontent.com/52243808/76209245-c162d780-6244-11ea-88e2-d418d5158d43.png" width="40%">  <img src="https://user-images.githubusercontent.com/52243808/76209281-d2abe400-6244-11ea-864c-320cf1c46be6.png" width="40%"> <img src="https://user-images.githubusercontent.com/52243808/76209248-c2940480-6244-11ea-9ad2-fa61421a6d91.png" width="40%"> <img src="https://user-images.githubusercontent.com/37360089/76289210-f37b4480-62eb-11ea-833a-0ac49eeedc50.png" width="40%"> <img src="https://user-images.githubusercontent.com/37360089/76289214-f4ac7180-62eb-11ea-9467-420748b6781e.png" width="40%"> <img src="https://user-images.githubusercontent.com/37360089/76289224-f9712580-62eb-11ea-82bb-258a448141a8.png" width="40%">
#### Arduino
<img src="https://user-images.githubusercontent.com/37360089/76210683-f58bc780-6247-11ea-8ccc-aaa18010fc40.jpg" width="40%"><img src="https://user-images.githubusercontent.com/37360089/72738200-6a496a80-3be4-11ea-87ab-3dd8c8f5f42d.png" width="60%">

#### RasberryPi
<img src="https://user-images.githubusercontent.com/37360089/76210681-f3c20400-6247-11ea-8641-bcb040c61425.jpg" width="50%">

#### Server
* UV4L
<img src="https://user-images.githubusercontent.com/37360089/76210685-f6bcf480-6247-11ea-82c1-12650695468c.png" width="50%">


### :pencil2: 참고
----------------------------------------------------------
#### 사이트
* 전반적인 웹구축 : http://www.hardcopyworld.com/ngine/aduino/index.php/archives/3343 
* 모듈에러 참고자료 : https://github.com/serialport/node-serialport/issues/1910
* node 버젼 변경시 참고자료 : https://futurecreator.github.io/2018/05/28/nodejs-npm-update-latest-or-stable-version/
* led GPIO 참고자료 : https://webnautes.tistory.com/836
https://m.blog.naver.com/PostView.nhn?blogId=roboholic84&logNo=220336410478&proxyReferer=https%3A%2F%2Fwww.google.com%2F
https://itnext.io/introduction-to-iot-with-raspberry-pi-and-node-js-using-rgb-led-lights-77f4750a5ea9
https://www.w3schools.com/nodejs/nodejs_raspberrypi_rgb_led_websocket.asp

* 파이카메라를 이용한 MMAL MOTION – MJPEG 스트리밍 : http://www.hardcopyworld.com/ngine/aduino/index.php/archives/1803
* 라즈베리파이 카메라 동영상 스트리밍 : http://blog.naver.com/PostView.nhn?blogId=3demp&logNo=221399934352&categoryNo=0&parentCategoryNo=52
&viewDate=&currentPage=1&postListTopCurrentPage=1&from=search
* 라즈베리파이를 이용한 음악 스트리밍 : https://writingdeveloper.tistory.com/8
Noje.js 스트리밍 : https://javafa.gitbooks.io/nodejs_server_basic/content/chapter11.html

#### 관련 논문
* [이군자,이명희(2002), 신생아 감각자극에 관한 국내 연구 논문 분석, 아동간호학회지, 8(3), 332p]
* [안영미, 손 민, 김남희, 강나래, 강승연, 정은미(2017), 고위험신생아의 저체온증 현황 및 관련요인, 아동간호학회지, 23(4), 505p]

