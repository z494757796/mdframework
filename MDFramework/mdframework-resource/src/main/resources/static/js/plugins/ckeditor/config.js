/**
 * @license Copyright (c) 2003-2018, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {
	//config.extraPlugins += (config.extraPlugins ? ',lineheight' : 'lineheight');
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	//上传图片路径
	//config.line_height="1em;1.1em;1.2em;1.3em;1.4em;1.5em" ;
	config.indentClasses=["text_indent"];
	config.indentOffset=2;
	config.indentUnit="em";
	config.filebrowserImageUploadUrl ="/file/ckeditorUpload";
	config.filebrowserFlvPlayerUploadUrl = "ckeditorPlayerUploadUrl.do";
	config.toolbar = 'MyToolbar';//把默认工具栏改为‘MyToolbar’
    config.font_names = '宋体/SimSun;新宋体/NSimSun;仿宋/FangSong;楷体/KaiTi;仿宋_GB2312/FangSong_GB2312;'+
    '楷体_GB2312/KaiTi_GB2312;黑体/SimHei;微软雅黑/Microsoft YaHei;微软正黑/Microsoft JhengHei;'+
    'Arial Black/Arial Black;'+ config.font_names;
	//config.extraPlugins = (config.extraPlugins ? ',indentblock' : 'indentblock');
    config.toolbar_MyToolbar =
    [
    	['SelectAll','-','Replace','RemoveFormat','-','Image','TextColor','BGColor','-','Font','FontSize'],
    	['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
    	['Undo','Redo','Outdent','Indent'],
    	['NumberedList','Bold','Italic','Underline','-','Maximize'],
    	/*['Source','-','Save','NewPage','Preview','-','Templates'],
        ['Cut','Copy','Paste','PasteText','PasteFromWord','-','Print', 'SpellChecker', 'Scayt'],
        ['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
        ['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'],
        '/',
        ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
         ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],*/
        /* ['Link','Unlink','Anchor'],
        	['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
        '/',
         ['Styles','Format','Font',],
         ['TextColor','BGColor']*/
    ];
    //config.extraPlugins += (config.extraPlugins ? ',lineheight' : 'lineheight');
};
