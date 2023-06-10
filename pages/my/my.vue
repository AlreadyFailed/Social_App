<template>
	<view>
		<!-- Loading -->
		<loading v-if="!show.main"></loading>
		
		<view class="personal" v-if="show.main">
			<view class="personal_title">
				<view class="personal_title_bg">
				</view>
				<view class="personal_title_user">
					<view class="user_avatar">
						<image :src="show.isLogin ? user_data.avatarSrc : src.default" ></image>
					</view>
					<view class="user_name text-center fs-600" v-if="show.isLogin">
						{{user_data.nickname}}
					</view>
					<view class="user_info" v-if="show.isLogin">
						<view class="info">
							<view class="fs-600 color-9191">用户名：{{user_data.username}}</view>
						</view>
					</view>
					<view class="user_login text-center fs-600 color-6362"
					 v-if="!show.isLogin"
					 @tap="handleLogin">
						登陆/注册
					</view>
				</view>
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
	export default {
		data() {
			return {
				show:{
					main:false,
					isLogin:null
				},
				user_data:{},
				user_socialize:{
					friendList:0,
					care:0,
					fans:0,
					group:0,
				},
				text:{
					energy:{friendList:'好友', care:'关注', fans:'粉丝', group:'群组'},
					socialeze:['我的动态', '谁看过我', '我互动过', '会员中心'],
					banner:['我的信息', '设置']
				},
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
			// uni.clearStorage();
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
					console.log(res.data);
				}
			})
		},
		methods: {
			handleLogin(){
				uni.navigateTo({
					url:"/pages/login/login"					
				})
			},
			handleInfo(index){
				handleBanner(index, this.show.isLogin);
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
