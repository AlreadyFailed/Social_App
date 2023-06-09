<template>
	<view>
		<picker mode="multiSelector" @columnchange="bindMultiPickerColumnChange" :value="multiIndex"
			:range="multiArray">
			<view style="background-color: white;">
				{{ multiArray[0][multiIndex[0]] }}，
				{{ multiArray[1][multiIndex[1]] }}，
				{{ multiArray[2][multiIndex[2]] }}
			</view>
		</picker>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				multiArray: [
					['亚洲', '欧洲'],
					['中国', '日本'],
					['北京', '上海', '广州']
				],
				multiIndex: [0, 0, 0]
			};
		},
		methods: {
			bindMultiPickerColumnChange: function(e) {
				this.multiIndex[e.detail.column] = e.detail.value;
				switch (e.detail.column) {
					case 0: //拖动第1列
						switch (this.multiIndex[0]) {
							case 0:
								this.multiArray[1] = ['中国', '日本'];
								this.multiArray[2] = ['北京', '上海', '广州'];
								break;
							case 1:
								this.multiArray[1] = ['英国', '法国'];
								this.multiArray[2] = ['伦敦', '曼彻斯特'];
								break;
						}
						this.multiIndex.splice(1, 1, 0);
						this.multiIndex.splice(2, 1, 0);
						break;
					case 1: //拖动第2列
						switch (
							this.multiIndex[0] //判断第一列是什么
						) {
							case 0:
								switch (this.multiIndex[1]) {
									case 0:
										this.multiArray[2] = ['北京', '上海', '广州'];
										break;
									case 1:
										this.multiArray[2] = ['东京', '北海道'];
										break;
								}
								break;
							case 1:
								switch (this.multiIndex[1]) {
									case 0:
										this.multiArray[2] = ['伦敦', '曼彻斯特'];
										break;
									case 1:
										this.multiArray[2] = ['巴黎', '马赛'];
										break;
								}
								break;
						}
						this.multiIndex.splice(2, 1, 0);
						break;
				}
				this.$forceUpdate();
			}
		}
	};
</script>
<style>

</style>
