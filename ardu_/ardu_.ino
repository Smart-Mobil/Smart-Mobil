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
  Serial.begin(9600);
  pm2008_i2c.command();
  Serial.println("비접촉식온도센서, 미세먼지센서, 온습도센서 test");  
  
  dht.begin();
  mlx.begin();  
    delay(1000);

}

void loop() {
  //비접촉식온도센서
   Serial.println("비접촉식온도 센서");
  Serial.print("외부 온도 = "); Serial.print(mlx.readAmbientTempC()); Serial.println("*C");
  Serial.print("접촉대상 온도 = "); Serial.print(mlx.readObjectTempC()); Serial.println("*C");
  //Serial.print("Ambient = "); Serial.print(mlx.readAmbientTempF()); 
  //Serial.print("*F\tObject = "); Serial.print(mlx.readObjectTempF()); Serial.println("*F");

  Serial.println();
  delay(500);

  //미세먼지
  uint8_t ret = pm2008_i2c.read();

   Serial.println("미세먼지 센서");
  if (ret == 0) {
    Serial.print("PM 1.0 (GRIMM) : ");
    Serial.println(pm2008_i2c.pm1p0_grimm);
    Serial.print("PM 2.5 (GRIMM) : : ");
    Serial.println(pm2008_i2c.pm2p5_grimm);
    Serial.print("PM 10 (GRIMM) : : ");
    Serial.println(pm2008_i2c.pm10_grimm);
    Serial.print("PM 1.0 (TSI) : ");
    Serial.println(pm2008_i2c.pm1p0_tsi);
    Serial.print("PM 2.5 (TSI) : : ");
    Serial.println(pm2008_i2c.pm2p5_tsi);
    Serial.print("PM 10 (TSI) : : ");
    Serial.println(pm2008_i2c.pm10_tsi);
    Serial.print("Number of 0.3 um : ");
    Serial.println(pm2008_i2c.number_of_0p3_um);
    Serial.print("Number of 0.5 um : ");
    Serial.println(pm2008_i2c.number_of_0p5_um);
    Serial.print("Number of 1 um : ");
    Serial.println(pm2008_i2c.number_of_1_um);
    Serial.print("Number of 2.5 um : ");
    Serial.println(pm2008_i2c.number_of_2p5_um);
    Serial.print("Number of 5 um : ");
    Serial.println(pm2008_i2c.number_of_5_um);
    Serial.print("Number of 10 um : ");
    Serial.println(pm2008_i2c.number_of_10_um);
  }
  
  Serial.println();
  delay(500);
  
  //온습도
  float h = dht.readHumidity();
  float t = dht.readTemperature();
 
  if (isnan(t) || isnan(h)) {
    //값 읽기 실패시 시리얼 모니터 출력
    Serial.println("Failed to read from DHT");
  } else {
    //온도, 습도 표시 시리얼 모니터 출력
    Serial.print("습도: "); 
    Serial.print(h);
    Serial.print(" %\t");
    Serial.print("온도: "); 
    Serial.print(t);
    Serial.println(" *C");
  }
  delay(2000);
  Serial.println();

}
