module.exports = function(Geo) {

    Geo.beforeRemote('**', function(ctx, user, next) {
        //
        //var header = ctx.req.headers;
        //
        //if (!header.hasOwnProperty('token')){
        //    next(new Error('please pass token in header'))
        //}
        //
        next();
    });

    Geo.remoteMethod(
        'upsertGeo',
        {
            accepts: { arg: 'data', type: 'object', http: { source: 'body' } } ,
            returns : {arg: 'response', type: 'object'},
            http: {path: '/upsert', verb:'POST'},
            description : 'Create record if doesnt exists or update same record'
        }
    );


    Geo.upsertGeo = function(json,cb) {

        if (!json.hasOwnProperty('userid')) {
            cb(new Error('cannot find userid in input. Please logout and login again'), null);
        }

        Geo.findOne({  where:
            {   userid :  json.userid

            }
            }, function(err,geoRec) {

                if (err) {
                    cb(new Error(err), null);
                }

                if (geoRec === null) {

                    Geo.upsert(json, function (err, res) {
                        if (err) {
                            cb(new Error(err), null);
                        }
                        cb(null, res);
                    });

                } else {

                    geoRec.lat = json.lat;
                    geoRec.lng = json.lng;
                    geoRec.radius = json.radius;

                    Geo.upsert(geoRec, function (err, res) {
                        if (err) {
                            cb(new Error(err), null);
                        }
                        cb(null, res);
                    });
                }

            }

        );



    }

    };
