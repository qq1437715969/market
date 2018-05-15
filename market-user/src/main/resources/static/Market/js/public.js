var sysInfo = '';
var ipAddrInfo = '';
var ipAddrBak = '';
var comInfo = '';
safeKeys = 'SafeKeysMap';
myStorage = window.localStorage;

var basePath = "http://www.Market.com/Market/";

String.prototype.replaceAll  = function(s1,s2){     
    return this.replace(new RegExp(/s1/,"gm"),s2);     
}  

function set2myStorage(key,val){
	myStorage.setItem(key,val);
}

function setSafeKeys2Storage(val){
	myStorage.setItem(safeKeys,val);
}

function getMyStoragr(key){
	return myStorage.getItem(key);
}

function getSafeKeys(){
	return myStorage.getItem(safeKeys);
}

window.IPCallBack = function IPCallBack(data) {
	return data;
}

<!-- Map相关的操作 -->
function Map(){
this.container = new Object();
}

Map.prototype.put = function(key, value){
this.container[key] = value;
}

Map.prototype.get = function(key){
return this.container[key];
}


Map.prototype.keySet = function() {
var keyset = new Array();
var count = 0;
for (var key in this.container) {
// 跳过object的extend函数
if (key == 'extend') {
continue;
}
keyset[count] = key;
count++;
}
return keyset;
}


Map.prototype.size = function() {
var count = 0;
for (var key in this.container) {
// 跳过object的extend函数
if (key == 'extend'){
continue;
}
count++;
}
return count;
}

Map.prototype.remove = function(key) {
delete this.container[key];
}

function getSysInfo(){
	var BrowserDetect = {
        init: function () {
            this.browser = this.searchString(this.dataBrowser) || "An unknown browser";
            this.version = this.searchVersion(navigator.userAgent)
                || this.searchVersion(navigator.appVersion)
                || "an unknown version";
            this.OS = this.searchString(this.dataOS) || "an unknown OS";
        },
        searchString: function (data) {
            for (var i=0;i<data.length;i++)    {
                var dataString = data[i].string;
                var dataProp = data[i].prop;
                this.versionSearchString = data[i].versionSearch || data[i].identity;
                if (dataString) {
                    if (dataString.indexOf(data[i].subString) != -1)
                        return data[i].identity;
                }
                else if (dataProp)
                    return data[i].identity;
            }
        },
        searchVersion: function (dataString) {
            var index = dataString.indexOf(this.versionSearchString);
            if (index == -1) return;
            return parseFloat(dataString.substring(index+this.versionSearchString.length+1));
        },
        dataBrowser: [
        	{
                string: navigator.userAgent,
                subString: "Edge",
                identity: "Edge"
            },
            {
                string: navigator.userAgent,
                subString: "Chrome",
                identity: "Chrome"
            },
            {     string: navigator.userAgent,
                subString: "OmniWeb",
                versionSearch: "OmniWeb/",
                identity: "OmniWeb"
            },
            {
                string: navigator.vendor,
                subString: "Apple",
                identity: "Safari",
                versionSearch: "Version"
            },
            {
                prop: window.opera,
                identity: "Opera"
            },
            {
                string: navigator.vendor,
                subString: "iCab",
                identity: "iCab"
            },
            {
                string: navigator.vendor,
                subString: "KDE",
                identity: "Konqueror"
            },
            {
                string: navigator.userAgent,
                subString: "Firefox",
                identity: "Firefox"
            },
            {
                string: navigator.vendor,
                subString: "Camino",
                identity: "Camino"
            },
            {        // for newer Netscapes (6+)
                string: navigator.userAgent,
                subString: "Netscape",
                identity: "Netscape"
            },
            {
                string: navigator.userAgent,
                subString: "MSIE",
                identity: "Explorer",
                versionSearch: "MSIE"
            },
            {
                string: navigator.userAgent,
                subString: "Gecko",
                identity: "Mozilla",
                versionSearch: "rv"
            },
            {         // for older Netscapes (4-)
                string: navigator.userAgent,
                subString: "Mozilla",
                identity: "Netscape",
                versionSearch: "Mozilla"
            }
        ],
        dataOS : [
            {
                string: navigator.platform,
                subString: "Win",
                identity: "Windows"
            },
            {
                string: navigator.platform,
                subString: "Mac",
                identity: "Mac"
            },
            {
                string: navigator.userAgent,
                subString: "iPhone",
                identity: "iPhone/iPod"
            },
            {
                string: navigator.platform,
                subString: "Linux",
                identity: "Linux"
            }
        ]
   
    };
    BrowserDetect.init();
    return { os : BrowserDetect.OS, browser : BrowserDetect.browser };
}

function createAccessSalt(){
	return uuid(8,16);	
}


function getSysBroInfo(){
	var sysInfo = navigator.userAgent;
	if(null!=sysInfo){
		
	}
}

function uuid(len, radix) {
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    var uuid = [], i;
    radix = radix || chars.length;
 
    if (len) {
      // Compact form
      for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
    } else {
      // rfc4122, version 4 form
      var r;
 
      // rfc4122 requires these characters
      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
      uuid[14] = '4';
 
      // Fill in random data. At i==19 set the high bits of clock sequence as
      // per rfc4122, sec. 4.1.5
      for (i = 0; i < 36; i++) {
        if (!uuid[i]) {
          r = 0 | Math.random()*16;
          uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
        }
      }
    }
    return uuid.join('');
}

function getXhr(){
	return createXhr();
}

function createXhr() {
        if (window.XMLHttpRequest) {
            return new XMLHttpRequest();
        } else {
            window.XMLHttpRequest = function() {
                try {
                    return new ActiveXObject("Msxml2.XMLHTTP.6.0");
                } catch (e1) {
                    try {
                        return new ActiveXObject("Msxml2.XMLHTTP.3.0");
                    } catch (e2) {
                        throw new Error("XMLHttpRequest is not supported");
                    }
                }
            }
        }
}

function setIpInfo(){
	ipAddrInfo = returnCitySN;
}

function setIpInfoBak(){
	console.log(window.IPCallBack);
}

function getComInfo(){
	var sys = true;
	var ip = true;
	sysInfo = getSysInfo();
	if(null==sysInfo||sysInfo==undefined||sysInfo==''){
		sys = false;
		sysInfo = '';
	}
	if(null==ipAddrInfo||ipAddrInfo==undefined||ipAddrInfo==''){
		ip = false;
		ipAddr = '';
	}
	var state = false;
	var baseInfo = null;
	var map = new Map();
	if(sys||ip){
		state = true;
		baseInfo = sysInfo+ipAddrInfo;
		map.put('sys',sysInfo);
		map.put('ip',ipAddrInfo);
		map.put('state',state);
		var json = JSON.stringify(map.container);
		console.log(json);
		return json;
	}else{
		map.put('state',state);
		map.put('sys',uuid(10,16));
		var json = JSON.stringify(map.container);
		console.log(json);
		return json;
	}
}

	
