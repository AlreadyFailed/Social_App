<template>
	<view>
		<loading v-if="!show.main"></loading>
		<scroll-view :scroll-into-view="topItem" scroll-with-animation class="scroll-cont" scroll-y="true"
			@scroll="handleScroll" v-if="show.main">
			<view id="top"></view>
			<view class="head">
				<view class="head_img">
					<image src="../../static/src/head_img.jpg" mode=""></image>
				</view>
				<view class="head_title">
					<view class="head_title_column" v-for="(item, index) in title" :key="index">
						<image :src="item" mode=""></image>
						<view class="head_title_text fs-28 text-center color-6362">
							{{index}}
						</view>
					</view>
				</view>
			</view>
			<view class="body">
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
				<view class="body_recommend">
					<view class="body_recommend_title fs-600 color-6362">
						每日推荐
					</view>
					<view v-for="(item, index) in user_Data.length" :key="index">
						<index-recommend :nickname="user_Data[index].nickname"
							:info="[user_Data[index].address, '|',user_Data[index].age, '|',user_Data[index].education]"
							:gender="user_Data[index].gender" :work="work">
						</index-recommend>
					</view>
				</view>
			</view>
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
				title: {
					'直播': '../../static/src/zhibo.png',
					'密语': '../../static/src/miyu.png',
					'短视频': '../../static/src/shiping.png',
					'热门动态': '../../static/src/dongtai.png',
				},
				user_Data: {
					// nickname:'',
					// gender:0,
					// address:'',
					// age:0,
					// education:''
				},
				work: ["销售总监", "168cm", "3-5K"],
				show: {
					top: false,
					main:false
				},
				topItem: ''

			}
		},
		onReady() {
			uni.request({
				url: this.url + "/users",
				method:"GET",
				success: (res) => {
					console.log(res.statusCode);
					this.user_Data = norData(res.data.data);
					this.show.main = true;
					console.log(this.user_Data);
				},
				fail: () => {
					uni.showToast({
						title:"网络信号不好",
						icon:"loading",
						duration:3000
					})
				}
			})
		},
		methods: {
			handleScroll(ev) {
				let scrollTop = ev.detail.scrollTop;
				this.show.top = scrollTop > 400;
				this.topItem = '';
			},
			goTop() {
				this.topItem = "top";
			}
		},
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