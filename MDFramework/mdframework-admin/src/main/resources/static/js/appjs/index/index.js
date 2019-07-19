//if (window.permissions != 'undefined' && window.permissions.length > 0) {
    $.ajax({
            cache : true,
            type : "POST",
            url : "/sys/menu/user",
            //data : $('#signupForm').serialize(),// 你的formid
            async : false,
            error : function(request) {
                parent.layer.alert("Connection error");
            },
            success : function(data) {
                window.permissions = data.permissions;
            }
        });
//}
