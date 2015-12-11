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
  <script src="/resources/js/jquery.min.js"></script>
  <!-- Include all compiled plugins (below), or include individual files as needed -->
  <script src="/resources/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
<div class="page-header"><h1>Files</h1></div>

<#function si num>
  <#assign order     = num?round?c?length />
  <#assign thousands = ((order - 1) / 3)?floor />
  <#if (thousands < 0)><#assign thousands = 0 /></#if>
  <#assign siMap = [ {"factor": 1, "unit": ""}, {"factor": 1000, "unit": "K"}, {"factor": 1000000, "unit": "M"}, {"factor": 1000000000, "unit":"G"}, {"factor": 1000000000000, "unit": "T"} ]/>
  <#assign siStr = (num / (siMap[thousands].factor))?string("0.#") + siMap[thousands].unit />
  <#return siStr />
</#function>

<p class="lead">${directory}</p>
<table width="100%" class="table table-striped">
  <tr>
    <th width="70%" align="left">File</th>
    <th align="left">Size</th>
    <th align="left">Changed</th>
  </tr>
<#list files as file>
  <tr>
    <td>
      <a href="/files/${directory}/${file.name}">${file.name}</a>
      <#if file.file><div class="pull-right">
        <div class="btn-group">
          <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            tail <span class="caret"></span>
          </button>
          <ul class="dropdown-menu">
            <li><a href="/files/${directory}/${file.name}?tail=100">last 100 lines</a></li>
            <li><a href="/files/${directory}/${file.name}?tail=200">last 200 lines</a></li>
            <li><a href="/files/${directory}/${file.name}?tail=300">last 300 lines</a></li>
            <li><a href="/files/${directory}/${file.name}?tail=500">last 500 lines</a></li>
            <li><a href="/files/${directory}/${file.name}?tail=1000">last 1000 lines</a></li>
          </ul>
        </div>
      </div></#if>
    </td>
    <td>${si (file.length())}</td>
    <td>${file.lastModified()?number_to_datetime }</td>
  </tr>
</#list>
</table>
</div>
</body>
</html>