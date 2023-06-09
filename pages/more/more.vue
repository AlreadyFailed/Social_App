<template>
    <view>
        <picker mode="selector" 
		:range="array" 
		:value="index" 
		range-key="na" 
		@change="handleChange">
        	<view>{{array[index].name}}</view>
        </picker>
    </view>
</template>
<script>
export default {
    data() {
        return {
            array: [{ na: '中国' }, { name: '美国' }, { name: '巴西' }, { name: '日本' }],
            index: 2,
        };
    },
    methods: {
        handleChange: function(e) {
            this.index = e.detail.value;
        }
    }
};
</script>

<style>

</style>
