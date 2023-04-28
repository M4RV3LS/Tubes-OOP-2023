public class PrintASCII {
    public void printWelcome(){
        System.out.println("───▄▀▀▀▄▄▄▄▄▄▄▀▀▀▄───");
        System.out.println("───█▒▒░░░░░░░░░▒▒█───");
        System.out.println("────█░░█░░░░░█░░█────");
        System.out.println("─▄▄──█░░░▀█▀░░░█──▄▄─");
        System.out.println("█░░█─▀▄░░░░░░░▄▀─█░░█");
        System.out.println("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█");
        System.out.println("█░░╦─╦╔╗╦─╔╗╔╗╔╦╗╔╗░░█");
        System.out.println("█░░║║║╠─║─║─║║║║║╠─░░█");
        System.out.println("█░░╚╩╝╚╝╚╝╚╝╚╝╩─╩╚╝░░█");
        System.out.println("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█");
    }

    public void printShop(){
        System.out.println(" .----------------.  .----------------.  .----------------.  .----------------. ");
        System.out.println(" | .--------------. || .--------------. || .--------------. || .--------------. |");
        System.out.println(" ||     _______   | || |  ____  ____  | || |     ____     | || |   ______     | |");
        System.out.println(" ||   /  ___  |   | || | |_   ||   _| | || |   .'    `.   | || |  |_   __ \\   | |");
        System.out.println(" ||   | (__ \\_|   | || |   | |__| |   | || |  /  .--.  \\  | || |    | |__) |  | |");
        System.out.println(" ||   '.___`-.    | || |   |  __  |   | || |  | |    |  ||| || |    |  ___/   | |");
        System.out.println(" ||  |`\\____) |   | || |  _| |  | |_  | || |  \\  `--'  /  | || |   _| |_      | |");
        System.out.println(" ||  |_______.'   | || | |____||____| | || |   `.____.'   | || |  |_____|     | |");
        System.out.println(" ||               | || |              | || |              | || |              | |");
        System.out.println(" |'--------------'  || '--------------' || '--------------' || '--------------' |");
        System.out.println(" |'--------------'  || '--------------' || '--------------' || '--------------' |");
        System.out.println(" '----------------'   '----------------'   '----------------'   '----------------' ");
    }

    public void printStats()
    {
        System.out.println("  ______   ______  ______   ______  ");
        System.out.println(" /\\  ___\\ /\\__  _\\/\\  __ \\ /\\__  _\\ ");
        System.out.println(" \\ \\___  \\\\/_/\\ \\/\\ \\  __ \\\\/_/\\ \\/ ");
        System.out.println("  \\/\\_____\\  \\ \\_\\ \\ \\_\\ \\_\\  \\ \\_\\ ");
        System.out.println("   \\/_____/   \\/_/  \\/_/\\/_/   \\/_/ ");
    }

    public void printEat()
    {
        System.out.println("              ,-------------------.");
        System.out.println("             ( Tried it, loved it! )");
        System.out.println("        munch `-v-----------------'");
        System.out.println(" ,---'. --------'");
        System.out.println(" C.^o^|   munch");
        System.out.println(" (_,-_)");
        System.out.println(",--`|-. ");
        System.out.println("|\\    ]\\__n_");
        System.out.println("||`   '---E/   Ojo98");
    }

    public void printMasak()
    {
        System.out.println("     ( ( (              ))     ");
        System.out.println("      ) ) )           ((       ");
        System.out.println("     ( ( (          ___o___");
        System.out.println("   '. ___ .'        |     |====O");
        System.out.println("  '  (> <) '        |_____|");
        System.out.println("--ooO-(_)-Ooo--------------------");
    }

    public void printupgradeRumah()
    {
        System.out.println("                             ___");
        System.out.println("                     /======/");
        System.out.println("            ____    //      \\___       ,/");
        System.out.println("             | \\\\  //           :,   ./");
        System.out.println("     |_______|__|_//            ;:; /");
        System.out.println("    _L_____________\\o           ;;;/");
        System.out.println("____(CCCCCCCCCCCCCC)____________-/___________________");
    }
        
    //Tetsing
    public static void main(String[] args) {
        PrintASCII test = new PrintASCII();
        test.printWelcome();
        test.printShop();
        test.printStats();
        test.printEat();
        test.printMasak();
        test.printupgradeRumah();
    }
}