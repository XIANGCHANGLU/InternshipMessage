/*!
 * Javascript library v3.0
 *
 * Date: 2015.04.02
 */

/**
 *
 * @param {[undefined]}
 *            undefined [确保undefined未被重定义]
 * @author lihao01@cfischina.com
 */
( /* <global> */function (undefined) {

	(function (factory) {
		"use strict";

		// amd module
		if (typeof define === "function" && define.amd) {
			define(["jquery"], factory);
		}
		// global
		else {
			factory();
		}

	})
	(function ($) {
		"use strict";

        if(!$) return ;

        //全屏
        var screen=function() {
            var fullScreen = {
                    LEFT: 'left',
                    FULL: 'full',
                    CONTAINER:'container',
                    MAIN: 'main'
                },
                show = {
                    SHOW: 'removeClass',
                    HIDE: 'addClass',
                    TOGGLE: 'toggleClass'
                },
                resizeHandlerList={},
                globalResizeHandlerList={},
                resizeTimeout;

            function toggleClass($elem,action,className){
                if(action === show.TOGGLE){
                    action = $elem.length?($elem.hasClass(className) ? show.SHOW : show.HIDE) : show.TOGGLE;
                }
                $elem[action](className);
                return action;
            }

            function full(fullScreenType, showType, $context) {
                showType = showType || show.TOGGLE;

                switch (fullScreenType) {
                    case fullScreen.MAIN:
                        //容器的左侧边栏
                        showType = toggleClass($('.main-left', $context).parent(), showType, 'full');
                    case fullScreen.CONTAINER:
                        //导航栏
                        showType = toggleClass($('body,#tabsContainer'), showType, 'full-container');
                    case fullScreen.FULL:
                        //顶部banner
                        showType=toggleClass($('body,#banner'),showType,'full');
                    case fullScreen.LEFT:
                        //左侧边栏
                        toggleClass($('body,#leftAside,#banner'),showType,'aside-left-out');
                }

                resize();
                return showType;

            }

            function resize() {
                window.clearTimeout(resizeTimeout);
                resizeTimeout = window.setTimeout(function () {
                    var uid;
                    for (uid in globalResizeHandlerList) {
                        if(globalResizeHandlerList[uid].timeout){
                            window.setTimeout(globalResizeHandlerList[uid].callback,globalResizeHandlerList[uid].timeout);
                        }else{
                            globalResizeHandlerList[uid].callback&&globalResizeHandlerList[uid].callback();
                        }
                    }

                    uid = app.dispatcher.currentHandler && app.dispatcher.currentHandler.uid;
                    if (uid && (uid = resizeHandlerList[uid])) {
                        if (uid.timeout) {
                            window.setTimeout(uid.callback);
                        } else {
                            uid.callback && uid.callback();
                        }
                    }
                    uid = null;
                }, 100);
            }


            full.fullScreen = fullScreen;
            full.show = show;
            full.addResizeHandler=function(options) {

                if(options&&options.uid&&options.callback){
                    if(options.isGlobal){
                        globalResizeHandlerList[options.uid]= {
                            callback: options.callback,
                            timeout: options.timeout || 0
                        };
                    }else {
                        resizeHandlerList[options.uid] = {
                            callback: options.callback,
                            timeout: options.timeout || 0
                        };
                    }
                }
            };
            full.removeResizeHandler=function(uid,isGlobal){
                if(uid){
                    if(isGlobal){
                        globalResizeHandlerList[uid] = null;
                        delete globalResizeHandlerList[uid];
                    }else{
                        resizeHandlerList[uid] = null;
                        delete resizeHandlerList[uid];
                    }
                }
            };
            full.triggerResizeHandler=function(uid,isGlobal) {
                if(uid) {
                    if (isGlobal) {
                        if(uid=globalResizeHandlerList[uid]){
                            uid.callback&&uid.callback();
                        }
                    } else if(uid=resizeHandlerList[uid]) {
                        uid.callback && uid.callback();
                    }
                    uid=null;
                }
            };

            $(window).resize(resize);


            return full;
        }();

        //遮罩
        var shelter = function () {
            var maskList = [],
                zIndexList=[],
                $mask,
                loadingTemp = '<div id="shelter" class="mask"><div class="maskTitle">_maskTitle_</div><div class="maskPic"></div><div class="maskPicLeft"></div><div class="maskPicCenter"></div><div class="maskPicRight"></div></div>',
                ___MAX_INDEX = 15000,
                timeOutHandler,
                hideTimeoutHandler;

            var hideAll = function(){
                window.clearTimeout(timeOutHandler);
                maskList.splice(0,maskList.length);
                if($mask){
                    $mask.fadeOut(200,function(){
                        $mask.remove();
                        $mask=null;
                        app.shelter.lowerZIndex();
                    });
                }
                app.shelter.lowerZIndex();
            };


            //绑定监听
            $(window).on('keyup', function(e){
                var key = e.which || window.event.keyCode;
                //假如key为27 遮罩消失
                if(key === 27) hideAll();
            });


            return {
                show: function (title, immediate) {
                	maskList.push(title);
                    window.clearTimeout(timeOutHandler);
                    timeOutHandler = setTimeout(function(){
                        window.clearTimeout(hideTimeoutHandler);
                        title=title || '请稍候…';
                        if($mask&&$mask.length){
                            $mask.children('.maskTitle').text(title);
                        }else{
                            $mask=$(loadingTemp.replace(/_maskTitle_/,title)).css('z-index',___MAX_INDEX);
                            $('body').append($mask);
                            app.shelter.upperZIndex(___MAX_INDEX+1);
                        }
                        hideTimeoutHandler = setTimeout(function(){
                            app.shelter.hide();
                            app.alert('遮罩超时','服务器无响应。', app.alertShowType.ERROR, app.alertMsgType.MESSAGE);
                        }, 60*1000);
                    },immediate?0:220);
                },
                hide: function () {
                    window.clearTimeout(timeOutHandler);
                    window.clearTimeout(hideTimeoutHandler);
                    maskList.pop();
                    if(maskList.length&&$mask){
                        $mask.children('.maskTitle').text(maskList[maskList.length-1]);
                    }else if($mask){
                        $mask.fadeOut(200,function(){
                            $mask.remove();
                            $mask=null;
                            app.shelter.lowerZIndex();
                        });
                    }
                },
                hideAll: hideAll,
                upperZIndex: function(alertZIndex,maskZIndex,alertTop) {
                    var $mask = $('#mask'),
                        $alert = $('#alertList');

                    alertZIndex = alertZIndex === false ? '' : (alertZIndex && parseInt(alertZIndex, 10) || 1052);
                    maskZIndex = maskZIndex && parseInt(maskZIndex, 10) || 4;

                    //备份上次的zIndex
                    zIndexList.push({
                        alertZIndex: $alert.css('zIndex'),
                        maskZIndex: $mask.css('zIndex')
                    });


                    $mask.addClass('mask').css({'z-index': maskZIndex});
                    $alert.css({'z-index': alertZIndex, 'top': alertTop === false ? '' : (alertTop || 0)});

                    $mask = null;
                    $alert = null;
                },
                lowerZIndex: function() {

                    //恢复上次的zIndex
                    var $mask = $('#mask'),
                        $alert = $('#alertList'),
                        lastZIndex = zIndexList.length ? zIndexList.pop() : {};

                    if (!parseInt(lastZIndex.maskZIndex, 10)) {//如果上一次没有遮罩的话，则将mask移除
                        $mask.removeClass('mask');
                        $alert.css('top', '');
                    }
                    $mask.css('z-index', lastZIndex.maskZIndex || '');
                    $('#alertList').css('z-index', lastZIndex.alertZIndex || '');

                    lastZIndex = null;//清理内存
                    $mask = null;
                    $alert=null;
                }
            };
        }();

        //确认
        var confirm = function(){
            var modalTemp = '<div class="modal hide fade" style="z-index:1064">' +
                '<div class="modal-header"><h4>_title_</h4></div>' +
                '<div class="modal-body"><p class="text-indent">_content_</p></div>' +
                '<div class="modal-footer"><button type="button" data-role="confirm" class="btn btn-success">_positive_</button><button type="button" data-role="cancel" class="btn btn-danger">_negative_</a></div></div>';

            var defaultOption = {
                title: '确认',                 //确认框标题，非必填
                content: '是否执行该操作',     //确认框内容，非必填
                btnConfirm: '是',             //确认按钮显示内容
                btnCancel: '否',              //却笑按钮显示内容
                confirmHandler: function(){},//点击确认按钮触发的函数，参数以数组形式写在args那里
                cancelHandler: function(){}, //点击取消按钮触发函数，参数写在args那里
                context: this,               //执行函数的上下文
                args: []                     //确认、取消触发函数的参数，以数组形式书写
            };


            return function(options){
                options = $.extend({},defaultOption,options);
                var html = modalTemp.replace(/_title_/,options.title)
                        .replace(/_content_/,options.content)
                        .replace(/_positive_/,options.btnConfirm)
                        .replace(/_negative_/,options.btnCancel),
                    $confirm=$(html);


                $confirm.appendTo('body');
                $confirm.modal({
                    backdrop: 'static',
                    keyboard: false,
                    show: true
                });
                app.shelter.upperZIndex(1061,1060);

                $confirm.on('hidden',function(){
                    app.shelter.lowerZIndex();
                    $(this).off().remove();
                });

                $confirm.one('click', 'button',function(){
                    $confirm.modal('hide');
                    $(this).attr('data-role') ==='confirm'?
                        options.confirmHandler.apply(options.context,options.args):
                        options.cancelHandler.apply(options.context,options.args);
                });
            };
        }();

        //顶部导航栏banner
        var banner = function() {
            var $menu = $('#bannerMenu', '#banner'),
                defaultOptions = {
                    items: [],
                    callback: function () {
                    },
                    args: []
                },
                itemOptions = {
                    href: '',
                    icon: '',
                    name: '项目'
                },
                menuTemp = '<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;"><i class="_icon_"></i>_title_</a><ul class="dropdown-menu">',
                itemTemp = '<li><a data-href="_href_"><i class="_icon_"></i>_name_</a></li>',
                uid='resizeBannerUID-'+Math.random()*10000;


            var removeHandler,
                bannerOptions,
                uid;

            //@html String 插入的模板
            //@callback 添加html的处理事件，包括绑定等等；
            var ban = function (options) {
                if (options && $.isArray(options.items)) {
                    uid = options.uid;
                    bannerOptions = $.extend({}, defaultOptions, options);

                    loadData(bannerOptions);
                }
            };

            function loadItems(items){
                var html='';
                for (var mainItem, i = -1; (mainItem = items[++i]);) {
                   // mainItem = $.extend({}, itemOptions, mainItem);
                    if (mainItem.subItems) {
                        html += menuTemp
                            .replace(/_icon_/, mainItem.icon||'')
                            .replace('_title_', mainItem.name||'');
                        html += loadItems( mainItem.subItems)+'</ul></li>';
                    } else {
                        html += itemTemp
                            .replace(/_href_/, mainItem.href)
                            .replace(/_icon_/, mainItem.icon)
                            .replace(/_name_/, mainItem.name);
                    }
                }
                return html;
            }

            function loadData(options) {
                window.clearTimeout(removeHandler);
                if($menu && $menu.children().length){
                    $menu.children().each(function (index, elem) {
                        TweenLite.killTweensOf(elem);
                    });
                }

                $menu.off().empty().html(loadItems(options.items));
                var timeout = .2;
                $menu.children().each(function (index, elem) {
                    TweenLite.fromTo(elem, .5, {
                        x: '-10%',
                        y: '5%',
                        opacity: 0,
                        ease: Quint.easeOut
                    }, {
                        x: '+=10%',
                        y: '-=5%',
                        opacity: 1,
                        ease: Quint.easeOut,
                        delay: timeout
                    });
                    timeout += .045;
                });
                resize();

                options.callback.apply($menu[0], options.args);
            }

            function remove(){
                ban=null;
            }

            function resize() {
                var $banner = $('#banner>.navbar-inner').css('overflow', 'hidden'),
                    $children = $banner.children().removeClass('hide'),
                    $asideMenu = $children.eq(0),
                    $bannerMenu = $children.eq(1),
                    $bannerOprMenu = $children.eq(2),
                    bannerWidth = $banner.innerWidth() - $asideMenu.outerWidth(),
                    bannerOprMenuWidth = $bannerOprMenu.width();

                if (bannerWidth > ($bannerMenu.width() + bannerOprMenuWidth)) {
                    $children.removeClass('hide');
                } else if (bannerWidth > bannerOprMenuWidth) {
                    $bannerMenu.addClass('hide');
                } else {
                    $children.filter(':gt(0)').addClass('hide');
                }

                $banner.css('overflow', '');
                $banner = null, $asideMenu = null, $bannerMenu = null, $bannerOprMenu = null;
            }

            ban.remove = function (id) {
                if (id && uid === id) {
                    var timeout = .2;
                    $menu.children().each(function (index, elem) {
                        TweenLite.fromTo(elem, .2, {
                            x: 0,
                            y: 0,
                            opacity: 1,
                            ease: Quint.easeOut
                        }, {
                            x: '-=10%',
                            y: '-=5%',
                            opacity: 0,
                            ease: Quint.easeOut,
                            delay: timeout
                        });
                        timeout += .045;
                    });

                    removeHandler = window.setTimeout(function () {
                        $menu.empty();
                        remove();
                    }, (timeout * 1000));

                    bannerOptions=null;
                    uid=null;
                }
            };
            ban.refresh = function (items, id) {
                if (uid === id) {
                    bannerOptions.items = items;
                    loadData(bannerOptions);
                }
            };


            //resize监听
            screen.addResizeHandler({
                uid: uid,
                isGlobal: true,
                callback: resize
            });
            $(function(){
                screen.triggerResizeHandler(uid,true);
            });


            return ban;
        }();

        //提示
        var alertClass = function static_alertClass() {
            //消息中心部分
            var $btn = $('#messageCenterShowBtn'),
                $icon = $btn.children('i'),
                _$container = $('#messageCenterContainer'),
                _$list = $('#messageCenterList', _$container),
                ___noRecord = '<li class="no-result">没有新通知</li>';

            //函数
            //清除全部
            function clear() {
                var timeout = .35;
                _$list.children(':not(.no-result)').each(function(index,elem){
                    TweenLite.to(elem,.5,{
                        x: '10%',
                        opacity: 0,
                        ease: Quint.easeOut,
                        delay: timeout
                    });
                    timeout = Math.min(timeout+=.05,1);
                });

                //清空事件alert提示框队列
                __queue&&__queue.splice(0,__queue.length);
                $alertList&&$alertList.find('.alert-close').click();

                window.setTimeout(function(){
                    _$list.html(___noRecord);
                }, timeout*1000);

                $icon.removeClass('fa-bell').addClass('fa-bell-o');
            }
            //清除全部
            $('.page-header .close', _$container).click(clear);
            //清除单个消息的事件
            $(_$list).click(function(e){
                var $elem = $(e.target || window.event.srcElement);
                if($elem.hasClass('close')){
                    TweenLite.to($elem.parent()[0],.8,{x: '50%', height: 0, overflow: 'hidden', opacity: 0, ease: Quint.easeOut});
                    window.setTimeout(function(){
                        $elem.parent().remove();
                        if(!_$list.children().length){
                            _$list.append(___noRecord);
                            $icon.removeClass('fa-bell').addClass('fa-bell-o');
                        }
                    }, 700);

                }
            });


            //初始化
            clear();


            //消息队列部分
            var __queue = [],
                __queueShowLength = Math.max(Math.ceil($(window).height()/100),3),
                $alertList = $('#alertList'),
                $temp = $('#tabsTemp'),
                alertTemp = $temp.length&&$temp.children(':eq(1)').get(0).outerHTML,
                msgTemp = $temp.length&&$temp.children(':eq(2)').get(0).outerHTML,
                _showType = {
                    SUCCESS: 'success',
                    DEFAULT: 'info',
                    ERROR: 'pink',//'error',
                    WARNING: 'warning',
                    PINK: 'pink'
                }, _msgType = {
                    MESSAGE: 'msg',
                    TIPS: 'tips'
                };

            var showInMessageCenter = function (title, msg) {
                    var d = new Date();
                    d = [d.getHours(), d.getMinutes(), d.getSeconds()]
                        .join(':');
                        //.replace(/((^(?!=\d{2}))|((\D)((?!=\d{2})|(\d$))))/g,'$10');
                    if(!msg){
                        msg = title;
                        title = '消息';

                    }

                    //加入消息中心
                    _$list.children('.no-result').remove();

                    _$list.prepend(msgTemp
                        .replace(/\{msg\}/g,msg)
                        .replace(/\{title\}/g,title)
                        .replace(/\{time\}/,d));

                    //改变icon
                    $icon.removeClass('fa-bell-o').addClass('fa-bell');
                },
                addToQueue = function (args) {
                    __queue.push(args);
                },
                delFormQueue = function () {
                    return __queue.shift();
                },
                execAlert = function (title, msg, type) {
                    var $alert=null;

                    //校验样式在_showType中
                    type = type || 'info';
                    type = (type.toUpperCase() in _showType)?type:_showType.DEFAULT;

                    if ($alertList.children().length<__queueShowLength) {
                        //仅有msg一个参数
                        if(!msg){
                            msg = title;
                            title = '消息';
                        }
                        $alert = $alertList
                            .append(alertTemp.replace(/showType/,type).replace(/\{title\}/g,title).replace(/\{content\}/g,msg))
                            .children(':last');

                        //出现
                        setTimeout(function(){
                            $alert.addClass('out');
                        }, 50+Math.random()*50);
                        //隐藏
                        setTimeout(function(){
                            $alert.animate({
                                top: '-1.5em',
                                opacity: 0,
                                height: 0
                            });
                            execNextAlert($alert);
                        }, 10000+Math.random()*1000);
                    } else {
                        addToQueue(arguments);
                    }
                },
                execNextAlert = function($lastElem){
                    setTimeout(function(){
                        if($lastElem){
                            $lastElem.remove();
                        }
                        if(__queue.length){
                            execAlert.apply(this, delFormQueue());
                        }
                    }, 500);
                };


            //关闭按钮
            $alertList.click(function(e){
                e = e.target || window.event.srcElement;
                if(e.className.indexOf('close')>=0){
                    $(e).parent().removeClass('out').animate({
                        height:0
                    });
                    execNextAlert($(e).parent());
                }
            });

            /*详情请见api部分*/
            return {
                showType: _showType,
                msgType: _msgType,
                alert: function (title, msg, showType,msgType) {
                    if (title instanceof Array) {
                        for (var i = -1, alt; alt = title[++i];) {
                            if(alt.length === 4 && alt[3] === _msgType.MESSAGE){
                                showInMessageCenter(alt[0],alt[1]);
                            }else if(alt.length === 3 && alt[2] === _msgType.MESSAGE){
                                showInMessageCenter('消息',alt[0]);
                            }
                            execAlert.apply(this, title[i]);
                        }
                    } else {
                        //加入消息中心
                        if(!msgType && showType === _msgType.MESSAGE){
                            showInMessageCenter(title,msg);
                        }else{
                            if(msgType === _msgType.MESSAGE){
                                showInMessageCenter(title, msg);
                            }
                            execAlert(title, msg, showType);
                        }
                    }
                }
            }
        }();

        //右侧边栏
        var rightAside = function () {
            /*右侧边栏*/
            var $elem = $('#rightAside'),
                $mask = $('#mask'),
                isMasking=false,
                show=function() {
                    //显示
                    $elem.removeClass('collapsed');
                    //出现遮罩
                    if ($('body').children('.modal-backdrop.fade.in').length) {
                        isMasking = true;
                        app.shelter.upperZIndex(false,1060,false);
                    }else{
                        app.shelter.upperZIndex(false,'',false);
                    }
                    $('#alertList').addClass('aside-right');
                    $mask.addClass('mask');
                },
                hide = function () {
                    $elem.addClass('collapsed');
                    //重置表单
                    reset();

                    if(isMasking) {
                        //恢复alert的z-index
                        isMasking=false;
                    }else{
                        //关闭按钮后遮罩取消
                        $mask.removeClass('mask');
                    }
                    app.shelter.lowerZIndex();
                    $('#alertList').removeClass('aside-right');
                },
                reset=function($el){
                    $(':input', $el || $elem).not(':button, :submit, :reset,:radio,:checkbox,:disabled').val('').removeAttr('selected');
                    $(':checked', $el || $elem).not(':disabled').removeAttr('checked');
                };

            $('form',$elem).on('submit', function(){
                return false;
            });
            $('#rightAsideCloseBtn', $elem).click(hide);

            return {
                __$elem: $elem,
                __$mask: $mask,
                __isMasking:isMasking,
                show: show,
                hide: hide,
                reset: reset,
                set: function (title, formHtml, listenHandler) {
                    //初始化
                    $('#rightAsideTitle', this.__$elem).text(title);
                    this.__$elem.children('form').html(formHtml);
                    this.__$elem.children('form').find('[data-inner-switcher]').bootstrapSwitch();
                    listenHandler && listenHandler(this,this.__$elem);

                    return this;
                },
                bootstrapUpload:function(options){
                    /*
                     * option:{
                     *   el: 上传的元素，选择器或jQuery对象
                     *   tips:鼠标移动到当前位置时显示的信息
                     *   canEditName:可以编辑文件名
                     * }
                     *
                     * */

                    var $el = $(options.el), $parent, $next, $title, extensionName='';
                    var html = '<i class="fa fa-cloud-upload"></i><div class="bootstrap-upload-title"><span></span>'
                        + (options.canEditName?'<input type="text" class="hide text-left"/>&nbsp;<i class="fa fa-edit hide"></i>':'')
                        +'</div>';

                    options.tips = options.tips||'点击上传文件';
                    $el.addClass('bootstrap-upload-input').wrap('<div class="bootstrap-upload"></div>');
                    $parent = $el.parent();

                    $parent.append(html);
                    $next = $el.next();

                    $parent.attr('title', options.tips);

                    $el.css({
                        'height': $next.height(),
                        'width': $next.width(),
                        'left': ($parent.width()-$next.width())/2
                    });


                    //标题绑定事件
                    $title=$parent.children('.bootstrap-upload-title');
                    $title.children('i').click(function(){
                        $title.children('i').addClass('hide');
                        $title.children('input')
                            .removeClass('hide')
                            .val($title.children('span').addClass('hide').text());
                        $title.children('input').focus();
                    });
                    $title.children('input').blur(function(){
                        $title.children('i').removeClass('hide');
                        $title.children('span')
                            .removeClass('hide')
                            .text($(this).addClass('hide').val());
                        $parent.attr('title', $(this).val());
                    });


                    //上传文件改变
                    $parent.change(function(e){
                        var $e=$(e.target);
                        if($e.is('input')) {
                            var name = $e.val(),
                                accept = '(' + ($el.prop('accept') || '').replace(/\s/g, '').split(',').join('|') + ')'.replace(/\./g, '\\\\\\\\.');

                            if (name) {
                                name = name.match(new RegExp('([^\\\\/]+)' + accept + '$','i'));
                                extensionName = name && name[2] || '';
                                name = name && name[1] || '';
                                if(!name){
                                    app.alert('提示',options.tips,app.alertShowType.WARNING);
                                }else{
                                    $title.children('span').text(name);
                                    $title.children('i').removeClass('hide');
                                    $parent.attr('title', name);
                                }
                            } else {
                                $title.children('span').text('');
                                $title.children('i').addClass('hide');
                                $parent.attr('title', options.tips);
                            }
                        }
                    });

                    return {
                        getName: function () {
                            return $title.children('span').text();
                        },
                        getExtensionName: function () {
                            return extensionName;
                        },
                        reset:function() {
                            $parent.children('input').change();
                        },
                        destroy: function () {
                            $parent.children(':not(:input)').remove();
                            $el.unwrap()
                                .removeClass('bootstrap-upload-input')
                                .unbind('change')
                                .val('')
                                .css({
                                    height: '',
                                    width: '',
                                    'margin-left': ''
                                });
                            $el = null, $parent = null, $title = null;
                            for (var p in this) {
                                delete this[p];
                            }
                        }
                    }
                }
            }
        }();

        shelter.show('正在加载页面，请稍候…');

        //全选 单选事件组件
        /*
         *
         * option={
         *   $context
         *   btnSelector
         *   tbodySelector, table body
         *   isDataTable
         *   ___notDisabledNum_cluster  关联集群时不可操作按钮
         *   selectChild //是否选择子设备
         *   deviceType  //设备类型，填了selectChild必须填deviceType
         *   addMethod(list,elem);
         *   getIDMethod(elem)
         *   getNode(list,id)
         * }
         * */
        var SelectComponent = function(options){
            var ___$context = options.$context,
                ___$btn = $(options.btnSelector, ___$context),
                ___$tb = $(options.tbodySelector, ___$context),
                ___list = {},
                ___child = options.selectChild,
                ___type = options.deviceType,
                ___operationButtons=options.operationButtons,
                ___notDisabledNum_cluster = options.notDisabledNum_cluster,
                __add = options.addMethod,
                __id = options.getIDMethod,
                __node = options.getNode,
                _getStatus=options.getStatusMethod,
                __cluster  = options.cluster,
                _add_ = function(elem){
                    __add(___list, elem);
                },
                _add = function(elem){
                    __add(___list, elem);
                    updBtnStyle();
                    return ___list;
                },
                _del = function(elem){
                    var id = typeof(elem) === 'string' ? elem : options.getIDMethod(elem);
                    ___list[id]&&(delete ___list[id]);
                    updBtnStyle();
                    return ___list;
                };


            //已关联集群，不可创建
            if (___operationButtons && ___operationButtons.list) {
                $(___operationButtons.list, ___$context).addClass('disabled');
            }


            //更改全选按钮选中状态
            function updBtnStyle(){
                var $checkbox = $(':checkbox', ___$tb),
                    checkedLength = $checkbox.filter(':checked').length,
                    enableButton;

                switch (checkedLength) {
                    case 0:
                        ___$btn.prop('indeterminate', false).removeAttr('checked');
                        break;
                    case $checkbox.length:
                        ___$btn.prop('indeterminate', false).attr('checked', 'checked');
                        break;
                    default:
                        ___$btn.prop('indeterminate', true).removeAttr('checked');
                }


                //更新操作按钮的样式
                if (___operationButtons && ___operationButtons.list && ___operationButtons.status) {
                    $(___operationButtons.list, ___$context).addClass('disabled');

                    if (checkedLength) {
                        if (enableButton = ___operationButtons.status[_getStatus(___list, options)]) {
                            enableButton = enableButton[checkedLength === 1 ? 0 : 1];
                            if (enableButton) {
                                $(enableButton, ___$context).removeClass('disabled');
                            }
                        }
                    }
                }

                if(__cluster){
                    $(___notDisabledNum_cluster, ___$context).addClass('disabled');
                }
            }

            function selectChildren($e, checked) {
                var execMethod = checked ? 'attr' : 'removeAttr';

                if (!checked) $e.removeAttr('checked');

                $('[data-did^="' + $e.attr('data-did') + '"]', ___$tb).not($e)[execMethod]('disabled', 'disabled')[execMethod]('checked', 'checked');
            }

            function clear(){
                window.setTimeout(function () {
                    ___$btn.removeAttr('checked').change();
                }, 50);

                for(var p in ___list){
                    ___list[p]=null;
                    delete ___list[p];
                }
            }

            //销毁
            function dispose() {
                ___$btn.off();
                ___$tb.off();

                if (options.isDataTable) {
                    $('.dataTables_paginate', ___$context).off();
                    $('.dataTables_filter', ___$context).find(':input').off();
                    $('.dataTables_length',__$context).find('select').off();
                }

                ___$btn = null, ___$tb = null, ___$context = null, ___list = null;

                options=null;
            }

            //绑定监听
            ___$btn.change(function () {

                var checked = this.checked,
                    children,
                    childrenType = [],
                    execMethod = checked ? _add_ : _del;

                if(___type && app.global.device[___type].next){
                    children = app.global.device[___type].next instanceof Array ?
                        app.global.device[___type].next : [app.global.device[___type].next];
                    children.push(___type);
                    $.each(children,function(index, key){
                        key = key.replace(/((lsr|out)([^$]+))/,'$3') || key;
                        childrenType.push('[data-type="'+key.toUpperCase()+'"]');
                    });
                    $(':checkbox', ___$tb).filter(childrenType.join(',')).each(function(index, elem){
                        elem.checked = checked;
                        execMethod(elem);
                        selectChildren($(elem), checked);
                    });
                }else{
                    $(':checkbox', ___$tb).attr('checked', this.checked).each(function (index, elem) {
                        execMethod(elem);
                    });
                }

                updBtnStyle();
            });

            ___$tb.click(function(e) {
                e = e.target || window.event.srcElement;
                if (e.type === 'checkbox') {
                    e.checked ? _add(e) : _del(e);

                    if(___child){
                        selectChildren($(e), e.checked);
                    }

                    updBtnStyle();
                }
            });

            //使用dataTable
            if(options.isDataTable){
                //翻页事件重新统计选中实例按钮的样式
                $('.dataTables_paginate', ___$context).click(function (e) {
                    var $e = e.target || window.event.srcElement;
                    if ($e.hasClass('paginate_button')||$e.parent().hasClass('paginate_button')) {
                        clear();
                    }
                });
                $('.dataTables_filter', ___$context).find(':input').keyup(clear);

                $('.dataTables_length', ___$context).find('select').change(clear);
            }

            return {
                add: _add,
                del: _del,
                list: function(){
                    return $.extend(true, {}, ___list);
                },
                clear:function(){
                    var ids = [];
                    for (var id in ___list) {
                        selectChildren($(__node(___list,id)).find(':checkbox'),false);
                        _del(id);
                        ids.push(id);
                    }
                    updBtnStyle();
                    return ids;
                },
                selectSize : function(){
                    return $(':checkbox:checked', ___$tb).length;
                },
                //销毁
                dispose:dispose
            };
        };

        /*
         * 获取hsl颜色
         * */
        var hsla=function (opt,random) {
            var $elem=$('<div>'),
                targetCSS='background-color',css;

            opt= $.extend(opt,this.defaltOptions);

            $elem.css(targetCSS, 'hsl('+ [(random?Math.floor(Math.random() * 361):opt.h),opt.s,opt.l].join(',')  +')');

            css = $elem.css(targetCSS).toString();

            if (jQuery.support.opacity) {
                return css.replace('rgb', 'rgba').replace(')', ',' + opt.a + ')');
            }
            return css;
        };
        hsla.defaltOptions= {
            h: Math.floor(Math.random() * 361),
            s: '50%',
            l: '50%',
            a: 1
        };

        //滚动到底部
        //$container 带滚动条的容器
        //$content 需要滚动到顶部的内容
        var scrollTop = function($container,$content,speed,marginTop) {
            var cttOffset = $content.offset(),
                ctnOffset = $container.offset();
            if(ctnOffset&&cttOffset) {
                marginTop = marginTop ? parseInt(marginTop) : 0;
                $container.animate({scrollTop: cttOffset.top + $container.scrollTop() - ctnOffset.top - marginTop}, speed || 200);
            }
        };
        scrollTop.position=function(evevnt,$container,$content,fixTop,fixLeft) {
            return {
                top: (($container.height() > $content.height() + evevnt.clientY) ? evevnt.clientY : (evevnt.clientY - $content.height())) - (fixTop || 0),
                left: (($container.width() > $content.width() + evevnt.clientX) ? evevnt.clientX : (evevnt.clientX - $content.width())) - (fixLeft || 0)
            }
        };


        //可键入下拉框
        (function ( $ ) {

            $.fn.editableSelect = function() {
                var $elem;

                this.each(function() {
                    var $wrapper,
                        $select = $(this), $input, $triangle, $list,
                        objID, rightPadding = 15,
                        isFormControl=$select.parent().hasClass('controls');
                    //check if element is a select
                    if ($select.is('select')) {
                        //wrap the original select
                        $select.wrap($('<div/>'));
                        $wrapper = $select.parent().css({display: isFormControl?'block':'inline-block'});

                        //place an input which will represent the editable select
                        $input = $('<input/>').insertBefore($select);

                        //get and remove the original id and value
                        objID = $select.attr('id');
                        $select.removeAttr('id');
                        $input.val($select.val());

                        //add the attributes from the original select
                        $input.attr({
                            autocomplete:'off',
                            alt: $select.attr('alt'),
                            title: $select.attr('title'),
                            'class': $select.attr('class'),
                            name: $select.attr('name'),
                            disabled: $select.attr('disabled'),
                            tabindex: $select.attr('tabindex'),
                            id: objID,
                            placeHolder: $select.children(':disabled').text()
                        });

                        //get the editable css properties from the select
                        $input.css({
                            padding: $select.css('padding'),
                            margin: $select.css('margin'),
                            width: $select.width() - rightPadding + parseInt($select.css('paddingRight')),
                            height: $select.height(),
                            border: $select.css('border'),
                            borderRadius: $select.css('borderRadius'),
                            fontFamily: $select.css('fontFamily'),
                            fontSize: $select.css('fontSize'),
                            background: $select.css('background'),
                            paddingRight: rightPadding
                        });


                        //add the triangle at the right
                        $triangle = $('<div/>').css({
                            height: 0, width: 0,
                            borderLeft: '5px solid transparent',
                            borderRight: '5px solid transparent',
                            borderTop: '7px solid #999',
                            position: 'relative',
                            top: -($input.outerHeight(true) / 2) - (isFormControl?5:7),
                            left: $input.width() + rightPadding - 10,
                            marginBottom: '-7px'
                        }).insertAfter($input);

                        //create the selectable list that will appear when the input gets focus
                        $list = $('<ol class="editable-select-list"/>')
                            .css({
                                display: 'none',
                                listStyleType: 'none',
                                width: $input.outerWidth() - 2,
                                maxHeight:$(window).height()/2,
                                overflow:'auto',
                                padding: 0,
                                margin: 0,
                                border: 'solid 1px #ccc',
                                fontFamily: $input.css('fontFamily'),
                                fontSize: $input.css('fontSize'),
                                background: '#fff',
                                position: 'fixed',
                                zIndex: 1000000
                            })
                            .insertAfter($triangle);


                        //add options
                        $select.children(':not(:disabled)').each(function (index, value) {
                            prepareOption($(value).attr('value'),$(value).text(), $wrapper);
                        });
                        $wrapper.on('mouseleave',function(){
                            $(this).children('ol').hide();
                        });
                        $input
                            .click(function(e){
                                var $this=$(this),
                                    $list=$this.siblings('ol').css('height',''),
                                    isBlock=$this.parent().css('display')==='block',
                                    offsetTop=parseInt($this.offset().top, 10)||0,
                                    top=isBlock?(offsetTop+$this.outerHeight(true)-1):(offsetTop+$this.outerHeight(true)-(parseInt($this.css('margin-bottom'))||0)),
                                    height='';

                                if(!($(window).height() > $list.height() + top)) {
                                    if ((top = offsetTop - $list.height()) < 0) {
                                        height = offsetTop - 10;
                                        top = 10;
                                    }
                                }

                                $list.css({
                                    top: top,
                                    display: 'block',
                                    height:height
                                });
                            })
                            .keyup(function (e) {
                                if (e.which == 13)    $(this).parent().trigger('mouseleave');
                            })
                            .change(function() {
                                var $this = $(this);
                                $this.attr('data-value', $this.val());
                            });
                        $triangle.click(function(){
                            $(this).siblings('input').click();
                        });
                        $list.click(function (e) {
                            //bind click on this option
                            var $e = $(e.target || window.event.srcElement),
                                $this;

                            if ($e.is('li')) {
                                $this=$(this);
                                $this.siblings('input')
                                    .val($e.text())
                                    .trigger('change')//注意顺序
                                    .attr('data-value', $e.attr('data-value'));
                                $this.hide();
                            }
                        });
                        //hide original element
                        $select.css({visibility: 'hidden', display: 'none',position:'absolute'});

                        //save this instance to return it
                        $elem = $input;
                    } else {
                        //not a select
                        return false;
                    }
                });//-end each

                /** public methods **/

                /**
                 * Adds an option to the editable select
                 * @param {String} value - the options value
                 * @returns {void}
                 */
                $elem.addOption = function(value,text) {
                    prepareOption(value,text, $(this).parent());
                };

                /**
                 * Removes a specific option from the editable select
                 * @param {String, Number} value - the value or the index to delete
                 * @returns {void}
                 */
                $elem.removeOption = function(value) {
                    var $this = $(this);
                    if(!value){
                        $this.siblings('ol').children().remove();
                    }else{
                        switch (typeof(value)) {
                            case 'number':
                                $this.siblings('ol').children(':nth(' + value + ')').remove();
                                break;
                            case 'string':
                                $this.siblings('ol').children().each(function (index, optionValue) {
                                    if ($(optionValue).attr('data-value') == value) {
                                        $(optionValue).remove();
                                    }
                                });
                                break;
                        }
                    }
                };


                /*
                *
                * 获取其值 真实值
                * */
                $elem.value=function(){
                  return   $(this).siblings('input').attr('data-value');
                };

                /**
                 * Resets the select to it's original
                 * @returns {void}
                 */
                $elem.restoreSelect = function() {
                    var $wrapper = $(this).parent(),
                        $select = $wrapper.children('select'),
                        objID = $elem.attr('id');

                    $wrapper.off();
                    $wrapper.children(':not(select)').off().remove();
                    $select.unwrap();
                    $wrapper = null;

                    $select.css({visibility: '', display: '',position:''});
                    $select.attr({id: objID});
                    $select = null;
                };

                //return the instance
                return $elem;
            };

            /** private methods **/

            function prepareOption(value,text, $wrapper) {
                text = $.trim(text || value);
                value = $.trim(value || text);
                $('<li data-value="' + value + '" title="' + text + '">' + text + '</li>').appendTo($wrapper.children('ol'));
            }


            var mousewheelHandler,hasWheel=false;
            $(window).on('mousewheel',function(e) {
                if (!hasWheel) {
                    hasWheel = true;
                    var $e = $(e.target || window.event.srcElement);
                    if (!($e.hasClass('editable-select-list') || $e.parent().hasClass('editable-select-list'))) {
                        $('.editable-select-list').hide();
                    }
                }
                window.clearTimeout(mousewheelHandler);
                mousewheelHandler = setTimeout(function () {
                    hasWheel = false;
                }, 100);
            });

        }( jQuery ));




        return {
			alert: alertClass.alert,
			showType: alertClass.showType,
			msgType: alertClass.msgType,
            confirm: confirm,
            formControl: rightAside,
            shelter: shelter,
            screen: screen,
            banner: banner,
            selectComponent: SelectComponent,
            scrollTop: scrollTop,
            hsla: hsla
		};
	});

})();