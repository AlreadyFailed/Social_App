<template>
	<view class="dad">
		<view class="box" @tap="leftSlid"></view>
		<view v-if="isShow" :class="['triangle']" :style="{'left':coordinateX + 'upx'}" @tap="rightSlid"></view>
	</view>

</template>


<script>
	export default {
		data() {
			return {
				isShow:false,
				throttle:0,
				coordinateX: 750
			}
		},
		methods: {
			leftSlid(){
				if(!this.throttle){
					this.throttle = 1;
					this.isShow = true;
					console.log("1");
					var timer = setInterval(()=>{
						this.coordinateX -= 50;
						if(this.coordinateX == 0){
							this.throttle = 0;
							clearInterval(timer);
						}
					}, 10)
				}
			},
			rightSlid(){
				if(!this.throttle){
					this.throttle = 1;
					console.log("2");
					var timer = setInterval(()=>{
						this.coordinateX += 50;
						if(this.coordinateX == 750){
							this.throttle = 0;
							this.isShow = false;
							clearInterval(timer);
						}
					}, 10)
				}
			}
		}
	}
</script>



<style>
	.dad{
		overflow: hidden;
		width: 750upx;
		height: 1668upx;
		background-color: gray;
	}
	.box{
		display: flex;
		height: 200upx;
		width: 750upx;
		background-color: pink;
	}
	.triangle{
		position: absolute;
		top: 0;
		height: 200upx;
		width: 750upx;
		background-color: skyblue;
	}
</style>