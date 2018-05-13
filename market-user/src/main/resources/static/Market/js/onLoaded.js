













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
	$.ajax({
		type:"get",
		data:{"foot":referStr,"info":info,"sign":sign,"accessTime":time,"mobileType":1},
		url:"http://127.0.0.1:26888/user/registPre.do",
		async:true,
		success: function(res){
			
		}
	});
})






