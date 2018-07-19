
    var toastTimer = null,
    UA = window.navigator.userAgent,
    uaName = UA.indexOf("Mac")!== -1 ? "Mac" : UA.indexOf("Win")!==-1 ? "Win" : "other";
    var start = 0;
    $.fn.extend({
       toast: function(text,type){
        clearTimeout(toastTimer);
        var me = this;
        var icon = "";

          switch(type){
            case 'success':
              icon = "<span class='iconfont'></span>";
              break;
            case 'error':
              icon = "<span class='iconfont'></span>";
              break;
            case 'warning':
              icon = "<span class='iconfont'></span>";
              break;
          }
          var node = "<div class='toast toast_"+type+"'>"+icon+text+"</div>";
          $(this).find(".toast").remove();
          $(me).append(node);
          $(me).find(".toast").animate({opacity:1},500,'linear',function(){
            toastTimer = setTimeout(function(){
              $(me).find(".toast").animate({opacity:0},500,'linear',function(){
                $(this).remove()
              })
            },3000)
          });
       },
       toastText: function(text,type){
        clearTimeout(toastTimer);
        var me = this;
        var icon = "";

          switch(type){
            case 'success':
              icon = "<span class='iconfont'></span>";
              break;
            case 'error':
              icon = "<span class='iconfont'></span>";
              break;
            case 'warning':
              icon = "<span class='iconfont'></span>";
              break;
          }
          var node = "<div class='toast toast_text_"+type+"'>"+icon+text+"</div>";
          $(this).find(".toast").remove();
          $(me).append(node);
          $(me).find(".toast").animate({opacity:1},500,'linear',function(){
            toastTimer = setTimeout(function(){
              $(me).find(".toast").animate({opacity:0},500,'linear',function(){
                $(this).remove()
              })
            },3000)
          });
       }
    })