<template>
	<view class="setting">
		<view class="head_title text-center main-bg-color">
			填写资料
		</view>
		<view class="body">
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
			<view class="nickname">
				<view class="fs-28 main-text-color fs-600 text-center">
					设置昵称
				</view>
				<input v-model="inputData.nickname" class="main-border-color fs-40" type="text" maxlength="15">
				<view :class="['nickname_tips','color-red','fs-22', {'display-none':show.showNN}]">
					请输入昵称
				</view>
			</view>
			<view class="sexy">
				<view class="fs-28 main-text-color fs-600 text-center ">
					选择性别
				</view>
				<view class="sexy_manOrwoman">
					<view :class="['sexy_MOW_circle', {'main-bg-color': !show.showSexy}]" @tap="choiceSexy1">
						<image src="../../static/src/sexy_0.png" mode=""></image>
					</view>
					<view  class="sexy_MOW_or fs-600 main-text-color">
						or
					</view>
					<view :class="['sexy_MOW_circle', {'main-bg-color': show.showSexy}]" @tap="choiceSexy2">
						<image src="../../static/src/sexy_1.png" mode=""></image>
					</view>
				</view>
			</view>
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
			<view class="complete fs-40 fs-600 text-center color-fff main-bg-color" @tap="upload">
				提交
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			const currentDate = this.getDate({
				format: true
			})
			return {
				inputData: {
					nickname: '',
					gender:0,
					avatar_src:"",
					date: currentDate
				},
				show:{
					showSexy: false,
					showText:false,
					showNN: true,
					showAva:true
				}
			}
		},
		computed: {
			startDate() {
				return this.getDate('start');
			},
			endDate() {
				return this.getDate('end');
			}
		},
		methods: {
			choiceSexy1() {
				this.show.showSexy = false;
			},
			choiceSexy2() {
				this.show.showSexy = true;
			},
			bindDateChange: function(e) {
				this.inputData.date = e.detail.value
			},
			getDate(type) {
				const date = new Date();
				let year = date.getFullYear();
				let month = date.getMonth() + 1;
				let day = date.getDate();

				if (type === 'start') {
					year = year - 60;
				} else if (type === 'end') {
					year = year + 2;
				}
				month = month > 9 ? month : '0' + month;
				day = day > 9 ? day : '0' + day;
				return `${year}-${month}-${day}`;
			},
			imgClick(){
				var _this = this;
				uni.chooseImage({
					count:3,
					sizeType:['compressed'],
					sourceType:['ablum'],
					crop:{width:500, height:500},
					success:function(res){
						console.log(JSON.stringify(res.tempFilePaths));
						_this.inputData.avatar_src = res.tempFilePaths[0];
						_this.show.showText = true;
					}
				})
				
			},
			upload(){
				// Judge inputdata is Empty
				if(this.inputData.nickname == '' || this.inputData.avatar_src == ''){
					if(this.inputData.avatar_src == ''){this.show.showAva = false;}
					if(this.inputData.nickname == ''){this.show.showNN = false;}
					else{this.show.showNN = true}
				}
				// Judge inputdata is correct
				else{
					uni.uploadFile({
						url:this.url + "/upload",
						filePath:this.inputData.avatar_src,
						name:"image",
						formData:{
							phoneNum:uni.getStorageSync("phoneNum"),
							nickname:this.inputData.nickname,
							gender:(this.show.showSexy ? 1 : 0),
							birthday:this.inputData.date
						},
						success: (res) => {
							console.log(res.data);
							// if(res.data.code == 1){
							// 	this.show.showAva = true;
							// 	this.show.showNN = true;
							// 	uni.setStorage({
							// 		key:"token",
							// 		data: res.data.data
							// 	})
							// 	uni.switchTab({
							// 		url:"/pages/index/index"
							// 	})
							// }else{
							// 	uni.showToast({
							// 		title:"数据出错",
							// 		icon:'none'
							// 	})
							// }
						}
					})
					
				}
			}
		}
	}
</script>

<style>
	@import url("../../static/css/setting.css");
</style>