// Import the module.
var Omx = require('node-omxplayer');
 
// Create an instance of the player with the source.
var player = Omx('./soundfile/baby_melody/a.mp3');
 
// Control video/audio playback.

/*
player.pause();
player.volUp();
player.quit();*/

player.play();