exports.recData = (data) => {
	var tempUser = [];
	for (var i = 0; i < data.length; i++) {
		if (data[i].fate > 70) {
			tempUser.push(data[i]);
		}
	}
	var index = Math.floor(Math.random() * tempUser.length);
	return tempUser[index];
};

exports.norData = (data) => {
	var tempUser = [];
	var norData = [];
	for (var i = 0; i < data.length; i++) {
		if (data[i].privilege <= 0) {
			tempUser.push(data[i]);
		}
	}
	var index = null;
	var len = tempUser.length < 7 ? tempUser.length : 7;
	
	while(tempUser.length){
		index = Math.floor(Math.random() * tempUser.length);
		norData.push(tempUser[index]);
		tempUser.splice(index, 1);
	}
	return norData;
}


exports.getAge = (strBirthday) => {

	var returnAge;
	var strBirthdayArr = strBirthday.split("-");
	var birthYear = strBirthdayArr[0];
	var birthMonth = strBirthdayArr[1];
	var birthDay = strBirthdayArr[2];

	d = new Date();
	var nowYear = d.getFullYear();
	var nowMonth = d.getMonth() + 1;
	var nowDay = d.getDate();

	if (nowYear == birthYear) {
		returnAge = 0; //同年 则为0岁
	} else {
		var ageDiff = nowYear - birthYear; //年之差
		if (ageDiff > 0) {
			if (nowMonth == birthMonth) {
				var dayDiff = nowDay - birthDay; //日之差
				if (dayDiff < 0) {
					returnAge = ageDiff - 1;
				} else {
					returnAge = ageDiff;
				}
			} else {
				var monthDiff = nowMonth - birthMonth; //月之差
				if (monthDiff < 0) {
					returnAge = ageDiff - 1;
				} else {
					returnAge = ageDiff;
				}
			}
		} else {
			returnAge = -1; //返回-1 表示出生日期输入错误 晚于今天
		}
	}

	return returnAge; //返回周岁年龄

}