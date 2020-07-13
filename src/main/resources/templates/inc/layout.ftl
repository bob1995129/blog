<#macro layout title>
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="utf-8">
        <title>${title}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <meta name="keywords" content="fly,layui,前端社区">
        <meta name="description" content="Fly社区是模块化前端UI框架Layui的官网社区，致力于为web开发提供强劲动力">
        <link rel="stylesheet" href="/layui/css/layui.css">
        <link rel="stylesheet" href="/css/global.css">
        <script src="/layui/layui.all.js"></script>
        <script src="/layui/layui.js"></script>
        <script src="/js/jquery-3.5.1.min.js"></script>
        <script src="/js/sockjs.js"></script>
         <script src="/js/stomp.js"></script>
         <script src="/js/im.js"></script>
         <script src="/js/jquery.min.js"></script>
         <script src="/js/chat.js"></script>
    </head>
    <body>
    <#include "/inc/common.ftl" /><#--分页-->
    <#include "/inc/header.ftl" /><#--包括头像和二级导航-->

    <#nested >
    <#include "/inc/footer.ftl" />

    <script>
        /*layui.cache.page = '';*/
        layui.cache.user = {
            username: '${profile.username!"游客"}'//session为空则为游客
            ,uid: ${profile.id!"-1"}
            ,avatar: '${profile.avatar!"/res/images/avatar/00.jpg"}'
            ,experience: 83
            ,sex: '${profile.sex!"男"}'
        };
        layui.config({
            version: "3.0.0"
            ,base: '/mods/' //这里实际使用时，建议改成绝对路径
        }).extend({
            fly: 'index'
        }).use('fly');
    </script>

<#--socket通信-->
    <script>
        function showTips(count) {
            var msg = $('<a class="fly-nav-msg" href="javascript:;">'+ count +'</a>');
            var elemUser = $('.fly-nav-user');
            elemUser.append(msg);
            msg.on('click', function(){
                location.href = "/user/mess";
            });
            layer.tips('你有 '+ count +' 条未读消息', msg, {
                tips: 3
                ,tipsMore: true
                ,fixed: true
            });
            msg.on('mouseenter', function(){
                layer.closeAll('tips');
            })
        }
        $(function () {
            var elemUser = $('.fly-nav-user');
            if(layui.cache.user.uid !== -1 && elemUser[0]){
                var socket = new SockJS("/websocket")
                stompClient = Stomp.over(socket);
                stompClient.connect({}, function (frame) {
                    stompClient.subscribe("/user/" + ${profile.id} + "/messCount", function (res) {

                        console.log(res);

                        // 弹窗
                        showTips(res.body);
                    })
                });

            }
        });
    </script>


    </body>
    </html>
</#macro>