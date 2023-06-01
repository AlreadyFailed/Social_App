// 计算距离   单位为KM
exports.getDistance = (la1, lo1, la2, lo2) => { // 当前的纬度，当前的经度，接口拿到的纬度，接口拿到的经度
	let La1 = la1 * Math.PI / 180.0;
	let La2 = la2 * Math.PI / 180.0;
	let La3 = La1 - La2;
	let Lb3 = lo1 * Math.PI / 180.0 - lo2 * Math.PI / 180.0;
	let distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(La3 / 2), 2) + Math.cos(La1) * Math.cos(La2) * Math.pow(Math.sin(Lb3 / 2), 2)));
	distance = distance * 6378.137;
	distance = Math.round(distance * 10000) / 10000;
	return distance;
}