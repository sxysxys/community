/**
 * 第一级评论
 */
function post() {
    var questionId = $("#question_id").val();
    var contentText = $("#content_text").val();
    comment(questionId,contentText,1);
}

/**
 * 封装的评论方法
 * @param parentId
 * @param contentText
 * @param type
 */
function comment(parentId,contentText,type) {
    if (!contentText){
        alert("不能回复空内容奥");
        return;
    }
    /*    console.log(questionId);
        console.log(contentText);*/
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data:
            JSON.stringify({
                "parentId": parentId,
                "content": contentText,
                "type": type,
                "commentator": 1
            }),
        success: function (response) {
            if (response.code==200){
                window.location.reload();
            }else {
                if (response.code==1600){
                    var confirm = window.confirm(response.message);  //浏览器弹出框，此时没有登录
                    if (confirm==true){  //按下了确定按钮
                        window.open("https://github.com/login/oauth/authorize?client_id=fa5640b107cf8fc78bb9&redirect_uri=http://localhost:8882/callback&scope=user&state=1");
                        localStorage.setItem("closable",true);
                    }
                }else {
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType: "json"
    })
    ;
}

/**
 * 第二级评论
 */
function comment2(e) {
    var id = e.getAttribute("data-id");
    var input = $("#input-"+id).val();
    comment(id,input,2);
}
/**
 * 展开评论
 */
function collapseComment(e) {
    var id = e.getAttribute("data-id");
    var commentId = $("#comment-"+id);
    if (e.getAttribute("data-collapse")){
        commentId.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }else{
        //展开二级评论
        commentId.addClass("in");
        //标记二级评论状态
        e.setAttribute("data-collapse","in");
        e.classList.add("active");
    }
}