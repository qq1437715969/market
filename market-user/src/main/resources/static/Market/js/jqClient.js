var newscript = document.createElement('script');  
newscript.setAttribute('type','text/javascript');  
newscript.setAttribute('src','js/Map.js');  
var head = document.getElementsByTagName('head')[0];  
head.appendChild(newscript);  

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

function getIpAddr(){
// var headMap = new Map();
// 	headMap.put("Referrer Policy","no-referrer-when-downgrade");
// 	headMap.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
// 	headMap.put("Accept-Encoding","gzip, deflate");
// 	headMap.put("Accept-Language","zh-CN,zh;q=0.9");
// 	headMap.put("Cache-Control","max-age=0");
// 	headMap.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
	
//	var xhr = createXhr();
	$.post("http://pv.sohu.com/cityjson?ie=utf-8",null,function(res){
		return res;
	});
//	xhr.open("POST","http://pv.sohu.com/cityjson?ie=utf-8",false);
	// var formData = new FormData();
	//	xhr.setRequestHeader(headMap);
//	return xhr.send(null);
	// console.log(xhr.readyState);
}