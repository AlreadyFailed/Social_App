exports.getIndex = (value, flag)=>{
	education = ['高中及以下', '专科', '本科', '硕士', '博士'];
	marry = ['保密', '单身', '未婚', '已婚'];
	salary = [5000,10000,20000];
	if(flag === 1){
		for(var i = 0; i < education.length; i++){
			if(value === education[i]){
				return i;
			}
		}
		return 1;
	}else if (flag === 2){
		for(var i = 0; i < marry.length; i++){
			if(value === marry[i]){
				return i;
			}
		}
	}else{
		if(value < 5000){
			return 0;
		}else if(value < 10000){
			return 1;
		}else if(value < 20000){
			return 2;
		}else{
			return 3;
		}
	}
}

exports.getValue = (value, flag)=>{
	value = Number(value);
	if(flag === 1){
		let weight = Math.floor((value-32) * 3.6);
		return weight;
	}else{		
		let height = Math.floor((value - 136) * 6.6);
		return height;
	}
}