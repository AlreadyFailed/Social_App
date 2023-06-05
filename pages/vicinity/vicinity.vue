<template>
	<view>
		<!-- Loading -->
		<loading v-if="!show.main"></loading>

		<!-- Body -->
		<view class="body" v-if="show.main">
			<swiper class="body_swiper" :current="index" :duration="300">
				<swiper-item v-for="(item, idx) in maxPerson" :key="idx">
					<person :nickname="user_Data[idx].nickname" :age="user_Data[idx].age"
						:gender="user_Data[idx].gender" :avatar_src="user_Data[idx].avatarSrc" :famale="src.famale" :male="src.male"
						:distance="user_Data[idx].distance" :distanceSrc="src.distance"></person>
				</swiper-item>
			</swiper>

			<view class="body_button">
				<view class="body_button_pos">
					<view class="body_button_img">
						<image :src="show.interest ? button[0]: button[1]" mode="" @tap="interest"></image>
					</view>
					<view class="body_button_text text-center color-6362 fs-40">
						{{text[0]}}
					</view>

				</view>
				<view class="body_button_pos">
					<view class="body_button_img">
						<image :src="button[2]" mode=""></image>
					</view>
					<view class="body_button_text text-center color-6362 fs-40">
						{{text[1]}}
					</view>
				</view>
				<view class="body_button_pos">
					<view class="body_button_img" @tap="nextPerson">
						<image :src="button[3]" mode=""></image>
					</view>
					<view class="body_button_text text-center color-6362 fs-40">
						{{text[2]}}
					</view>
				</view>
				<!-- <slider @change="durationChange" :value="duration" min="500" max="2000" /> -->
			</view>
		</view>

		<!-- Screen -->
		<view class="screen" v-if="show.screen" @tap="closeScreen">
			<view class="screen_body" @tap.stop="testFn">
				<!-- Screen Close -->
				<image class="screen_body_close" src="../../static/src/fanhui-xiao.png" mode="" @tap="closeScreen">
				</image>

				<!-- Screen Title -->
				<view class="screen_body_title text-center main-text-color fs-600 fs-40">
					筛选
				</view>

				<!-- Screen Gender -->
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

				<!-- Screen Age -->
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

				<!-- Screen Distance Interval -->
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

				<!-- Screen Salary Interval -->
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

				<!-- Screen Button -->
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
				show: {
					main: false,
					interest: true,
					screen: false,
					gender1: true,
					gender2: false,
					gender3: false
				},
				user_Data: {},
				src: {
					famale: "../../static/src/0.png",
					male: "../../static/src/1.png",
					gender: ['../../static/src/gender_0.png', '../../static/src/gender_1.png',
						'../../static/src/gender_other.png'
					],
					distance: "../../static/src/dingwei.png"
				},
				button: ['../../static/src/guanzhu2.png', '../../static/src/guanzhu.png', '../../static/src/dazhaohu.png',
					'../../static/src/xiayige.png'
				],
				coordinate: {
					longitude: 0,
					latitude: 0,
					x: 120,
					x2: 130,
					x3: 40,

				},
				flag: {
					gender: 0
				},
				age: {
					left: 24,
					right: 30
				},
				salary: ['0-10万', '10-20万', '20-30万', '不限'],
				salary_idx: 0,
				text: ['关注', '打招呼', '下一个'],
				distance: 500,
				index: 0,
				maxPerson: 0,
			}
		},
		onLoad() {
			// uni.getLocation({
			// 	type: 'gcj02',
			// 	geocode: true,
			// 	success: ({longitude,latitude, address}) => {
			// 		console.log('当前位置的经度：' + longitude)
			// 		console.log('当前位置的纬度：' + latitude)
			// 		console.log(address);
			// 		this.coordinate.longitude = longitude;
			// 		this.coordinate.latitude = latitude;
			// 		let distance = getDistance(this.coordinate.longitude, this.coordinate.latitude, 110, 30);
			// 		distance = Number(distance).toFixed(2)
			// 		console.log(distance);
			// 	}
			// })

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
			nextPerson() {
				if (this.index < this.maxPerson - 1) {
					this.index += 1;
				} else {
					return;
				}
			},
			interest() {
				this.show.interest = !this.show.interest;
			},
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
			testFn(e) {
				e.preventDefault();
			},
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
			salaryInterval(e, index) {
				this.salary_idx = index;
			},
			handleScreen() {
				// Limit salary range
				var salaryMax = null;
				var salaryMin = null;
				if (this.salary_idx == 0) {
					salaryMax = 10000;
					salaryMin = 0;
				} else if (this.salary_idx == 1) {
					salaryMax = 20000;
					salaryMin = 10000;
				} else if (this.salary_idx == 2) {
					salaryMax = 30000;
					salaryMin = 20000;
				} else {
					salaryMax = 100000;
					salaryMin = 0;
				}
				
				// Send Request
				uni.request({
					url: this.url + "/users",
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
		onNavigationBarButtonTap(e) {
			this.show.screen = true;
		}
	}
</script>

<style>
	@import url("../../static/css/vicinity.css");
</style>