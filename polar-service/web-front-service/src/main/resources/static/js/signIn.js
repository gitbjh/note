$(document).ready(() => {

    $('#signin').click(() => {

        let userId = $('#user_id').val();
        let password = $('#password').val();

        let formData = {
            userId : userId,
            password : password
        }

        $.ajax({
            type: 'POST',
            url: '/login',
            data: JSON.stringify(formData), // 데이터를 JSON 형식으로 변환
            contentType: 'application/json; charset=utf-8', // 전송 데이터의 타입, json으로 보내겠다 알려주는 것
            dataType: 'json', // 서버에서 받을 데이터의 타입
            success: (response) => {
                console.log(response);
                alert(response.message);
                localStorage.setItem("accessToken", response.accessToken)
                window.location.href = response.url;
            },
            error: (error) => {
                console.log('오류발생 : ', error);
                alert('로그인 중 오류가 발생했습니다.');
            }
        });

    });

});