//アカウント編集ボタンのアラート
function CheckChange(){
    let result = window.confirm("変更してもよろしいですか？");
    if (!result) return false;
}

//アカウント削除ボタンのアラート
function CheckDelete(){
    let result = window.confirm("削除してもよろしいですか？");
    if (!result) return false;
}