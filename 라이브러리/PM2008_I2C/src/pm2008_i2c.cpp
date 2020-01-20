/*
pm2008_i2c.cpp -- Arduino library to control Cubic PM2008 I2C

Copyright (c) 2018 Neosarchizo.
All rights reserved.

This file is part of the library PM2008 I2C.

PM2008 I2C is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

PM2008 I2C is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

*/

#include "pm2008_i2c.h"

/**
 * Start PM2008 I2C library
 */
void PM2008_I2C::begin() {
  // Start the Wire library
  Wire.begin();
}

/**
 * Send command data
 */
void PM2008_I2C::command() {
  _buffer[0] = PM2008_I2C_FRAME_HEADER;
  _buffer[1] = 0x7; // frame length
  _buffer[2] = PM2008_I2C_CONTROL_MODE;

  uint16_t data;

  switch (PM2008_I2C_CONTROL_MODE) {
    case PM2008_I2C_CTRL_SET_UP_CONTINUOUSLY_MEASUREMENT:
      data = 0xFFFF;
      break;
    case PM2008_I2C_CTRL_SET_UP_CALIBRATION_COEFFICIENT:
      data = PM2008_I2C_CALIBRATION_COEFFICIENT;
      break;
    default:
      data = PM2008_I2C_MEASURING_TIME;
      break;
  }

  _buffer[3] = data >> 8;
  _buffer[4] = data & 0xFF;
  _buffer[5] = 0; // Reserved

  // Calculate checksum
  _buffer[6] = _buffer[0];

  for (uint8_t i = 1; i < 6; i++) {
    _buffer[6] ^= _buffer[i];
  }

  Wire.beginTransmission(PM2008_I2C_ADDRESS);
  Wire.write(_buffer, 7);
  Wire.endTransmission();
}

/**
 * Read PM2008 value
 * @return {@code 0} Reading PM2008 value succeeded
 *         {@code 1} Buffer(index) is short
 *         {@code 2} Frame header is different
 *         {@code 3} Frame length is not 32
 *         {@code 4} Checksum is wrong
 */
uint8_t PM2008_I2C::read() {
  Wire.requestFrom(PM2008_I2C_ADDRESS, 32);
  uint8_t idx = 0;

  while (Wire.available()) { // slave may send less than requested
    uint8_t b = Wire.read();
    _buffer[idx++] = b;
    if (idx == 32) break;
  }

  if (idx < 32) {
#ifdef PM2008_I2C_DEBUG
    Serial.println("PM2008_I2C::read : buffer is short!");
#endif
    return 1;
  }

  // Check frame header
  if (_buffer[0] != PM2008_I2C_FRAME_HEADER) {
#ifdef PM2008_I2C_DEBUG
    Serial.print("PM2008_I2C::read : frame header is different ");
    Serial.println(_buffer[0], HEX);
#endif
    return 2;
  }

  // Check frame length
  if (_buffer[1] != 32) {
#ifdef PM2008_I2C_DEBUG
    Serial.println("PM2008_I2C::read : frame length is not 32");
#endif
    return 3;
  }

  // Check checksum
  uint8_t check_code = _buffer[0];

  for (uint8_t i = 1; i < 31; i++) {
    check_code ^= _buffer[i];
  }

  if (_buffer[31] != check_code) {
#ifdef PM2008_I2C_DEBUG
    Serial.print("PM2008_I2C::read failed : check code is different - _buffer[31] : ");
    Serial.print(_buffer[31], HEX);
    Serial.print(", check_code : ");
    Serial.println(check_code, HEX);
#endif
    return 4;
  }

  /// Status
  status = _buffer[2];
  measuring_mode = (_buffer[3] << 8) + _buffer[4];
  calibration_coefficient = (_buffer[5] << 8) + _buffer[6];
  pm1p0_grimm = (_buffer[7] << 8) + _buffer[8];
  pm2p5_grimm = (_buffer[9] << 8) + _buffer[10];
  pm10_grimm = (_buffer[11] << 8) + _buffer[12];
  pm1p0_tsi = (_buffer[13] << 8) + _buffer[14];
  pm2p5_tsi= (_buffer[15] << 8) + _buffer[16];
  pm10_tsi= (_buffer[17] << 8) + _buffer[18];
  number_of_0p3_um= (_buffer[19] << 8) + _buffer[20];
  number_of_0p5_um= (_buffer[21] << 8) + _buffer[22];
  number_of_1_um= (_buffer[23] << 8) + _buffer[24];
  number_of_2p5_um= (_buffer[25] << 8) + _buffer[26];
  number_of_5_um= (_buffer[27] << 8) + _buffer[28];
  number_of_10_um= (_buffer[29] << 8) + _buffer[30];

  return 0;
}
