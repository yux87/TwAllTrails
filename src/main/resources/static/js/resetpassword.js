const app = Vue.createApp({
    data() {
        return {
            emailRegex: /^[^\s@]+@[^\s@]+\.[^\s@]+$/
        }
    },
    methods: {
        validateUsername(event) {
            const usernameInput = event.target;
            const usernameError = document.getElementById('usernameError');

            if (!this.emailRegex.test(usernameInput.value)) {
                usernameError.textContent = '請輸入有效的電子郵件地址';
            } else {
                usernameError.textContent = '';
            }
        },
        validatePassword() {
            const passwordInput = document.getElementById('password');
            const confirmPasswordInput = document.getElementById('confirmPassword');
            // const passwordError = document.getElementById('passwordError');
            const confirmPasswordError = document.getElementById('confirmPasswordError');

            if (passwordInput.value !== confirmPasswordInput.value) {
                // passwordError.textContent = '密碼和確認密碼不一致';
                confirmPasswordError.textContent = '密碼和確認密碼不一致';
            } else {
                // passwordError.textContent = '';
                confirmPasswordError.textContent = '';
            }
        },
        async resetPassword(event) {
            event.preventDefault();

            const usernameInput = document.getElementById('username');
            const passwordInput = document.getElementById('password');

            const sendingToast = Swal.fire({
                title: '傳送中...請稍後',
                icon: 'info',
                allowOutsideClick: false,
                showConfirmButton: false,
                timerProgressBar: true
            });

            try {
                const response = await axios.post('/api/members/resetpassword', {
                    "username": usernameInput.value,
                    "password": passwordInput.value
                });

                if (response.status >= 200 && response.status < 300) {
                    sendingToast.close();
                    const result = await Swal.fire({
                        icon: 'success',
                        title: '密碼已成功重設',
                        text: '請查看您的郵件，以確認重設密碼。',
                        showConfirmButton: true
                    });

                    if (result.isConfirmed) {
                        window.close();
                    }
                } else {
                    sendingToast.close();
                    Swal.fire({
                        icon: 'error',
                        title: '錯誤',
                        text: '請求失敗，該用戶不存在。'
                    });
                }
            } catch (error) {
                sendingToast.close();
                Swal.fire({
                    icon: 'error',
                    title: '錯誤',
                    text: '請求失敗，該用戶不存在'
                });
            }
        }
    },
    mounted() {
        const form = document.getElementById('resetPasswordForm');
        const usernameInput = document.getElementById('username');
        const passwordInput = document.getElementById('password');
        const confirmPasswordInput = document.getElementById('confirmPassword');

        usernameInput.addEventListener('blur', this.validateUsername);
        passwordInput.addEventListener('input', this.validatePassword);
        confirmPasswordInput.addEventListener('input', this.validatePassword);
        form.addEventListener('submit', this.resetPassword);
    }
});

app.mount('#app');