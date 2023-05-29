<template>
	<view>
		<view class="three">
			<view class="get" @tap="getCheckNum()">
				<text>{{!codeTime?'获取验证码':codeTime+'s'}}</text>
			</view>
			<view class="all">
				<view class="left">验证码</view>
				<input  placeholder="请输入验证码" />
			</view>
			<button class="btn" @tap='sure'>确认</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				codeTime: 0,
			}
		},
		methods: {
			getCheckNum() {
				if (this.codeTime > 0) {
					uni.showToast({
						title: '不能重复获取',
						icon: "none"
					});
					return;
				} else {
					this.codeTime = 60
					let timer = setInterval(() => {
						this.codeTime--;
						if (this.codeTime < 1) {
							clearInterval(timer);
							this.codeTime = 0
						}
					}, 1000)
				}
			}
		}
	}
</script>

<style>
	.all {
		margin: 30rpx;
		border-bottom: 2rpx solid #EEEEEE;
		display: flex;
		flex-wrap: nowrap;
	}

	.left {
		margin-bottom: 30rpx;
		margin-right: 40rpx;
		width: 150rpx;
	}

	.three {
		background-color: white;
		width: 92%;
		border-radius: 10rpx;
		padding: 20rpx 0;
		margin: 20rpx auto;
		position: relative;
	}

	.btn {
		background-color: orange;
		font-size: 28rpx;
		width: 160rpx;
		height: 70rpx;
		line-height: 70rpx;
		margin-top: 40rpx;
		color: white;
		font-weight: 600;
	}

	.get {
		position: absolute;
		top: 40rpx;
		right: 30rpx;
		background-color: orange;
		height: 70rpx;
		line-height: 70rpx;
		color: white;
		border-radius: 10rpx;
		padding: 0 20rpx;
	}
</style>