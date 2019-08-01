'use strict';

$('#logout-btn').on('click', () => {
    $.post('/j_security_logout').done((res)=>{
        location.href = res.forwardUrl;
    }).fail((err)=>{
        console.log(err);
    });
});