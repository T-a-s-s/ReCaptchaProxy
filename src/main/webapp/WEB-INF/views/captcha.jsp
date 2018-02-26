<html>
  <head>
     <title>Captcha - Tokiomarine</title>
     <script src="https://www.google.com/recaptcha/api.js" async defer></script>
  </head>
  <body>
  	<h1>Captcha Tokio Marine</h1>
    <form action="../verifyCaptcha" method="POST">
      <div class="g-recaptcha" data-sitekey="${recaptcha_site_key}"></div>
      <br/>
      <input type="submit" value="Submit">
    </form>
  </body>
</html>