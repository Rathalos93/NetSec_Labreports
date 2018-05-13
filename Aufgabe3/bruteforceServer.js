const net = require("net");

var client = new net.Socket();
const targetIp = "license-server.svslab";
const port = 1337;
const logfile = "foundkeys.txt" //file needs to exist to work properly.
var keygen = 0;


client.connect(port, targetIp, function() {
  console.log("Connected on: " + targetIp + ":" + port);
  client.write("Kickoff");
  console.log("Kickoff send");
});

client.on("data", function(data) { 
  if(data.includes("SERIAL_VALID=1"))
  {
    var sKey = generateSerialKey(keygen);
    console.log("Key found: " + sKey);
    logFoundKey(sKey);
  }
  
  if(keygen > Math.pow(10, 6))
  {
    console.log("Finished!");
  }
  else
  {
    keygen++;
    client.write("serial=" + generateSerialKey(keygen));  
  } 
});


function generateSerialKey(key)
{
  const digits = key.toString().length();

  while(key.toString().length() < 6)
  {
    key = "0" + key;
  }

  return key + "00";
}

function logFoundKey(str)
{
  if (!str) return;
  fs.appendFile(logfile, str + "\n", function (err)
  {
    if (err)
    {
      return console.log(err);
    }
  });
}
