<template>
	<view>
		<picker mode="multiSelector" 
		:value="multiIndex" :range="multiArray" 
		@columnchange="handleChange">
			<view>
			{{multiArray[0][multiIndex[0]]}}
			{{multiArray[1][multiIndex[1]]}}
			{{multiArray[2][multiIndex[2]]}}</view>
		</picker>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				multiArray:[['游泳','瑜伽','健身'],['网球','羽毛球','篮球'],['唱歌','听歌','看书']],
				multiIndex:[0,0,0]
			}
		},
		methods: {
			handleChange(e){
				this.multiIndex[e.detail.column] = e.detail.value;
				console.log(this.multiIndex);
				this.$forceUpdate();
			}
		}
	}
</script>

<style>

</style>