

$(function(){
	$("#username").blur(function(){
		checkExist();
	});
	
	var checkSubmit = false;
	$("#regBtn").click(function(){
		if(!checkSubmit){
			regist();
		}
	});
	
})

function checkExist(){
	var userName = Base.encode($("#username").val());
	$.post("/user/checkExist.do",{'userName':userName},function(res){
		if(res.code!=0){
			alert(res.msg);
		}
	},"JSON");
}

function regist(){
	var userName = $("#username").val();
	var pass = hex_md5_num($("#password").val()+"www.market.com",3);
	var rePass = hex_md5_num($("#rePass").val()+"www.market.com",3);
	if(!checkRegistParams(userName,pass,rePass)){
		return;
	}
	var data = "{'userName':'"+userName+"','pass':'"+pass+"'}";
	var sysInfo = getSysInfo();
	var random = Math.ceil(Math.random()*100);
	var key = getKey(random);
	var encrypt = new JSEncrypt();
	encrypt.setPublicKey(key);
	var mydata = encrypt.encrypt(data)+"."+Base.encode(random);
	var salt = uuid(8,16);
	var sign = hex_md5_num(mydata+salt,random%6);
	mydata = mydata+"."+salt;
	var ipInfo = Base.encode(JSON.stringify(ipAddrInfo));
	sysInfo = Base.encode(JSON.stringify(sysInfo));
	$.ajax({
		type:"post",
		data: {"info":mydata,"sign":sign,"imgYzm":'123456',"ipInfo":ipInfo,"sysInfo":sysInfo},
		url:"/user/regist.do",
		async: true,
		success: function(res){
			console.log(res);
			checkSubmit = true;
		}
	});
	
}

function getKey(num){
	num = ""+num%6;
	if(num==0){
		num = 1;
	}
	var keysMap = getSafeKeys();
	keysMap = Base.decode(keysMap);
	keysMap = json2Map(keysMap);
	return keysMap.get(num);
}

function json2Map(keysMap){
	var map = new Map();
	datas = JSON.parse(keysMap);
	for(var i = 0 ;i<datas.length;i++){
		map.put(datas[i].random,datas[i].publicKey);
	}
	return map;
}

function checkRegistParams(userName,pass,rePass){
	if(checkIsStr(userName)&&checkIsStr(pass)&&checkIsStr(rePass)){
	}else{
		alert("必要参数缺失");
		return false;
	}
	if(userName.length<3||userName.length>18){
		alert("用户名长度必须在3到10之间");
		return false;
	}
	if(!checkPwd(pass,rePass)){
		return false;
	}
	return true;
}


function checkIsStr(str){
	if(null==str||undefined==str||str==''){
		return false;
	}
	return true;
}

function checkPwd(pass,rePass){
	if(pass.length<6||pass>18){
		alert("密码长度太短或太长");
		return false;
	}
	if(pass==rePass){
		return true;
	}
	return false;
}
