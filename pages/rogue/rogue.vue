<template>
	<view class="">
		<view :class="['box', {'box_gray':isShow == index}]" v-for="(item, index) in 3" :key="index" 
		@touchstart="handleShow(index)"
		@touchend="handleJump(index)">
			
		</view>
	</view>

</template>

<script>
	export default {
			data() {
				return {
					isShow : -1
				}
			},
			onLoad() {
		
			},
			methods: {
				handleShow(index){
					if(index == 0){
						this.isShow = 0;
					}else if(index == 1){
						this.isShow = 1;
					}else{
						this.isShow = 2;
					}
				},
				handleJump(index){
					this.isShow = -1;
				}
			}
		}

</script>

<style>
	.box{
		margin-top: 20upx;
		width: 750upx;
		height: 100upx;
		background-color: pink;
	}
	.box_gray{
		background-color: gray;
	}
</style>