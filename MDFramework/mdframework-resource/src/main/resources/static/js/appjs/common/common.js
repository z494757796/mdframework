function hasPermission(permission) {
    if ( window.parent.permissions!=undefined && window.parent.permissions.indexOf(permission) > -1) {
        return true;
    } else {
        return false;
    }
}

/**打开弹出框*/
function openModel(title,url) {
    layer.open({
    		type : 2,
    		title : title,
    		maxmin : true,
    		shadeClose : false, // 点击遮罩关闭层
    		area : [ '800px', '520px' ],
    		content :url
    	});
}

/**打开弹出框*/
function openModel(title,url,width,height) {
    layer.open({
    		type : 2,
    		title : title,
    		maxmin : true,
    		shadeClose : false, // 点击遮罩关闭层
    		area : [ width, height ],
    		content :url
    	});
}

//获取url参数
function getParams(url) {
        var theRequest = new Object();
        if (!url)
            url = location.href;
        if (url.indexOf("?") !== -1)
        {
            var str = url.substr(url.indexOf("?") + 1) + "&";
            var strs = str.split("&");
            for (var i = 0; i < strs.length - 1; i++)
            {
                var key = strs[i].substring(0, strs[i].indexOf("="));
                var val = strs[i].substring(strs[i].indexOf("=") + 1);
                theRequest[key] = val;
            }
        }
        return theRequest;
    }


    /**
    查看图片
    */
    function openPhotos(imgurl) {
     var data = new Object(); //创建一个Object 对象
       data.title="查看图片";
       data.id=123;
       data.start=0;
       var detail = new Object();
        detail.alt="图片";
        detail.pid=666;
        detail.src=imgurl;
        detail.thumb=imgurl;
        var mycars = new Array()
        mycars[0] = detail;
        data.data = mycars;
        layer.photos({
            photos: data //格式见API文档手册页
            ,anim: 2 //0-6的选择，指定弹出图片动画类型，默认随机
          });
    }

    /**
    * 省市区联动--省份选择
    */
    function changeProvince(value,cityId,areaId,townId) {
        $(cityId).html('<option value="">城市</option>');
        $(areaId).html('<option value="">地区</option>');
        $(townId).html('<option value="">城镇</option>');

        if ('' == value) {
            return;
        }

        //异步查询城市
        $.ajax({
        type : 'GET',
        url :  '/city/listByProvinceId?fatherid='+value,
        success : function(r) {
            if (r.code == 0) {
                $.each(r.data, function(){
                 $(cityId).append('<option value="'+this.cityid+'">'+this.city+'</option>')
                });
            } else {
                parent.layer.msg("查询失败");
            }
        }
    });
    }
    /**
    * 省市区联动--城市选择
    */
    function changeCity(value,areaId,townId) {
        $(areaId).html('<option value="">地区</option>');
        $(townId).html('<option value="">城镇</option>');
        if ('' == value) {
            return;
        }
        //异步查询地区
        $.ajax({
            type : 'GET',
            url :  '/area/listByCityId?fatherid='+value,
            success : function(r) {
                if (r.code == 0) {
                    $.each(r.data, function(){
                     $(areaId).append('<option value="'+this.areaid+'">'+this.area+'</option>')
                    });
                } else {
                    parent.layer.msg("查询失败");
                }
            }
        });
    }    /**
    * 省市区联动--地区选择
    */
    function changeArea(value,townId) {
        $(townId).html('<option value="">城镇</option>');
        if ('' == value) {
            return;
        }
        //异步查询地区
        $.ajax({
            type : 'GET',
            url :  '/town/listByAreaId?fatherid='+value,
            success : function(r) {
                if (r.code == 0) {
                    $.each(r.data, function(){
                     $(townId).append('<option value="'+this.townid+'">'+this.town+'</option>')
                    });
                } else {
                    parent.layer.msg("查询失败");
                }
            }
        });
    }