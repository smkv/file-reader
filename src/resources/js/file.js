$(function() {


    $.ajaxSetup({
        beforeSend:function(){
            // show gif here, eg:
            $("#loading_indicator").show();
        },
        complete:function(){
            // hide gif here, eg:
            $("#loading_indicator").hide();
        }
    });

    $(window).on('resize' , function(){
        $('#file_content').css({height: $(window).height() - $('#file_content').offset().top - 25 });
    }).resize();


    var lastClass = '';
    function makeLine(data){
      if(data.content.indexOf(' WARN ') >=0){
          lastClass = 'bg-warning  text-warning';
      }else if(data.content.indexOf(' ERROR ') >=0){
          lastClass = 'bg-danger text-danger';
      }else if(data.content.indexOf(' FATAL ') >=0){
          lastClass = 'bg-danger text-danger';
      }else if(data.content.indexOf(' INFO ') >=0){
          lastClass = '';
      }else if(data.content.indexOf(' DEBUG ') >=0){
          lastClass = 'text-mute';
      }else if(data.content.indexOf(' TRACE ') >=0){
          lastClass = 'text-mute';
      }
      return $('<div>', {'data-start': data.startPoint,'data-end': data.endPoint ,'class':lastClass}).text(data.content);
    }


    function appendLines(data){
        if (data) {

        //$('.last-end', $('#file_content')).removeClass('last-end');
        $('#file_content div div').last().addClass('last-end');

          for(var i = 0 ; i < data.length ; i++){
            $('#file_content div.container').append(makeLine(data[i]));
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
        loadMore(10);
      }
    }
    function loadMore(size) {
       console.log("Loading more starting from  " + start);
       $.ajax('/logs'+ file, {data: {start : start, size: size} , dataType:'json'}).done(function(data) {
         appendLines(data);
       });
      $('#file_content').on('scroll', onScroll);
     }
    if(tail){
       $.ajax('/logs'+ file, {data: {tail : tail} , dataType:'json'}).done(function(data) {
         appendLines(data);
         $('#file_content').on('scroll', onScroll);
       });
    }else{
       loadMore(200);
    }

   function loadPrev(event) {
           if(event.deltaY > 0 && $('#file_content').scrollTop() == 0){
               console.log('Load previous');
               $('#file_content').off('mousewheel');

                var el = $('#file_content div div').first()
                var start = $('#file_content div div').first().attr('data-start');
                if(start <=0 ){
                return;
                }

                $.ajax('/logs'+ file, {data: {start : start, direction: 'backward' , size: 10} , dataType:'json'}).done(function(data) {

                    if (data) {
                      //$('.last-end', $('#file_content')).removeClass('last-end');
                      el.addClass('last-end');
                      for(var i = 0 ; i < data.length ; i++){
                        el.before(makeLine(data[i]));
                      }
                    }


                    $('#file_content').on('mousewheel' , loadPrev);
                });
           }
       }

    $('#file_content').on('mousewheel' , loadPrev);


  });