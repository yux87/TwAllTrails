const title = document.querySelector('.title')
const leaf1 = document.querySelector('.leaf1')
const leaf2 = document.querySelector('.leaf2')
const bush2 = document.querySelector('.bush2')
const mount1 = document.querySelector('.mount1')
const mount2 = document.querySelector('.mount2')

document.addEventListener('scroll', function() {
    let value = window.scrollY
    // console.log(value)
    title.style.marginTop = value * 1.1 + 'px'

    leaf1.style.marginLeft = -value + 'px'
    leaf2.style.marginLeft = value + 'px'

    bush2.style.marginBottom = -value + 'px'

    mount1.style.marginBottom = -value * 1.1 + 'px'
    mount2.style.marginBottom = -value * 1.2 + 'px'
})

window.onload = async function () {
    const memberTag = document.getElementById('member-tag');

    // 發送 GET 請求
    const response = await axios.get('/getUserProfiles');
    memberid = response.data.memberid;
    // 根據響應結果導向不同的頁面
    if (memberid != null) {
        // 導向會員頁面
        memberTag.href = '/member';
        memberTag.innerHTML = '用戶中心';
        console.log('導向會員頁面');
    } else {
        // 導向登入頁面
        memberTag.href = '/login';
        memberTag.innerHTML = '登入';
        console.log('導向登入頁面');
    }
};


// 檢查會員 ID,如果為 2 則顯示商品管理選項
axios.get('/getUserProfiles').then(response => {
    const member = response.data;
    if (member.memberid === 2) {
        document.getElementById('productManagementLink').style.display = 'block';
    }
}).catch(error => {
    console.error('獲取會員信息失敗:', error);
});
