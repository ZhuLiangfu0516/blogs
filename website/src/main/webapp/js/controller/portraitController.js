//管理
app.controller('portraitController', function ($scope, $location, $http, portraitService) {

    //保存头像
    $scope.uploadFile = function () {
        var form = new FormData();
        var file = document.getElementById("fileUpload").files[0];
        form.append('file', file);
        $http({
            method: 'POST',
            url: '/portrait/upload',
            data: form,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity
        }).success(function (request) {
            if (request.flag == true) {
                swal({
                    title: "图片上传成功!",
                    text: request.message,
                    type: "success",
                });
            } else {
                swal({
                    title: "图片上传失败？",
                    text: request.message,
                    type: "error",
                });
            }
        })
    };


    //获取头像
    $scope.imgUser = function () {
        portraitService.imgUser($scope.entity).success(
            function (response) {
                $scope.users = response;

            }
        )
    };

});

