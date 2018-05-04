const net = require("net");

var client = new net.Socket();
const targetIp = "127.0.0.1";
const port = 80;

var keygen = 0;

client.connect(port, targetIp, function() {
  console.log("Connected on: " + targetIp + ":" + port);
  client.write("Kickoff");
  console.log("Kickoff send");
});

client.on("data", function(data) {
  //TODO check if send serial was correct
  if(data.includes("SERIAL_VALID=1"))
  {
    console.log("Key found: " + generateSerialKey(keygen));
  }
  else
  {
    if(keygen > Math.pow(10, 8))
    {
      console.log("I should never be called!")
    }
    else
    {
      client.write("serial=" + generateSerialKey(keygen));
    }
  }
});


function generateSerialKey(key)
{
  const digits = key.toString().length();

  while(key.toString().length() < 8)
  {
    key = "0" + key;
  }

  return key;
}
