<template>
	<view>
		<!-- 加载标签，用于每个页面的初始化加载，只有当请求发送完毕，且数据返回之后，才会消失 -->
		<loading v-if="!show.main"></loading>
		
		<!-- 正文标签，页面的主要内容 -->
		<scroll-view :scroll-into-view="topItem" scroll-with-animation class="scroll-cont" scroll-y="true"
			@scroll="handleScroll" v-if="show.main">			
			<!-- 定位标签，用于scroll返回顶部 -->
			<view id="top"></view>
			
			<!-- 正文，头部 -->
			<view class="head">
				<!-- 头部：图片 -->
				<view class="head_img">
					<image src="../../static/src/head_img.jpg" mode=""></image>
				</view>
				<!-- 头部：标题 -->
				<view class="head_title">
					<view class="head_title_column" v-for="(item, index) in title" :key="index">
						<image :src="item" mode=""></image>
						<view class="head_title_text fs-28 text-center color-6362">
							{{index}}
						</view>
					</view>
				</view>
			</view>
			
			<!-- 正文：内容体 -->
			<view class="body">
				<!-- 内容体：直播板块 -->
				<view class="body_live">
					<view class="body_live_title fs-600 color-6362">
						直播交友
					</view>
					<index-live 
					live_online="../../static/src/live-online.png" 
					live="true" :info="[user_Data[0].address, '|',user_Data[0].age, '|',user_Data[0].education]" 
					:gender="user_Data[0].gender"
					:nickname="user_Data[0].nickname"
				></index-live>
				</view>
				
				<!-- 内容体：推荐板块 -->
				<view class="body_recommend">
					<view class="body_recommend_title fs-600 color-6362">
						每日推荐
					</view>
					<view v-for="(item, index) in user_Data.length" :key="index">
						<index-recommend :nickname="user_Data[index].nickname"
							:info="[user_Data[index].address, '|',user_Data[index].age, '|',user_Data[index].education]"
							:gender="user_Data[index].gender" :work="work"
							:avatar_src="user_Data[index].avatarSrc">
						</index-recommend>
					</view>
				</view>
			</view>
			
			<!-- 返回顶部按钮 -->
			<view class="goTop" @tap="goTop" v-if="show.top">
				<image src="../../static/src/goTop.png" mode=""></image>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	import Index_live from "../../components/index/index-live.vue";
	import Index_recommend from '../../components/index/index-recommend.vue';
	import Loading from "../../components/loading.vue";
	import {
		norData,
		getAge
	} from "../../static/js/index.js";
	export default {
		data() {
			return {
				// 头部标题的文本
				title: {
					'直播': '../../static/src/zhibo.png',
					'密语': '../../static/src/miyu.png',
					'短视频': '../../static/src/shiping.png',
					'热门动态': '../../static/src/dongtai.png',
				},
				
				// 页面预加载的数据
				user_Data: {},
				
				// 个人信息：工作信息
				work: ["销售总监", "168cm", "3-5K"],
				
				// 展示数组：用于控制html中需要显示和隐藏的标签
				show: {
					top: false,
					main:false
				},
				
				// 用于返回顶部的变量
				topItem: ''

			}
		},
		
		// 页面预加载
		onLoad() {
			
			// get请求：当请求成功时，加载标签隐藏，显示正文
			uni.request({
				url: this.url + "/users",
				method:"GET",
				success: (res) => {
					
					// 将返回的数据进行筛选，并随机排序。
					this.user_Data = norData(res.data.data);
					this.show.main = true;
					console.log(this.user_Data);
				},
				fail: () => {
					// 请求失败，显示网络出错
					uni.showToast({
						title:"网络信号不好",
						icon:"loading",
						duration:3000
					})
				}
			})
		},
		methods: {
			
			// 返回顶部
			handleScroll(ev) {
				// 获取当前页面的滑动值，并判断是否达到条件（滑动超过400像素）
				let scrollTop = ev.detail.scrollTop;
				// 显示返回按钮
				this.show.top = scrollTop > 400;
				// 将返回顶部变量置空，防止失效。
				this.topItem = '';
			},
			goTop() {
				// 返回的定位
				this.topItem = "top";
			}
		},
		// 导入组件
		components: {
			'index-live': Index_live,
			'index-recommend': Index_recommend,
			'loading':Loading
		}
	}
</script>

<style>
	@import "../../static/css/index.css";
</style>