<template>
	<view>
		<loading v-if="!show.main"></loading>
		<view class="body" v-if="show.main">
			
			<!-- Title -->
			<view class="body_title text-center color-fff fs-40">
				消息
			</view>
			
			<!-- Inherent list -->
			<view class="body_inherent">
				<view :class="['body_inherent_lis', {'inherent_lis_color':show.inherent == index}]"
				@touchstart="convertColorToMain(index)" 
				@touchend="convertColorToNor(index)"
				v-for="(item, index) in 3" :key="index"
				>
					<view class="inherent_lis_img" >
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
			
			<!-- Session list -->
			<view class="body_inherent" v-for="(item, index) in user_data.length" :key="index">
				<view :class="['body_inherent_lis', {'inherent_lis_color':show.session == index}]" 
				@touchstart="convertColorToMain(index)"
					@touchend="convertColorToNor(index)">
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
			<!-- <view class="body_session">
				
			</view> -->
		</view>
		
		<!-- Chat page -->
		<view class="chatPage" :style="{'left': coordinate.x + 'upx'}" v-if="show.chat">			
			<view class="chatPage_title fs-40 fs-600 text-center">
				{{user_data[index].nickname}}
				<view class="chatPage_title_left" @tap="handleGoback">
					<image src="../../static/xiangzuojiantou.png" mode=""></image>
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
				show:{
					main:false,
					inherent:-1,
					session:-1,
					chat:false
				},
				src:{
					inherent:['../../static/src/new_pengyou.png', '../../static/src/yonhu.png', '../../static/src/qunliao.png'],
					session:['../../static/src/avatar_1.jpg']
				},
				user_data:{},
				coordinate:{
					x:750,
					y:0
				},
				text:['添加好友', '仅聊天的会话', '群聊'],
				flag:[false, false, false],
				index:0,
				throttle: 0
			}
		},
		onLoad(){
			uni.request({
				url:this.url + "/users/msg",
				method:"GET",
				header:{
					"token": uni.getStorageSync("token")
				},
				success:(res)=>{
					if(res.data.code == 1){
						console.log(res.data.data);
						this.user_data = res.data.data;
					}else{
						console.log(res.data.data);						
					}
					this.show.main = true;					
				},
				fail:(res)=>{
					console.log(res.data);
					this.show.main = false;
				}
			})
		},
		methods:{
			handleColor(index){				
				if(index == 0){
					console.log(index);
					this.flag[0] = true;
					this.flag[1] = false;
					this.flag[2] = false;
				}else if(index == 1){
					this.flag[0] = false;
					this.flag[1] = true;
					this.flag[2] = false;
				}else{
					this.flag[0] = false;
					this.flag[1] = false;
					this.flag[2] = true;
				}
			},
			convertColorToMain(index){
				if(index == 0){
					this.show.inherent = 0;
				}else if(index == 1){
					this.show.inherent = 1;
				}else{
					this.show.inherent = 2;
				}
			},
			convertColorToNor(index){
				this.show.inherent = -1;
				if(!this.throttle){
					this.throttle = 1;
					this.show.chat = true;
					console.log("1");
					var timer = setInterval(()=>{
						this.coordinate.x -= 50;
						if(this.coordinate.x == 0){
							this.throttle = 0;
							clearInterval(timer);
						}
					}, 15)
				}
			},
			handleGoback(){
				if(!this.throttle){
					this.throttle = 1;
					console.log("2");
					var timer = setInterval(()=>{
						this.coordinate.x += 50;
						if(this.coordinate.x == 750){
							this.throttle = 0;
							this.show.chat = false;
							clearInterval(timer);
						}
					}, 15)
				}
			}
		},
		components:{
			'loading':Loading
		}
	}
</script>

<style>
@import url("../../static/css/msg.css");
</style>
