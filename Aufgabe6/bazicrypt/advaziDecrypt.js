const fs = require('fs');

const sFile = "advaziCryptfiles/n04.txt.enc" //filename goes here

fs.readFile(sFile, function read(err, dataBuffer) {
    if (err) {
        throw err;
    }
    console.log(typeof dataBuffer);
    console.log(dataBuffer);
    var sHexKey = getAdvaziKey(dataBuffer);

    console.log(typeof sHexKey);
    console.log("Key found: " +
    Buffer.from(sHexKey, 'hex').toString('utf8'));

    var clear = decrypt(sHexKey, dataBuffer);
    console.log("Klartext: " + clear);

});

function getAdvaziKey(data) {
  var bufferPaddedKey = data.slice(data.length -10, data.length);
  return "5A";
}

function decrypt(sHexKey, sMessage) {
  var sFullKey = "";

  while(sFullKey.length < sMessage.length) {
    sFullKey += sHexKey;
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
