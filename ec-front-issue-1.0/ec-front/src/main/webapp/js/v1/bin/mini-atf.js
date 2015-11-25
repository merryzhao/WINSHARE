seajs.use("http://static.winxuancdn.com/libs/widgets/miniATF",function(miniATF){
    var headATF = miniATF({context:".atf-head",vertical:"top",refer:"#cart-list .tfoot",animate:false,scrollT:61}).init();
    var balanceATF = miniATF({context:".atf-balance",vertical:"bottom",refer:"#cart-list .tfoot",animate:true,scrollT:0}).init();
});