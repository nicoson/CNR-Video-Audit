/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: CN
 */
jQuery.extend(jQuery.validator.messages, {
		required: jQuery.validator.format("{0}不能为空"),
		remote: "请修正该字段",
		email: jQuery.validator.format("{0}格式不正确，请输入正确的电子邮箱"),
		url: "{0}的格式不正确，请输入正确的网站地址",
		date: "请输入合法的日期",
		dateISO: "请输入合法的日期 (ISO).",
		number: jQuery.validator.format("{0}只能输入数字"),
		digits: jQuery.validator.format("{0}只能输入整数"),
		creditcard: "请输入合法的信用卡号",
		equalTo: jQuery.validator.format("{1}与{2}必须一致"),
		accept: "请输入拥有合法后缀名的字符串",
		maxlength: jQuery.validator.format("{1}的长度不能超过{0}个字符"),
		minlength: jQuery.validator.format("{1}的长度不能少于{0}个字符"),
		rangelength: jQuery.validator.format("{2}的长度应介于{0}个字符和{1}个字符之间"),
		range: jQuery.validator.format("{2}应介于{0}和{1}之间"),
		max: jQuery.validator.format("{1}最大不能超过{0}"),
		min: jQuery.validator.format("{1}最小不能低于 {0}"),
		greaterThan: jQuery.validator.format("{1}必须大于 {2}"),
		lessThan: jQuery.validator.format("{1}必须小于 {2}"),
		greaterEqual: jQuery.validator.format("{1}不能小于{2}"),
		lessEqual: jQuery.validator.format("{1}不能大于{2}"),
		idcard: jQuery.validator.format("请输入合法的{0}"),
		ip: jQuery.validator.format("{0}格式不正确，请输入合法的IP地址"),
		mobileOrTel:jQuery.validator.format("{0}的格式不正确"),
		decimal:jQuery.validator.format("{0}的格式不正确,只能输入两位小数"),
		letterOrNum:jQuery.validator.format("{0}只能输入英文字母或数字"),
		equalToUpperCase:jQuery.validator.format("{1}与{2}必须一致")
});