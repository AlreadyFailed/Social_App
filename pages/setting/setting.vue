<template>
	<!-- 注册时的资料填写页面 -->
	<view class="setting">
		<!-- 头部：标题 -->
		<view class="head_title text-center main-bg-color">
			填写资料
		</view>
		<!-- 内容体 -->
		<view class="body">
			<!-- 内容体：设置头像 -->
			<view :class="['avatar', 'main-border-color', 'text-center', {'overflow-hidden': show.showText}]" @tap="imgClick">
				<image :class="['avatar_src', {'display-none': !show.showText}]" :src="inputData.avatar_src" mode=""></image>
				<view :class="['avatar_text','main-text-color', 'fs-28','fs-600', {'display-none':show.showText}]">上传头像</view>
				<view :class="['avatar_add', {'display-none':show.showText}]">
					<image :class="{'display-none':show.showText}" src="../../static/src/jiahao.png" mode=""></image>
				</view>
				<view :class="['avatar_tips','color-red','fs-22', {'display-none':show.showAva}]">
					请设置头像
				</view>
			</view>
			<!-- 内容体：设置昵称 -->
			<view class="nickname">
				<view class="fs-28 main-text-color fs-600 text-center">
					设置昵称
				</view>
				<input v-model="inputData.nickname" class="main-border-color fs-40" type="text" maxlength="15">
				<view :class="['nickname_tips','color-red','fs-22', {'display-none':show.showNN}]">
					请输入昵称
				</view>
			</view>
			<!-- 内容体：设置性别 -->
			<view class="sexy">
				<view class="fs-28 main-text-color fs-600 text-center ">
					选择性别
				</view>
				<view class="sexy_manOrwoman">					
					<view :class="['sexy_MOW_circle', {'main-bg-color': !show.showSexy}]" @tap="choiceSexy1">
						<image src="../../static/src/sexy_0.png" mode=""></image>
					</view>
					<view :class="['sexy_MOW_famale', 'fs-28', {'main-text-color': !show.showSexy}]">
						女
					</view>
					<view  class="sexy_MOW_or fs-600 main-text-color">
						or
					</view>
					<view :class="['sexy_MOW_circle', {'main-bg-color': show.showSexy}]" @tap="choiceSexy2">
						<image src="../../static/src/sexy_1.png" mode=""></image>
					</view>
					<view :class="['sexy_MOW_male', 'fs-28', {'main-text-color': show.showSexy}]">
						男
					</view>
				</view>
			</view>
			<!-- 内容体：设置生日 -->
			<view class="birthday">
				<view class="fs-28 main-text-color fs-600 text-center ">
					设置生日
				</view>
				<view class="birthday_date main-border-color">
					<picker mode="date" :value="inputData.date" :start="startDate" :end="endDate"
						@change="bindDateChange">
						<view class="text-center fs-40">{{inputData.date}}</view>
					</picker>
				</view>
			</view>
			<!-- 内容体：提交 -->
			<view class="complete fs-40 fs-600 text-center color-fff main-bg-color" @tap="upload">
				提交
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			// 今天的日期
			const currentDate = this.getDate({
				format: true
			})
			return {
				// 保存用户填写的信息
				inputData: {
					nickname: '',
					gender:0,
					avatar_src:"",
					date: currentDate
				},
				// 显示对象
				show:{
					showSexy: false,
					showText:false,
					showNN: true,
					showAva:true
				},
				// 坐标对象
				coordinate:{
					longitude: 0.0,
					latitude: 0.0
				}
				
			}
		},
		computed: {
			// 计算日期范围
			startDate() {
				return this.getDate('start');
			},
			endDate() {
				return this.getDate('end');
			}
		},
		methods: {
			// 选择性别
			choiceSexy1() {
				this.show.showSexy = false;
			},
			choiceSexy2() {
				this.show.showSexy = true;
			},
			
			// 显示选择的日期
			bindDateChange: function(e) {
				this.inputData.date = e.detail.value
			},
			getDate(type) {
				const date = new Date();
				let year = date.getFullYear()-18;
				let month = date.getMonth() + 1;
				let day = date.getDate();

				if (type === 'start') {
					year = year - 60;
				} else if (type === 'end') {
					year = year ;
				}
				month = month > 9 ? month : '0' + month;
				day = day > 9 ? day : '0' + day;
				return `${year}-${month}-${day}`;
			},
			
			// 选择图片
			imgClick(){
				var _this = this;
				uni.chooseImage({
					count:3,
					sizeType:['compressed'],
					sourceType:['ablum'],
					crop:{width:1000, height:1000},
					success:function(res){
						console.log(JSON.stringify(res.tempFilePaths));
						_this.inputData.avatar_src = res.tempFilePaths[0];
						_this.show.showText = true;
					}
				})
				
			},
			
			// 上传图片
			upload(){
				// 判断是否有没填写的信息
				if(this.inputData.nickname == '' || this.inputData.avatar_src == ''){
					if(this.inputData.avatar_src == ''){this.show.showAva = false;}
					if(this.inputData.nickname == ''){this.show.showNN = false;}
					else{this.show.showNN = true}
				}
				// 判断填写的信息，是否违规。
				else{
					uni.getLocation({
						type: 'gcj02',
						geocode: true,
						success: ({longitude,latitude, address}) => {
							console.log('当前位置的经度：' + longitude)
							console.log('当前位置的纬度：' + latitude)
							console.log(address);
							this.coordinate.longitude = longitude;
							this.coordinate.latitude = latitude;
						}
					})
					// 因为uni的接口时异步执行，为了保证先后顺序，这里用了一个定时器。
					setTimeout(()=>{
						uni.uploadFile({
							url:this.url + "/upload",
							filePath:this.inputData.avatar_src,
							name:"image",
							formData:{
								phoneNum:uni.getStorageSync("phoneNum"),
								nickname:this.inputData.nickname,
								birthday:this.inputData.date,
								gender:(this.show.showSexy ? 1 : 0),							
								coordinateLat : this.coordinate.latitude,
								coordinateLong: this.coordinate.longitude
							},
							success: (res) => {
								let json_data = JSON.parse(res.data);
								console.log(json_data);
								if(json_data.code == 1){
									this.show.showAva = true;
									this.show.showNN = true;								
									uni.switchTab({
										url:"/pages/my/my"
									})
								}else{
									uni.showToast({
										title:"数据出错",
										icon:'none'
									})
								}
							}
						})
					}, 2000)
					
					
					
				}
			}
		}
	}
</script>

<style>
	@import url("../../static/css/setting.css");
</style>