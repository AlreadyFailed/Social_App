<template>
	<view>
		<view :class="'title-tanhua' + index" v-for="(item, index) in 3" @tap="handleJump(index)">
			<view :class="'tanhua-bg' + index">
				<text :class="['iconfont', 'image', src[index]]" ></text>
			</view>
			<text class="text fs-16 color-fff">{{prop[index]}}</text>
		</view>
	</view>
</template>

<script>
	export default {
		name: "index-title",
		data() {
			return {
				prop:['探花', '搜附近', '测灵魂'],
				src:['icon-pengyoufill', 'icon-dingweixiao', 'icon-danao'],
				url:["/pages/rogue/rogue", "/pages/vicinity/vicinity","/pages/soul/soul"]
			};
		},
		methods:{
			handleJump(index){
				uni.navigateTo({
					url:this.url[index]
				})
			}
		}
	}
</script>

<style>
	.title-tanhua0 {
		position: absolute;
		top: 60upx;
		left: 150upx;
		width: 100upx;
		height: 140upx;
		text-align: center;
	}
	.title-tanhua1 {
		position: absolute;
		top: 60upx;
		left: 340upx;
		width: 100upx;
		height: 140upx;
		/* background-color: deepskyblue; */
		text-align: center;
	}
	.title-tanhua2 {
		position: absolute;
		top: 60upx;
		left: 530upx;
		width: 100upx;
		height: 140upx;
		text-align: center;
	}
	.tanhua-bg0{
		width: 100%;
		height: 100upx;
		background-color: red;
		border-radius: 50%;
		text-align: center;
		line-height: 100upx;
	}
	.tanhua-bg1{
		width: 100%;
		height: 100upx;
		background-color: royalblue;
		border-radius: 50%;
		text-align: center;
		line-height: 100upx;
	}
	.tanhua-bg2{
		width: 100%;
		height: 100upx;
		background-color: #ded556;
		border-radius: 50%;
		text-align: center;
		line-height: 100upx;
	}
	.image{
		color: #ffffff;
		font-size: 60upx;
		/* width: 70%;
		height: 70%; */
	}

</style>