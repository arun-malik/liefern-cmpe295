var Crypto = require('../utils/authorization');
var uuid = require('node-uuid');
var hash = require("hashids");
var Payments = require('./payments');


module.exports = function(Liefernuser) {

    //Liefernuser.beforeRemote('prototype.*', function(ctx, user, next) {
    //
    //    var header = ctx.req.headers;
    //
    //    if (!header.hasOwnProperty('token')){
    //        next(new Error('please pass token in header'))
    //    }
    //
    //    next();
    //});

    Liefernuser.remoteMethod(
        'loginUser',
        {
            accepts: { arg: 'data', type: 'object', http: { source: 'body' } } ,
            returns : {arg: 'response', type: 'object'},
            http: {path: '/login', verb:'POST'},
            description : 'Authenticate and return session token to user'
        }
    );

    Liefernuser.remoteMethod(
        'logoutUser',
        {
            // accepts: { arg: 'data', type: 'object', http: { source: 'body' } } ,
            returns : {arg: 'logout', type: 'Boolean'},
            http: {path: '/logout', verb:'POST'},
            description : 'clear session token and set 0 in active'
        }
    );

    // add session key and hash password to DB
  Liefernuser.beforeRemote('create', function(ctx, unused, next) {
     var json = ctx.req.body;

    if (!json.hasOwnProperty('email')){
      next(new Error('email is mandatory field'))
    }

    if (!json.hasOwnProperty('mobile')){
      next(new Error('mobile is mandatory field'))
    }

    if (!json.hasOwnProperty('password')){
      next(new Error('password is mandatory field'))
    }


    var secretKey = 'liefernmalik';
      //console.log("secretkey : ",secretKey);

    var hashPassword = Crypto.encrypt(secretKey,json.password);
    //console.log("hashPassword : " ,hashPassword);

    //var revUserPassword = Crypto.decrypt(hashPassword,secretKey);
    //console.log("revUserPassword : " ,revUserPassword);

    var now = new Date().getTime().toString();
    var sessionToken = Crypto.encrypt(secretKey,now);
    //console.log("SessionToken: ",sessionToken);

    //var revtime = Crypto.decrypt(sessionToken,secretKey); // to be removed
    //console.log("revTime : " ,revtime);

    ctx.req.body.hashpassword = hashPassword;
    ctx.req.body.secretkey =  secretKey;
    ctx.req.body.sessiontoken = sessionToken;

    next();

  });

    Liefernuser.beforeRemote('logoutUser', function(ctx, unused, next) {

        var header = ctx.req.headers;

        if (!header.hasOwnProperty('token')){
            next(new Error('please pass token in header'))
        }

        Liefernuser.findOne({  where:
                              {   sessiontoken : header.token

                              }
        }, function(err,user){

            if(err){
                next(new Error(err));
            }

            if(user.length ===0){
                next(new Error("Invalid Token. Please login again"));
            }else{
                user.sessiontoken = null;
                user.active = 0;

                Liefernuser.update( {sessiontoken : header.token}, {sessiontoken : null , active : 0} ,{ upsert: true },
                function(err,res) {
                    if(err){
                      //console.log(err);
                      next(new Error(err));

                    }

                    ctx.req.body.success = true;
                    next();

                  });
            }

        });
    });

    Liefernuser.logoutUser = function(cb){
        cb(null,true);
    };

    // session token has time, can add logic for session time out if required
    Liefernuser.loginUser = function(json,cb){

        if (!json.hasOwnProperty('email')){
            cb(new Error('email is required field'),null);
        }

        validateUserAndPassword(json, function(err, res){

            if(err)
            {
                cb(err,null);
            }

            if(res === null){
                cb(new Error("Error login."))
            }else{
                cb(null,res);
            }

        });


    };


validateUserAndPassword =  function (json,callback) {


    Liefernuser.findOne({  where:
    {   email :  json.email

    }
    }, function(err,user){

        //console.log(user);

        if(err){
            callback(new Error(err),null);
        }

        if(user === null){
            callback(new Error("Invalid email"),null);
        }else{

            var revUserPassword = Crypto.decrypt(user.hashpassword,user.secretkey);
            //console.log("revUserPassword : " ,revUserPassword);

            if(revUserPassword !== json.password){

                callback(new Error("Incorrect password."),null);
            }

            if(user.sessiontoken === null && user.active ===0){

                var secretKey = uuid.v1();
                var now = new Date().getTime().toString();
                var sessionToken = Crypto.encrypt(secretKey,now);

                user.sessiontoken =sessionToken;
                user.secretkey = secretKey;
                user.active = 1;

            Liefernuser.update( {email : user.email}, {sessiontoken : sessionToken , active : 1, secretkey : secretKey} ,{ upsert: true },
                    function(err,res) {
                        if(err){
                            //console.log(err);
                            callback(new Error(err),null);

                        }

                        callback(null,user);

                    });


            }else{
                callback(null,user);
            }


        }

    });

};


    module.exports.getUser =  function (token,cb) {

        Liefernuser.findOne({  where:
        {   sessiontoken : token

        }
        }, function(err,user){

            if(err){
                //return new Error(err);
                cb(null,new Error(err));
            }

            if(user.length ===0){
                //return new Error("Invalid Token. Please login again");
                cb(null,new Error("Invalid Token. Please login again"));
            }else {


                Payments.getPaymentDetails(user.userid,cb),function(err,res){


                    if(err){
                        //return new Error(err);
                        cb(err,null);
                    }

                    //return res;
                    cb(res,null);

                }
                //Payments.find({  where:
                //{   userid : user.userid
                //
                //}
                //}, function(err,pay){
                //
                //    if(err){
                //        next(new Error(err));
                //    }
                //
                //    if(pay.length ===0){
                //        next(new Error("Please add paymnent details before processing order"));
                //    }else {
                //
                //        next();
                //    }
                //
                //});
            }

        });
    };



};
