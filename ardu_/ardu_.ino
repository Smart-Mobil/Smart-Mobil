#include <Wire.h>
#include <Adafruit_MLX90614.h>
#include <pm2008_i2c.h>
#include "DHT.h"

#define DHTPIN 2        // SDA 핀의 설정
#define DHTTYPE DHT22   // DHT22 (AM2302) 센서종류 설정
 
Adafruit_MLX90614 mlx = Adafruit_MLX90614();
PM2008_I2C pm2008_i2c;
DHT dht(DHTPIN, DHTTYPE);

void setup() {
  pm2008_i2c.begin();
  pm2008_i2c.command();
  
  dht.begin();
  mlx.begin();  
  Serial.begin(9600);
}

void loop() {
   /* 
   1. 미세먼지
   2. 온습도
   3. 체온 (비접촉식온도센서)
   */
   
  //미세먼지
  uint8_t ret = pm2008_i2c.read();
  if (ret == 0) {
    Serial.print("1.");
    Serial.print(pm2008_i2c.pm10_tsi);
    Serial.print(",");
    Serial.print(pm2008_i2c.pm2p5_tsi);
  }
  
  Serial.println();
  delay(1000);
  
  //온습도
  float h = dht.readHumidity();
  float t = dht.readTemperature();

  if (isnan(t) || isnan(h)) {
  //값 읽기 실패시
  } else {
    Serial.print("2.");
    Serial.print(t);
    Serial.print("*C");
    Serial.print(",");
    Serial.print(h);
    Serial.print("%");
  }
  
  Serial.println();
  delay(1000);
  
  //비접촉식온도센서
  Serial.print("3."); 
  Serial.print(mlx.readObjectTempC()); 
  Serial.print("*C"); 

  Serial.println();
  delay(1000);
}
