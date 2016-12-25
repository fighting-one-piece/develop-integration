!function() {
	laydate.skin('molv'); //切换皮肤，请查看skins下面皮肤库
}();
laydate({
	elem : '#startData',
	format : 'YYYY-MM-DD',
	festival : true, //显示节日
});
laydate({
	elem : '#endData',
	format : 'YYYY-MM-DD',
	destival : true,
});