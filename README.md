# SmartMobil
한국산업기술대학교 컴퓨터공학과 졸업작품<br>
Korea Polytechnic University<br>
Computer Engineering Senier Project<br>
(Professor) 공기석, (Student) 김진엽, 박현욱, 신용원  

https://youtu.be/NGTB0ODe2eY
--------------------------------------

### :bulb: 프로젝트명 
#### 원격 제어가 가능한 신생아 건강 스마트 모빌  

### :books: 목차
----------------------------------------------------------
1. 목적
2. 기능
3. 역할
4. 개발환경
5. 시연
6. 참고

### :pencil2: 1. 목적
----------------------------------------------------------
* 민감한 아기를 위한 실시간 환경 모니터링
* 애플리케이션을 통하여 육아에 필요한 여러 기능 제공     

### :pencil2: 2. 기능 
----------------------------------------------------------
* 환경, 체온 데이터 수집/제공 
* 무드등  
* 음악 재생
* 원격 동작 제어(모터)
* 육아정보    
* d-day
* SOS (문구 + 현재위치)
* 동화책 읽어주기

### :pencil2: 3. 역할
----------------------------------------------------------
* 김진엽 : 안드로이드 애플리케이션 제작, 온습도/미세먼지/비접촉식 모듈
* 신용원 : UV4L/LED/서보모터/애플리케이션 프론트앤드/프레임제작  
* 박현욱 : nodeJS 웹서버/웹 프론트앤드/SoC보드 통신/보드-애플리케이션 통신  


### :pencil2: 4. 개발환경
----------------------------------------------------------
* SoC Board : Arduino Uno / Raspberry Pi 4 B+  
* Device : Galaxy S10, LG q9
* Module : DHT22 / SG90 / PM2008M / MLX90614  
* OS : Raspbian  
* DB : MySQL  
* Main Server : nodeJS  
* Streaming Server : UV4L  
* Develop Env : MacOS, Windows10

### :pencil2: 5. 시연
----------------------------------------------------------
#### Application
* App 프로토타입
<img src="https://user-images.githubusercontent.com/37360089/80593558-68e9d280-8a5c-11ea-86b9-7a3859a4b599.png"/>

#### Arduino, RasberryPi
* 회로도
<img src="https://user-images.githubusercontent.com/37360089/80593982-207ee480-8a5d-11ea-9919-a68cfae01e78.PNG"/>

#### Server
* node.js
<img src="https://user-images.githubusercontent.com/37360089/80594451-f8dc4c00-8a5d-11ea-8c42-aceba031186a.png"/>

* UV4L
<img src="https://user-images.githubusercontent.com/37360089/76210685-f6bcf480-6247-11ea-82c1-12650695468c.png" width="50%">


### :pencil2: 6. 참고
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

