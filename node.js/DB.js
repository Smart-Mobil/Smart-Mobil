var mysql      = require('mysql');

var connection = mysql.createConnection({
  port: "3306",
  host     : 'localhost',
  user     : 'root',
  password : '*B3ECDE53DBC07418E558CAE7F34AD5CC4761C4F6',
  database : 'smartmobil'
});


connection.connect(function(err) {
  
  console.log("Connected!");
  var sql = "INSERT INTO ENVIRONMENT (time, tempandhum, temperature, dust ) VALUES ('test', 'test', 'test', 'test')";
  connection.query(sql, function (err, result) {
    console.log("1 record inserted");
  });
});

connection.query('select * from ENVIRONMENT', function (err, rows, fields) {
  if (!err) {
      console.log(rows);
      console.log(fields);
      var result = 'rows : ' + JSON.stringify(rows) + '<br><br>' +
          'fields : ' + JSON.stringify(fields);
      res.send(result);
  } else {
      console.log('query error : ' + err);
      
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