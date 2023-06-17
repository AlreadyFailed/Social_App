<template>
	<!-- 通用的加载组件 -->
	<view class="loaidng">		
		<image src="../static/src/loading.gif" mode=""></image>
	</view>
</template>

<script>
	export default {
		name:"loading",
		data() {
			return {
				
			};
		}
	}
</script>

<style>
	.loaidng{		
		text-align: center;
	}
.loaidng image{	
	margin-top: 500upx;
	width: 300upx;
	height: 300upx;
}
</style>