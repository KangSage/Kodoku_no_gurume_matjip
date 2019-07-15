"use strict";
$('#login-form').submit((e) => {
    e.preventDefault();
    const target = $(e.target);
    const url = target[0].action;
    const data = target.serialize();
    $.post(url, data, 'JSON').done(({result}) => {
    result === 'success' ? location.href = '/html/user/list.html'
                         : location.reload();
    }).fail((err) => {
        console.log(err);
    });
});