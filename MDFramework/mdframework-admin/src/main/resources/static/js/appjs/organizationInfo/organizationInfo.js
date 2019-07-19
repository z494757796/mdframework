var prefix = "/organizationInfo"
$(function () {
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTreeTable(
            {
                id : 'id',
                code : 'id',// 用于设置父子关系
                parentColumn : 'parentId',
                type : "GET", // 请求数据的ajax类型
                url : prefix + '/list', // 请求数据的ajax的url
                ajaxParams : {}, // 请求数据的ajax的data属性
                expandColumn : '0',// 在哪一列上面显示展开按钮
                striped : true, // 是否各行渐变色
                bordered : true, // 是否显示边框
                expandAll : true, // 是否全部展开
                // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                // queryParamsType = 'limit' ,返回参数必须包含
                // limit, offset, search, sort, order 否则, 需要包含:
                // pageSize, pageNumber, searchText, sortName,
                // sortOrder.
                // 返回false将会终止请求
                columns: [
                             /*                                                                           {
                                    field: 'id',
                                    title: 'ID'
                                },*/
                                                                                                                                                            {
                                    field: 'name',
                                    title: '名称'
                                },
                                                                                                                                                            {
                                    field: 'createTime',
                                    title: '创建时间'
                                },
                                                                                                                                                            {
                                    field: 'updateTime',
                                    title: '更新时间'
                                },
                                                                                                                                                            {
                                    field: 'orderBy',
                                    title: '排序'
                                },
                                                                                                                                                            {
                                    field: 'status',
                                    title: '状态',
                                    align: 'center',
                                    formatter: function ( row,value, index) {
                                        if (row.status == '0') {
                                            return '<span class="label label-danger">禁用</span>';
                                        } else if (row.status == '1') {
                                            return '<span class="label label-primary">正常</span>';
                                        }
                                    }
                                },
                                                                                            {
                        title: '操作',
                        field: 'id',
                        align: 'center',
                        formatter: function (row,value, index) {
                            var str = '';
                             if (hasPermission('organizationInfo:update')) {
                                str += '<a class="btn btn-primary btn-sm" href="#" mce_href="#" title="添加下级" onclick="add('
                                    + row.id
                                    + ')"><i class="fa fa-plus"></i></a> ';
                            }
                            if (hasPermission('organizationInfo:update')) {
                                str += '<a class="btn btn-primary btn-sm" href="#" mce_href="#" title="编辑" onclick="edit(\''
                                    + row.id
                                    + '\')"><i class="fa fa-edit"></i></a> ';
                            }
                            if (hasPermission('organizationInfo:remove')) {
                                str += '<a class="btn btn-warning btn-sm" href="#" title="删除"  mce_href="#" onclick="remove(\''
                                    + row.id
                                    + '\')"><i class="fa fa-remove"></i></a> ';
                            }
                            return str;
                        }
                    }]
            });
}
function reLoad() {
    load();
}
function refresh() {
    load();
}
function add(parentId) {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add?parentId='+parentId // iframe的url
	});
}
function edit(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/edit/' + id // iframe的url
    });
}
function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/remove",
            type: "post",
            data: {
                'id': id
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}

function resetPwd(id) {
}
function batchRemove() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要删除的数据");
        return;
    }
    layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        var ids = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function (i, row) {
            ids[i] = row['id'];
        });
        $.ajax({
            type: 'POST',
            data: {
                "ids": ids
            },
            url: prefix + '/batchRemove',
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {

    });
}