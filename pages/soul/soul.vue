<template>
	<view>
		<scroll-view class="box" :style="{'height':height + 'upx'}" scroll-y="true" 
		:scroll-into-view="scrollToView"
		scroll-with-animation="false">
			<view  v-for="(item, index) in 50" :key="index" :id="'msg' + index">
				Text{{index}}
			</view>
		</scroll-view>
		<view class="wrap" :style="{'bottom':bottom + 'upx'}">
			<view class="triangle">
				<button @tap="handleShow">Click</button>
				<button @tap="handleSend">Send</button>
			</view>
			<view :class="['circle', {'display-none' : isShow}]" >
				
			</view>
		</view>
		
	</view>
</template>
<script>
	export default {
		data() {
			return {
				isShow:true,
				bottom: -500,
				throttle: 0,
				flag: 1,
				height:1380,
				scrollToView:null
			}
		},
		computed: {
			
		},
		methods: {
			getHeight(classNa, flag){
				setTimeout(()=>{
						const query = uni.createSelectorQuery().in(this);
						query.select(classNa).boundingClientRect(data=>{
							if(flag == 1){
								this.height = this.height - (data.height*2) + 100;
								this.scrollToView = '';
								this.$nextTick(()=>{
									this.scrollToView = 'msg' + 49;
								})
							}else{
								this.height = this.height + (data.height*2) - 100;
								this.scrollToView = '';
								this.$nextTick(()=>{
									this.scrollToView = 'msg' + 49;
								})
							}
							
						}).exec();
					},10);
			},
			heightChange(height){
				// console.log(height);
				// this.height = this.height - (height*2) - 100;
				// this.scrollToView = '';
				// this.$nextTick(()=>{
				// 	this.scrollToView = 'msg' + 49;
				// })
			},
			handleShow(){				
				if(this.flag == 1){
					if(!this.throttle){						
						this.throttle = 1;
						this.isShow = false;
						var timer = setInterval(()=>{
							this.bottom += 50;
							if(this.bottom == 0){
								clearInterval(timer);
								this.getHeight('.wrap', this.flag)
								this.throttle = 0;
								this.flag = 0;
							}
						}, 15)
					}
				}else{
					if(!this.throttle){
						this.throttle = 1;
						var timer = setInterval(()=>{
							this.bottom -= 50;
							if(this.bottom == -500){
								clearInterval(timer);
								this.getHeight('.wrap');
								this.isShow = true;
								this.throttle = 0;
								this.flag = 1;
							}
						}, 15)
					}
				}
			},
			handleSend(){
				this.scrollToView = null;
				this.$nextTick(()=>{
					this.scrollToView = 'msg' + 49;
				})
			}
		},
		onLoad: () => {
		}
	}
</script>
<style lang="less">
	.box{
		width: 750upx;
		// height: 1400upx;
		background-color: gray;
	}
	.wrap{
		position: fixed;
		width: 750upx;
		height: 600upx;
		background-color: #fff;
		.triangle{
			width: 750upx;
			height: 100upx;
			background-color: dimgray;
			button{
				float: left;
				height: 80upx;
				width: 150upx;
				line-height: 80upx;
			}
		}
		.circle{
			width: 750upx;
			height: 500upx;
			background-color: darkslategray
		}
	}
	
</style>
