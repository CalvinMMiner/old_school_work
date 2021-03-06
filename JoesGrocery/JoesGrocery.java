import java.text.DecimalFormat;
import java.util.Random;
/**
 * the main class, contains a driver method that uses the register objects
 * methods to simulate a store
 *
 * @Calvin M Miner
 * @10/10/19
 */
public class JoesGrocery
{
    /**
     * the main method declare the ints and arrays required to run the 
     * programs main logic and contains the main logic its self, wich runs
     * from a pair of for loops that simulate one day at the registers of a 
     * Grocery store
     */
    static int openRegisters=1;
    public static void main()
    {
        DecimalFormat df = new DecimalFormat("#.0");
        Register[] registers = new Register[5];
        Random rnd = new Random();
        double TotalCheckOutTime=0;
        double TotalCustomers=0;
        int overload=0;
        for(int i =0;i<5;i++)//initilizes five register objects for latter use
        {
            registers[i]= new Register();
        }
        for(int z = 0; z < 600; z++)
        {
            //adds new customers to the store and calls the relevent methods
            if (z % 2 == 0)
            {
                int NewCustomers = rnd.nextInt(3);
                TotalCustomers+=NewCustomers;
                for(int i = 0; i<NewCustomers;i++)
                {
                    int checkoutTime = rnd.nextInt(10)+1;
                    TotalCheckOutTime+=checkoutTime;
                    overload += checkRegisters(registers,rnd,checkoutTime);
                }
            }
            //prosseses the customers curently in line
            for (int i = 0; i < 5; i++)
            {
                registers[i].checkOutCustomers();
            }
        }
        do//keeps looping throght the registers to empty their lines
        {
            int emptyLines=0;
            for (int i = 0; i < 5; i++)
            {
                registers[i].checkOutCustomers();
                if(registers[i].getCustomersInLine()==0)
                {
                    emptyLines++;
                }
            }
            if(emptyLines==5)
                break;
        }while(true);
        System.out.println("\f\nRegisters opened: "+(openRegisters));
        System.out.println("Total Customers: "+TotalCustomers);
        System.out.println("Total CheckOut Time: "+TotalCheckOutTime);
        System.out.println("average CheckOut time: "
            + df.format(TotalCheckOutTime/TotalCustomers));
        System.out.println("Total  overloads : "+overload);
    }
 
    /**
     * a method that check the registers to see the length of their 
     * lines, then adds a customer to the shortest open line. if 
     * there are no open lines a customer is added to a random line
     * 
     * @param registers - the registers that the customer are added to
     * @param rnd - a random class used if there are no open registers
     * @return 0 - returns 0 if there is an empty register
     * @return 1 - returns 1 if there is no open register
     */
    public static int checkRegisters(Register[] registers,Random rnd,int checkoutTime)
    {
        for (int i = 0; i < openRegisters; i++)
        {
            int shortestLine=0;
            for (int o = 0; o < openRegisters; o++)
            {
                if (registers[o].getCustomersInLine()
                <registers[shortestLine].getCustomersInLine())
                {
                    shortestLine=o;
                }
            }
            if(registers[shortestLine].getCustomersInLine()<3)
            {
                registers[shortestLine].newCustomer(checkoutTime);
                return 0;
            }
            else if (openRegisters<5)
            {
                openRegisters+=1;
            }
        }
        registers[rnd.nextInt(5)].newCustomer(checkoutTime);
        return 1;
    }
}