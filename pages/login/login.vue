<template>
	<view class="login">
		<!-- Background -->
		<image class="login_bg" src="../../static/src/login_bg.jpg" mode=""></image>	
		<!-- Title -->
		<view class="login_title main-text-color fs-600 text-center">
			<view :class="[{'login_title_border color-fff': signIn}]" @tap="handleSignIn" >
				用户名登录
			</view>
			<view :class="{'login_title_border color-fff': !signIn}" @tap="handleSignUp">
				手机号登录
			</view>
		</view>
		<!-- Body -->
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
	import Login_Comp from "../../components/login/login.vue";
	import Login_Img from "../../components/login/login-img.vue";
	export default {
		data() {
			return {
				signIn:true,
				index:0,
				flag:true,
				img_src:["../../static/src/qq.png", "../../static/src/weixin.png"],
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
