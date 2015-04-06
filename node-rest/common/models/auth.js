var crypto = require('crypto');

module.exports = function(Auth) {

  // this is to disable api
  Auth.disableRemoteMethod('encryptPassword', true);
  Auth.disableRemoteMethod('decryptPassword', true);


  //Auth.remoteMethod(
  //  'encryptPassword',
  //  {
  //    accepts: { arg: 'auth', type: 'object', http: { source: 'body' } } ,
  //    returns : {arg: 'code', type: 'string'},
  //    http: {path: '/encryptPassword', verb:'POST'}
  //  }
  //);
  //
  //
  //Auth.remoteMethod(
  //  'decryptPassword',
  //  {
  //    accepts: { arg: 'auth', type: 'object', http: { source: 'body' } } ,
  //    returns : {arg: 'password', type: 'string'},
  //    http: {path: '/decryptPassword', verb:'POST'}
  //  }
  //);


  Auth.encryptPassword = function(auth,cb){

    console.log("Auth: %s",auth);
    var input = auth.userid;
    var password = auth.password;

    Auth.encrypt(input, password, function (encoded)
    {  console.log(encoded);
       cb(null,encoded);
    });




  };

  Auth.decryptPassword = function(auth,cb){

    console.log("Auth: %s",auth);
    var input = auth.userid;
    var password = auth.password;

    decrypt(input, password, function (output)
    {
      console.log(output);
      cb(null,output);
    });
  };


  Auth.encrypt = function(input, password, callback){

    var cipher = crypto.createCipher("aes192", password);
    cipher.update(input, "binary", "hex");
    var encoded = cipher.final("hex");
    callback(encoded);

  };


  var decrypt = function (input, password, callback) {
    var decipher = crypto.createDecipher("aes192", password);
    decipher.update(input, "hex", "binary");
    var plainText = decipher.final("binary");
    callback(plainText);
  };



};
