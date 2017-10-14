<%-- 
    Document   : Display
    Created on : Oct 7, 2017, 1:24:31 PM
    Author     : menna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
                  <link rel="stylesheet" href="style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Result</title>
    </head>
    <body style="font-family: Raleway">
        <br><br><br>
        <div class="calculate">
              <div class="calculate-triangle"></div>
              <h2 class="calculate-header"</h2>
            <div class="calculate-container">
        <span id ="expr">
            
        <%
            
            out.println(request.getParameter("expression")+ " =");
           
            out.println(request.getAttribute("result"));
            
            %>
            
        </span>
            </div>
        </div>
    </body>
    <script>
        var t = document.getElementById("expr");
        var text = t.innerHTML;
        var newText = "";
        for (var i = 0; i < text.length; i++) {
            if (text[i] === "+" || text[i] === "-") {
                newText += text.charAt(i).fontcolor("red");
            }
            else if (text[i] === "*" || text[i] === "/") {
                newText += text.charAt(i).fontcolor("blue");
            }
            else {
                newText += text.charAt(i).fontcolor("black");
            }
        }
        newText.fontsize(20);
        t.innerHTML = newText;
        </script>
</html>
