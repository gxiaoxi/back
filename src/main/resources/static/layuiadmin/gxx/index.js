
$.ajax({
    url:'/back/infoList',
    async:false,
    success:function(result){
        if(result.code == 200){
            var userrealname = result.data.userrealname;
            var menuList = result.data.menuList;
            var menuList2 = result.data.menuList;
            $("#userrealname").html(userrealname);
            $("#LAY-system-side-menu").empty();
            var menuHtml = "";
            for (var i=0;i<menuList.length;i++){
                var hasSecondFlag = 0;
                var secondHeadHtml = "";
                var secondBodyHtml = "";
                var secondEndHtml = '</dl>';
                if(menuList[i].parentId == 0){

                    menuHtml += '<li data-name="'+menuList[i].menuName+'" class="layui-nav-item"><a href="javascript:;" lay-tips="'+menuList[i].menuName+'" lay-direction="2">'+
                        '<i class="layui-icon '+menuList[i].menuPicClass+'"></i><cite>'+menuList[i].menuName+'</cite></a>';
                    for (var j=0;j<menuList2.length;j++){
                        if(menuList2[j].parentId == menuList[i].menuId){
                            hasSecondFlag = 1;
                            secondHeadHtml = '<dl class="layui-nav-child">';
                            secondBodyHtml += '<dd data-name="'+menuList[i].menuName+'" ><a lay-href="'+menuList[j].menuUrl+'">'+menuList[j].menuName+'</a></dd>';
                        }
                    }
                    if(hasSecondFlag == 1){
                        menuHtml += secondHeadHtml+secondBodyHtml+secondEndHtml;
                    }
                }
            }
            $("#LAY-system-side-menu").append(menuHtml);
            //element.init();
        }else{
            layer.msg("数据异常", {
                offset: '15px'
                ,icon: 1
                ,time: 1000
            })
        }
    }
})

$("#logout").click(function(){
    $.ajax({
        url:'/back/logout',
        type:'post',
        dataType:'json',
        async:false,
        success:function(data){
            window.location.href = "login.html";
        }
    });
})


$(document).ajaxComplete(function(event,obj,settings){
    if (obj.responseText == 'timeout') { //超时标识
        window.location.href='login.html'; //跳转到登录页面
    }
})
