KindEditor.lang({
    editImage: '图片属性',
    deleteImage: '删除图片',
    editFlash: 'Flash属性',
    deleteFlash: '删除Flash',
    editMedia: '视音频属性',
    deleteMedia: '删除视音频',
    editLink: '超级链接属性',
    deleteLink: '取消超级链接',
    editAnchor: '锚点属性',
    deleteAnchor: '删除锚点',
    tableprop: '表格属性',
    tablecellprop: '单元格属性',
    tableinsert: '插入表格',
    tabledelete: '删除表格',
    tablecolinsertleft: '左侧插入列',
    tablecolinsertright: '右侧插入列',
    tablerowinsertabove: '上方插入行',
    tablerowinsertbelow: '下方插入行',
    tablerowmerge: '向下合并单元格',
    tablecolmerge: '向右合并单元格',
    tablerowsplit: '拆分行',
    tablecolsplit: '拆分列',
    tablecoldelete: '删除列',
    tablerowdelete: '删除行',
    noColor: '无颜色',
    pleaseSelectFile: '请选择文件。',
    invalidImg: "请输入有效的URL地址。\n只允许jpg,gif,bmp,png格式。",
    invalidMedia: "请输入有效的URL地址。\n只允许swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb格式。",
    invalidWidth: "宽度必须为数字。",
    invalidHeight: "高度必须为数字。",
    invalidBorder: "边框必须为数字。",
    invalidUrl: "请输入有效的URL地址。",
    invalidRows: '行数为必选项，只允许输入大于0的数字。',
    invalidCols: '列数为必选项，只允许输入大于0的数字。',
    invalidPadding: '边距必须为数字。',
    invalidSpacing: '间距必须为数字。',
    invalidJson: '服务器发生故障。',
    uploadSuccess: '上传成功。',
    cutError: '您的浏览器安全设置不允许使用剪切操作，请使用快捷键(Ctrl+X)来完成。',
    copyError: '您的浏览器安全设置不允许使用复制操作，请使用快捷键(Ctrl+C)来完成。',
    pasteError: '您的浏览器安全设置不允许使用粘贴操作，请使用快捷键(Ctrl+V)来完成。',
    ajaxLoading: '加载中，请稍候 ...',
    uploadLoading: '上传中，请稍候 ...',
    uploadError: '上传错误',
    'plainpaste.comment': '请使用快捷键(Ctrl+V)把内容粘贴到下面的方框里。',
    'wordpaste.comment': '请使用快捷键(Ctrl+V)把内容粘贴到下面的方框里。',
    'code.pleaseInput': '请输入程序代码。',
    'link.url': 'URL',
    'link.linkType': '打开类型',
    'link.newWindow': '新窗口',
    'link.selfWindow': '当前窗口',
    'flash.url': 'URL',
    'flash.width': '宽度',
    'flash.height': '高度',
    'flash.upload': '上传',
    'flash.viewServer': '文件空间',
    'media.url': 'URL',
    'media.width': '宽度',
    'media.height': '高度',
    'media.autostart': '自动播放',
    'media.upload': '上传',
    'media.viewServer': '文件空间',
    'hello.remoteImage': '网络图片',
    'hello.localImage': '本地上传',
    'hello.remoteUrl': '图片地址',
    'hello.localUrl': '上传文件',
    'hello.size': '图片大小',
    'hello.width': '宽',
    'hello.height': '高',
    'hello.resetSize': '重置大小',
    'hello.align': '对齐方式',
    'hello.defaultAlign': '默认方式',
    'hello.leftAlign': '左对齐',
    'hello.rightAlign': '右对齐',
    'hello.imgTitle': '图片说明',
    'hello.upload': '浏览...',
    'hello.viewServer': '图片空间',
    'multiimage.uploadDesc': '允许用户同时上传<%=uploadLimit%>张图片，单张图片容量不超过<%=sizeLimit%>',
    'multiimage.startUpload': '开始上传',
    'multiimage.clearAll': '全部清空',
    'multiimage.insertAll': '全部插入',
    'multiimage.queueLimitExceeded': '文件数量超过限制。',
    'multiimage.fileExceedsSizeLimit': '文件大小超过限制。',
    'multiimage.zeroByteFile': '无法上传空文件。',
    'multiimage.invalidFiletype': '文件类型不正确。',
    'multiimage.unknownError': '发生异常，无法上传。',
    'multiimage.pending': '等待上传',
    'multiimage.uploadError': '上传失败',
    'filemanager.emptyFolder': '空文件夹',
    'filemanager.moveup': '移到上一级文件夹',
    'filemanager.viewType': '显示方式：',
    'filemanager.viewImage': '缩略图',
    'filemanager.listImage': '详细信息',
    'filemanager.orderType': '排序方式：',
    'filemanager.fileName': '名称',
    'filemanager.fileSize': '大小',
    'filemanager.fileType': '类型',
    'insertfile.url': 'URL',
    'insertfile.title': '文件说明',
    'insertfile.upload': '上传',
    'insertfile.viewServer': '文件空间'
});

