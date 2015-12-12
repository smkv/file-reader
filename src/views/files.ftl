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
  <script src="/resources/js/html5shiv.min.js"></script>
  <script src="/resources/js/respond.min.js"></script>
  <![endif]-->

  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
  <script src="/resources/js/jquery.min.js"></script>
  <!-- Include all compiled plugins (below), or include individual files as needed -->
  <script src="/resources/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
<div class="page-header">
  <h1>Log viewer </h1>
</div>


<p class="lead">
  <#if parent??><a class="btn btn-default btn-sm" href="/logs${parent}"><span class="glyphicon glyphicon-level-up"></span> Level up</a></#if>
  ${directory}
</p>
<table width="100%" class="table table-striped">
  <tr>
    <th width="70%" align="left">File</th>
    <th align="left">Size</th>
    <th align="left">Changed</th>
  </tr>
<#list files as file>
  <tr>
    <td>
      <a href="/logs${directory}/${file.name}">${file.name}</a>
      <#if file.file><div class="pull-right">
        <div class="btn-group">
          <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            tail <span class="caret"></span>
          </button>
          <ul class="dropdown-menu">
            <li><a href="/logs${directory}/${file.name}?tail=100">last 100 lines</a></li>
            <li><a href="/logs${directory}/${file.name}?tail=200">last 200 lines</a></li>
            <li><a href="/logs${directory}/${file.name}?tail=300">last 300 lines</a></li>
            <li><a href="/logs${directory}/${file.name}?tail=500">last 500 lines</a></li>
            <li><a href="/logs${directory}/${file.name}?tail=999">last 999 lines</a></li>
          </ul>
        </div>
      </div></#if>
    </td>
    <td><#if file.file>${sizeHR (file.length())}</#if></td>
    <td>${file.lastModified()?number_to_datetime }</td>
  </tr>
</#list>
</table>
</div>
</body>
</html>