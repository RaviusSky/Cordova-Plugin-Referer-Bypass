var RefererBypassLoader = function(require, exports, module)
{
	var exec = require('cordova/exec');

	function RefererBypass() {}

	RefererBypass.prototype.get = function(success, failure, url, referer)
	{
		exec(success, failure, 'RefererBypass', 'get', [url, referer]);
	}

	var refererBypass = new RefererBypass();
	module.exports = refererBypass;
}

RefererBypassLoader(require, exports, module);

cordova.define("cordova/plugin/RefererBypass", RefererBypassLoader);