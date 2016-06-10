/**
 * 日期格式化filter
 */
(function () {
    'use strict';

    angular.module('app')
        .filter('dateFormat', function () {
            return function (input) {
                input = input || '';
                var date = input;
                var now = new Date().getTime();

                var out = "";
                var gapSeconds = Math.round((now - date) / 1000);
                if (gapSeconds < 60) {
                    out = gapSeconds + "秒前";
                } else {
                    var gapMinutes = Math.round(gapSeconds / 60);
                    if (gapMinutes < 60) {
                        out = gapMinutes + "分钟前";
                    } else {
                        var gapHours = Math.round(gapMinutes / 60);
                        if (gapHours < 24) {
                            out = gapHours + "小时前";
                        } else {
                            var gapDays = Math.round(gapHours / 30);
                            if (gapDays < 30) {
                                out = gapDays + "天前";
                            } else {
                                out = date.getFullYear() + "-" + date.getMonth() + "-" + date.getDay() + " " +
                                    date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds()
                            }
                        }
                    }
                }

                return out;
            };
        });
})();