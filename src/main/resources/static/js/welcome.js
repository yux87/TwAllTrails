axios.get('/getUserProfiles')
    .then(response => {
        const member = response.data; // 獲取伺服器返回的會員物件
        const nickname = member.nickname; // 從會員物件中提取 nickname 屬性
        const memberid = member.memberid; // 從會員物件中提取 memberid 屬性
        console.log('獲取會員信息成功，會員id為:', memberid);
        // 顯示 SweetAlert 彈窗,並將 nickname 插入到 text 中
        Swal.fire({
            title: '登入成功!',
            text: `歡迎，${nickname}!`,
            icon: 'success',
            confirmButtonText: '繼續',
            allowOutsideClick: false
        }).then(result => {
            try {
                const previousTwoPagesURL = document.referrer;
                console.log(previousTwoPagesURL);
                // 檢查 URL 中是否包含 "login" 或 "verify"
                if (previousTwoPagesURL && (previousTwoPagesURL.includes("login") || previousTwoPagesURL.includes("verify"))) {
                    // 如果包含就跳轉到 '/index'
                    window.location.href = '/index';
                    // console.log("跳轉到 /index");
                } else {
                    // 否則返回上兩頁
                    window.history.go(-2);
                    // console.log("返回上兩頁");
                }
            } catch (error) {
                console.error("錯誤:", error);
            }
        });
    })
    .catch(error => {
        console.error('獲取會員信息失敗:', error);
        // 處理錯誤情況
    });