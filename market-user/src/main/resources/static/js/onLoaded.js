













$(function(){
	window.baseInfo = comInfo;
});


$(function(){
	var head = {"typ":"JWT","alg":"HS256"};
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
	$.ajax({
		type:"get",
		data:{"info":info,"sign":sign,"accessTime":time},
		url:"http://localhost:26888/user/registPre.do",
		async:true,
		success: function(res){
			
		}
	});
})






