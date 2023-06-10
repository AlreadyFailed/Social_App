<template>
	<view>
		<loading v-if="!show.main"></loading>
		<!-- Infomation page -->
		<view class="info" v-if="show.main">
			<!-- Head -->
			<view class="info_head">
				<view class="back" @tap="handleBack">
					<image src="../../static/xiangzuojiantou.png"></image>
				</view>
				<view class="head_title fs-600 color-6362">
					完善资料
				</view>
			</view> 
			
			<!-- Body -->
			<scroll-view scroll-y="true" class="info_body" :scroll-into-view="scroll.toView">
				<view class="body_avatar" @tap="handleChangeAvatar">
					<image :src="input_data.image"></image>
				</view>
				<!-- <view class="body_lis">
					<view class="basic  fs-40">
						用户��
					</view>
					<input type="text" maxlength="20" v-model="input_data.username" :value="input_data.username == null ? '': input_data.username" placeholder="设置用户��可用于登�� placeholder-style="font-size:35upx;font-weight:100">
				</view> -->
				<view class="body_lis">
					<view class="basic">
						昵称
					</view>
					<input type="text" maxlength="15" v-model="input_data.nickname" :value="input_data.nickname" placeholder="填写昵称" placeholder-style="font-size:35upx;font-weight:100">
				</view>
				<view class="body_lis">
					<view class="basic">
						生日
					</view>
					<view class="date fs-600 fs-40">
						<picker mode="date" 
						 @change="handleChangeDate"
						 :start="startTime"
						 :end="endTime"
						 :value="input_data.birthday">
							<view>{{input_data.birthday}}</view>
						</picker>
					</view>
				</view>
				<view class="body_lis">
					<view class="basic">
						性别
					</view>
					<view :class="['gender_img',{'main-bg-green': index === input_data.gender}]" 
					v-for="(item, index) in 2" :key="index"
					@tap="handleChangeGender(index)">
						<image :src="gender[index]" ></image>
					</view>
				</view>
				<view class="body_lis">
					<view class="basic">
						体重
					</view>
					<view class="slide">
						<view class="slide_line">
							<view class="slide_line_value" :style="{'width' : body_lis.weightWidth + 'upx'}">								
							</view>
						</view>
						<view class="slide_dot main-bg-color" 
						:style="{'left': body_lis.weightLeft + 'upx'}"
						@touchmove="handleChangeWeight">
							<view class="slide_dot_view fs-22 color-fff text-center">
								{{input_data.weight}}kg
							</view>
						</view>
					</view>
				</view>
				<view class="body_lis">
					<view class="basic">
						身高
					</view>
					<view class="slide">
						<view class="slide_line">
							<view class="slide_line_value" :style="{'width' : body_lis.heightWidth + 'upx'}">								
							</view>
						</view>
						<view class="slide_dot main-bg-color" 
						:style="{'left': body_lis.heightLeft + 'upx'}"
						@touchmove="handleChangeHeight">
							<view class="slide_dot_view fs-22 color-fff text-center">
								{{input_data.height}}cm
							</view>
						</view>
					</view>
				</view>
				<view class="body_lis">
					<view class="basic">
						爱好
					</view>
					<input type="text" maxlength="20" v-model="input_data.hobby" :value="input_data.hobby" placeholder-style="font-size:35upx;font-weight:100" placeholder="如:打球,看书,旅游">
				</view>
				<view class="body_lis">
					<view class="basic">
						学历
					</view>
					<view class="picker_selector fs-600 fs-40">
						<picker mode="selector" 
						:range="education" 
						:value="education_index"
						range-key="name"
						@change="handleChangeEducation">
							<view>{{education[education_index].name}}</view>
						</picker>
					</view>
				</view>
				<view class="body_lis">
					<view class="basic">
						感情状态
					</view>
					<view class="picker_selector fs-600 fs-40">
						<picker mode="selector" 
						:range="marry" 
						range-key="name"
						:value="marry_index"
						@change="handleChangeMarry">
							<view>{{marry[marry_index].name}}</view>
						</picker>
					</view>
				</view>
				<view class="body_lis">
					<view class="basic">
						收入
					</view>
					<view class="picker_selector fs-600 fs-40">
						<picker mode="selector" 
						:range="salary" 
						range-key="name"
						:value="salary_index"
						@change="handleChangeSalary">
							<view>{{salary[salary_index].name}}</view>
						</picker>
					</view>
				</view>
			</scroll-view>
			
			<!-- Foot -->
			<view class="info_foot">
				<view class="foot_btn text-center main-bg-color color-fff fs-40 fs-600" @tap="handleSubmit">
					完成
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {getDate} from "../../static/js/getdate.js";
	import Loading from "../../components/loading.vue";
	export default {
		data() {
			return {
				show:{
					main:false
				},
				user_data:{},
				input_data:{
					image:'',
					nickname:'',
					birthday:'',
					gender:2,
					weight:40,
					height:170,
					hobby:'',
					education:'',
					marry:'',
					salary:''
				},
				scroll:{
					toView:null,
				},
				gender:['../../static/src/nv-1.png', '../../static/src/nan-1.png'],
				gender_index: 1,
				body_lis:{
					weightLeft:30,
					weightWidth: 55,
					heightLeft:230,
					heightWidth: 255
				},
				education:[
					{name:'高中及一下'}, 
					{name:'专科'}, 
					{name:'本科'}, 
					{name:'硕士'}, 
					{name:'博士'}
					],
				education_index:1,
				marry:[
					{name:'保密'},
					{name:'单身'},
					{name:'未婚'},
					{name:'已婚'},
				],
				marry_index:0,
				salary:[
					{name:'5000以下'},
					{name:'5001-10000'},
					{name:'10001-20000'},
					{name:'20001以上'}					
				],
				salary_index:1
			}
		},
		onLoad() {
			this.user_data = uni.getStorageSync('user');
			console.log(this.user_data);
			this.input_data.image = this.user_data.avatarSrc;
			this.input_data.nickname = this.user_data.nickname
			this.input_data.gender = this.user_data.gender;
			this.input_data.birthday = this.user_data.birthday;
			this.input_data.hobby = this.user_data.hobby;
			this.show.main = true;
		},		
		methods: {
			handleBack() {
				uni.navigateBack({
					delta: 1,
					animationType: 'pop-out',
					animationDuration: 500
				})
			},
			handleChangeAvatar(){
				var _this = this;
				uni.chooseImage({
					count:1,
					sizeType:['compressed'],
					sourceType:['ablum'],
					crop:{width:1000, height:1000},
					success: (res) => {
						console.log(JSON.stringify(res.tempFilePaths[0]));
						_this.input_data.image = res.tempFilePaths[0];
					}
				})
			},
			handleChangeGender(index){
				this.input_data.gender = index;
			},
			handleChangeDate(e){
				this.input_data.birthday = e.detail.value;
			},
			handleChangeWeight(e){
				var endX = e.changedTouches[0].pageX;
				if(endX > 30 && endX < 430){
					this.body_lis.weightLeft = endX;
					this.body_lis.weightWidth = endX + 10;
					this.input_data.weight = Math.floor(endX / 3.6 + 32);
				}
			},
			handleChangeHeight(e){
				var endX = e.changedTouches[0].pageX;
				if(endX > 30 && endX < 430){
					this.body_lis.heightLeft = endX;
					this.body_lis.heightWidth = endX + 10;
					this.input_data.height = Math.floor(endX / 6.6 + 136);
				}
			},
			handleChangeEducation(e){
				this.education_index = e.detail.value;
			},
			handleChangeMarry(e){
				this.marry_index = e.detail.value;
			},
			handleChangeSalary(e){
				this.salary_index = e.detail.value;
			},
			handleSubmit(){
				this.input_data.education = this.education[this.education_index].name;
				this.input_data.marry = this.marry[this.marry_index].name
				if(this.salary_index === 0){
					this.input_data.salary = 5000;
				}else if(this.salary_index === 1){
					this.input_data.salary = 10000;
				}else if(this.salary_index === 2){
					this.input_data.salary = 20000
				}else{
					this.input_data.salary = 20001
				}
				
				uni.uploadFile({
					url:this.url+'/users/update',
					filePath:this.input_data.image,
					name:'avatarSrc',
					formData:{
						phoneNum:this.user_data.phoneNum,
						nickname:this.input_data.nickname,
						birthday:this.input_data.birthday,
						gender:this.input_data.gender,
						weight:this.input_data.weight,
						height:this.input_data.height,
						hobby:this.input_data.hobby,
						education:this.input_data.education,
						marry:this.input_data.marry,
						salary:this.input_data.salary
					},
					success: (res) => {
						let json_data = JSON.parse(res.data);
						console.log(json_data);
					}
					
				})
			}
		},
		computed:{
			startTime(){
				return getDate('start');
			},
			endTime(){
				return getDate('end');
			}
		},
		components:{
			'loading':Loading
		}
	}
</script>

<style>
	@import url("../../static/css/info.css");
</style>