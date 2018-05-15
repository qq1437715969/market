

$(function(){
	$("#regBtn").click(function(){
		regist();
	});
	
})

function regist(){
	var userName = $("#username").val();
	var pass = $("#password").val();
	var rePass = $("#rePass").val();
	checkRegistParams(userName,pass,rePass)
	var random = Math.ceil(Math.random()*100);
	var key = getKey(random);
	var encrypt = new JSEncrypt();
	encrypt.setPublicKey(key);
	userName = encrypt.encrypt(userName);
	pass = encrypt.encrypt(pass);
	$.ajax({
		type:"post",
		data: {"userName":userName,"pass":pass,"random":random},
		url:"/user/regist.do",
		async: true,
		success: function(res){
			
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
