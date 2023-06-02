<template>
	<swiper class="body_login" :current="index" disable-touch="true" :duration="300">
		<swiper-item>
			<view class="swiper-item">
				
				<input class="telephone text-center color-fff" type="number" maxlength="11" placeholder="手机号/用户名"
					v-model="signInTel" adjust-position="true" placeholder-class="style-PL">
				<view :class="['tips2', 'color-red', 'fs-22',{'display-none': show_TEL}]">
					手机号输入错误
				</view>
				
				<input class="telephone text-center color-fff" type="text" :password="true" :maxlength="24"
					v-model="signInPwd" placeholder="输入密码" placeholder-class="style-PL">
				<view class="forget color-fff fs-22">
					忘记密码？
				</view>
				
				<button class="login color-fff" @tap="SignIn">登录</button>
				<view :class="['tips', 'color-red', 'fs-22',{'display-none': show_PWD}] ">
					手机号或密码错误
				</view>
				
				<view class="body_agreement">
					<view class="body_agreement_text color-fff fs-28">
						<view :class="['body_agreement_dot', {'main-bg-color':accept_agree}]" @tap="acceptAgree"></view>
						 我已阅读并同意<a href="">用户协议</a>和<a href="">隐私政策</a>	
						 <view :class="['body_agreement_tips', 'color-red', 'fs-22', {'display-none': show_Agree}]">
						 	未阅读用户协议和隐私政策
						 </view>
					</view>
				</view>
			</view>
		</swiper-item>
		<swiper-item>
			<view class="swiper-item">
				
				<input class="telephone text-center color-fff" type="number" maxlength="11" placeholder="手机号"
					v-model="signUpTel" placeholder-class="style-PL">
				<view :class="['tips2', 'color-red', 'fs-22',{'display-none': show_TEL2}]">
					手机号输入错误
				</view>
				
<!-- 				<input class="telephone text-center color-fff" type="text" :password="true" :maxlength="24"
					v-model="signUpPwd" placeholder="输入密码" placeholder-class="style-PL">
				<view :class="['tips2', 'color-red', 'fs-22',{'display-none': show_PWD2}]">
					{{signUpPwd_error}}
				</view> -->
				
				<input class="telephone telephone_verify text-center color-fff" type="number" :maxlength="6"
					placeholder="输入验证码" placeholder-class="style-PL" v-model="inputcode">
				<view :class="['tips2', 'color-red', 'fs-22', {'display-none':show_Vericode}]">
					{{vericode_Error}}
				</view>
				
				<view :class="['verify_code', 'fs-20', {'color-fff' : !codeTime}, 'text-center']" @tap="sendVericode">
					<text>{{!codeTime ? "获取验证码" : "已发送 (" + codeTime + "s)"}}</text>
				</view>
				
				<view class="login color-fff text-center" @tap="SignUp">登录</view>
				
				<view class="body_agreement">
					<view class="body_agreement_text color-fff fs-28">
						<view :class="['body_agreement_dot', {'main-bg-color':accept_agree}]" @tap="acceptAgree"></view>
						 我已阅读并同意<a href="">用户协议</a>和<a href="">隐私政策</a>	
						 <view :class="['body_agreement_tips', 'color-red', 'fs-22', {'display-none': show_Agree2}]">
						 	未阅读用户协议和隐私政策
						 </view>
					</view>
				</view>				
			</view>
		</swiper-item>
	</swiper>
</template>

