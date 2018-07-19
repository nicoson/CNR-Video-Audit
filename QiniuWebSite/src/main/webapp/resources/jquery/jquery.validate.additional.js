(function() {
	/**
	 * 图片校验码忽略大小
	 */
	$.validator.addMethod("equalToUpperCase", function(value, element, param) {
		var target = $(param[0]).unbind(".validate-equalToUpperCase").bind("blur.validate-equalToUpperCase", function() {
			$(element).valid();
		});
		return value.toUpperCase() == target.val().toUpperCase();
	});
	
	/**
	 * IP地址
	 */
	$.validator.addMethod("ip", function(value, element) {
		var ip = /^((25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d))).){3}(25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))$/;
		if (ip.test(value)) {
			return true;
		}
		return false;
	});
	/**
	 * 与页面元素比较，大于
	 */
	$.validator.addMethod("greaterThan", function(value, element, param) {
		var target = $(param[0]).unbind(".validate-greaterThan").bind("blur.validate-greaterThan", function() {
			$(element).valid();
		});
		if (isNaN(value) || isNaN(target.val())) {
			return false;
		}
		return parseInt(value, 10) > parseInt(target.val(), 10);
	});
	/**
	 * 与页面元素比较，小于
	 */
	$.validator.addMethod("lessThan", function(value, element, param) {
		var target = $(param[0]).unbind(".validate-lessThan").bind("blur.validate-lessThan", function() {
			$(element).valid();
		});
		if (isNaN(value) || isNaN(target.val())) {
			return false;
		}
		return parseInt(value, 10) < parseInt(target.val(), 10);
	});
	
	/**
	* 小数
	 */
	$.validator.addMethod("decimal", function(value, element) {
		var decimal =/^\d+(.\d{1,2})?$/;
		if (value.length == 0 || decimal.test(value)) {
			if(value.length>2 ||value.length<0)
		return true;
		}
		return false;
	});			
	
	/**
	 * 与页面元素比较，大于等于
	 */
	$.validator.addMethod("greaterEqual", function(value, element, param) {
		var target = $(param[0]).unbind(".validate-greaterEqual").bind("blur.validate-greaterEqual", function() {
			$(element).valid();
		});
		if (isNaN(value) || isNaN(target.val())) {
			return false;
		}
		return parseInt(value, 10) >= parseInt(target.val(), 10);
	});
	/**
	 * 与页面元素比较，小于等于
	 */
	$.validator.addMethod("lessEqual", function(value, element, param) {
		var target = $(param[0]).unbind(".validate-lessEqual").bind("blur.validate-lessEqual", function() {
			$(element).valid();
		});
		if (isNaN(value) || isNaN(target.val())) {
			return false;
		}
		return parseInt(value, 10) <= parseInt(target.val(), 10);
	});
	/**
	 * 电话号码
	 */
	$.validator.addMethod("mobileOrTel", function(value, element) {
		var mobile = /^(\+?86)?1\d{10}$/;
		var tel = /^(\+?86)?\d{3,4}-?\d{7,8}(-?\d{3,4})?$/;
		if (value.length == 0 || mobile.test(value) || tel.test(value)) {
			return true;
		}
		return false;
	});
	/**
	 * 英数字
	 */
	$.validator.addMethod("letterOrNum", function(value, element) {
		var regexp = /^[a-zA-Z0-9]*$/g;
		if (regexp.test(value)) {
			return true;
		}
		return false;
	});
	/**
	 * 验证身份证号
	 */
	$.validator.addMethod("idcard", function(value, element) {
		if ($.trim(value) == "" || value.length != $.trim(value).length || value.length != 15 && value.length != 18) {
			return false;
		}

		if (value.length == 15) {
			return checkIdcard15(value);
		} else if (value.length == 18) {
			return checkIdcard18(value);
		}
		return false;
	});
	/**
	 * 验证15位身份证号
	 */
	function checkIdcard15(cardId) {
		cardId = cardId + "";
		for (var i = 0; i < cardId.length; i += 1) {
			//校验每一位的合法性
			if (cardId.charAt(i) < '0' || cardId.charAt(i) > '9') {
				return false;
			}
		}
		var year = cardId.substr(6, 2);
		var month = cardId.substr(8, 2);
		var day = cardId.substr(10, 2);
//		var sexBit = cardId.substr(14);
		
		//校验年份位
		if (year < '01' || year > '90') {
			return false;
		}
		//校验月份
		if (month < '01' || month > '12') {
			return false;
		}
		//校验日
		if (day < '01' && day > '31') {
			return false;
		}
		
		return true;
	}
	/**
	 * 验证18位身份证号
	 */
	function checkIdcard18(cardId) {
		var powers = ["7","9","10","5","8","4","2","1","6","3","7","9","10","5","8","4","2"];
		var parityBit = ["1","0","X","9","8","7","6","5","4","3","2"];
		cardId = cardId + "";
		var _num = cardId.substr(0, 17);
		var _parityBit = cardId.substr(17);
		var _power = 0;
		for(var i = 0;i < 17;i += 1){
			//校验每一位的合法性
			if (_num.charAt(i) < '0'|| _num.charAt(i) > '9') {
				return false;
			} else {
				//加权
				_power += parseInt(_num.charAt(i), 10) * parseInt(powers[i], 10);
			}
		}
		//取模
		var mod = parseInt(_power, 10) % 11;
		if (parityBit[mod] == _parityBit) {
			return true;
		}
		return false;
	}
})();

