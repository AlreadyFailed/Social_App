exports.recData = (data)=>{
	var tempUser = [];
	
	for (var i = 0; i < data.length; i++) {
		if(data[i].fate > 70){
			tempUser.push(data[i]);
		}
	}
	var index = Math.floor(Math.random() * tempUser.length);
	// console.log(index);
	return tempUser[index];	
};

exports.norData = (data)=>{
	var tempUser = [];
	var norData = [];
	for(var i = 0; i < data.length; i++){
		if(data[i].fate <= 70){
			tempUser.push(data[i]);
		}
	}
	
	for(var i = 0; i < (tempUser.length < 7 ? tempUser.length : 7); i++){
		var index = Math.floor(Math.random() * tempUser.length);
		norData.push(tempUser[index]);
	}
	return norData;
}

