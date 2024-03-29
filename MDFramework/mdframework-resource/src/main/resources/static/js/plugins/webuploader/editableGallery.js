/* 
 * 可编辑的相册
 * 
 */
( function( $ ) {
    
    var EditableGallery = function () {
    }
    
    EditableGallery.prototype.addDeletedUrl = function (deletedUrl) {
        if (!this.deletedUrls) {
            this.deletedUrls = [];
        }
        this.deletedUrls.push(deletedUrl);
    }
    
    EditableGallery.prototype.getDeletedUrls = function () {
        return this.deletedUrls;
    }
    
    EditableGallery.prototype.clearDeletedUrls = function () {
        this.deletedUrls = [];
    }
    
    $.fn.extend({
        editableGallery: function( option ) {
            
            var defaults = {
                urls : [],
                canDelete : false
            };
            
            var plugin = this;
            
            var value,
                args = Array.prototype.slice.call(arguments, 1);
            
            // 样式可以挂到“上传”那边。
            function makeGallery($wrap, urls, canDelete) {
                var data = $wrap.data('editableGallery');
                data['clearDeletedUrls'].apply(data);
                var $fileList = $('<ul class="filelist"></ul>');
                $wrap.empty().append($fileList);
                $.each(urls, function(index, element) {
                    // fancybox放大图片。data-fancybox-group设置为一个独特的值，确保“放大后切换图片时只显示这一组图片”。
                    var $li = $('<li id="' + index + '">' +
                        '<p class="imgWrap"><a class="fancybox" data-fancybox-group="gallery' + $wrap.attr("id")
                        + '" href="' + element + '"><img src="' + element + '"></a></p>' +
                        '</li>');
                    
                    if (canDelete) {
                        var $btns = $('<div class="file-panel">' +
                            '<span class="cancel">删除</span>' +
                            '</div>').appendTo( $li );
                        $li.on( 'mouseenter', function() {
                            $btns.stop().animate({height: 30});             
                        });

                        $li.on( 'mouseleave', function() {
                            $btns.stop().animate({height: 0});
                        });
                        $btns.on( 'click', 'span', function() {
                            var index = $(this).index();

                            switch ( index ) {
                                case 0:
                                    data['addDeletedUrl'].apply(data, [element]);
                                    $li.off().find('.file-panel').off().end().remove();
                                    return;
                                
                            }

                        });
                    }
                    
                    $li.appendTo($fileList);
                });
            }
            
            plugin.each( function() {
                var $this = $( this ),
                    data = $this.data('editableGallery'),
                    options =  $.extend(defaults, typeof option === 'object' && option);
                if ( $this ) {
                    if (typeof option === 'string') {
                        if (!data) {
                            return;
                        }
                        
                        value = data[option].apply(data, args);
                    } else {
                        if (!data) {
                            $this.data('editableGallery', (data = new EditableGallery()));
                        }
                        makeGallery($this, options.urls, options.canDelete);
                    }
                }
            });
            
            return typeof value === 'undefined' ? plugin : value;
            
        } // end editableGallery
    });
}( jQuery ));

