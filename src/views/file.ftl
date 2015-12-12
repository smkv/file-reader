<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8" />
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

  <script src="/resources/js/jquery.min.js"></script>
  <script src="/resources/js/bootstrap.min.js"></script>
  <script src="/resources/js/jquery.mousewheel.min.js"></script>
  <script>
    var file = '${directory}/${file.name}';
    var tail = ${tail!'undefined'}
  </script>

  <style>
      #file_content{
        overflow: auto;
        font-family: monospace;
      }
      #file_content > div.container > div:hover{
        background-color: #eee;
      }


      .last-end:before{
        content:'\2023';
        position: relative;
          margin-left: -10px;
      }
  </style>
</head>
<body>

<div class="container">
    <div class="page-header"><h1>Log viewer</h1>
    </div>
    <div class="lead">
        <div class="pull-right">
            <div class="btn-group">
                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                   Reload file <span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li><a href="/logs${directory}/${file.name}">From beginning</a></li>
                    <li><a href="/logs${directory}/${file.name}?tail=100">last 100 lines</a></li>
                    <li><a href="/logs${directory}/${file.name}?tail=200">last 200 lines</a></li>
                    <li><a href="/logs${directory}/${file.name}?tail=300">last 300 lines</a></li>
                    <li><a href="/logs${directory}/${file.name}?tail=500">last 500 lines</a></li>
                    <li><a href="/logs${directory}/${file.name}?tail=999">last 999 lines</a></li>
                </ul>
            </div>
        </div>
        <#if parent??><a class="btn btn-default btn-sm" href="/logs${parent}"><span class="glyphicon glyphicon-level-up"></span> Level up</a></#if>
        ${directory}/${file.name} (${sizeHR (file.length())})
        <small id="loading_indicator" style="display: none;">Loading data ...</small>
        <div class="clearfix"></div>
    </div>

    <div class="panel panel-default">
        <div id="file_content" class="panel-body"><div class="content"></div></div>
    </div>


</div>

<script src="/resources/js/file.js"></script>
</body>
</html>