/*
 * This Java source file was generated by the Gradle 'init' task.
 */


package BIL481_project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.*;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;




import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;




public class App {
    public String getGreeting() {
        return "Hello world. deneme";
    }

    public static boolean search(ArrayList<Integer> array, int e) {
        System.out.println("inside search");
        if (array == null) return false;
  
        for (int elt : array) {
          if (elt == e) return true;
        }
        return false;
      }


    public static void main(String[] args) {

        Logger logger = LogManager.getLogger(App.class);

        System.out.println(new App().getGreeting());

        int port = Integer.parseInt(System.getenv("PORT"));
        port(port);

        port(getHerokuAssignedPort());

        

        logger.error("Current port number:" + port);


        get("/", (req, res) -> "Hello, World");

        post("/compute", (req, res) -> {
          //System.out.println(req.queryParams("input1"));
          //System.out.println(req.queryParams("input2"));

          String input1 = req.queryParams("input1");
          java.util.Scanner sc1 = new java.util.Scanner(input1);
          sc1.useDelimiter("[;\r\n]+");
          java.util.ArrayList<Integer> inputList = new java.util.ArrayList<>();
          while (sc1.hasNext())
          {
            int value = Integer.parseInt(sc1.next().replaceAll("\\s",""));
            inputList.add(value);
          }
          sc1.close();
          System.out.println(inputList);


          String input2 = req.queryParams("input2").replaceAll("\\s","");
          Integer input2AsInt = Integer.parseInt(input2);

          Integer result = App.recursiveBinarySearch(inputList, 0, 10000,input2AsInt);

          Map<String, Integer> map = new HashMap<String, Integer>();
          map.put("result", result);
          return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());

        get("/compute",
        (rq, rs) -> {
          Map<String, String> map = new HashMap<String, String>();
          map.put("result", "not computed yet!");
          return new ModelAndView(map, "compute.mustache");
        },
        new MustacheTemplateEngine());





    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    
    public static Integer recursiveBinarySearch(ArrayList<Integer> arr, int firstElement, int lastElement, Integer elementToSearch) {

        // termination condition
        if (lastElement >= firstElement) {
            Integer mid = firstElement + (lastElement - firstElement) / 2;
    
            // if the middle element is our goal element, return its index
            if (arr.get(mid) == elementToSearch)
                return mid;
    
            // if the middle element is bigger than the goal element
            // recursively call the method with narrowed data
            if (arr.get(mid)> elementToSearch)
                return recursiveBinarySearch(arr, firstElement, mid - 1, elementToSearch);
    
            // else, recursively call the method with narrowed data
            return recursiveBinarySearch(arr, mid + 1, lastElement, elementToSearch);
        }
    
        return -1;
    }

}
