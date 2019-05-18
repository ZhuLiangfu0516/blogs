//管理
app.service("userService",function($http){

    this.regist=function(entity){
			return $http.post("/user/regist",entity);
	};

	this.sendSms=function (phone) {
		return $http.post("/sms/sendSms?phone="+phone);
    };

    this.login=function (entity) {
        return $http.post("/user/login",entity);
    };

    this.imgUser = function (entity) {
        return $http.get("/portrait/imgUser",entity);
    };

});
