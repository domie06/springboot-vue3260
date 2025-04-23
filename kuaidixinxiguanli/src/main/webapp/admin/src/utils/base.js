const base = {
    get() {
        return {
            url : "http://localhost:8080/kuaidixinxiguanli/",
            name: "kuaidixinxiguanli",
            // 退出到首页链接
            indexUrl: 'http://localhost:8080/kuaidixinxiguanli/front/index.html'
        };
    },
    getProjectName(){
        return {
            projectName: "快递信息管理系统"
        } 
    }
}
export default base
