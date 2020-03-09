# SmartMobil
Korea Polytechnic University  
Computer Engineering Senier Project  

# 0. 프로젝트 명(Project Title)
#### 원격 제어가 가능한 신생아 건강 스마트 모빌  
Remotely Controllable Smart Mobil for newborn baby  
Korea Polytechnic University  
Professor 공기석 Student 김진엽 박현욱 신용원  

# 1. Introduction  
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

# 2. Proposal
모빌에 부착된 다양한 모듈을 통해 환경 데이터 수집 및 영상 스트리밍  
Collect environmental data and stream video through various modules attached to mobiles  
애플리케이션과 웹을 통해 다양한 환경 데이터 전달, 모빌 원격 제어  
Various environmental data delivery through mobile and application, mobile remote control  

# 3. Main Function  
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


# 4. Role
김진엽 : 온습도/미세먼지/비접촉식 모듈, 앱 제작/데이터베이스 구축  
신용원 : UV4L/LED/서보모터/애플리케이션 프론트앤드/프레임제작  
박현욱 : nodeJS 웹서버/웹 프론트앤드/SoC보드 통신/보드-애플리케이션 통신  

# 5. Detail
SoC Board : Arduino Uno / Raspberry Pi 4 B+  
Device : Galaxy S8  
Module : DHT22 / SG90 / PM2008M / MLX90614  
OS : Raspbian  
DB : MySQL  
Server : nodeJS  
Streaming Server : UV4L  

# 6. 제작과정 시연/참고 자료
앱 프론트앤드(Adobe XD/200219) : https://xd.adobe.com/view/4d7cd635-81fe-4a37-79d2-4c57d108293a-3b4d/  
개발 사진(아두이노 테스트)
<div>
<img src="https://user-images.githubusercontent.com/37360089/72738192-661d4d00-3be4-11ea-90a1-54e613cbda81.jpg"></img>  
<img src="https://user-images.githubusercontent.com/37360089/72738200-6a496a80-3be4-11ea-87ab-3dd8c8f5f42d.png"></img>
</div>

