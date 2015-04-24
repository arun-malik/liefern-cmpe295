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
            Address.upsert(json,function(err,res) {
                if(err){
                    callback(new Error(err),null);
                }
                callback(null,res);
            });
        }
    }

    module.exports.profileAddress =  function (userid,callback) {

        Address.findOne({  where: {and : [{userid : userid}, {home : 1}] }}, function(err,res){

            if(err){
                callback(new Error(err));
            }

                callback(null,res);

        });
    }

};
