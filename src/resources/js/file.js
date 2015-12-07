$(function() {

    var start = 0;

    function loadMore() {
      console.log("Loading page ");
      $.ajax('/files'+ file, {data: {start : start} , dataType:'json'}).done(function(data) {
        if (data) {
          for(var i = 0 ; i < data.length ; i++){
            $('#file_content').append(
                $('<div>', {'data-start': data[i].startPoint,'data-end': data[i].endPoint }).text(data[i].content)
            );
            start = data[i].endPoint;
          }
        }
      });
      $(window).on('scroll', onScroll);
    }

    function onScroll() {
      if (window.innerHeight + document.body.scrollTop >= document.body.offsetHeight) {
        $(window).off('scroll');
        loadMore();
      }
    }
    onScroll();
  });