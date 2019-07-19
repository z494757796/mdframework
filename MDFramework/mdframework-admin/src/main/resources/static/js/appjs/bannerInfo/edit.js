$().ready(function () {
    validateRule();
});

$.validator.setDefaults({
    submitHandler: function () {
        update();
    }
});

function update() {
    $.ajax({
        cache: true,
        type: "POST",
        url: "/bannerInfo/update",
        data: $('#signupForm').serialize(),// 你的formid
        async: false,
        error: function (request) {
            alert("Connection error");
        },
        success: function (data) {
            console.log(data)
            if (data.code == 0) {
                parent.layer.msg(data.msg);
                parent.reLoad();
                var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                parent.layer.close(index);

            } else {
                parent.layer.msg(data.msg);
            }

        }
    });

}

function getCheckedRoles() {
    var adIds = "";
    $("input:checkbox[name=role]:checked").each(function (i) {
        if (0 == i) {
            adIds = $(this).val();
        } else {
            adIds += ("," + $(this).val());
        }
    });
    return adIds;
}

function setCheckedRoles() {
    var roleIds = $("#roleIds").val();

    var adIds = "";
    $("input:checkbox[name=role]:checked").each(function (i) {
        if (0 == i) {
            adIds = $(this).val();
        } else {
            adIds += ("," + $(this).val());
        }
    });
    return adIds;
}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            title: {
                required: true
            },
            linkUrl: {
                required: true
            },
            orderBy: {
                number: true
            },
            payAmount: {
                number: true
            }
        },
        messages: {
            title: {
                required: icon + "请输入名称"
            },
            linkUrl: {
                required: icon + "请输入链接"
            },
            orderBy: {
                number: icon + "请输入数字"
            },
            payAmount: {
                number: icon + "请输入数字"
            }

        }
    })
}


// 初始化
var uploader = WebUploader.create({

    // swf文件路径
    swf: BASE_URL + '/js/Uploader.swf',

    // 文件接收服务端。
    server: UPLOAD_URI,

    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: '#picker',

    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
    resize: false,
    // 只允许选择图片文件。
    accept: {
        title: 'Images',
        extensions: 'gif,jpg,jpeg,bmp,png',
        mimeTypes: 'image/*'
    }
});
uploader.on('uploadSuccess', function (file, res) {
    if (res.code == 0) {
        $('#mainPic').val(res.data);
        $('#mainPicImg').attr('src', res.data);

        $('#fileQueued').text('上传完成');
    } else {
        $('#fileQueued').text('上传失败');
    }
})
uploader.on('uploadFinished', function () {
    //清空队列
    uploader.reset();
});
uploader.on('fileQueued', function (file) {
    $('#thelist').html('<div id="' + file.id + '" class="item">' +
        '<h4 class="info">' + file.name + '</h4>' +
        '<p class="state" id="fileQueued">等待上传...</p>' +
        '</div>');
});

$('#ctlBtn').on('click', function () {
    if ($(this)[0].state === 'uploading') {
        uploader.stop();
    } else {
        uploader.upload();
    }
});