KindEditor.plugin('hello', function (K) {
    var self = this, name = 'hello',
        imageTabIndex = K.undef(self.imageTabIndex, 0),
        imgPath = self.pluginsPath + 'image/images/',
        filePostName = K.undef(self.filePostName, 'imgFile'),
        fillDescAfterUploadImage = K.undef(self.fillDescAfterUploadImage, false),
        lang = self.lang(name + '.');
    self.plugin.imageDialog = function (options) {
        var imageUrl = options.imageUrl,
            imageWidth = K.undef(options.imageWidth, ''),
            imageHeight = K.undef(options.imageHeight, ''),
            imageTitle = K.undef(options.imageTitle, ''),
            imageAlign = K.undef(options.imageAlign, ''),
            showRemote = K.undef(options.showRemote, true),
            showLocal = K.undef(options.showLocal, true),
            tabIndex = K.undef(options.tabIndex, 0),
            clickFn = options.clickFn;
        var target = 'kindeditor_upload_iframe_' + new Date().getTime();
        var hiddenElements = [];
        var html = [
            '<div style="padding:20px;">',
            '<div class="tabs"></div>',
            '<div class="tab1" style="display:none;">',
            '<div class="ke-dialog-row">',
            '<label for="remoteUrl" style="width:60px;">' + lang.remoteUrl + '</label>',
            '<input type="text" id="remoteUrl" class="ke-input-text" name="url" value="" style="width:200px;" /> &nbsp;',
            '<span class="ke-button-common ke-button-outer">',
            '<input type="button" class="ke-button-common ke-button" name="viewServer" value="' + lang.viewServer + '" />',
            '</span>',
            '</div>',
            '<div class="ke-dialog-row">',
            '<label for="remoteWidth" style="width:60px;">' + lang.size + '</label>',
            lang.width + ' <input type="text" id="remoteWidth" class="ke-input-text ke-input-number" name="width" value="" maxlength="4" /> ',
            lang.height + ' <input type="text" class="ke-input-text ke-input-number" name="height" value="" maxlength="4" /> ',
            '<img class="ke-refresh-btn" src="' + imgPath + 'refresh.png" width="16" height="16" alt="" style="cursor:pointer;" title="' + lang.resetSize + '" />',
            '</div>',
            '<div class="ke-dialog-row">',
            '<label style="width:60px;">' + lang.align + '</label>',
            '<input type="radio" name="align" class="ke-inline-block" value="" checked="checked" /> <img name="defaultImg" src="' + imgPath + 'align_top.gif" width="23" height="25" alt="" />',
            ' <input type="radio" name="align" class="ke-inline-block" value="left" /> <img name="leftImg" src="' + imgPath + 'align_left.gif" width="23" height="25" alt="" />',
            ' <input type="radio" name="align" class="ke-inline-block" value="right" /> <img name="rightImg" src="' + imgPath + 'align_right.gif" width="23" height="25" alt="" />',
            '</div>',
            '<div class="ke-dialog-row">',
            '<label for="remoteTitle" style="width:60px;">' + lang.imgTitle + '</label>',
            '<input type="text" id="remoteTitle" class="ke-input-text" name="title" value="" style="width:200px;" />',
            '</div>',
            '</div>',
            '<div class="tab2" style="display:none;">',
            '<iframe name="' + target + '" style="display:none;"></iframe>',
            '<form class="ke-upload-area ke-form" method="post" enctype="multipart/form-data" target="' + target + '" action="">',
            '<div class="ke-dialog-row">',
            hiddenElements.join(''),
            '<label style="width:60px;">' + lang.localUrl + '</label>',
            '<input type="text" name="localUrl" class="ke-input-text" tabindex="-1" style="width:200px;" readonly="true" /> &nbsp;',
            '<input type="button" class="ke-upload-button" value="' + lang.upload + '" />',
            '</div>',
            '</form>',
            '</div>',
            '</div>'
        ].join('');
        var dialogWidth = showLocal ? 450 : 400,
            dialogHeight = showLocal && showRemote ? 300 : 250;
        var dialog = self.createDialog({
                name: name,
                width: dialogWidth,
                height: dialogHeight,
                title: self.lang(name),
                body: html,
                yesBtn: {
                    name: self.lang('yes'),
                    click: function (e) {
                        if (dialog.isLoading) {
                            return;
                        }
                        if (showLocal && showRemote && tabs && tabs.selectedIndex === 1 || !showRemote) {
                            if (uploadbutton.fileBox.val() == '') {
                                alert(self.lang('pleaseSelectFile'));
                                return;
                            }
                            dialog.showLoading(self.lang('uploadLoading'));
                            // firbase
                            fire.upload();
                            localUrlBox.val('');
                            return;
                        }
                        var url = K.trim(urlBox.val()),
                            width = widthBox.val(),
                            height = heightBox.val(),
                            title = titleBox.val(),
                            align = '';
                        alignBox.each(function () {
                            if (this.checked) {
                                align = this.value;
                                return false;
                            }
                        });
                        if (url == 'http://' || K.invalidUrl(url)) {
                            alert(self.lang('invalidUrl'));
                            urlBox[0].focus();
                            return;
                        }
                        if (!/^\d*$/.test(width)) {
                            alert(self.lang('invalidWidth'));
                            widthBox[0].focus();
                            return;
                        }
                        if (!/^\d*$/.test(height)) {
                            alert(self.lang('invalidHeight'));
                            heightBox[0].focus();
                            return;
                        }
                        clickFn.call(self, url, title, width, height, 0, align);
                    }
                },
                beforeRemove: function () {
                    viewServerBtn.unbind();
                    widthBox.unbind();
                    heightBox.unbind();
                    refreshBtn.unbind();
                }
            }),
            div = dialog.div;

        var fire = {
            upload: function () {
                var file = $('input[type="file"][name="imgFile"]')[0];
                file = file.files[0];
                var metadata = {
                    contentType: 'image/jpeg'
                };
                var path = "images/articleImages/" + Math.random().toString(36).substr(2) + '.jpg';
                var storageRef = firebase.storage().ref();
                var uploadTask = storageRef.child(path).put(file, metadata);

                uploadTask.on(firebase.storage.TaskEvent.STATE_CHANGED, function (snapshot) {
                }, function (error) {
                }, function () {
                    var url = uploadTask.snapshot.downloadURL;
                    console.log(url);

                    dialog.hideLoading();
                    
                });
            }
        };

        var urlBox = K('[name="url"]', div),
            localUrlBox = K('[name="localUrl"]', div),
            viewServerBtn = K('[name="viewServer"]', div),
            widthBox = K('.tab1 [name="width"]', div),
            heightBox = K('.tab1 [name="height"]', div),
            refreshBtn = K('.ke-refresh-btn', div),
            titleBox = K('.tab1 [name="title"]', div),
            alignBox = K('.tab1 [name="align"]', div);
        var tabs;
        if (showRemote && showLocal) {
            tabs = K.tabs({
                src: K('.tabs', div),
                afterSelect: function (i) {
                }
            });
            tabs.add({
                title: lang.remoteImage,
                panel: K('.tab1', div)
            });
            tabs.add({
                title: lang.localImage,
                panel: K('.tab2', div)
            });
            tabs.select(tabIndex);
        } else if (showRemote) {
            K('.tab1', div).show();
        } else if (showLocal) {
            K('.tab2', div).show();
        }
        var uploadbutton = K.uploadbutton({
            button: K('.ke-upload-button', div)[0],
            fieldName: filePostName,
            form: K('.ke-form', div),
            target: target,
            width: 60,
            afterUpload: function (data) {
                dialog.hideLoading();
                if (data.error === 0) {
                    var url = data.url;
                    if (self.afterUpload) {
                        self.afterUpload.call(self, url, data, name);
                    }
                    if (!fillDescAfterUploadImage) {
                        clickFn.call(self, url, data.title, data.width, data.height, data.border, data.align);
                    } else {
                        K(".ke-dialog-row #remoteUrl", div).val(url);
                        K(".ke-tabs-li", div)[0].click();
                        K(".ke-refresh-btn", div).click();
                    }
                } else {
                    alert(data.message);
                }
            },
            afterError: function (html) {
                dialog.hideLoading();
                self.errorDialog(html);
            }
        });
        uploadbutton.fileBox.change(function (e) {
            localUrlBox.val(uploadbutton.fileBox.val());
        });
        viewServerBtn.hide();

        var originalWidth = 0, originalHeight = 0;

        function setSize(width, height) {
            widthBox.val(width);
            heightBox.val(height);
            originalWidth = width;
            originalHeight = height;
        }

        refreshBtn.click(function (e) {
            var tempImg = K('<img src="' + urlBox.val() + '" />', document).css({
                position: 'absolute',
                visibility: 'hidden',
                top: 0,
                left: '-1000px'
            });
            tempImg.bind('load', function () {
                setSize(tempImg.width(), tempImg.height());
                tempImg.remove();
            });
            K(document.body).append(tempImg);
        });
        widthBox.change(function (e) {
            if (originalWidth > 0) {
                heightBox.val(Math.round(originalHeight / originalWidth * parseInt(this.value, 10)));
            }
        });
        heightBox.change(function (e) {
            if (originalHeight > 0) {
                widthBox.val(Math.round(originalWidth / originalHeight * parseInt(this.value, 10)));
            }
        });
        urlBox.val(options.imageUrl);
        setSize(options.imageWidth, options.imageHeight);
        titleBox.val(options.imageTitle);
        alignBox.each(function () {
            if (this.value === options.imageAlign) {
                this.checked = true;
                return false;
            }
        });
        if (showRemote && tabIndex === 0) {
            urlBox[0].focus();
            urlBox[0].select();
        }
        return dialog;
    };
    self.plugin.image = {
        edit: function () {
            var img = self.plugin.getSelectedImage();
            self.plugin.imageDialog({
                imageUrl: img ? img.attr('data-ke-src') : 'http://',
                imageWidth: img ? img.width() : '',
                imageHeight: img ? img.height() : '',
                imageTitle: img ? img.attr('title') : '',
                imageAlign: img ? img.attr('align') : '',
                showRemote: true,
                showLocal: true,
                tabIndex: img ? 0 : imageTabIndex,
                clickFn: function (url, title, width, height, border, align) {
                    if (img) {
                        img.attr('src', url);
                        img.attr('data-ke-src', url);
                        img.attr('width', width);
                        img.attr('height', height);
                        img.attr('title', title);
                        img.attr('align', align);
                        img.attr('alt', title);
                    } else {
                        self.exec('insertimage', url, title, width, height, border, align);
                    }
                    setTimeout(function () {
                        self.hideDialog().focus();
                    }, 0);
                }
            });
        },
        'delete': function () {
            var target = self.plugin.getSelectedImage();
            if (target.parent().name == 'a') {
                target = target.parent();
            }
            target.remove();
            self.addBookmark();
        }
    };
    self.clickToolbar(name, self.plugin.image.edit);
});