<template>
	<view>
		<view class="body_live_content">
			<image :src="live_online" mode=""></image>
			<view class="content_mask" v-if="live">
				<view class="content_mask_user color-fff fs-600">
					<view>{{nickname}}</view>
					<view>
						<image :src="gender == 0 ? data.famale : data.male" mode=""></image>
					</view>
				</view>
				<view class="content_mask_info fs-26 ">
					<view v-for="(item, index) in info" :key="index">
						{{item}}
					</view>
				</view>
				<view class="content_mask_live fs-28 main-bg-color text-center color-fff">
					直播中...
				</view>
			</view>
			<view class="content_mask" v-if="!live">
				<view class="content_mask_NOT fs-600 color-fff">
					主播正在赶来...
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props:['live_online', 'nickname', 'gender', 'info', 'live'],
		data() {
			return {
				show:{
					gender:false
				},
				data:{
					famale:'../../static/src/sexy_0.png',
					male:'../../static/src/sexy_1.png'
				}
			}
		},
		methods: {

		}
	}
</script>

<style>
	.body_live_content {
		position: relative;
		height: 470upx;
		border-radius: 20upx;
		background-color: #a6a6a6;
	}

	.body_live_content image {
		margin:30upx 150upx;
		width: 400upx;
		height: 400upx;
	}


	.content_mask {
		position: absolute;
		bottom: 0;
		height: 150upx;
		width: 100%;
		border-radius: 0 0 20upx 20upx;
		background-color: rgba(0, 0, 0, .5)
	}
	.content_mask_user{
		margin:20upx 20upx 10upx;
		width: 450upx;
		height: 50upx;
		line-height: 50upx;
		/* background-color: #fff; */
	}
	.content_mask_user view{
		float: left;
		margin-right: 10upx;
		/* background-color: gray; */
	}
	
	.content_mask_user view:last-child{
		position: relative;
	}
	.content_mask_user view image{
		position: absolute;
		top: -30upx;
		left: -150upx;
		width: 50upx;
		height: 50upx;
		border-radius: 10upx;
		background-color: #6adfd6;
	}
	.content_mask_info{		
		margin-left: 20upx;
		width: 300upx;
		height: 50upx;
		line-height: 50upx;
		color: #d8d8d8;
		/* background-color: #fff; */
	}
	.content_mask_info view{
		float: left;
		margin-right: 10upx;
	}
	.content_mask_live{
		position: absolute;
		top: 45upx;
		right: 30upx;
		width: 150upx;
		height: 60upx;
		line-height: 60upx;
		border-radius: 30upx;
		background-color: #fff;
	}
	.content_mask_NOT{
		margin:40upx;
		height: 60upx;
		width: 300upx;
		line-height: 60upx;
		letter-spacing: 5upx;
		/* background-color: #fff; */
	}
</style>