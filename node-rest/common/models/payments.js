module.exports = function(Payments) {

    module.exports.getPaymentDetails = function (userid,cb) {

        Payments.findOne({  where:
        {   userid : userid

        }
        }, function(err,pay){

            if(err){
                //return new Error(err);
                cb(new Error(err),null);
            }

            if(pay === null){
                //return new Error("Please enter payment details before placing an order.");
                cb(new Error("Please enter payment details before placing an order."),null);
            }else {
                //return pay;
                cb(null,pay);
            }

        });
    };
}