
        // 顯示 SweetAlert 彈窗
        Swal.fire({
            title: '驗證失敗',
            text: '驗證碼無效或已過期。',
            icon: 'error',
            confirmButtonText: '回到首頁',
            allowOutsideClick: false // 防止用戶點擊外部關閉彈窗
        }).then(result => {
            if (result.isConfirmed) {
                window.location.href = '/index';
            }
        });