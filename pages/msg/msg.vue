<template>
	<view>
		<loading v-if="!show.main"></loading>
		<view class="body" v-if="show.main">
			<view class="body_inherent">
				<view class="" v-for="(item, index) in 3" :key="index">
					<inherentline :text="text[index]" :img="src.inherent[index]" :showArrow="true"></inherentline>
				</view>
			</view>
			<view class="body_session">
				<view class="" v-for="(item, index) in 3" :key="index">
					<!-- <sessionline :nickname="user_data[index].nickname" :img="user_data[index].avatarSrc" ></sessionline> -->
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import inherentLine from"../../components/msg/inherentLine.vue";
	import sessionLine from "../../components/msg/sessionLine.vue";
	import Loading from "../../components/loading.vue"
	export default {
		data() {
			return {
				show:{
					main:false
				},
				src:{
					inherent:['../../static/src/new_pengyou.png', '../../static/src/yonhu.png', '../../static/src/qunliao.png'],
					session:['../../static/src/avatar_1.jpg']
				},
				user_data:{},
				text:['添加好友', '仅聊天的会话', '群聊'],
				flag:[false, false, false]
			}
		},
		onLoad(){
			uni.request({
				url:this.url + "/users/msg",
				method:"GET",
				success:(res)=>{
					console.log(res.data);
					this.show.main = true;
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
			}
		},
		components:{
			'inherentline':inherentLine,
			'sessionline':sessionLine,
			'loading':Loading
		}
	}
</script>

<style>
@import url("../../static/css/msg.css");
</style>
