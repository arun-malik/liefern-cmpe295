module.exports = function(Address) {

    //Address.beforeRemote('**', function(ctx, user, next) {
    //
    //    var header = ctx.req.headers;
    //
    //    if (!header.hasOwnProperty('token')){
    //        next(new Error('please pass token in header'))
    //    }
    //
    //    next();
    //});

    module.exports.saveAddress =  function (json,callback) {

        if(json === null){
            callback(new Error("Invalid address"),null);
        }else{
            Address.create(json,function(err,res) {
                if(err){
                    callback(new Error(err),null);
                }
                callback(null,res);
            });
        }
    }

};
