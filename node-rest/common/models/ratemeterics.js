module.exports = function(Ratemeterics) {

    Ratemeterics.beforeRemote('**', function(ctx, user, next) {

        var header = ctx.req.headers;

        if (!header.hasOwnProperty('token')){
            next(new Error('please pass token in header'))
        }

        next();
    });
};
