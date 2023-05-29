<template>
	<view class="good-value" >
		<image src="../../static/src/heart.png" mode=""></image>
		<text class="color-9191">{{value}}</text>
	</view>
</template>

<script>
	export default {
		props:["value"],
		data() {
			return {
				
			}
		},
		methods: {
			
		}
	}
</script>

<style>
.good-value{
	width: 150upx;
	height: 70upx;
	/* background-color: lightpink; */
	line-height: 70upx;
}
image{
	margin:0 20upx 10upx 0;
	width: 60upx;
	height: 45upx;
	vertical-align: middle;
}

</style>
