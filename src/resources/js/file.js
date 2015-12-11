$(function() {

    $(window).on('resize' , function(){
        $('#file_content').css({height: $(window).height() - $('#file_content').offset().top - 10 });
    }).resize();


    var lastClass = '';
    function appendLines(data){
        if (data) {
          for(var i = 0 ; i < data.length ; i++){
              if(data[i].content.indexOf(' WARN ') >=0){
                  lastClass = 'bg-warning  text-warning';
              }else if(data[i].content.indexOf(' ERROR ') >=0){
                  lastClass = 'bg-danger text-danger';
              }else if(data[i].content.indexOf(' FATAL ') >=0){
                  lastClass = 'bg-danger text-danger';
              }else if(data[i].content.indexOf(' INFO ') >=0){
                  lastClass = '';
              }else if(data[i].content.indexOf(' DEBUG ') >=0){
                  lastClass = 'text-mute';
              }else if(data[i].content.indexOf(' TRACE ') >=0){
                  lastClass = 'text-mute';
              }
            $('#file_content div.container').append(
                $('<div>', {'data-start': data[i].startPoint,'data-end': data[i].endPoint ,'class':lastClass}).text(data[i].content)
            );
            start = data[i].endPoint;
          }
        }
    }



    var start = 0;


    function onScroll() {
      var containerHeight = $('#file_content div.container').height();
      var bottomPosition = $('#file_content').scrollTop() + $('#file_content').height();
      if (  bottomPosition >=  containerHeight) {
        $('#file_content').off('scroll');
        loadMore();
      }
    }
    function loadMore() {
       console.log("Loading more starting from  " + start);
       $.ajax('/files'+ file, {data: {start : start} , dataType:'json'}).done(function(data) {
         appendLines(data);
       });
      $('#file_content').on('scroll', onScroll);
     }
    if(tail){
       $.ajax('/files'+ file, {data: {tail : tail} , dataType:'json'}).done(function(data) {
         appendLines(data);
         $('#file_content').on('scroll', onScroll);
       });
    }else{
       loadMore();
    }





  });