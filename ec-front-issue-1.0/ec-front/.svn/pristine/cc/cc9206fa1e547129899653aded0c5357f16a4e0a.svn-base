/**
 * @author libin
 */
seajs.use("messenger", function(Messenger) {
    var iframe = document.getElementById('bookFrame');
    var messenger = Messenger.initInParent(iframe);
    messenger.onmessage = function (data) {
        setTimeout(function(){iframe.src=data;},500);
        
    }; 
})