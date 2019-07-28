/**
 * arrow func's this = window object
 */

"use strict";

$(document).ready(() => {
    setAjaxCsrfToken();
});

$('#sign-up-form').submit((e) => {
    e.preventDefault();
    Swal.fire({
        title: '이 정보로 가입하시겠습니까?',
        text: "일부 개인정보는 회원가입 후 변경할 수 없습니다.",
        type: 'warning',
        showCancelButton: true,
        customClass: {
            confirmButton: 'btn btn-primary',
            cancelButton: 'btn btn-danger'
        },
        // confirmButtonColor: '#3085d6',
        // cancelButtonColor: '#d33',
        confirmButtonText: '가입'
    }).then((result) => {
        if (result.value) {
            $.postSecJSON('/user/register', $('form').serializeObject())
                .done((result) => {
                    Swal.fire('가입 성공!', '성공적으로 가입되셨습니다.')
                        .then(() => {
                            location.href = '/html/login.html';
                        });
                }).fail((error) => {
                    console.log('error : %o', error)
            });
        }
    })

});


