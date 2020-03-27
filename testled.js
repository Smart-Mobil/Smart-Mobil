// import `onoff` package
const { Gpio } = require( 'onoff' );

// configure LED pins
const pin_red = new Gpio( 26, 'out' );
const pin_green = new Gpio( 19, 'out' );
const pin_blue = new Gpio( 13, 'out' );

// toggle LED states
exports.toggle = ( r, g, b ) => {
  pin_red.writeSync( r ? 1 : 0 );
  pin_green.writeSync( g ? 1 : 0 );
  pin_blue.writeSync( b ? 1 :0 );
};