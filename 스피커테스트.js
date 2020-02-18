var lame = require(theApp.module_path +'lame');

var wav = require(theApp.module_path +'wav');

var Speaker = require(theApp.module_path + 'speaker');

var lame_obj = {

                    file : fs.createReadStream('./a.mp3' ),
                    reader : new lame.Reader()

                };
                lame_obj.reader.on('format',function(format) {

                    //console.log(format);

                    lame_obj.speaker = new Speaker(format);

                    lame_obj.reader.pipe(lame_obj.speaker);


                });

                lame_obj.file.pipe(lame_obj.reader);
