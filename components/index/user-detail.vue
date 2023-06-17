<template>
	<!-- 个人信息栏组件 -->
	<view>
		<view class="detail-user">
			<text class="text">{{username}}</text>
			<image class="image" :src="gender == 1 ? '../../static/src/1.png' : '../../static/src/0.png'" mode=""></image>
			<text class="text">{{age}}岁</text>
		</view>
		<view class="detail-info">
			<text class="detail-info-content color-9191" v-for="(item, index) in detail">{{item}}</text>
		</view>
		<view class="detail-value"></view>
	</view>
</template>

<script>
	export default {
		props:["username", "gender", "age", "detail"],
		data() {
			return {
				isShow : true
			}
		},
		methods: {

		}
	}
</script>

<style>
	.detail-user {
		box-sizing: border-box;
		display: block;
		margin-top: 55upx;
		height: 60upx;
		width: 65%;
		/* background-color: #fff; */
	}

	.text {
		margin-left: 5upx;
		line-height: 60upx;
		font-size: 26upx;
		font-weight: 600;
		color: #626060
	}

	.image {
		margin-left: 5upx;
		width: 40upx;
		height: 40upx;
		vertical-align: middle;
	}

	.detail-info {
		margin-top: 10upx;
		height: 60upx;
		width: 65%;
		/* background-color: #fff; */
	}
	.detail-info-content{
		margin-left: 10upx;
		line-height: 60upx;
		font-size: 24upx;
	}
</style>