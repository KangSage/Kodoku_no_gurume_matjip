"use strict";

const getCookie = function(name) {
    const value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return value ? value[2] : null;
};

/// AJAX 통신의 header에 csrf token을 설정한다.
const setAjaxCsrfToken = () => {
    let csrfHeader = 'X-XSRF-TOKEN';
    let csrfToken = getCookie("XSRF-TOKEN");
    $.ajaxSetup({
        beforeSend : (jqxhr) => {
            jqxhr.setRequestHeader(csrfHeader, csrfToken);
        }
    });
};

$.fn.serializeObject = function () {
    "use strict";
    const result = {};
    const extend = function (i, element) {
        const node = result[element.name];
        if ("undefined" !== typeof node && node !== null) {
            if ($.isArray(node)) {
                node.push(element.value)
            } else {
                result[element.name] = [node, element.value]
            }
        } else {
            result[element.name] = element.value
        }
    };
    $.each(this.serializeArray(), extend);
    return result
};

$['postSecJSON'] = (url, data) => {
    return $.ajax({
        url: url,
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'JSON',
        data: JSON.stringify(data)
    });
};