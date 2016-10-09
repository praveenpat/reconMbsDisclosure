<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title><tiles:getAsString name="title" /></title>

        <!-- Latest compiled and minified CSS 3.2.0 -->
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
        
        <!-- Optional theme bootstrap-theme.min.css -->
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">

        <link rel="stylesheet" href="/static/css/site.css">
        
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <body>
        <!-- Header -->
        <tiles:insertAttribute name="header" />
        <!-- Body -->
        <div class="container">
	        <tiles:insertAttribute name="body" />
	        <!-- Footer -->
	        <tiles:insertAttribute name="footer" />
        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <!-- Latest compiled and minified JavaScript -->
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>   
        
        <script>
        /*
         * Do nothing
         */
        function mvmNOP() {}

        /*
         * Force a page reload when a modal with class mvm is hidden. Used in confirmation dialogs
         */
        $('body').on('hidden.bs.modal', '.mvm', function () {
            window.location.reload();
          });

        /*
         * Enable HTML markup for auto loading content using Ajax into a container.
         *
         * Add an onClick event handler to all A link tags marked with data-ajax-load
         * attribute. The value of the data-ajax-load attribute is the selector used
         * to identify where to inject the data into the page. This is also used to
         * pull the data out of the response.
         *
         * The URL of the A tag is used to fetch the page.
         */     
        $(document).on('click', 'a[data-ajax-load]', function(event) {
            var tUrl = $(event.target).attr('href');
            var tTarget = $(event.target).data('ajax-load');
            $(tTarget).load(tUrl + ' ' + tTarget, 
                function(response, status, xhr) {
                  if (status == "error") {
                    console.log(xhr.status + " " + xhr.statusText);
                  }
                }
            );
            return false;
        });
        </script>
    </body>
</html>