/**
 * arrow func's this = window object
 */

"use strict";

$(document).ready(() => {
    setAjaxCsrfToken();
});

$('#sign-up-form').submit((e) => {
    e.preventDefault();
    $.postSecJSON('/user/register', $('form').serializeObject())
        .done((result) => {
            console.log('result : %o', result);
        }).fail((error) => {
            console.log('error : %o', error)
        });
});


