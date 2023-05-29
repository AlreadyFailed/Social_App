<template>
	<view class="img">
		<image :src="img_src" mode=""></image>
	</view>
</template>

<script>
	export default {
		name: "login-img",
		props:["img_src"],
		data() {
			return {

			};
		}
	}
</script>

<style>
	.img{
		float: left;
		width: 100upx;
		height: 100upx;
		border-radius: 50%;
		background-color: #fff;
		margin-left: 180upx;
	}
	.img image{
		margin: 10upx 10upx;
		width: 80upx;
		height: 80upx;
	}
</style>