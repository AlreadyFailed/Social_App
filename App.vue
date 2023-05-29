<script>	
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
	/*每个页面公共css */
	@import "common/uni.css";
	@import "common/common.css";
	@import "common/iconfont.css";
	

	uni-page-head {
		letter-spacing: 10upx;
	}

	uni-page-head .uni-page-head__title {
		font-size: 38upx !important;
		line-height: 60rpx !important;
		font-weight: normal;
	}
</style>