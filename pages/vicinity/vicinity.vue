<template>
	<!-- 附近的人页 -->
	<view>
		<!-- 加载标签 -->
		<loading v-if="!show.main"></loading>

		<!-- 内容体 -->
		<view class="body" v-if="show.main">
			<!-- 滑动显示附近的人 -->
			<swiper class="body_swiper" :current="index" :duration="300">
				<swiper-item v-for="(item, idx) in maxPerson" :key="idx">
					<person :nickname="user_Data[idx].nickname" :age="user_Data[idx].age"
						:gender="user_Data[idx].gender" :avatar_src="user_Data[idx].avatarSrc" :famale="src.famale" :male="src.male"
						:distance="user_Data[idx].distance" :distanceSrc="src.distance"></person>
				</swiper-item>
			</swiper>
			<!-- 列表-->
			<view class="body_button">
				<!-- 点赞按钮 -->
				<view class="body_button_pos">
					<view class="body_button_img">
						<image :src="show.interest ? button[0]: button[1]" mode="" @tap="interest"></image>
					</view>
					<view class="body_button_text text-center color-6362 fs-40">
						{{text[0]}}
					</view>
				</view>
				<!-- 聊天按钮 -->
				<view class="body_button_pos">
					<view class="body_button_img">
						<image :src="button[2]" mode=""></image>
					</view>
					<view class="body_button_text text-center color-6362 fs-40">
						{{text[1]}}
					</view>
				</view>
				<!-- 下一个按钮 -->
				<view class="body_button_pos">
					<view class="body_button_img" @tap="nextPerson">
						<image :src="button[3]" mode=""></image>
					</view>
					<view class="body_button_text text-center color-6362 fs-40">
						{{text[2]}}
					</view>
				</view>
			</view>
		</view>

		<!-- 筛选页 -->
		<view class="screen" v-if="show.screen" @tap="closeScreen">
			<view class="screen_body" @tap.stop="testFn">
				<!-- 关闭按钮 -->
				<image class="screen_body_close" src="../../static/src/fanhui-xiao.png" mode="" @tap="closeScreen"></image>

				<!-- 标题 -->
				<view class="screen_body_title text-center main-text-color fs-600 fs-40">
					筛选
				</view>

				<!-- 筛选性别 -->
				<view class="screen_body_gender">
					<view class="gender_title fs-28 color-6362">
						性别
					</view>
					<view class="gender_sexy">
						<view class="gender_sexy_pos">
							<view :class="['gender_sexy_img', ,{'main-bg-color main-border-color' : show.gender1}]"
								:index="flag.gender" @tap="gender">
								<image :src="src.gender[0]" mode=""></image>
							</view>
							<view :class="['text-center', 'color-6362', {'main-text-color' : show.gender1}]">
								女
							</view>
						</view>

						<view class="gender_sexy_pos">
							<view :class="['gender_sexy_img', {'main-bg-color main-border-color' : show.gender2}]"
								@tap="gender">
								<image :src="src.gender[1]" mode=""></image>
							</view>
							<view :class="['text-center', 'color-6362', {'main-text-color' : show.gender2}]">
								男
							</view>
						</view>


						<view class="gender_sexy_pos">
							<view :class="['gender_sexy_img', {'main-bg-color main-border-color' : show.gender3}]"
								@tap="gender">
								<image :src="src.gender[2]" mode=""></image>
							</view>
							<view :class="['text-center', 'color-6362', {'main-text-color' : show.gender3}]">
								不限
							</view>
						</view>

					</view>
				</view>

				<!-- 筛选年龄区间 -->
				<view class="screen_body_age">
					<view class="age_title fs-28 color-6362">
						年龄(岁)
					</view>
					<view class="age_interval">
						<view class="age_interval_line main-bg-color">
							<view class="age_interval_line2" :style="{'width': coordinate.x + 'upx'}"></view>
							<view class="age_interval_line3" :style="{'width': (270 - coordinate.x2) + 'px'}"></view>
							<!-- <view class="age_interval_line2 main-bg-color" :style="{'width': coordinate.x2 + 'px'}"></view> -->
						</view>

						<view class="age_interval_dot main-bg-color" :style="{'left': coordinate.x + 'upx'}"
							@touchmove="drawMove">
							<view class="age_interval_tips text-center color-fff">
								{{age.left}}
							</view>
						</view>

						<view class="age_interval_dot main-bg-color" :style="{'left': coordinate.x2 + 'px'}"
							@touchmove="drawMove2">
							<view class="age_interval_tips text-center color-fff">
								{{age.right}}
							</view>
						</view>

					</view>
				</view>

				<!-- 筛选距离 -->
				<view class="screen_body_distance">
					<view class="distance_title fs-28 color-6362">
						范围
					</view>
					<view class="distance_interval">
						<view class="distance_interval_line">
							<view class="distance_interval_line2 main-bg-color"
								:style="{'width': coordinate.x3 + 'px'}"></view>
						</view>
						<view class="distance_interval_dot main-bg-color" :style="{'left': coordinate.x3 + 'px'}"
							@touchmove="drawMove3">
							<view class="distance_interval_tips text-center fs-22 color-fff">
								{{distance}}m
							</view>
						</view>
					</view>
				</view>

				<!-- 筛选薪资 -->
				<view class="screen_body_salary">
					<view class="salary_title color-6362 fs-28">
						年收入
					</view>
					<view class="salary_interval_labels">
						<view ref="salary_box"
							:class="['labels', 'fs-28', 'text-center', 'color-fff',{'main-bg-color': salary_idx == 0 }]"
							@tap="salaryInterval($event, 0)">{{salary[0]}}</view>
						<view ref="salary_box"
							:class="['labels', 'fs-28', 'text-center', 'color-fff',{'main-bg-color': salary_idx == 1 }]"
							@tap="salaryInterval($event, 1)">{{salary[1]}}</view>
						<view ref="salary_box"
							:class="['labels', 'fs-28', 'text-center', 'color-fff',{'main-bg-color': salary_idx == 2 }]"
							@tap="salaryInterval($event, 2)">{{salary[2]}}</view>
						<view ref="salary_box"
							:class="['labels', 'fs-28', 'text-center', 'color-fff',{'main-bg-color': salary_idx == 3 }]"
							@tap="salaryInterval($event, 3)">{{salary[3]}}</view>
					</view>
				</view>

				<!-- 提交筛选条件 -->
				<view @tap="handleScreen" class="screen_body_button main-bg-color color-fff text-center fs-600 fs-40">
					搜索
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import Loading from "../../components/loading.vue";
	import Person from "../../components/vicinity/person.vue";
	import Login_img from "../../components/login/login-img.vue"
	import {
		norData
	} from "../../static/js/index.js";
	import {
		getDistance
	} from "../../static/js/calDistance.js"
	export default {
		data() {
			return {
				// 显示对象
				show: {
					main: false,
					interest: true,
					screen: false,
					gender1: true,
					gender2: false,
					gender3: false
				},
				// 用户信息对象
				user_Data: {},
				// 图片对象
				src: {
					famale: "../../static/src/0.png",
					male: "../../static/src/1.png",
					gender: ['../../static/src/gender_0.png', '../../static/src/gender_1.png',
						'../../static/src/gender_other.png'
					],
					distance: "../../static/src/dingwei.png"
				},
				// 按钮图标对象
				button: ['../../static/src/guanzhu2.png', '../../static/src/guanzhu.png', '../../static/src/dazhaohu.png',
					'../../static/src/xiayige.png'
				],
				// 坐标对象
				coordinate: {
					longitude: 0,
					latitude: 0,
					x: 120,
					x2: 130,
					x3: 40,

				},
				// 标识对象
				flag: {
					gender: 0
				},
				// 年龄对象
				age: {
					left: 24,
					right: 30
				},
				// 薪资对象
				salary: ['0-5万', '5-10万', '10-20万', '不限'],
				// 薪资下标
				salary_idx: 0,
				// 文本对象
				text: ['关注', '打招呼', '下一个'],
				// 距离
				distance: 500,
				index: 0,
				maxPerson: 0,
			}
		},
		onLoad() {
			// 预加载
			uni.request({
				url: this.url + "/users/vic",
				method: "GET",
				header:{
					"token": uni.getStorageSync("token")
				},
				data:{
					distance:5000
				},
				success: (res) => {
					this.user_Data = norData(res.data.data);
					this.maxPerson = this.user_Data.length;
					this.show.main = true;
				},
				fail: () => {
					uni.showToast({
						title: "网络信号不好",
						icon: "loading",
						duration: 3000
					})
				}
			})
		},
		methods: {
			// 切换下一个
			nextPerson() {
				if (this.index < this.maxPerson - 1) {
					this.index += 1;
				} else {
					return;
				}
			},
			// 点攒
			interest() {
				this.show.interest = !this.show.interest;
			},
			// 关闭筛查页
			closeScreen() {
				this.show.screen = false;
				this.coordinate.x = 120;
				this.coordinate.x2 = 130;
				this.coordinate.x3 = 40;
				this.flag.gender = 0;
				this.show.gender1 = true;
				this.show.gender2 = false;
				this.show.gender3 = false;
				this.age.left = 24;
				this.age.right = 30;
				this.salary_idx = 0;
				this.distance = 500;
			},
			// 防止穿透
			testFn(e) {
				e.preventDefault();
			},
			// 性别筛选
			gender(e) {
				if (e.target.offsetLeft < 50) {
					this.show.gender1 = !this.show.gender1;
					this.show.gender2 = false;
					this.show.gender3 = false
					if (this.show.gender1) this.flag.gender = 0;
					else this.flag.gender = null;
				} else if (e.target.offsetLeft < 155) {
					this.show.gender2 = !this.show.gender2;
					this.show.gender1 = false;
					this.show.gender3 = false
					if (this.show.gender2) this.flag.gender = 1;
					else this.flag.gender = null;
				} else {
					this.show.gender3 = !this.show.gender3;
					this.show.gender1 = false;
					this.show.gender2 = false;
					if (this.show.gender3) this.flag.gender = 2;
					else this.flag.gender = null;
				}
			},
			// 拖动区间选择器
			drawMove(e) {
				let endX = e.changedTouches[0].pageX;
				if (endX > 0 && endX < 120) {
					this.coordinate.x = endX;
					this.age.left = Math.trunc(this.coordinate.x / 18) + 18;
				}
			},
			drawMove2(e) {
				let endX = e.changedTouches[0].pageX;
				if (endX > 130 && endX < 250) {
					this.coordinate.x2 = endX;
					this.age.right = Math.trunc((endX) / 6) + 9;
				}
			},
			drawMove3(e) {
				let endX = e.changedTouches[0].pageX;
				if (endX > 40 && endX < 240) {
					this.coordinate.x3 = endX;
					this.distance = Math.trunc(((this.coordinate.x3) * 100) / 4) - 525;
					if (this.distance < 500) this.distance = 500;
					if (this.distance > 5000) this.distance = 5000;
				}
			},
			
			// 筛选薪资区间
			salaryInterval(e, index) {
				this.salary_idx = index;
			},
			// 根据薪资范围，确定最大和最小区间
			handleScreen() {
				// Limit salary range
				var salaryMax = null;
				var salaryMin = null;
				if (this.salary_idx == 0) {
					salaryMax = 5000;
					salaryMin = 0;
				} else if (this.salary_idx == 1) {
					salaryMax = 10000;
					salaryMin = 5001;
				} else if (this.salary_idx == 2) {
					salaryMax = 20000;
					salaryMin = 10001;
				} else {
					salaryMax = 100000;
					salaryMin = 0;
				}
				
				// 提交筛选条件
				uni.request({
					url: this.url + "/users/screen",
					method: 'POST',
					header: {
						"token": uni.getStorageSync("token")
					},
					data: {
						gender: this.flag.gender,
						ageMax: this.age.right,
						ageMin: this.age.left,
						distance: this.distance,
						salaryMax: salaryMax,
						salaryMin : salaryMin
					},
					success: (res) => {
						console.log(res.data);
					},
					fail: (res) => {
						uni.showToast({
							title: res.data,
							icon: "error"
						})
					}
				})
			}
		},
		components: {
			'loading': Loading,
			'person': Person,
			'login-img': Login_img
		},
		// 隐藏底部tabbar
		onNavigationBarButtonTap(e) {
			this.show.screen = true;
		}
	}
</script>

<style>
	@import url("../../static/css/vicinity.css");
</style>