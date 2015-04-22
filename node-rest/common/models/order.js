var Liefernuser = require('./liefernuser');
var Package = require('./package');
var server = require('../../server/server');
var Address = require('./address');
var Package = require('./package');

module.exports = function(Order) {

    //var secretKey = server.get('secretSkipHeader');
    //
    //Order.beforeRemote('**', function(ctx, user, next) {
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

    Order.remoteMethod(
        'saveOrder',
        {
            accepts: { arg: 'data', type: 'object', http: { source: 'body' } },
            returns : {arg: 'response', type: 'object'},
            http: {path: '/saveAll', verb:'POST'},
            description : 'API will be used to get all request made by customer.'
        }
    );


    Order.remoteMethod(
        'getCustomerDetails',
        {
            accepts: {arg: 'id', type: 'integer', required:true},
            returns : {arg: 'response', type: 'object'},
            http: {path: '/customer', verb:'GET'},
            description : 'API will be used to get all request made by customer.'
        }
    );

    Order.saveOrder = function(data,callback) {

        if(null !=  data.fromlocation){

            Address.saveAddress(data.fromlocation,function(err,res) {
                if(err){
                    callback(new Error(err));
                }
                //var fromLocationAddressId = res.addressid;
                data.fromloc = res.addressid;
                data.fromlocation.addressid = res.addressid;

                if(null !=  data.tolocation){

                    Address.saveAddress(data.tolocation,function(err,res) {
                        if(err){
                            callback(new Error(err));
                        }
                        //var toLocationAddressId = res.addressid;
                        data.toloc = res.addressid;
                        data.tolocation.addressid = res.addressid;

                        Order.create(data, function(err,res){
                            if(err){
                                callback(new Error(err),null);
                            }

                            var orderId = res.orderid;
                            data.orderid = orderId;

                            if(data.packages.length > 0 ){

                                Package.savePackages(data.packages,orderId,function(err,res) {
                                    if(err){
                                        callback(new Error(err));
                                    }

                                    for (index = 0; index < res.length; ++index) {
                                        data.packages[index].packageid = res[index].packageid;
                                    }

                                    callback(null,data);
                                });
                            }

                        });

                    });
                }
            });
        }
    }


        Order.getCustomerDetails = function(id,callback){

        var customerId = id;



        Order.find({  where:
        {   customerid :  customerId

        }
        }, function(err,orderArray){

            if(err){
                callback(new Error(err),null);
            }

            if(orderArray === null){
                callback("No order found for user",null);
            }else{

                for (var i in orderArray) {
                    orderArray[i].customer = orderArray[i].customer();
                }


                //var orderList = [];

                //for (var i in orderArray) {
                    //orderList.add(orderArray[i].orderid);

                    //Package.getPackageDetails(orderArray,callback),function(err,packageOrder){
                    //
                    //
                    //    if(err){
                    //        //return new Error(err);
                    //        callback(err,null);
                    //    }
                    //
                    //    orderList.add(packageOrder);
                    //    callback(null,null);
                    //
                    //}

                    //Package.find({  where:
                    //{   orderid :  orderArray[i].orderid
                    //
                    //}
                    //}, function(err,packageArray){
                    //
                    //    if(err){
                    //        callback(new Error(err),null);
                    //    }
                    //
                    //    if(packageArray === null){
                    //        //callback("No order found for user",null);
                    //    }else{
                    //
                    //        orderArray[i].packages = packageArray;
                    //        // insert packageArray in Order Array
                    //        callback(null,orderArray);
                    //    }
                    //
                    //});
                //}
                    callback(null,orderArray);
            }

        });


    };
};
