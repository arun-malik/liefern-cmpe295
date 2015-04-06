var crypto = require('crypto');
var Hashids = require("hashids");


module.exports.encrypt = function (secretKey, userPassword) {

    var cipher = crypto.createCipher("aes192", secretKey);
    cipher.update(userPassword, "binary", "hex");
    var encodedPassword = cipher.final("hex");

    return encodedPassword;

  };


module.exports.decrypt = function (encodedPassword, secretKey) {
    var decipher = crypto.createDecipher("aes192", secretKey);
    decipher.update(encodedPassword, "hex", "binary");
    var result = decipher.final("binary");

    return result;
  };


module.exports.generateSessionToken =  function (email) {

  var now = new Date().getTime().toString();
  var cipher = crypto.createCipher("aes192", email);
  cipher.update(now, "binary", "hex");
  var encodedPassword = cipher.final("hex");

  return encodedPassword;
};

module.exports.createHashPassword =  function (email,mobile) {

  var hashSessionId = new Hashids(email);
  return hashSessionId.encode(mobile);
};

