/**
 * 日期格式化filter
 */
(function () {
    'use strict';

    angular.module('app')
        .filter('dateFormat', function () {
            return function (input) {
                input = input || '';
                return moment(input).fromNow();
            };
        });
})();

/**
 * html过滤
 */
(function () {
    'use strict';

    angular.module('app')
        .filter('trustHtml', function ($sce) {
            return function (input) {
                return $sce.trustAsHtml(input);
            }
        });
})();

/**
 * 消息类型过滤
 */
(function () {
    'use strict';

    angular.module('app')
        .filter('msgType', function () {
            return function (input) {
                var result = "系统消息";
                switch(input) {
                    case 1:
                        result = "系统消息";
                        break;
                    case 2:
                        result = "关注人消息";
                        break;
                    case 3:
                        result = "活动消息";
                        break;
                }
                return result;
            }
        });
})();