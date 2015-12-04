<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
  <title>Log files ${directory}</title>

  <!-- Bootstrap -->
  <link href="/resources/css/bootstrap.min.css" rel="stylesheet">

  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
  <script src="/resources/jquery.min.js"></script>
  <!-- Include all compiled plugins (below), or include individual files as needed -->
  <script src="/resources/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
  <div class="page-header"><h1>${directory}${file.name}</h1></div>


<pre id="file_content"><#if startPage gt 0 >...skipped ...</#if> </pre>

<script>
  $(function() {
    var page = ${startPage!"0"};
    function loadMore() {
      console.log("Loading page " + page);
      $.ajax('/files${directory}${file.name}', {data: {page: page}}).done(function(data) {
        if (data) {
          $('#file_content').append($('<span>', {'data-page': page++}).text(data));
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

</script>

</body>
</html>