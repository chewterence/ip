import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Duke duke = new Duke();
        Scanner sc = new Scanner(System.in);
        
        String input = "";
        String output = "";

        duke.displayWelcomeMessage();

        while(duke.isRunning()) {
            while(!sc.hasNext());
            if (sc.hasNext()) input = sc.nextLine();
            output = duke.getResponse(input);
            System.out.println(output);
        }

        sc.close();
    }
}