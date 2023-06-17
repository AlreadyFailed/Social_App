<template>
	<!-- 消息页 -->
	<view>
		<!-- 加载标签 -->
		<loading v-if="!show.main"></loading>
		<!-- 正文 -->
		<view class="body" v-if="show.main">

			<!-- 标题 -->
			<view class="body_title text-center color-fff fs-40">
				消息
			</view>

			<!-- 固有列表 -->
			<view class="body_inherent">
				<view :class="['body_inherent_lis', {'inherent_lis_color':show.inherent == index}]"
					@touchstart="convertColorToMain(index)" @touchend="convertColorToNor(index)"
					v-for="(item, index) in 3" :key="index">
					<view class="inherent_lis_img">
						<image :src="src.inherent[index]" mode=""></image>
					</view>
					<view class="inherent_lis_right">
						<view class="inherent_lis_right_inherent color-6362">
							{{text[index]}}
						</view>
						<view class="inherent_lis_right_arrow">
							<image src="../../static/src/xiangyoujiantou.png" mode=""></image>
						</view>
					</view>
				</view>
			</view>

			<!-- 聊天列表 -->
			<view class="body_inherent" v-for="(item, index) in user_data.length" :key="index">
				<view :class="['body_inherent_lis', {'inherent_lis_color':show.session == index}]"
					@touchstart="convertColorToMain(index)" @touchend="convertColorToNor(index)">
					<view class="inherent_lis_imgSS">
						<image :src="user_data[index].avatarSrc" mode=""></image>
					</view>
					<view class="inherent_lis_right">
						<view class="inherent_lis_right_user color-6362">
							{{user_data[index].nickname}}
						</view>
						<view class="inherent_lis_right_text fs-22 color-9b9b">
							asdfasdf
						</view>
					</view>
				</view>
			</view>

		</view>

		<!-- 聊天页 -->
		<view class="chatPage" :style="{'left': coordinate.x + 'upx'}" v-if="show.chat">
			<!-- Title -->
			<view class="chatPage_title fs-40 fs-600 text-center">
				{{user_data[0].nickname}}
				<view class="chatPage_title_left" @tap="handleGoback">
					<image src="../../static/xiangzuojiantou.png" mode=""></image>
				</view>
			</view>
			<!-- 聊天区 -->
			<scroll-view class="chatPage_chatRoom" scroll-y="true" :style="{'height' : scroll.height + 'upx'}"
				:scroll-into-view="scroll.toView" scroll-with-animation="false">
				<view class="chatRoom_item" v-for="(item, index) in msg" :id="'msg' + item.id" :key="item.id">
					<view class="chatRoom_time fs-20 color-9b9b text-center" v-if="item.isShowTime">
						{{handleTime(item.time)}}
					</view>
					<view class="chatRoom_left" v-if="item.fromId !== flag.uid">
						<image :src="item.imgUrl" class="avatar avator-left"></image>
						<view class="chatRoom_left_text_triangle"></view>
						<textarea class="chatRoom_left_text" rows="2" disabled="true"
							:style="{'height':item.height + 'upx'}" :value="item.text"
							v-if="item.types === '0'"></textarea>
						<view class="chatRoom_left_image" v-if="item.types === '1'">....</view>
						<view class="chatRoom_left_voice" v-if="item.types === '2'">....</view>
						<view class="chatRoom_left_site" v-if="item.types === '3'">....</view>
					</view>
					<view class="chatRoom_right" v-if="item.fromId === flag.uid">
						<image :src="item.imgUrl" class="avatar avator-right"></image>
						<view class="chatRoom_right_text_triangle"></view>
						<textarea class="chatRoom_right_text" disabled="true" rows="2"
							:style="{'height':item.height + 'upx'}" :value="item.text"
							v-if="item.types === '0'"></textarea>
						<view class="chatRoom_right_image" v-if="item.types === '1'">....</view>
						<view class="chatRoom_right_voice" v-if="item.types === '2'">....</view>
					</view>
				</view>
			</scroll-view>
			<!-- 聊天按钮 -->
			<view class="chatPage_buttom" :style="{'bottom':scroll.bottom + 'upx'}">
				<view class="chatPage_buttom_warp">
					<view class="chatPage_bottom_voice">
						<image src="../../static/src/yuyin.png" mode=""></image>
					</view>
					<textarea class="chatPage_bottom_content" rows="10" name="" id=""></textarea>
					<view class="chatPage_bottom_expression" @tap="getExpression">
						<image src="../../static/src/biaoqing.png" mode=""></image>
					</view>
					<view class="chatPage_bottom_ohter">
						<image src="../../static/src/qita.png" mode=""></image>
					</view>
				</view>
				<view :class="['chatPage_buttom_more', {'display-none' : show.more}]">
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import Loading from "../../components/loading.vue"
	export default {
		data() {
			return {
				// 显示数组,控制html中需要隐藏和显示的标签
				show: {
					main: false,
					inherent: -1,
					session: -1,
					chat: false,
					more: true
				},
				// src数组，保存固有列表和对话列表中的图标和头像。
				src: {
					inherent: ['../../static/src/new_pengyou.png', '../../static/src/yonhu.png',
						'../../static/src/qunliao.png'
					],
					session: ['../../static/src/avatar_1.jpg']
				},
				// 后端返回的数据
				user_data: {},
				// html中标签的坐标，用于控制滑动展示
				coordinate: {
					x: 750,
					y: 0
				},
				// 固有列表的文本
				text: ['添加好友', '仅聊天的会话', '群聊'],
				color: [false, false, false],
				// 每次发送的消息说明。
				msg: [{
					id: 1,
					nickname: '法外狂徒',
					isShowTime: true,
					time: 1686024823557,
					fromId: 2,
					types: '0',
					imgUrl: '../../static/src/avatar_1.jpg',
					height: 125,
					text: "laksdjflkasdjgflasgjlkasdjglaskjdflaskjdflskadjf"
				}],
				// 滑动按钮所需的参数
				scroll: {
					toView: null,
					height: 1390,
					bottom:-500
				},
				// 标志，用于条件判断
				flag: {
					uid: 2,
					sign: 1,
					throttle: 0
				}

			}
		},
		// 预加载
		onLoad() {
			uni.request({
				url: this.url + "/users/msg",
				method: "GET",
				header: {
					"token": uni.getStorageSync("token")
				},
				success: (res) => {
					if (res.data.code == 1) {
						console.log(res.data.data);
						this.user_data = res.data.data;
					} else {
						console.log(res.data.data);
					}
					this.show.main = true;
				},
				fail: (res) => {
					console.log(res.data);
					this.show.main = false;
				}
			})
		},
		methods: {
			// 点击的交互效果
			handleColor(index) {
				if (index == 0) {
					this.color[0] = true;
					this.color[1] = false;
					this.color[2] = false;
				} else if (index == 1) {
					this.color[0] = false;
					this.color[1] = true;
					this.color[2] = false;
				} else {
					this.color[0] = false;
					this.color[1] = false;
					this.color[2] = true;
				}
			},
			// 交互效果
			convertColorToMain(index) {
				if (index == 0) {
					this.show.inherent = 0;
				} else if (index == 1) {
					this.show.inherent = 1;
				} else {
					this.show.inherent = 2;
				}
			},
			// 弹出消息页
			convertColorToNor(index) {
				this.show.inherent = -1;
				if (!this.flag.throttle) {
					this.flag.throttle = 1;
					this.show.chat = true;
					var timer = setInterval(() => {
						this.coordinate.x -= 50;
						if (this.coordinate.x == 0) {
							this.flag.throttle = 0;
							clearInterval(timer);
						}
					}, 15)
					// 隐藏底部tabbar
					uni.hideTabBar();
				}
			},
			// 从聊天页返回消息页
			handleGoback() {
				if (!this.flag.throttle) {
					this.flag.throttle = 1;
					console.log("2");
					var timer = setInterval(() => {
						this.coordinate.x += 50;
						if (this.coordinate.x == 750) {
							this.flag.throttle = 0;
							this.show.chat = false;
							clearInterval(timer);
						}
					}, 15)
					uni.showTabBar();
				}
			},
			// 计算星期
			handleTime(e) {
				var time = new Date();
				var year = time.getFullYear();
				var month = time.getMonth() + 1;
				var date = time.getDate();
				var day = time.getDay();
				switch (day) {
					case 1: {
						day = '一';
						break;
					}
					case 2: {
						day = '二';
						break;
					}
					case 3: {
						day = '三';
						break;
					}
					case 4: {
						day = '四';
						break;
					}
					case 5: {
						day = '五';
						break;
					}
					case 6: {
						day = '六';
						break;
					}
					case 7: {
						day = '日';
						break;
					}
					default: {
						day = 'Day Error';
						break;
					}
				}
				var datetime = year + '年' + month + '月' + date + '日' + ' ' + '周' + day;
				return datetime;
			},
			chatPageScroll(e) {

			},
			// 打开表情列表
			getExpression() {
				if (this.flag.sign === 1) {
					if (!this.flag.throttle) {
						this.flag.throttle = 1;
						this.show.more = false;
						var timer = setInterval(() => {
							this.scroll.bottom += 50;
							if (this.scroll.bottom === 0) {
								clearInterval(timer);
								this.getHeight('.chatPage_buttom', this.flag.sign)
								this.flag.throttle = 0;
								this.flag.sign = 0;
							}
						}, 15)
					}
				} else {
					if (!this.flag.throttle) {
						this.flag.throttle = 1;
						var timer = setInterval(() => {
							this.scroll.bottom -= 50;
							if (this.scroll.bottom === -500) {
								clearInterval(timer);
								this.getHeight('.chatPage_buttom', this.flag.sign);
								this.show.more = true;
								this.flag.throttle = 0;
								this.flag.sign = 1;
							}
						}, 15)
					}
				}
			},
			getHeight(classNa, flag) {
				setTimeout(() => {
					const query = uni.createSelectorQuery().in(this);
					query.select(classNa).boundingClientRect((data) => {
						if (flag === 1) {
							this.scroll.height = this.scroll.height - (data.height * 2) + 100;
							this.scroll.toView = null;
							this.$nextTick(() => {
								this.scroll.toView = '';
							})
						} else {
							this.scroll.height = this.scroll.height + (data.height * 2) - 100;
							this.scroll.toView = null;
							this.$nextTick(() => {
								this.scroll.toView = '';
							})
						}
					}).exec();
				}, 10)
			}
		},
		
		components: {
			'loading': Loading
		}
	}
</script>

<style>
	@import url("../../static/css/msg.css");
</style>