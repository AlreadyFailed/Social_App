<template>
	<view>
		<!-- 加载标签 -->
		<loading v-if="!show.main"></loading>
		<!-- 个人中心页 -->
		<view class="personal" v-if="show.main">
			<!-- 头部 -->
			<view class="personal_title">
				<!-- 头部：背景 -->
				<view class="personal_title_bg"></view>
				<!-- 头部：用户信息-->
				<view class="personal_title_user">
					<!-- 头像 -->
					<view class="user_avatar">
						<image :src="show.isLogin ? user_data.avatarSrc : src.default" ></image>
					</view>
					<!-- 姓名 -->
					<view class="user_name text-center fs-600" v-if="show.isLogin">
						{{user_data.nickname}}
					</view>
					<!-- 信息 -->
					<view class="user_info" v-if="show.isLogin">
						<view class="info">
							<view class="fs-600 color-9191">用户名：{{user_data.username}}</view>
						</view>
					</view>
					<!-- 如果未登录，则显示需要注册 -->
					<view class="user_login text-center fs-600 color-6362"
					 v-if="!show.isLogin"
					 @tap="handleLogin">
						登陆/注册
					</view>
				</view>
				<!-- 头部：活跃参数-->
				<view class="personal_title_energy">
					<view class="energy_lis text-center" v-for="(item,index) in user_socialize" :key="index">
						<view class="amount fs-600 ">
							{{item}}
						</view>
						<view class="designation color-9191 fs-40">
							{{text.energy[index]}}
						</view>
					</view>
				</view>
				<!-- 头部：社交 -->
				<view class="personal_title_socialize">
					<view class="socialize_lis text-center" v-for="(item,index) in 4" :key="index">
						<view class="img">
							<image :src="src.socialize[index]" ></image>
						</view>
						<view class="describe">
							{{text.socialeze[index]}}
						</view>
					</view>
				</view>
			</view>
			<!-- 内容体 -->
			<view class="personal_banner">
				<view class="personal_banner_lis" v-for="(item, index) in 2" :key="index">
					<view class="lis_image">
						<image :src="src.banner[index]" ></image>
					</view>
					<view class="lis_title color-6362" @tap="handleInfo(index)">
						<text>{{text.banner[index]}}</text>
						<view class="arrow">
							<image src="../../static/src/xiangyoujiantou.png" ></image>
						</view>
					</view>
					
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import Loading from "../../components/loading.vue";
	import {handleBanner} from "../../static/js/personal.js"
	// import {reload} from "../../static/js/reload.js"
	export default {
		data() {
			return {
				// 显示数组
				show:{
					main:false,
					isLogin:null
				},
				// 用户信息
				user_data:{},
				// 社交信息
				user_socialize:{
					friendList:0,
					care:0,
					fans:0,
					group:0,
				},
				// 文本数组
				text:{
					energy:{friendList:'好友', care:'关注', fans:'粉丝', group:'群组'},
					socialeze:['我的动态', '谁看过我', '我互动过', '会员中心'],
					banner:['我的信息', '设置']
				},
				// 图片数组
				src:{
					default:'../../static/src/moren.png',
					socialize:['../../static/src/fabu.png',
					'../../static/src/kanguo.png',
					'../../static/src/zuji.png',
					'../../static/src/huanguan.png'],
					banner:['../../static/src/ziliao.png',
					'../../static/src/set.png'
					]
				}
			}
		},
		onLoad(){
			// 预加载， 同时将该用户的数据保存到本地，方便后续的个人资料修改。
			uni.request({
				url:this.url + '/users/personal',
				method:"GET",
				header:{
					'token':uni.getStorageSync('token')
				},
				success:(res)=>{
					if(res.data.code === 1){
						console.log(res.data);
						this.user_data = res.data.data[0];
						this.user_socialize.friendList = res.data.data[1].friendList;
						this.user_socialize.care = res.data.data[1].care;
						this.user_socialize.fans = res.data.data[1].fans;
						uni.setStorage({
							key:'user',
							data:this.user_data
						})
						this.show.isLogin = true;
					}else{
						this.show.isLogin = false;
						console.log(res.data);
					}
					this.show.main = true;
				},
				fail:(res)=>{
					console.log(res.data)
				}
			})
		},
		methods: {
			// 未登录，则需要先登录
			handleLogin(){
				uni.navigateTo({
					url:"/pages/login/login"					
				})
			},
			// 如果为以登录，则可以进行个人资料修改
			handleInfo(index){
				if(index == 0){
					uni.navigateTo({
						url:"/pages/info/info",
						animationType:"slide-in-right",
						animationDuration:500
					})
				}else{
					uni.showToast({
						title:"请登录",
						icon:"error"
					})
				}
			},
			reload(){
				this.$forceUpdate();
			}
		},
		components:{
			'loading':Loading
		}
	}
</script>

<style>
@import url("../../static/css/personal.css");
</style>
