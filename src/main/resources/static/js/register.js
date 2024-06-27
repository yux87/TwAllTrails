        // 顯示 SweetAlert 彈窗
        Swal.fire({
            title: '註冊成功!',
            text: `帳號已啟動，請登入。`,
            icon: 'success',
            confirmButtonText: '登入頁面',
            allowOutsideClick: false // 防止用戶點擊外部關閉彈窗
        }).then(result => {
            if (result.isConfirmed) {
                window.location.href = '/login';
            }
        });