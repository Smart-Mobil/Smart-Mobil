/*
pm2008_i2c.h -- Arduino library to control Cubic PM2008 I2C

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

#ifndef _PM2008_I2C_h
#define _PM2008_I2C_h

#include <Arduino.h>
#include <Wire.h>

#define PM2008_I2C_ADDRESS                    0x28

// Control modes
#define PM2008_I2C_CTRL_CLOSE_MEASUREMENT                 0x1
#define PM2008_I2C_CTRL_OPEN_SINGLE_MEASUREMENT           0x2
#define PM2008_I2C_CTRL_SET_UP_CONTINUOUSLY_MEASUREMENT   0x3
#define PM2008_I2C_CTRL_SET_UP_TIMING_MEASUREMENT         0x4
#define PM2008_I2C_CTRL_SET_UP_DYNAMIC_MEASUREMENT        0x5
#define PM2008_I2C_CTRL_SET_UP_CALIBRATION_COEFFICIENT    0x6
#define PM2008_I2C_CTRL_SET_UP_WARM_MODE                  0x7

#define PM2008_I2C_CONTROL_MODE               PM2008_I2C_CTRL_SET_UP_CONTINUOUSLY_MEASUREMENT

#define PM2008_I2C_MEASURING_TIME             180
#define PM2008_I2C_CALIBRATION_COEFFICIENT    70
#define PM2008_I2C_FRAME_HEADER               0x16

// Status
#define PM2008_I2C_STATUS_CLOSE               0x1
#define PM2008_I2C_STATUS_UNDER_MEASURING     0x2
#define PM2008_I2C_STATUS_FAILED              0x7
#define PM2008_I2C_STATUS_DATA_STABLE         0x80

// #define PM2008_I2C_DEBUG

class PM2008_I2C {

public:
  void begin();
  void command();
  uint8_t read();

  uint8_t     status;
  uint16_t    measuring_mode;
  uint16_t    calibration_coefficient;
  uint16_t    pm1p0_grimm;
  uint16_t    pm2p5_grimm;
  uint16_t    pm10_grimm;
  uint16_t    pm1p0_tsi;
  uint16_t    pm2p5_tsi;
  uint16_t    pm10_tsi;
  uint16_t    number_of_0p3_um;
  uint16_t    number_of_0p5_um;
  uint16_t    number_of_1_um;
  uint16_t    number_of_2p5_um;
  uint16_t    number_of_5_um;
  uint16_t    number_of_10_um;

private:
  uint8_t _buffer[32];
};

#endif
