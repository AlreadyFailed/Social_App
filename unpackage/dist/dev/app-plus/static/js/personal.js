exports.handleBanner = (index, flag)=>{
	switch(index){
		case 0:{
			if(flag){
				uni.navigateTo({
					url:"/pages/info/info",
					animationType:"slide-in-right",
					animationDuration:500
				})
				break;
			}else{
				uni.showToast({
					title:"请登录",
					icon:"error"
				})
				break;
			}
		}
		case 1 :{
			uni.showToast({
				title:"开始设置",
				icon:"error"
			})
			break;
		}
		default:{
			uni.showToast({
				title:"出现错误！",
				icon:"error"
			})
			break;
		}
	}
}