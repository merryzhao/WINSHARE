define(function(require,exports){

	exports.arrayRemove=function(array,item){
		var i=0,length=array.length;
		
		for(i;i<length;i++){
			if(array[i]==item){
				array.splice(i,1);
			}
		}
	};
});