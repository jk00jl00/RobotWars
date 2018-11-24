import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by isjo16 on 2018-11-23.
 */
public class Launcher {
    public static void main(String[] args) {
        while(true) {
            Out.clrscr();
            System.out.println("Welcome to Robot Wars 3.0!\n" +
                    "When entering ticks you can either enter an amount of ticks to run before drawing by simply entering the desired amount as an integer e.g 100 which is 100 updates before drawing. \n" +
                    "Should you want to see each update enter the rate at which you wish to see the updates by puttting a forward slash (/) directly after the tick amount followed by the update rate\n" +
                    "e.g 100/4 100 updates with 4 updates every second.\n" +
                    "In order to exit the program enter /1 and in order to return here enter /2.\n" +
                    "If you wish to edit options type options here.\n" +
                    "To start the program enter the desired witdth and height e.g 20 20 for a 20 *  20 simulation.");

        /*
        * The current values are displayed under Options. To edit any value simply type the name followed by = and then the new value e.g fuelPerCell=5.
        * Remember to enter integers after the =.
        * Options:
        * fuelPerCell= (The amount of fuel that can spawn per cell)
        * maximumLightRadius= (The maximum radius light can spread from a light)
        * minimumLightRadius= (The minimum raduis the light will spread from a light)
        * maximumWallsPerChain= (The largest possible chain of walls, roughly 50% will end at the maximum/2)
        * minimumFuelValue= (The minimum energy given to a robot picking up a fuel cell)
        * maximumFuelValue= (The maximum energy given to a robot picking up a fuel cell)
        * cellWidth= (The width of each generation cell)
        * cellHeight= (The height of each generation cell)
        */
            Scanner sc = new Scanner(System.in);
            String s = sc.nextLine();
            int width = 9;
            int height = 9;
            if(s.toLowerCase().matches("options")){
                while(true) {
                    Out.clrscr();
                    sc.reset();
                    System.out.println("The current values are displayed under Options.\n" +
                            "To edit any value simply type the name followed by = and then the new value e.g fuelPerCell=5.\n" +
                            "Remember to enter integers after the =.\n" +
                            "To return to the menu type back or /b.\n" +
                            "\n" +
                            "Options:\n" +
                            "fuelPerCell="+ World.getFuelPerCell() +" (The amount of fuel that can spawn per cell)\n" +
                            "maximumLightRadius="+World.getMaxLightRadius()+" (The maximum radius light can spread from a light)\n" +
                            "minimumLightRadius="+World.getMinLightRadius()+" (The minimum raduis the light will spread from a light)\n" +
                            "maximumWallsPerChain="+World.getMaxwalls()+" (The largest possible chain of walls, roughly 50% will end at the maximum/2)\n" +
                            "minimumFuelValue="+World.getMinFuelValue()+" (The minimum energy given to a robot picking up a fuel cell)\n" +
                            "maximumFuelValue="+World.getMaxFuelValue()+" (The maximum energy given to a robot picking up a fuel cell)\n" +
                            "cellWidth="+World.getBoxWidth()+" (The width of each generation cell)\n" +
                            "cellHeight="+World.getBoxHeight()+" (The height of each generation cell)");
                    s = sc.nextLine();
                    s = s.toLowerCase();
                    if (s.matches("back") || s.matches("/b")) {
                        break;
                    } else if (s.matches("[a-z]+=[0-9]+")) {
                        Pattern pattern = Pattern.compile("[a-z]+");
                        Matcher matcher = pattern.matcher(s);
                        matcher.find();

                        String name = matcher.group();

                        pattern = Pattern.compile("[0-9]+");
                        matcher = pattern.matcher(s);
                        matcher.find();
                        int num = 0;
                        try {
                            num = Integer.parseInt(matcher.group());
                        } catch (NumberFormatException e) {
                            sc.reset();
                            System.out.println("Remember to enter a number after the =, it has to be an integer.");
                            sc.nextLine();
                            continue;
                        }

                        switch (name) {
                            case "fuelpercell":
                                World.setFuelPerCell(num);
                                break;
                            case "maximumlightradius":
                                World.setMaxLightRadius(num);
                                break;
                            case "minimumlightradius":
                                World.setMinLightRadius(num);
                                break;
                            case "maximumwallsrerchain":
                                World.setMaxwalls(num);
                                break;
                            case "minimumfuelvalue":
                                World.setMinFuelValue(num);
                                break;
                            case "maximumfuelvalue":
                                World.setMaxFuelValue(num);
                                break;
                            case "cellwidth":
                                World.setBoxWidth(num);
                                break;
                            case "cellheight":
                                World.setBoxHeight(num);
                                break;
                        }

                    }
                }
                continue;
            } else if(s.matches("/1")){
                return;
            } else {
                try {
                    String w = "";
                    String h = "";
                    int a = 0;
                    while (a < s.length() && !s.substring(a, a + 1).matches(" ")) {
                        w += s.substring(a, a + 1);
                        a++;
                    }
                    a++;
                    while (a < s.length()) {
                        h += s.substring(a, a + 1);
                        a++;
                    }

                    width = Integer.parseInt(w);
                    height = Integer.parseInt(h);
                } catch (NumberFormatException e) {
                    System.out.println("Remember to enter two possitive integers or 'options'");
                    sc.reset();
                    sc.nextLine();
                    continue;
                }
            }

            sc = new Scanner(System.in);


            
            new Controller(width, height).start();
            System.out.println("If you wish to exit please enter 'Exit'");
            s = sc.nextLine();
            if(s.toLowerCase().matches("exit")) break;
        }
    }

}
