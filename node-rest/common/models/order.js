var Liefernuser = require('./liefernuser');
var server = require('../../server/server');

module.exports = function(Order) {

    var secretKey = server.get('secretSkipHeader');

    Order.beforeRemote('**', function(ctx, user, next) {

        var header = ctx.req.headers;

        if(header.hasOwnProperty('skip') && header.skip === secretKey){
            next();
        }
        else{
            if (!header.hasOwnProperty('token')){
                next(new Error('please pass token in header'))
            }

            Liefernuser.getUser(header.token,function(err, res){

                if(err){
                    next(err,null);
                }
                if(res === null){
                    next(res,null);
                }

                next();
            });
        }
    });
};
