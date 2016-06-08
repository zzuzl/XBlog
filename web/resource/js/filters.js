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

                console.log(now + "   " + date);

                var out = "";
                var gapSeconds = Math.floor((now - date) / 1000);
                if (gapSeconds < 60) {
                    out = gapSeconds + "秒前";
                } else {
                    var gapMinutes = Math.floor(gapSeconds / 60);
                    if (gapMinutes < 60) {
                        out = gapSeconds / 60 + "分钟前";
                    } else {
                        var gapHours = Math.floor(gapMinutes / 60);
                        if (gapHours < 24) {
                            out = gapHours + "小时前";
                        } else {
                            var gapDays = Math.floor(gapHours / 30);
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