//单纯组织机构代码校验方法
function checkOrganization(code){
	var ws  = [3,7,9,10,5,8,4,2]; //组织机构代码的加权因子
	var str  = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ'; //组织机构代码 代码字符集
	var reg = /^([0-9A-HJ-NPQRTUWXY]){9}$/; //组织机构代码的正则   8+1
	var sum = 0;
	var index_9 = ""; //组织机构代码第9位
	code = code.replace(/-/,"");  // 把'-' 替换成 '' 空字符串
	
	if(code.length == 9){ //当输入长度为9时
		if(!reg.test(code)){
			layer.alert("您的组织机构代码不合法!");
			return false;
		}
		//前八位求和
		for(var i = 0; i < code.length; i++){
			if(i<8){
				sum += str.indexOf(code.charAt(i)) * ws[i];
			}else{
				index_9 = code.charAt(i);
			}
		}
		//求模
		var c9 = 11 - (sum % 11);
		
		if (c9 == 11) {
			c9 = '0';
		} else if (c9 == 10){
			c9 = 'X'
		} else {
			c9 = c9
		}
		/*
		else{
			//将str转变成数组
			var arr = str.split("");
			//遍历arr2
			for(var i = 0; i < arr.length; i++){
				if(c9 == i){
					c9 = arr[i];
				}
			}
		}*/
		
		if(c9 != index_9){
			layer.alert("您的组织机构代码校验失败!");
			return false;
		}
	} else if(code.length < 9 && code.length > 0) {
		layer.alert("组织机构代码长度不正确!");
		return false;
	} else if(code.length > 9){
		layer.alert("组织机构代码长度过长!");
		return false;
	} else {
		return true;
	}
}

//单纯统一社会信用代码校验方法
function checkSocialCredit(code){
	var ws = [1,3,9,27,19,26,16,17,20,29,25,13,8,24,10,30,28];//统一信用代码的加权因子
	var str = '0123456789ABCDEFGHJKLMNPQRTUWXY'; //统一信用代码 代码字符集
	var reg = /[0-9A-HJ-NPQRTUWXY]{2}\d{6}[0-9A-HJ-NPQRTUWXY]{10}/;//统一信用代码的正则 17+1
	var sum = 0;
	var index_18 = ""; //统一信用代码第18位
	if(code.length == 18){ //当输入长度为18位时
		if(!reg.test(code)){
			layer.alert("您的统一社会信用代码不合法!");
			return false;
		}
		//前17位求和
		for(var i = 0; i < code.length; i++){
			if(i<17){
				sum += str.indexOf(code.charAt(i)) * ws[i];
			}else{
				index_18 = code.charAt(i);
			}
		}
		//求模
		var c18 = 31 - (sum % 31);
		
		if(c18 == 31){
			c18 = '0';
		} else {
			//将str转变成数组
			var arr = str.split("");
			//遍历arr2
			for(var i = 0; i < arr.length; i++){
				if(c18 == i){
					c18 = arr[i];
				}
			}
		}
		
		if(c18 != index_18){
			layer.alert("您的统一社会信用代码校验失败!");
			return false;
		}
	} else if(code.length < 18 && code.length > 0) {
		layer.alert("统一社会信用代码长度不正确!");
		return false;
	} else if(code.length > 18){
		layer.alert("统一社会信用代码长度过长!");
		return false;
	}else {
		return true;
	}
}

//台湾身份证校验
function TWCheck(code){
	var str = "ABCDEFGHJKLMNPQRSTUVXYWZIO";
	var ws = [9,8,7,6,5,4,3,2,1];
	var reg = /^[A-Z][1,2][0-9]{8}$/;
	var sum = 0;
	if(code.length != 10){
		alert("长度有误");
		return false;
	}
	if(!reg.test(code)){
		alert("输入有误");
		return false;
	}
	
	var arr = code.split("");
	var num = 0;
	for(var i = 0;i<code.length;i++){
		if(i==0){
			num = str.indexOf(code[i]) + 10; //每个字母对应一个两位数字
			var num1 = parseInt(num/10); //十位取整
			var num2 = num%10; //个位
			sum = num1 + num2*ws[i];
			continue;
		}
		if(i>0&&i<9){
			sum += arr[i] * ws[i];
			continue;
		}
		var c10 = 10 - sum%10;
		if(c10 != arr[9]){
			alert("输入有误");
		}
	}
}

//澳门身份证校验
function MOCheck(code){
   //网上没有找到澳门的校验规则，待补充
}

//香港身份证校验
function HGCheck(code){
	code = code.replace("\(","").replace("\)","");
	var str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var ws = [7,6,5,4,3,2]; //香港中间6位加权因子
	var sum = 0;
	var reg = /^[A-Z][0-9]{6}[0-9A]$/;
	if(code.length != 8){
		alert("长度有误");
		return false;
	}
	if(!reg.test(code)){
		alert("输入有误")
		return false;
	}
	
	var arr = code.split("");
	var num = 0;
	for(var i = 0;i<arr.length;i++){
		if(i==0){ //首位*8
			num = str.indexOf(arr[i]) + 1;
			sum += num * 8;
			continue;
		}else if(i>0 && i<7){//中间6位乘以对应的加权因子
			sum += arr[i] * ws[i-1]
			continue;
		}
		var c8 = "";
		var mod = sum%11;
		if(mod==0){
			c8='0';
		}else if(mod == 1){
			c8='A'
		}else{
			mod = 11-mod;
			c8 = mod;
		}
		
		if(arr[7]!=c8){ //前7位总和求模 与最后一位比较
			alert("输入有误")
		}
	}
}
