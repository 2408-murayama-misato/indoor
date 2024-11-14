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

//発送完了のボタンアラート
function CheckShipped(){
    let result = window.confirm("発送完了通知を購入者にお送りしてよろしいですか？");
    if (!result) return false;
}

$( function() {
            $('button[name="cart-button"]').on('click',function(){
                 if (confirm("カートから削除してよろしいですか？")){
                    return true;
                 } else {
                     return false;
                 }
             });
          });
