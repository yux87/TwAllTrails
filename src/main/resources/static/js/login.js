const signupForm = document.getElementById('signup-form');

signupForm.addEventListener('submit', (event) => {
    event.preventDefault();
    Swal.fire({
        title: '傳送中...請稍後',
        icon: 'info',
        allowOutsideClick: false, // 防止用戶點擊外部關閉彈窗
        showConfirmButton: false, // 不顯示確認按鈕
        timerProgressBar: true, // 顯示定時器進度條
        showLoaderOnConfirm: true, // 顯示等待動畫
    });
    const formData = new FormData(signupForm);
    const nickname = formData.get('nickname');
    const username = formData.get('username');
    const password = formData.get('password');

    axios.post('/api/members/register', {
        nickname,
        username,
        password
    })
        .then((response) => {
            console.log(response.data);
            Swal.fire({
                title: '註冊還差一步..',
                text: '確認信件已發送，請查收郵件並進行確認。',
                icon: 'success',
                confirmButtonText: 'OK',
                allowOutsideClick: false
            }).then((result) => {
                if (result.isConfirmed) {
                    window.close();
                }
            });
        })
        .catch((error) => {
            console.error(error);
            Swal.fire({
                title: 'Error!',
                text: '註冊信箱已存在。',
                icon: 'error',
                confirmButtonText: 'Okay'
            })
        });
});

// 電子郵件格式正則表達式
const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

// 註冊信箱輸入欄位
const usernameS = document.getElementById('username-s');
const mailErrorS = document.getElementById('mail-error-s');

// 登入信箱輸入欄位
const usernameL = document.getElementById('username-l');
const mailErrorL = document.getElementById('mail-error-l');

// 註冊信箱輸入欄位失去焦點事件
usernameS.addEventListener('blur', function() {
    if (!emailRegex.test(usernameS.value)) {
        mailErrorS.textContent = '請輸入有效的電子郵件地址';
    } else {
        mailErrorS.textContent = '';
    }
});

// 登入信箱輸入欄位失去焦點事件
usernameL.addEventListener('blur', function() {
    if (!emailRegex.test(usernameL.value)) {
        mailErrorL.textContent = '請輸入有效的電子郵件地址';
    } else {
        mailErrorL.textContent = '';
    }
});

function typeText(account) {
    // 將 id 為 username-l 的 value 設為 <account>@<account>.com
    document.getElementById('username-l').value = `${account}@${account}.com`;

    // 將 id 為 password-l 的 value 設為 "123456"
    document.getElementById('password-l').value = '123456';
}
