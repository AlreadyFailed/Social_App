<template>
	<view class="">
		<view ref="box_center" class="box_center" v-for="(item,index) in 5" :key="item._id" @touchstart="drawStart"
			@touchmove="drawMove(index,$event)"
			@touchend="drawEnd(index,$event)"
			@click="addX(index)">
			<view class="text">
				<image src="" mode=""></image>
				<text class="value">
					<p>小明</p>
					<p>15635686525</p>
				</text>
			</view>
			<view class="function">
				<text class="compile" @click="compile(item._id)">编辑</text>
				<text class="delete" @click="Delete(item._id)">删除</text>
			</view>
		</view>
	</view>

</template>


<script>
	export default {
		data() {
			return {
				clientX: 0,
			}
		},
		methods: {
			// 编辑
			compile(id) {},
			// 删除
			Delete(id) {},
			// 开始触摸
			drawStart(e) {
				console.log('触摸', e);
				this.clientX = e.changedTouches[0].clientX
			},
			// 触摸过程
			drawMove(id, e) {
				console.log('过程', e.changedTouches[0]);
				var endX = e.changedTouches[0].clientX;
				let diff = endX - this.clientX;
				console.log(diff);

				if (Math.abs(diff) <= 130 && diff < 0) {
					let box_center = this.$refs.box_center[id]
					// console.log(diff);
					box_center.$el.style.left = diff + 'px'
				}

			},
			// 结束触摸
			drawEnd(id, e) {
				// console.log('结束',e.changedTouches[0]);
				var endX = e.changedTouches[0].clientX;
				let diff = endX - this.clientX;
				let box_center = this.$refs.box_center[id]
				if (Math.abs(diff) > 20) {
					// console.log('移动',diff,id,e);
					if (diff < 0) {
						box_center.$el.style.left = '-130px'
					} else {
						box_center.$el.style.left = '0'
					}

				} else {
					box_center.$el.style.left = '0'
				}

			},
			// 点击恢复原始数据
			addX(id) {
				let box_center = this.$refs.box_center[id]
				box_center.$el.style.left = '0'
			},
		}
	}
</script>



<style>
	.box_center {
		width: 750rpx;
		height: 150rpx;
		background-color: #fff;
		margin: 10rpx 0;
		display: flex;
		align-items: center;
		position: relative;
		border-radius: 20rpx 0 0 20rpx;

		.text {
			width: 100%;
			position: absolute;
			left: 0;
			display: flex;

			image {
				width: 120rpx;
				height: 120rpx;
				margin: 0 20rpx;
			}

			.value {
				line-height: 60rpx;
			}
		}

		.function {
			width: 260rpx;
			height: 150rpx;
			// background-color: red;
			position: absolute;
			right: -260rpx;
			display: flex;
			border-radius: 0 20rpx 20rpx 0;
			overflow: hidden;

			text {
				width: 50%;
				height: 100%;
				line-height: 150rpx;
				background-color: #0000ff;
				text-align: center;
				color: #fff;
			}

			.delete {
				background-color: #aa0000;
			}
		}
	}
</style>