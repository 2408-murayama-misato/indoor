//アカウント編集ボタンのアラート
function CheckChange(){
    let result = window.confirm("変更してよろしいですか？");
    if (!result) return false;
}

//アカウント削除ボタンのアラート
function CheckDelete(){
    let result = window.confirm("削除してよろしいですか？");
    if (!result) return false;
}

//購入ボタンのアラート
function CheckPurchase(){
    let result = window.confirm("購入してよろしいですか？");
    if (!result) return false;
}