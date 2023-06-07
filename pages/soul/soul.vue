<template>
	<view>
		<scroll-view scroll-y="true" class="scroll-Y" :scroll-into-view="scrollToView"
	     scroll-with-animation="true">
			<view id="damo1" class="demo uni-bg-green text-center">a</view>
			<view id="damo2" class="demo uni-bg-red text-center">b</view>
			<view id="damo3" class="demo uni-bg-blue text-center">c</view>
		</scroll-view>
		<button @tap="ToView">返回</button>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				scrollToView: null,
			}
		},
		computed: {
			
		},
		methods: {
			ToView(){
				this.scrollToView = null;
				this.$nextTick(()=>{
					this.scrollToView = 'damo3';
				})
			}
		},
		onLoad: () => {
		}
	}
</script>
<style >
	.scroll-Y{
		width: 300upx;
		height: 300upx;
	}
	.demo{
		width: 300upx;
		height: 300upx;
		line-height: 300upx;
	}
</style>