<script>
	export default {
		name: "login",
		props: ["index"],
		data() {
			return {
				show_TEL: true,
				show_TEL2: true,
				show_PWD: true,
				show_PWD2:true,
				show_Vericode: true,
				show_Agree:true,
				show_Agree2:true,
				accept_agree:false,
				signInTel: '',
				signInPwd: '',
				signUpTel: '',
				currentTel:'',
				signUpPwd_error: '密码输入错误',
				inputcode: '',
				getcode:'',
				vericode_Error:'验证码输入错误',
				codeTime : 0,
				startTime:0,
				endTime:0
			};
		},
		methods: {
			SignIn() {
				const reg =
					/^[1](([3][0-9])|([4][5-9])|([5][0-3, 5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$/;
				var determine = reg.test(this.signInTel);
				// Judge Tel,PWD,Agreemetn are empty				
				if(this.signInTel.length != 11 || this.signInPwd.length <= 6 || !this.accept_agree){
					if(this.signInTel.length != 11 || this.signInPwd.length <= 6){
						this.show_PWD = false;
					}else{
						this.show_PWD = true;
						}
					if(!this.accept_agree){
						this.show_Agree = false;
					}else{
						this.show_Agree = true;
					}
				}
				// Judge them’s correctness
				else{
					if(determine && this.accept_agree){
						uni.request({
							url:this.url + "/signin",
							method:'POST',
							data:{
								phoneNum : this.signInTel,
								password : this.signInPwd
							},
							success:(res)=>{
								var error = res.data.code;						
								if(error === 1){
									uni.setStorage({
										key:"token",
										data:res.data.data,
										success:()=>{
											console.log("Sign In Success");
										},
									}),
									uni.switchTab({
										url:"/pages/index/index"
									})
								}else{
									console.log("Login falied");
									this.show_TEL = true;
									this.show_Agree = true;
									this.show_PWD = false;
								}
							}
						})
					}
				}
			},
			sendVericode(e){
				const reg =
					/^[1](([3][0-9])|([4][5-9])|([5][0-3, 5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$/;
				var determineTel = reg.test(this.signUpTel);				
				if(determineTel){					
					if(!this.codeTime){
						this.show_TEL2 = true;	
						this.inputcode = '';
						uni.request({
							method:'POST',
							url:this.url + '/signup/send',
							data:{
								phoneNum : this.signUpTel
							},
							success:res=>{
								this.startTime = (new Date().getTime()) + (this.codeTime * 1000);
								this.getcode = res.data.data;
								this.currentTel = this.signUpTel;
								console.log(res.data);
								uni.showToast({
									title:this.getcode,
									icon:'none',
									duration: 5000
								})
							},
							fail:()=>{
								uni.showToast({
									title:"验证码发送失败。。",
									icon:'error'
								})
							}
						})
						
						this.codeTime = 15;
						let timer = setInterval(()=>{
							this.codeTime--;
							if(this.codeTime < 1){
								clearInterval(timer);
								this.codeTime = 0;
							}
						}, 1000)
					}else{
						uni.showToast({
							title:"不能重复获取",
							icon:"none",
						})
						return;
					}
				}else{
					this.show_TEL2 = false;
					this.show_Vericode = true;
					this.show_Agree2 = true;
					console.log("Telephone Number is Error");
				}				
			},
			SignUp(){
				// Judge input data is Empty
				if(this.inputcode.length == 0 || this.signUpTel.length != 11 || !this.accept_agree){
					if(this.inputcode.length != 6){
						this.vericode_Error = "请获取验证码";
						this.show_Vericode = false;
					}else{this.show_Vericode = true;}
					
					if(this.signUpTel.length != 11) {this.show_TEL2 = false;}
					else{this.show_TEL2 = true;}
					
					if(!this.accept_agree){this.show_Agree2 = false;}
					else{this.show_Agree2 = true}
				}
				
				// Judge input data is correct
				else{
					// 
					// var reg = /^(?=.*\d)(?=.*[A-Z])[a-zA-Z0-9]{6,18}$/;
					// var determinePWD = reg.test(this.signUpPwd);
					// if(this.getcode != this.inputcode){
					// 	this.vericode_Error = "验证码输入错误";
					// 	this.show_Vericode = false;
					// 	console.log("Verify code is Error");
					// }else 
					if(this.currentTel != this.signUpTel){
						this.show_TEL2 = false;
						console.log("Telephone Number is Error");
					}else{
						this.show_TEL2 = true;
						this.show_PWD2 = true;
						this.show_Vericode = true;
						this.show_Agree2 = true;
						this.endTime = new Date().getTime();
						if(this.startTime >= this.endTime){
							uni.request({
								url:this.url + "/signup/",
								method:'POST',
								data:{
									phoneNum: this.signUpTel,
									password: this.signUpPwd,
									veriCode: this.inputcode
								},
								success: (res) => {
									if(res.data.code == 1){
										this.show_Vericode = true;
										uni.setStorage({
											key:"phoneNum",
											data:this.signUpTel
										})
										uni.setStorage({
											key:"token",
											data: res.data.data
										})
										uni.navigateTo({
											url:"/pages/setting/setting"
										})
									}else{
										this.show_Vericode = false;
										console.log("Verify code is Error");
									}
								}
							})
							
							
						
						}
						else{
							this.vericode_Error = "验证码已过期，请重新获取";
							this.show_Vericode = false;
							console.log("The verify code has expired");
						}
										
					}					
				}
				
			},
			acceptAgree(){
				this.accept_agree = !this.accept_agree;
			}
		}
	}
</script>

<style>
	.body_login {
		position: absolute;
		box-sizing: border-box;
		top: 450upx;
		width: 750upx;
		height: 700upx;
		padding: 30upx 100upx;
		/* background-color: pink */
	}

	.telephone {
		height: 65upx;
		margin-bottom: 80upx;
		font-size: 35upx;
		border-radius: 50upx;
		border: 2px solid #fff;
	}

	.telephone_verify {
		width: 350upx;
	}

	.style-PL {
		font-size: 30upx;
		color: #fff;
	}

	.tips {
		position: absolute;
		margin: -140upx 40upx;
	}
	.tips2{
		position: absolute;
		margin: -70upx 40upx;
	}
	.forget {
		position: absolute;
		top: 240upx;
		left: 400upx;
	}

	.login {
		width: 550upx;
		height: 70upx;
		line-height: 70upx;
		border-radius: 50upx;
		background-color: dodgerblue;
	}

	.verify_code {
		position: absolute;
		top: 155upx;
		left: 400upx;
		line-height: 70upx;
		width: 150upx;
		height: 70upx;
		padding: 0;
		color: #b4b4b4;
		border-radius: 20upx;
		background-color: #d4237a
	}
	.body_agreement{
		position: absolute;
		top: 550upx;
		width: 750upx;
		height: 50upx;
		line-height: 50upx;
		/* background-color: lightgray;		 */
	}
	.body_agreement_dot{
		box-sizing: border-box;
		float: left;
		margin: 10upx 30upx 0 10upx;
		width: 35upx;
		height: 35upx;
		border-radius: 50%;
		border: #fff 2px solid;
		/* // background-color: #d4237a; */
	}
	a{
		text-decoration: none;
		color: rgb(172, 25, 140);
	}
	.body_agreement_tips{
		/* margin-left: 20upx; */
		width: 350upx;
		height: 50upx;
		/* background-color: lightgray; */
	}
</style>