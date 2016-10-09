<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!-- A Nav Bar -->
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
  <div class="container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#"><tiles:getAsString name="brand" /></a>
      
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="/home">Home</a></li>
      <li><a href="/uploadAndCompare">Upload To Compare</a></li>
    
    </ul>
     

    </div><!-- /.container-fluid -->
</div>