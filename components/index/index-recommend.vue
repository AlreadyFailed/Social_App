<template>
	<view>
		<view class="body_recommend_content">
			<view class="content_left">
				<image :src="avatar_src" mode=""></image>
			</view>
			<view class="content_right">
				<view class="content_right_user">
					<view class="fs-28 fs-600">{{nickname}}</view>
					<view> <image :src="gender == 0 ? data.famale : data.male" mode=""></image> </view>
				</view>
				<view class="content_right_info color-9191 fs-26">
					<view v-for="(item, index) in info" :key="index">
						{{item}}
					</view>
				</view>
				<view class="content_right_work">
					<view class="fs-28" v-for="(item, index) in work" :key="index">{{item}}</view>
				</view>
				<view class="content_right_img">
					<view v-for="(item, index) in 3" :key="index">
						<image :src="avatar_src" mode=""></image>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props:['nickname', 'gender', 'info', 'work', 'avatar_src'],
		data() {
			return {
				data:{
					famale:'../../static/src/0.png',
					male:'../../static/src/1.png'
				}
			}
		},
		methods: {

		}
	}
</script>

<style>
	.body_recommend_content {
		box-sizing: border-box;
		margin-top: 40upx;
		/* padding: 0 20upx; */
		height: 350upx;
		width: 750upx;
		border-bottom: 5px solid #dddddd;
		/* background-color: skyblue; */
	}

	.content_left {
		float: left;
		margin-left: 20upx;
		overflow: hidden;
		width: 300upx;
		height: 300upx;
		border-radius:20upx;
		/* background-color: lightgray; */
	}
	.content_left image{
		width: 300upx;
		height: 300upx;
	}

	.content_right {
		position: relative;
		float: left;
		margin-left: 20upx;
		width: 400upx;
		height: 300upx;
		border-radius: 0 20upx 20upx 0;
		/* background-color: slategray; */
	}
	.content_right_user{
		margin-top: 5upx;
		width: 380upx;
		height: 50upx;
		/* background-color: #fff; */
	}
	.content_right_user view{		
		float: left;
		line-height: 50upx;
	}
	.content_right_user image{
		float: left;
		margin:5upx 10upx;
		width: 40upx;
		height: 40upx;
		border-radius:10upx;
	}
	.content_right_info{
		float: left;
		margin-top: 5upx;
		width: 380upx;
		height: 50upx;
		/* background-color: #fff; */
	}
	.content_right_info view{
		float: left;
		margin-right: 10upx;
		line-height: 50upx;
	}
	.content_right_work{
		float: left;
		
		margin-top: 5upx;
		width: 380upx;
		height: 50upx;
		line-height: 50upx;
		color: #cca9ff;
		/* background-color: #fff; */
	}
	.content_right_work view{		
		float: left;
		padding: 0 10upx;
		margin-right: 10upx;
		height: 48upx;
		border-radius: 10upx;
		border:1px solid #cca9ff;
	}
	.content_right_img{
		float: left;
		margin-top: 18upx;
		height: 120upx;
		width: 380upx;
		/* background-color: #fff; */
	}
	.content_right_img view{
		float: left;
		margin-right: 5upx;
		overflow: hidden;
		height: 120upx;
		width: 120upx;
		border-radius: 10upx;
		/* background-color: lightgray */
	}
	.content_right_img view image{
		width: 120upx;
		height: 120upx;
	}
</style>