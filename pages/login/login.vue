<template>
	<!-- 登录页 -->
	<view class="login">
		<!-- 背景图 -->
		<image class="login_bg" src="../../static/src/login_bg.jpg" mode=""></image>	
		<!-- 返回按钮 -->
		<view class="back" @tap="handleBack">
			<image src="../../static/xiangzuojiantou.png" mode=""></image>
		</view>
		<!-- 标题 -->
		<view class="login_title main-text-color fs-600 text-center">
			<view :class="[{'login_title_border color-fff': signIn}]" @tap="handleSignIn" >
				用户名登录
			</view>
			<view :class="{'login_title_border color-fff': !signIn}" @tap="handleSignUp">
				手机号登录
			</view>
		</view>
		<!-- 内容体 -->
		<view class="login_body">
			<login-comp :index='index'></login-comp>			
			<view class="body_3th text-center color-fff fs-28">
				其他方式登录
			</view>
			<view class="body_3th_login" >
				<login-img :img_src="item" v-for="(item, index) in img_src" :key="index"></login-img>
			</view>
		</view>
	</view>
</template>

<script>
	// 导入组件
	import Login_Comp from "../../components/login/login.vue";
	import Login_Img from "../../components/login/login-img.vue";
	export default {
		data() {
			return {
				// 登录标志
				signIn:true,
				// 切换登录方式的下标
				index:0,
				
				flag:true,
				// 第三方登录图标数组
				img_src:["../../static/src/qq.png", "../../static/src/weixin.png"],
				// 显示是否接受协议
				showAgree:false
			}
		},
		methods: {
			handleSignIn(){
				this.signIn = true;
				this.index = 0;
			},
			handleSignUp(){
				this.signIn = false;
				this.index = 1;	
			},
			acceptAgree(){
				this.showAgree = !this.showAgree;				
			},
			handleBack(){
				uni.navigateBack({
					delta:1,
					animationType: 'slide-out-left',
					animationDuration: 1000
				})
			}
		},
		components:{
			'login-comp':Login_Comp,
			'login-img':Login_Img
		}
	}
</script>

<style>
@import url("/static/css/login.css");
</style>
