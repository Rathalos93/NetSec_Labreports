const md5 = require("md5");

const hash = "2b2935865b8a6749b0fd31697b467bd7";
const salt = "8kofferradio";
const account = "testaccount";
const cardinality = 6;
var result = "";

//check if trunc is valid operation
Math.trunc = Math.trunc || function(x) {
  if (isNaN(x)) {
    return NaN;
  }
  if (x > 0) {
    return Math.floor(x);
  }
  return Math.ceil(x);
};

const alphabet = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                  'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                  'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'];

for(var i = 0; i < Math.pow(alphabet.length, cardinality+1); i++)
{
  //first arity
  result = alphabet[i%36];
  if(i >= Math.pow(36, 1))
  {
    //second arity
    result = alphabet[Math.trunc(i/36) % 36] + result;
    if(i >= Math.pow(36, 2))
    {
      //third arity
      result = alphabet[Math.trunc(i/Math.pow(36,2)) % 36] + result;
      if(i >= Math.pow(36, 3))
      {
        //fourth arity
        result = alphabet[Math.trunc(i/Math.pow(36,3)) % 36] + result;
        if(i >= Math.pow(36, 3))
        {
          //fifth arity
          result = alphabet[Math.trunc(i/Math.pow(36,4)) % 36] + result;
          if(i >= Math.pow(36, 4))
          {
            //sixth arity
            result = alphabet[Math.trunc(i/Math.pow(36,5)) % 36] + result;
          }
        }
      }
    }
  }
  var saltAndPasswd = salt + result;
  var hashedPasswd = md5(saltAndPasswd);

  if(hash === hashedPasswd)
  {
    console.log("Password found! :")
    console.log("User: " + account + " Password: " + result);
    break;
  }
  else
  {

    console.log( i + ". at " + result + "-- Retry");
  }
}

console.log("############################################### --- *hackervoice* I'M IN --- ##############################################################");
