//管理
app.service("portraitService",function($http){

   /* this.upload = function (entity) {
        return $http.post("/portrait/upload",entity.file);
    };*/

    this.imgUser = function (entity) {
        return $http.get("/portrait/imgUser",entity.username);
    };

});