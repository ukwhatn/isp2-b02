window.addEventListener("load", function () {
    // pathにerror=1が含まれていたら、エラーメッセージを表示する
    const url = new URL(location.href);
    const error = url.searchParams.get('error');
    if (error === '1') {
        alert('ログインに失敗しました。');
        // パスパラメータ削除
        history.replaceState('', '', location.pathname);
    }
});