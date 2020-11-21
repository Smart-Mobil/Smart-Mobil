var mysql      = require('mysql');

var connection = mysql.createConnection({
  port: "3306",
  host     : 'localhost',
  user     : 'root',
  password : '',
  database : 'smartmobil',
  insecureAuth : true
});


//console.log("Connected!");
connection.connect();
exports.adddata = function main( argument1,argument2, argument3, argument4 ) {
  
  console.log("Asddas");
  var sql = "INSERT INTO ENVIRONMENT (time, tempandhum, temperature, dust ) VALUES (?,?,?,?)";
  var params =[ argument1, argument2, argument3, argument4]

  connection.query(sql,params,function(err,rows,fields) {
    if(err){
      console.log(err);
    }else{
      console.log(rows.insertId);
    }  });

   // connection.end();

};


/*
connection.connect();

connection.query('SELECT * from s', function(err, rows, fields) {
  if (!err)
    console.log('The solution is: ', rows);
  else
    console.log('Error while performing Query.', err);
});

connection.end();
*/