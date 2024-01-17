/**
 * ログインチェック
 */
const checkLogin = () => {
    fetch(
        '/isp2/actor',
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        }).then(
        response => {
            console.log(location.pathname);
            if (response.status === 200) {
                if (location.pathname === '/isp2/signin' || location.pathname === '/isp2/signin/') {
                    location.href = '/isp2';
                } else if (location.pathname === '/isp2/signup' || location.pathname === '/isp2/signup/') {
                    location.href = '/isp2';
                }
            } else if (response.status === 401) {
                if (location.pathname === '/isp2' || location.pathname === '/isp2/') {
                    location.href = '/isp2/signin';
                }
            } else {
                alert('ERROR');
            }
        }
    )
}

window.addEventListener("load", function () {
    checkLogin();
});

/**
 * logout
 */
const logout = () => {
    const confirmLogout = confirm('ログアウトしますか？');
    if (!confirmLogout) {
        return;
    }
    fetch(
        '/isp2/session',
        {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        }).then(
        response => {
            if (response.status === 200) {
                location.href = '/isp2';
            } else {
                alert('ERROR');
            }
        }
    )
};