var mysql      = require('mysql');

var connection = mysql.createConnection({
  host     : 'localhost:3306/smartmobil',
  user     : 'root',
  password : '5289',
  database : 'ENVIRONMENT'
});

con.connect(function(err) {
  if (err) throw err;
  console.log("Connected!");
  var sql = "INSERT INTO customers (name, address) VALUES ('Company Inc', 'Highway 37')";
  con.query(sql, function (err, result) {
    if (err) throw err;
    console.log("1 record inserted");
  });
});

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