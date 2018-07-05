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

function getAdvaziKey(data) {
	var bPaddedKey = data.slice(data.length -10, data.length);
	var sPaddedKey = Buffer.from(bPaddedKey, 'hex').toString('utf8');
	var sData = Buffer.from(data, 'hex').toString('utf8');
	var sPaddingFill = "";
	var sPaddingFillFull = "";
	
	var i = 0;
	while(sData.charAt(sData.length - (i+1)) == sPaddedKey.charAt(sPaddedKey.length - ((i%10)+1))) {
		i++;
	}
	
	sPaddingFill = String.fromCharCode(i);
	console.log("CONVERTED I TO HEXSTRING: " + i + " = " + sPaddingFill);
	
	for(var j = 0; j < 10; j++){
		sPaddingFillFull += sPaddingFill;
	}
	sPaddingFillFull = Buffer.from(new Buffer(sPaddingFillFull), 'hex').toString('utf8');
	console.log(sPaddingFillFull);
	var trueKey = xor(sPaddingFillFull, sPaddedKey);
	console.log(Buffer.from(trueKey, 'hex').toString('utf8'));
  return trueKey;
}