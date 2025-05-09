package factory;



public class CucumberHooks extends DriverFactory {

    public void setup() {
     if(driver==null)
     {
         initializeDriver();
     }

    }



    public void tearDown() {


    }





}
