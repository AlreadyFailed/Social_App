<template>
	<view>
		<loading v-if="!show.main"></loading>
		<!-- 个人资料详情页 -->
		<view class="info" v-if="show.main">
			<!-- 头部 -->
			<view class="info_head">
				<!-- 关闭当前页，并返回上一页标签 -->
				<view class="back" @tap="handleBack">
					<image src="../../static/xiangzuojiantou.png"></image>
				</view>
				
				<!-- 头部：标题 -->
				<view class="head_title fs-600 color-6362">
					完善资料
				</view>
			</view> 
			
			<!-- 内容体 -->
			<scroll-view scroll-y="true" class="info_body" :scroll-into-view="scroll.toView">
				<!-- 修改头像 -->
				<view class="body_avatar" @tap="handleChangeAvatar">
					<image :src="input_data.image"></image>
				</view>
				<!-- 修改昵称 -->
				<view class="body_lis">
					<view class="basic">
						昵称
					</view>
					<input type="text" maxlength="15" v-model="input_data.nickname" placeholder="填写昵称" placeholder-style="font-size:35upx; font-weight:100;">
				</view>
				<!-- 修改生日 -->
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
				<!-- 修改性别 -->
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
				<!-- 修改体重 -->
				<view class="body_lis">
					<view class="basic">
						体重
					</view>
					<view class="slide">
						<view class="slide_line">
							<view class="slide_line_value" :style="{'width' : body_lis.weightLeft + 10 + 'upx'}">								
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
				<!-- 修改身高 -->
				<view class="body_lis">
					<view class="basic">
						身高
					</view>
					<view class="slide">
						<view class="slide_line">
							<view class="slide_line_value" :style="{'width' : body_lis.heightLeft + 10 + 'upx'}">								
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
				<!-- 修改爱好 -->
				<view class="body_lis">
					<view class="basic">
						爱好
					</view>
					<input type="text" maxlength="20" v-model="input_data.hobby"  placeholder-style="font-size:35upx;font-weight:100;" placeholder="如:打球,看书,旅游">
				</view>
				<!-- 修改学历 -->
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
				<!-- 修改感情状态 -->
				<view class="body_lis">
					<view class="basic">
						感情状态
					</view>
					<view class="picker_selector fs-600 fs-40">
						<picker mode="selector" 
						:range="marry" 
						:value="marry_index"
						range-key="name"
						@change="handleChangeMarry">
							<view>{{marry[marry_index].name}}</view>
						</picker>
					</view>
				</view>
				<!-- 修改收入 -->
				<view class="body_lis">
					<view class="basic">
						收入
					</view>
					<view class="picker_selector fs-600 fs-40">
						<picker mode="selector" 
						:range="salary" 						
						:value="salary_index"
						range-key="name"
						@change="handleChangeSalary">
							<view>{{salary[salary_index].name}}</view>
						</picker>
					</view>
					
					
				</view>
			</scroll-view>
			
			<!-- 提交 -->
			<view class="info_foot">
				<view class="foot_btn text-center main-bg-color color-fff fs-40 fs-600" @tap="handleSubmit">
					完成
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	// 导入所需组件和函数
	import {getDate} from "../../static/js/getdate.js";
	import Loading from "../../components/loading.vue";
	import {getIndex, getValue} from "../../static/js/getIndex.js"
	export default {
		data() {
			return {
				// 显示数组，用于控制html中需要显示和隐藏的标签
				show:{
					main:false
				},
				// 保存后端的返回数据
				user_data:{},
				// 保存前端用户修改的参数，并将其传到后端
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
				// 返回顶部
				scroll:{
					toView:null,
				},
				// 性别src数组，保存男女图片
				gender:['../../static/src/nv-1.png', '../../static/src/nan-1.png'],
				// 确定选择的下标
				gender_index: 1,
				// 身高和体重的滑动值和摆动条位置
				body_lis:{
					weightLeft:30,
					weightWidth: 55,
					heightLeft:230,
					heightWidth: 255
				},
				// selector选择器的数组
				education:[
					{name:'高中及以下'}, 
					{name:'专科'}, 
					{name:'本科'}, 
					{name:'硕士'}, 
					{name:'博士'}
					],
				// 下标
				education_index:1,
				marry:[
					{name:'保密'},
					{name:'单身'},
					{name:'未婚'},
					{name:'已婚'}
				],
				marry_index:0,
				salary:[
					{name:'5000以下'},
					{name:'5000-9999'},
					{name:'10000-19999'},
					{name:'20000以上'}					
				],
				salary_index:1,
				duplicateImg:false
			}
		},
		// 初始化页面
		onLoad() {
			// 从本地缓存中拿到请求好的数据
			this.user_data = uni.getStorageSync('user');
			console.log(this.user_data);
			this.input_data.image = this.user_data.avatarSrc;
			this.input_data.nickname = this.user_data.nickname;
			this.input_data.gender = this.user_data.gender;
			this.input_data.birthday = this.user_data.birthday;
			this.input_data.hobby = this.user_data.hobby;
			this.input_data.weight = this.user_data.weight;
			this.input_data.height = this.user_data.heigth;
			
			// 计算下标和滑动值
			this.body_lis.weightLeft = getValue(this.user_data.weight, 1);
			this.body_lis.heightLeft = getValue(this.user_data.heigth, 2);
			this.education_index = getIndex(this.user_data.education, 1);
			this.marry_index = getIndex(this.user_data.marry, 2);
			this.salary_index = getIndex(this.user_data.salary, 3);
			this.show.main = true;
		},		
		methods: {
			// 返回上一页
			handleBack() {
				uni.navigateBack({
					delta: 1,
					animationType: 'pop-out',
					animationDuration: 500
				})
			},
			// 上传图片
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
						this.duplicateImg = true;
					}
				})
			},
			// 选择性别
			handleChangeGender(index){
				this.input_data.gender = index;
			},
			// 修改生日
			handleChangeDate(e){
				this.input_data.birthday = e.detail.value;
			},
			// 修改体重
			handleChangeWeight(e){
				var endX = e.changedTouches[0].pageX;
				if(endX > 30 && endX < 430){
					this.body_lis.weightLeft = endX;
					this.body_lis.weightWidth = endX + 10;
					this.input_data.weight = Math.floor(endX / 3.6 + 32);
				}
			},
			// 修改身高
			handleChangeHeight(e){
				var endX = e.changedTouches[0].pageX;
				if(endX > 30 && endX < 430){
					this.body_lis.heightLeft = endX;
					this.body_lis.heightWidth = endX + 10;
					this.input_data.height = Math.floor(endX / 6.6 + 136);
				}
			},
			// 修改学历
			handleChangeEducation(e){
				this.education_index = e.detail.value;
			},
			// 修改感情状态
			handleChangeMarry(e){
				this.marry_index = e.detail.value;
			},
			// 修改薪资
			handleChangeSalary(e){
				this.salary_index = e.detail.value;
			},
			// 提交
			handleSubmit(){
				this.input_data.education = this.education[this.education_index].name;
				this.input_data.marry = this.marry[this.marry_index].name;
				if(this.salary_index === 0){
					this.input_data.salary = 4999;
				}else if(this.salary_index === 1){
					this.input_data.salary = 9999;
				}else if(this.salary_index === 2){
					this.input_data.salary = 19999;
				}else{
					this.input_data.salary = 20000;
				}
				// 上传文件接口
				uni.uploadFile({
					url:this.url+'/users/update',
					filePath:this.duplicateImg ? this.input_data.image : '',
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
					// 成功之后将后端返回的信息转为json对象，并展示
					success: (res) => {
						let json_data = JSON.parse(res.data);
						console.log(json_data);
						console.log(this.input_data.image);
					}					
				})
				
			}
		},
		computed:{
			// 计算生日范围
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