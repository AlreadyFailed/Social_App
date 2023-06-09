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
				<view class="body_avatar">
					<image :src="user_data.avatarSrc"></image>
				</view>
				<view class="body_lis">
					<view class="basic  fs-40">
						用户名
					</view>
					<input type="text" maxlength="20" placeholder="设置用户名,可用于登陆" placeholder-style="font-size:35upx;font-weight:100">
				</view>
				<view class="body_lis">
					<view class="basic fs-40">
						昵称
					</view>
					<input type="text" maxlength="15" placeholder="填写昵称" placeholder-style="font-size:35upx;font-weight:100">
				</view>
				<view class="body_lis">
					<view class="basic  fs-40">
						生日
					</view>
					<view class="date fs-600 fs-40">
						<picker mode="date" 
						 @change="handleChangeDate"
						 :start="startTime"
						 :end="endTime"
						 :value="body_lis.date">
							<view>{{body_lis.date}}</view>
						</picker>
					</view>
				</view>
				<view class="body_lis">
					<view class="basic fs-40">
						性别
					</view>
					<view :class="['gender_img',{'main-bg-green': index === gender_index}]" 
					v-for="(item, index) in 2" :key="index"
					@tap="handleChangeGender(index)">
						<image :src="gender[index]" ></image>
					</view>
				</view>
				<view class="body_lis">
					<view class="basic fs-40">
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
								{{body_lis.weight}}kg
							</view>
						</view>
					</view>
				</view>
				<view class="body_lis">
					<view class="basic fs-40">
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
								{{body_lis.height}}cm
							</view>
						</view>
					</view>
				</view>
				<view class="body_lis">
					<view class="basic fs-40">
						爱好
					</view>
					<input type="text" maxlength="20" placeholder-style="font-size:35upx;font-weight:100" placeholder="如:打球,看书,旅游">
				</view>
				<view class="body_lis">
					<view class="basic fs-40">
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
					<view class="basic fs-40">
						情感
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
					<view class="basic fs-40">
						收入
					</view>
					<view class="picker_selector fs-600 fs-40">
						<picker mode="selector" 
						:range="salary" 
						range-key="name"
						:value="salary_index"
						@change="handleChangeSalary">
							<view>{{salary[salary_index].name}} 元</view>
						</picker>
					</view>
				</view>
			</scroll-view>
			
			<!-- Foot -->
			<view class="info_foot">
				<view class="foot_btn text-center main-bg-color color-fff fs-40 fs-600">
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
				scroll:{
					toView:null,
				},
				gender:['../../static/src/nan-1.png', '../../static/src/nv-1.png'],
				gender_index:1,
				body_lis:{
					date:'2023-6-8',
					weight:40,					
					weightLeft:30,
					weightWidth: 55,
					height:170,
					heightLeft:230,
					heightWidth: 255
				},
				education:[
					{name:'高中及以下'}, 
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
					{name:'2000以下'},
					{name:'2000-5000'},
					{name:'5000-10000'},
					{name:'10000-20000'},
					{name:'20000以上'}					
				],
				salary_index:1
			}
		},
		onLoad() {
			// uni.request({
			// 	url:this.url +
			// })
		},		
		methods: {
			handleBack() {
				uni.navigateBack({
					delta: 1,
					animationType: 'pop-out',
					animationDuration: 500
				})
			},
			handleChangeGender(index){
				this.gender_index = index;
			},
			handleChangeDate(e){
				this.date = e.detail.value;
			},
			handleChangeWeight(e){
				var endX = e.changedTouches[0].pageX;
				if(endX > 30 && endX < 430){
					this.body_lis.weightLeft = endX;
					this.body_lis.weightWidth = endX + 10;
					this.body_lis.weight = Math.floor(endX / 3.6 + 32);
				}
			},
			handleChangeHeight(e){
				var endX = e.changedTouches[0].pageX;
				if(endX > 30 && endX < 430){
					this.body_lis.heightLeft = endX;
					this.body_lis.heightWidth = endX + 10;
					this.body_lis.height = Math.floor(endX / 6.6 + 136);
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