const fs = require('fs');

const sFile = "advaziCryptfiles/n04.txt.enc" //filename goes here

fs.readFile(sFile, function read(err, data) {
    if (err) {
        throw err;
    }
    console.log(data);
    var sKey = getAdvaziKey(data);

    console.log("Key found: " +
    Buffer.from(sKey, 'hex').toString('utf8'));

    var clear = decrypt(sKey, data);
    console.log("Klartext: " + clear);

});

function getAdvaziKey(data) {
  return "0A";
}

function decrypt(sKey, sMessage) {
  var sFullKey = "";

  while(sFullKey.length < sMessage.length) {
    sFullKey += sKey;
  }

  return Buffer.from(xor(sFullKey, sMessage), 'hex').toString('utf8');
}

function xor(a, b) {
  if (!Buffer.isBuffer(a)) a = new Buffer(a)
  if (!Buffer.isBuffer(b)) b = new Buffer(b)
  var res = []
  if (a.length > b.length) {
    for (var i = 0; i < b.length; i++) {
       res.push(a[i] ^ b[i])
    }
 } else {
 for (var i = 0; i < a.length; i++) {
   res.push(a[i] ^ b[i])
   }
 }
 return new Buffer(res);
}
