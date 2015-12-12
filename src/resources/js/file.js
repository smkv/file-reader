$(function () {

    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE ");

    var file_content = $('#file_content');

    $.ajaxSetup({
        beforeSend: function () {
            // show gif here, eg:
            $("#loading_indicator").show();
        },
        complete: function () {
            // hide gif here, eg:
            $("#loading_indicator").hide();
        }
    });

    $(window).on('resize', function () {
        file_content.css({height: $(window).height() - file_content.offset().top - 25});
    }).resize();

    function showFileVisiblePercent() {
        var lastVisible = $(document.elementFromPoint(file_content.offset().left + 30, file_content.offset().top + $('#file_content').height()));
        var end = lastVisible.attr('data-end');
        var percent = end * 100 / fileSize;
        if (!isNaN(percent)) $('#load_percent').text(Math.round(percent) + "%");
    }


    var lastClass = '';
    function getClassName(text){
            if (text.indexOf(' WARN ') >= 0) {
                lastClass = 'bg-warning  text-warning';
            } else if (text.indexOf(' ERROR ') >= 0) {
                lastClass = 'bg-danger text-danger';
            } else if (text.indexOf(' FATAL ') >= 0) {
                lastClass = 'bg-danger text-danger';
            } else if (text.indexOf(' INFO ') >= 0) {
                lastClass = '';
            } else if (text.indexOf(' DEBUG ') >= 0) {
                lastClass = 'text-mute';
            } else if (text.indexOf(' TRACE ') >= 0) {
                lastClass = 'text-mute';
            }
            return lastClass;
    }

    function makeLine(data) {

        return $('<div>', {
            'data-start': data.startPoint,
            'data-end': data.endPoint,
            'class': getClassName(data.content)
        }).text(data.content);
    }

    function recolorize(element , count){
        for(var i = 0 ; i < count ; i++){
            element = element.next();
            element.removeClass('bg-warning  text-warning bg-danger text-danger text-mute').addClass(getClassName(element.text()));
        }
    }


    function appendLines(data) {
        if (data) {
            $('div.content div', file_content).last().addClass('last-end');

            for (var i = 0; i < data.length; i++) {
                $('div.content', file_content).append(makeLine(data[i]));
                start = data[i].endPoint;
            }
        }
    }


    var start = 0;


    function onScroll() {
        var containerHeight = $('div.content', file_content).height();
        var bottomPosition = file_content.scrollTop() + file_content.height();
        if(msie){
            bottomPosition += 30;
        }
        if (bottomPosition >= containerHeight) {
            file_content.off('scroll');
            loadMore(10);
        }
    }

    function loadMore(size) {
        console.log("Loading more starting from  " + start);
        $.ajax('/logs' + file, {data: {start: start, size: size}, dataType: 'json'}).done(function (data) {
            appendLines(data);
        });
        file_content.on('scroll', onScroll);
    }

    if (tail) {
        $.ajax('/logs' + file, {data: {tail: tail}, dataType: 'json'}).done(function (data) {
            appendLines(data);
            file_content.on('scroll', onScroll);
            showFileVisiblePercent();
        });
    } else {
        loadMore(200);
    }

    function loadPrev(event) {


        if (event.deltaY > 0 && file_content.scrollTop() == 0) {
            console.log('Load previous');
            file_content.off('mousewheel');

            var el = $('div.content div', file_content).first();
            var start = $('div.content div', file_content).first().attr('data-start');
            if (start <= 0) {
                return;
            }

            $.ajax('/logs' + file, {
                data: {start: start, direction: 'backward', size: 10},
                dataType: 'json'
            }).done(function (data) {

                if (data) {
                    el.addClass('last-end');
                    for (var i = 0; i < data.length; i++) {
                        el.before(makeLine(data[i]));
                    }
                    recolorize(el.prev() ,100);
                }


                file_content.on('mousewheel', loadPrev);
            });
        }

        showFileVisiblePercent();
    }

    file_content.on('mousewheel', loadPrev);


});