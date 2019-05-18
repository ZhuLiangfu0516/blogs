//管理
app.service("articleService",function($http){

   /* this.upload = function (entity) {
        return $http.post("/portrait/upload",entity.file);
    };*/

    this.inquire = function (entity) {
        return $http.get("/artivle/inquire",entity);
    };

    this.singlec = function (entity) {
        return $http.get("/artivle/singlec?id="+entity.id)
    }
});