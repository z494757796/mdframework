
var prefix = "/organizationInfo"
$(function() {
	load();
});
var tree ;
var selectId= null;
var selectName;
function load() {
	$.ajax({
        url : prefix+"/tree",
        get : "post",
        dataType:'json',
        success : function(r) {
            tree = r;
            $('#treeview4').treeview({
                color: "#428bca",
                data: tree,
                  onNodeSelected: function (event, node) {
                        selectId = node.href;
                        selectName = node.text;
                  }
            });
        }
    });
}


function selectColumn() {
    if (null == selectId) {
         parent.layer.msg("请选择组织架构");
         return;
    }
    var data = new Object(); //创建一个Object 对象
    data.selectId = selectId; //
    data.selectName = selectName;
    var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
    data.$pageName = 'getColumn';
    window.parent.postMessage(JSON.stringify(data), location.origin);
    parent.layer.close(index);

}