<%--
  Created by IntelliJ IDEA.
  User: lepre
  Date: 15.06.2022
  Time: 20:06
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="description" content="Cats site">
    <meta name="author" content="Vitalii Seniuk">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100;0,300;0,700;1,300&display=swap"
          rel="stylesheet">
    <link rel="icon" type="image/x-icon" href="assets/favicon.ico">
    <title>Main</title>

</head>


<body class="menu">

<header class="header">
    <div class="container">
        <div class="header_inner">
            <div class="logo">Main</div>

            <nav>
                <a class="nav_link_hold" href="#">Main</a>

                <a class="nav_link" href="main.html">Cats</a>
                <a class="nav_link" href="rate.html">Rate Cats</a>
                <a class="nav_link" href="info.html">Info</a>

            </nav>
        </div>
    </div>
</header>

<div class="intro">
    <div class="container">
        <div class="intro_inner">
            <h1 class="intro_title">Let's see cats!</h1>
            <a class="intro_btn" href="main.html"><span class="flip">go next!</span></a>
        </div>
    </div>

</div>


</body>

</html>