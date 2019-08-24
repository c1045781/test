function post() {
    var id = $('#question_id').val();
    var content = $('#comment_content').val();
    comment(content, 1, id);

}

function comment(content, type, id) {
    if (!content) {
        alert("内容不能为空");
        return;
    }
    $.ajax({
        type: 'POST',
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "type": type,
            "content": content,
            "parentId": id
        }),
        success: function (date) {
            if (date.code == 2003) {
                confirm(date.message);
                window.localStorage.setItem("closable", true);
                window.open("https://github.com/login/oauth/authorize?client_id=d87b2b59752a3f484ac6&scope=user&redirect_uri=http://localhost:8080/callback&state=1");
            } else if (date.code == 200) {
                window.location.reload();
            } else {
                alert(date.message);
            }
        },
        dataType: "json"
    });
}

function comment_second(data) {
    var parent_id = data.getAttribute("data-id");
    var content = $('#comment-input-' + parent_id).val();
    comment(content, 2, parent_id);

}

function collapse(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    if (comments.hasClass("in")) {
        comments.removeClass("in");
        e.classList.remove("active");
    } else {
        if ($("#comment-" + id).children().length == 1) {
            $.getJSON("/comment/" + id, function (data) {
                var items = "";
                $.each(data.data, function (index, comment) {
                    items = items +
                        "<div class='media comment-div'>\n" +
                        "<div class='media-left'>\n" +
                        "<img class='media-object ' src=" + comment.user.avatarUrl + ">\n" +
                        "</div>\n" +
                        "<div class='media-body'>\n" +
                        "<h4 class='media-heading comment-name' >" + comment.user.name + "</h4>\n" +
                        "<span class='comment-content' >" + comment.content + "</span>\n" +
                        "<div class='comment-icon'>\n" +
                        "<span class='glyphicon glyphicon-thumbs-up icon' aria-hidden='true'></span>\n" +
                        "<span class='pull-right'>"+ moment(comment.gmtCreate).format('YYYY-MM-DD hh:mm:ss')+"</span>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "</div>";
                    console.log(items);
                });
                $("#comment-" + id).prepend(items);

                comments.addClass("in");
                e.classList.add("active");
            });
        } else {
            comments.addClass("in");
            e.classList.add("active");
        }
    }
}

function selectedTag(e) {
    var tag = e.getAttribute("data-tag");
    var tags = $("#tag").val();
    var tagArr = tags.split(',');
    if(tags){
        if($.inArray(tag,tagArr) == -1 ){
            $("#tag").val(tags+","+tag);
        }
    }else{
        $("#tag").val(tag);
    }
}

function showTag() {
    $("#selectTag").toggle();
}
