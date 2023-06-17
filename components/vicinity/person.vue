<template>
	<!-- 个人信息展示组件 -->
	<view class="swiper-item">
		<view class="body_person">
			<view class="body_person_img">
				<image :src="avatar_src" mode=""></image>
			</view>
			<view class="body_person_info">
				<view class="person_info_left">
					<view class="color-6362">
						{{nickname}}
					</view>
					<view class="color-6362">
						{{age}}岁
					</view>
					<view class="info_gender">
						<image :src="gender == 0 ? famale : male" mode=""></image>
					</view>
				</view>
				<view class="person_info_right">
					<view class="color-6362">
						距离 {{distance}} km
					</view>
					<view class="">
						<image :src="distanceSrc" mode=""></image>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: ['nickname', 'age', 'gender', 'avatar_src','famale', 'male', 'distance', 'distanceSrc'],
		data() {
			return {

			}
		},
		methods: {

		}
	}
</script>

<style>
	.body_swiper {
		width: 100%;
		height: 60%;
		/* background-color: #fff */
	}

	.body_person {
		position: absolute;
		top: 120upx;
		left: 50upx;
		height: 648upx;
		width: 650upx;
		border-radius: 20upx;
		border: 1px solid #dddddd;
		background-color: #fff;
	}

	.body_person_img {
		overflow: hidden;
		width: 100%;
		height: 76%;
		border-radius: 20upx 20upx 0 0;
		/* background-color: lightgray; */

	}

	.body_person_img image {
		margin: -130upx 0;
		height: 650upx;
		width: 650upx;
	}

	.body_person_info {
		overflow: hidden;
		height: 23%;
		width: 100%;
		line-height: 150upx;
		border-radius: 0 0 20upx 20upx;
		/* background-color: dimgray;*/
	}

	.person_info_left view {
		float: left;
		margin-left: 30upx;
		/* background-color: #fff; */
	}

	.person_info_left image {
		margin-top: 45upx;
		width: 50upx;
		height: 60upx;
	}

	.person_info_right view {
		float: right;
		margin-right: 20upx;
		/* background-color: #fff; */

	}

	.person_info_right image {
		margin-top: 50upx;
		width: 50upx;
		height: 50upx;
	}
</style>