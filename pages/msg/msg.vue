<template>
	<view>
		<view class="body">
			<view class="body_inherent">
				<view :class="['body_inherent_lis', {'main-bg-color':handleColor(event,index)}]" :index="index" v-for="(item, index) in 3" :key="index" >
					<view class="inheren_lis_img">
						<image :src="src[index]" mode=""></image>
					</view>
					<view class="inheren_lis_title color-6362">
						{{text[index]}}
					</view>
					<view class="inheren_lis_arrow">
						<image src="../../static/src/xiangyoujiantou.png" mode=""></image>
					</view>
				</view>
			</view>
			<view class="body_session">
				
			</view>
		</view>
	</view>
</template>

<script>
	
	export default {
		data() {
			return {
				src:['../../static/src/new_pengyou.png', '../../static/src/yonhu.png', '../../static/src/qunliao.png'],
				text:['添加好友', '仅聊天的会话', '群聊']
			}
		},
		methods:{
			handleColor(e, index){
				console.log(index);
				return true;
			}
		}
	}
</script>

<style>
@import url("../../static/css/msg.css");
</style>
