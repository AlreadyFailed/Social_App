<template>
	<!-- 固有列表组件 -->
	<view>
		<view :class="['body_inherent_lis', {'inherent_lis_color':isShow}]" 
		@touchstart="convertColorToMain" 
		@touchend="convertColorToNor"
		>
			<view class="inherent_lis_img" >
				<image :src="img" mode=""></image>
			</view>
			<view class="inherent_lis_right">
				<view class="inherent_lis_right_text color-6362">
					{{text}}
				</view>
				<view class="inherent_lis_right_arrow">
					<image src="../../static/src/xiangyoujiantou.png" mode=""></image>
				</view>
			</view>			
		</view>
	</view>
</template>

<script>
	export default {
		props:['img', 'text','coordinateX'],
		data() {
			return {
				isShow: false,
				throttle : 0
			}
		},
		methods: {
			convertColorToMain(){
				this.isShow = true
			},
			convertColorToNor(e){
				this.isShow = false;
				const poe_title = e.currentTarget.dataset.poe.poe_title
			}
		}
	}
</script>

<style>
	.body_inherent_lis {
		box-sizing: border-box;
		margin-top: 15upx;
		height: 150upx;
		width: 100%;		
		/* background-color: lightgray; */
	}
	.inherent_lis_color{
		background-color: #ebebeb;
	}

	.inherent_lis_img {
		box-sizing: border-box;
		float: left;
		overflow: hidden;
		margin: 25upx 20upx 25upx 20upx;
		width: 100upx;
		height: 100upx;
		border-radius: 50upx;
		border: 2upx solid #f5c5f5;
		background-color: #f0f0f0;
	}

	.inherent_lis_img image {
		margin: 5upx 10upx;
		width: 80upx;
		height: 80upx;
	}
	
	.inherent_lis_right{
		float: left;
		box-sizing: border-box;
		height: 148upx;
		width: 590upx;
		border-bottom: 1upx solid lightgray;
		/* background-color: gray; */
	}

	.inherent_lis_right_text {
		box-sizing: border-box;
		float: left;
		margin: 50upx 0upx;
		padding-left: 10upx;
		height: 50upx;
		width: 250upx;
		line-height: 50upx;
		/* background-color: #fff; */
	}

	.inherent_lis_right_arrow {
		float: right;
		margin: 55upx 20upx;
		width: 40upx;
		height: 40upx;
		/* background-color: #fff; */
	}

	.inherent_lis_right_arrow image {
		width: 40upx;
		height: 40upx;
	}
	
	
	
</style>