<template>
	<view>
		<loading v-if="!show.main"></loading>
	</view>
</template>

<script>
	import Loading from "../../components/loading.vue"
	export default {
		data() {
			return {
				show:{
					main:false
				}
			}
		},
		onReady(){
			this.show.main = true
		},
		methods: {
			
		},
		components:{
			'loading' : Loading
		}
	}
</script>

<style>

</style>
