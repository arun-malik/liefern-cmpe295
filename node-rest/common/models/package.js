var Liefernuser = require('./liefernuser');
var server = require('../../server/server');


module.exports = function(Package) {

    //Package.beforeRemote('**', function(ctx, user, next) {
    //
    //    var header = ctx.req.headers;
    //
    //    if (!header.hasOwnProperty('token')){
    //        next(new Error('please pass token in header'))
    //    }
    //
    //    next();
    //});

    //var secretKey = server.get('secretSkipHeader');

    //Package.beforeRemote('**', function(ctx, user, next) {
    //
    //    var header = ctx.req.headers;
    //
    //    if(header.hasOwnProperty('skip') && header.skip === secretKey){
    //        next();
    //    }
    //    else{
    //        if (!header.hasOwnProperty('token')){
    //            next(new Error('please pass token in header'))
    //        }
    //
    //        Liefernuser.getUser(header.token,function(err, res){
    //
    //            if(err){
    //                next(err,null);
    //            }
    //            if(res === null){
    //                next(res,null);
    //            }
    //
    //            next();
    //        });
    //    }
    //});

    module.exports.getPackageDetails = function (orderArray,cb) {

        Package.find({  where:
        {   orderid : order.orderid

        }
        }, function(err,packageArray){

            if(err){
                //return new Error(err);
                cb(new Error(err),null);
            }

            if(packageArray.length === 0){
                //return new Error("Please enter payment details before placing an order.");
                //cb(new Error("Please enter payment details before placing an order."),null);
                cb(null,null);

            }else {
                //return pay;
                order.packages = packageArray;
                cb(null,order);
            }

        });
    };

    module.exports.savePackages =  function (packagesArray,orderid,callback) {

        packagesArray.forEach(function(entry){
            entry.orderid = orderid;
        });

        if(packagesArray === null){
            callback(new Error("Invalid address"),null);
        }else{

             packagesArray.every(function(element, index, array) {

                Package.create(array,function(err,res) {
                    if(err){
                        callback(new Error(err),null);
                    }
                    callback(null,res);
                });

            });


        }
    }
};
