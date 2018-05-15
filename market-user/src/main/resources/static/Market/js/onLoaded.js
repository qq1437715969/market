













$(function(){
	window.baseInfo = comInfo;
});

$(function(){
	var head = {"typ":"JWT","alg":"SHA256"};
	var userInfo = {"ipAddr":comInfo};
	head = JSON.stringify(head);
	userInfo = Base.encode(JSON.stringify(userInfo));
	head64 = Base.encode(head.toString());
	var info = head64+"."+userInfo;
	console.log(info);
	var salt = uuid(8,16);
	var sign = hex_md5_num(info+salt,5);
	sign = sign+"."+salt;
	console.log("signMsg:"+sign);
	var time = new Date().getTime();
	var referStr = {"refer":window.location.href};
	referStr = Base.encode(JSON.stringify(referStr));
	var flag = false;
	$.ajax({
		type:"get",
		data:{"foot":referStr,"info":info,"sign":sign,"accessTime":time,"mobileType":1},
		url:"http://127.0.0.1:26888/user/SafeAccessPre.do",
		async:false,
		success: function(res){
			flag = true;
		}
	});
	if(flag){
		$.ajax({
			type:"get",
			data:{"foot":referStr,"info":info,"sign":sign,"accessTime":time,"mobileType":1},
			url:"http://127.0.0.1:26888/userKeys/userViewKeys.do",
			async:true,
			success: function(res){
				console.log(res);
				res = res.substring(1,res.length-1);
				res = Base.decode(res);
				console.log(res);
				var keysMap =  new Map();
				res = JSON.parse(res);
				console.log(res.code);
				if(res.code==1){
					var datas = res.data;
					for(var i = 0 ;i<datas.length;i++){
						keysMap.put(datas[i].random,datas[i].publicKey+"."+datas[i].endTime);
					}
					setSafeKeys2Storage();
				}else{
					console.log("可以退出了");
				}
			}
		});
	}
})






