var mysql      = require('mysql');

var connection = mysql.createConnection({
  port: "3306",
  host     : 'localhost',
  user     : 'root',
  password : '',
  database : 'smartmobil',
  insecureAuth : true
});

connection.connect();
console.log("Connected!");


  var sql = "INSERT INTO ENVIRONMENT (time, tempandhum, temperature, dust ) VALUES (?,?,?,?)";
  var params = ['test title','testuser','testpw','testcontent'];

  connection.query(sql,params,function(err,rows,fields) {
    if(err){
      console.log(err);
    }else{
      console.log(rows.insertId);
    }
  });


var sql2 = 'SELECT * FROM ENVIRONMENT';

connection.query(sql2,function(err,rows,fields) {
  if(err){
    console.log(err);
  }else{
    for(var i =0;i<rows.length;i++){
      console.log(rows[i].board_title);
    }
  }
});

connection.end();

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