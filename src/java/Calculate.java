
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Character.isDigit;
import java.util.Collections;
import java.util.Stack;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author menna
 */
@WebServlet(urlPatterns = {"/Calculate"})
public class Calculate extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            Boolean error = false;
            String expression = request.getParameter("expression");
            
            
            // this loop ensures that the string is correct (only digits and operators)
            // and that it is formatted by having a space before and after each operator
            for (int i = 0; i < expression.length(); i++) {
                if (expression.charAt(i) == '+' || expression.charAt(i) == '-'
                        || expression.charAt(i) == '*' || expression.charAt(i) == '/') {
                    // if operator in beginning or end -> cant evaluate
                    if (i == 0 || i == expression.length() - 1) error = true;
                   
                    else {
                        if (expression.charAt(i - 1) != ' ' && expression.charAt(i + 1) != ' ') {
                            String before = expression.substring(0, i);
                            String modified = " " + expression.charAt(i) + " ";
                            String after = expression.substring(i + 1, expression.length());
                            expression = before + modified + after;
                            i += 3;
                         
                        }
                        else if (expression.charAt(i - 1) != ' ' && expression.charAt(i + 1) == ' ') {
                            String before = expression.substring(0, i);
                            String after = expression.substring(i + 1, expression.length());
                            String modified = " " + expression.charAt(i);
                            expression = before + modified + after;
                            i += 3;
                        }
                        else if (expression.charAt(i - 1) == ' ' && expression.charAt(i + 1) != ' ') {
                            String before = expression.substring(0, i);
                            String after = expression.substring(i + 1, expression.length());
                            String modified = expression.charAt(i) + " ";
                            expression = before + modified + after;
                            i += 2;
                        }
                    }
                    
                }
                 else if (!isDigit(expression.charAt(i)) && expression.charAt(i) != ' ')
                        error = true;
                    
            }
            if (error) expression = "Error parsing the expression.";
            else {
                Stack<String> operators = new Stack();
                Stack<String> operands = new Stack();
                
                String[] array = expression.split(" ");

                for (String item : array) {
                    switch (item) {
                        case "+": operators.push(item);
                        break;
                        case "-": operators.push(item);
                        break;
                        case "/": operators.push(item);
                        break;
                        case "*": operators.push(item);
                        break;
                        default:
                            operands.push(item);
                            if (!operators.empty()) {
                            if (operators.peek().equals("*") || operators.peek().equals("/")) {
                                Double second = Double.parseDouble((String) operands.pop());
                                Double first = Double.parseDouble((String) operands.pop());
                                
                                Double res;
                                if (operators.peek().equals("*")) res = first * second;
                                else res = first / second;
                                operands.push(res.toString());
                               
                                operators.pop();
                            }
                            }
                    }
                }

                Collections.reverse(operands);
                Collections.reverse(operators);

                    
                  while(!operators.empty()) {
                      String f = (String) operands.pop();
                      String s = (String) operands.pop();
                     
                      Double second = Double.parseDouble(s);
                      Double first = Double.parseDouble(f);
                     
                      Double res;
                     
                      if (operators.peek().equals("+")) res = first + second;
                      else res = first - second;
                      operands.push(res.toString());
                      operators.pop();
                  }
                  
                  expression = operands.peek().toString();
                  
            }         
            
            request.setAttribute("result", expression);
           
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("Display.jsp");
            requestDispatcher.forward(request, response);
            
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
