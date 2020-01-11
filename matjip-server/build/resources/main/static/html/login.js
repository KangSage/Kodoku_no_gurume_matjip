'use strict';

$('#login-form').submit((e) => {
  e.preventDefault();
  const target = $(e.target);
  const url = target[0].action;
  const data = target.serialize();
  $.post(url, data, 'JSON').done((res) => {
    console.log('resBody: %o', res);
    res.result === 'success' ? location.href = res.redirectUrl
      : location.reload();
  }).fail((err) => {
    console.log('err: %o', err);
    Swal.fire({
      type: 'error',
      title: '로그인 실패!',
      text: err.responseJSON.message
    });
  });
});
