//管理
app.controller('userController', function ($scope, $location, userService) {

    //发短信
    $scope.sendSms = function () {
        var phone1 = $scope.entity.username;
        var phoneReg = /^(13[0-9]|14[5-9]|15[012356789]|166|17[0-8]|18[0-9]|19[8-9])[0-9]{8}$/;
        if (!phoneReg.test(phone1.trim())) {
            swal({
                title: "手机号异常!",
                text: "手机号格式：不正确，请核实！",
                type: "error",
            });
        } else {
            swal('短信正在发送，该验证码五分钟之内有效,请注意查收!');
            setTimeout(8);
            userService.sendSms($scope.entity.username).success(
                function (request) {
                    swal(request.message);
                })
        }
    };

    //注册
    $scope.regist = function () {
        var passwordReg = /^(?![0-9]+$)(?![a-zA-Z]+$)(?!([^(0-9a-zA-Z)]|[\\(\\)])+$)([^(0-9a-zA-Z)]|[\\(\\)]|[a-zA-Z]|[0-9]){6,16}$/;
        if (passwordReg.test($scope.entity.password)) {
            userService.regist($scope.entity).success(
                function (request) {
                    if (request.flag == true) {
                        swal({
                            title: "注册正常!",
                            text: request.message,
                            type: "success",
                        }, function () {
                            setTimeout(location.href = "/login.html", 3);
                        });
                    } else {
                        swal({
                            title: "注册异常!",
                            text: request.message,
                            type: "error",
                        }, function () {
                            setTimeout(location.href = "/signin.html", 3);
                        });
                    }
                }
            );
        } else {
            swal('密码不正确, 请输入包含有字母和数字的6-16位密码! ');
        }
    };

    //登录
    $scope.login = function () {
        var phoneReg = /^(13[0-9]|14[5-9]|15[012356789]|166|17[0-8]|18[0-9]|19[8-9])[0-9]{8}$/;
        if (phoneReg.test($scope.entity.username)) {
            userService.login($scope.entity).success(
                function (request) {
                    if (request.flag == true) {
                        var login = document.getElementById('login-from');
                        login.submit();
                    } else {
                        swal('登录失败!　' + request.message + 'error');
                    }
                }
            )
        } else {
            swal("登录失败,您的账号有误,请重新输入!");
        }
    };


    //修改密码
    $scope.forgetPwd = function () {
        var phoneReg = /^(13[0-9]|14[5-9]|15[012356789]|166|17[0-8]|18[0-9]|19[8-9])[0-9]{8}$/;
        //密码校验规则6-16必须包含数字及字母两种
        /*var passwordReg = /^(?![0-9]+$)(?![a-zA-Z]+$)(?!([^(0-9a-zA-Z)]|[\\(\\)])+$)([^(0-9a-zA-Z)]|[\\(\\)]|[a-zA-Z]|[0-9]){6,16}$/;*/
        if (!phoneReg.test($scope.entity.username)) {
            swal('手机号格式有误', "请输入正确的手机号!", 'error');
        } else if ($scope.password1 != $scope.password2) {
            swal('密码不一致', "两次输入的密码不一致,请重新输入!", 'error');
        } else {
            $scope.entity.password = $scope.password1;
            userService.forget($scope.entity).success(
                function (request) {
                    if (request.flag == true) {
                        swal({
                            title: '密码修改成功', text: request.message, type: request.code,
                            showCancelButton: true,
                            closeOnConfirm: false,
                            showLoaderOnConfirm: true,
                            confirmButtonText: '确认',
                            cancelButtonText: '取消'
                        }, function () {
                            setTimeout(function () {
                                location.href = "/login.html";
                            }, 300);
                        });
                    } else {
                        swal({
                            title: '密码修改失败', text: request.message, type: request.code,
                            showCancelButton: true,
                            closeOnConfirm: false,
                            showLoaderOnConfirm: true,
                            confirmButtonText: '确认',
                            cancelButtonText: '取消'
                        }, function () {
                            setTimeout(function () {
                                location.href = "/forget_pwd.html";
                            }, 300);
                        });
                    }
                }
            );
        }
    };

    //获取头像
    $scope.imgUser = function () {
        userService.imgUser($scope.entity).success(
            function (response) {
                $scope.imgUsers = response;
            }
        )
    };
});

