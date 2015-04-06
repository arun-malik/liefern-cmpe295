var Crypto = require('../utils/authorization');
var Hashids = require("hashids");

module.exports = function(Liefernuser) {

  // this is to disable api
  //Liefernuser.disableRemoteMethod('saveUser', true); // GET /Liefernuser


  //will be used in every API to check token
  Liefernuser.beforeRemote('find', function(ctx, unused, next) {

    var email  =  ctx.req.body.email;

    if(ctx.req.headers.token) {
      console.log(ctx.req.headers.token);
      var user = Liefernuser.find({"where": {"sessiontoken": ctx.req.headers.token}});
      console.log(user);

      next();
    } else {
      next(new Error('must be logged in to update'))
    }
  });


// should check token for all request except user creation
  Liefernuser.beforeRemote('find', function(ctx, unused, next) {
    var token = ctx.req.headers.token;
    if(token) {
        //request.get({
        //  url: 'http://127.0.0.1:3000/api/liefernusers//findOne?filter={"where": {"sessiontoken": "'+token+'"}}',
        //  method: 'GET',
        //  json: ctx.result
        //}, function (err, res) {
        //  if (err){
        //    console.error(err);
        //  }else{
        //
        //    //var now = new Date().toLocaleTimeString();
        //    //console.log(now);
        //    //
        //    //var SessionToken = createHashPassword.decrypt(now,"malik.mgm@gmail.com");
        //    //console.log("SessionToken : ",SessionToken);
        //    //
        //    //var lastAuthTime = createHashPassword.decrypt(SessionToken,"malik.mgm@gmail.com");
        //    //console.log("lastAuthTime: ",lastAuthTime);
        //
        //    console.log("token: ",token);
        //    console.log("email: ",JSON.parse(res.body).email);
        //    var lastAuthTime = createHashPassword.decrypt(token,JSON.parse(res.body).email);
        //    console.log("lastAuthTime: ",lastAuthTime);
        //  }
        //  next();
        //});
     }
  });



  Liefernuser.beforeRemote('create', function(ctx, unused, next) {
     var json = ctx.req.body;

    if (!json.hasOwnProperty('email')){
      next(new Error('email is mandatory field'))
    }

    if (!json.hasOwnProperty('mobile')){
      next(new Error('email is mandatory field'))
    }

    if (!json.hasOwnProperty('mobile')){
      next(new Error('email is mandatory field'))
    }

    var secretKey = Crypto.createHashPassword(json.email,json.mobile);

    var hashPassword = Crypto.encrypt(secretKey,json.password);
    //console.log("hashPassword : " ,hashPassword);

    //var revUserPassword = Crypto.decrypt(hashPassword,secretKey); // to be removed
    //console.log("revUserPassword : " ,revUserPassword);

    var sessionToken = Crypto.generateSessionToken(json.email); //TODO: this will be replace by combination of device UUID and email id
    //console.log("SessionToken: ",sessionToken);

    //var revtime = Crypto.decrypt(sessionToken,json.email); // to be removed
    //console.log("revTime : " ,revtime);

    ctx.req.body.hashpassword = hashPassword;
    ctx.req.body.secretkey =  secretKey;
    ctx.req.body.sessiontoken = sessionToken;

    next();

  });


  Liefernuser.remoteMethod(
    'login',
    {
      accepts: { arg: 'data', type: 'object', http: { source: 'body' } } ,
      returns : {arg: 'userSessionToken', type: 'string'},
      http: {path: '/login', verb:'POST'},
      description : 'Authenticate and return session token to user'
    }
  );


  Liefernuser.login = function(data,cb){

    if (!data.hasOwnProperty('email')){
      cb(null,'email is mandatory field');
    }

    Liefernuser.findOne({"where":{"email": data.email}}, function(err,res){
      if(err){
        console.log(err);
        cb(null,err);

      }

      //if(res){
      //
      //  var revUserPassword = Crypto.decrypt(res.hashpassword,res.secretkey);
      //
      //  if(revUserPassword === data.password){
      //    if((new Date().getTime() - res.sessiontoken) > 60000){ // session expires after 10 mins
      //      var sessionToken = Crypto.generateSessionToken(data.email); //TODO: this will be replace by combination of device UUID and email id
      //      res.sessiontoken = sessionToken;
      //
      //      Liefernuser.upsert(res, function(err,res) {
      //        if(err){
      //          console.log(err);
      //          cb(null,err);
      //
      //        }
      //
      //        cb(null,res.sessiontoken);
      //      });
      //    }
      //      cb(null,res.sessiontoken);
      //  }
      //}

       if(null === res){
         cb(null,"user not found");

       }

    });
  };


};
