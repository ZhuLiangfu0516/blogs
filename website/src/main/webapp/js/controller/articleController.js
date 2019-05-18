//管理
app.controller('articleController', function ($scope, $location, $http, articleService) {

    //上传文章
    $scope.save = function () {
        var formData = new FormData(document.querySelector("form"));
        formData.set("content",editor.txt.text());
        var objData = {};
        for (var entry of formData.entries()){
            objData[entry[0]] = entry[1];
        }

        $http({
            method: 'POST',
            url: '/artivle/save',
            data: JSON.stringify(objData),
            contentType: "application/json;charset=UTF-8",
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity
        }).success(function (request) {
            if (request.flag == true) {
                swal({
                    title: "上传成功!",
                    text: request.message,
                    type: "success",
                });
            } else {
                swal({
                    title: "上传失败？",
                    text: request.message,
                    type: "error",
                });
            }
        })
    };

    $scope.entity={
        id:"",
        title:"",
        antistop:"",
        author:"",
        describes:"",
        articleTime:"",
        image:"",
        content:"",
        pageview:"",
        enjoy:"",
        catalog:""
    };

    //获取文章信息
    $scope.inquire = function () {
        articleService.inquire($scope.entity).success(
            function (response) {
                $scope.list = response;
            }
        )
    };

    $scope.single = function (id) {
        location.href = "/blogger/read.html#?id=" + id;
    };

    $scope.singlec=function () {
        $scope.entity.id = $location.search()["id"];
        articleService.singlec($scope.entity).success(
            function (response) {
                $scope.conents = response;
            }
        )
    }


});

