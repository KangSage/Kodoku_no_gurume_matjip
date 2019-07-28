/**
 * arrow func's this = window object
 */
"use strict";

let $signUpForm = $('#sign-up-form');

$(document).ready(() => {
    setAjaxCsrfToken();
});

$signUpForm.submit((e) => {
    e.preventDefault();
    Swal.fire({
        title: '이 정보로 가입하시겠습니까?',
        text: "일부 개인정보는 회원가입 후 변경할 수 없습니다.",
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#9cd60d',
        cancelButtonColor: '#dd6070',
        confirmButtonText: '가입',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.value) {
            $.postJSON('/user/register', $('form').serializeObject())
                .done((res) => {
                    if (res.result === 'success') {
                        Swal.fire({
                            type: 'success',
                            title: '가입 성공!',
                            text: '성공적으로 가입되셨습니다.'
                        }).then(() => {
                            location.href = '/html/login.html';
                        });
                    }
                }).fail((error) => {
                    console.log('error : %o', error)
            });
        }
    })

});


