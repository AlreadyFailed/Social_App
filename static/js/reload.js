// exports.reload = ()=>{
// 	uni.request({
// 		url:this.url + '/users/personal',
// 		method:"GET",
// 		header:{
// 			'token':uni.getStorageSync('token')
// 		},
// 		success:(res)=>{
// 			return res.data;
// 		},
// 		fail:(res)=>{
// 			console.log(res.data);
// 		}
// 	})
// }