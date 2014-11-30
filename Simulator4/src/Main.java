/*提案手法
* セルを進行方向の向きに平行にページングする*/
public class Main {

    //フィールドの範囲
    final static int fieldx = 100;
    final static int fieldy = 100;

    //シミュレーション時間
    final static int SimuTime = 60*60*12;
    final static int refresh_rate = 1;
    //MNの数
    final static int nodeNum = 1;
    //MNがMAGにいる時間
    final static int mn_mag_stay_time[] = {648,324,215,162,107,81,64,53,43};
    //C_*の変数はコストを表す


    static final double C_AAA_info = Main.AAAinf*Main.d_LMA_AAA;
    static final double C_leg = (Main.exPBU+Main.PBA)*Main.d_LMA_MAG+C_AAA_info;
    static final double C_leg2 = (Main.exPBU+Main.PBA)*Main.d_LMA_MAG;


    static final double exRS = 0.7;
    static final double RS = 0.5;
    static final double RA = 0.7;
    static final double exPBU = 1.05;
    static final double PBU = 1;
    static final double PBA = 0.8;
    static final double AAAinf = 1;
    static final double PR = 0.8;
    static final int d_LMA_MAG = 5;
    static final int d_LMA_AAA = 1;



    static double total_cost = 0;
    static int paging_delay = 0;
    static double location_registration_cost=0;
    static int straight = 0;
    static int PBU_count = 0;
    static int PBU2_count = 0;

    //LMA
    final static int lambda = 3;
    final static int per_unit_time = 60*60;


    final static int active_timer[] = {60,120,180};

    //5km
    final static int paging_cost_array0[] = {9,6,6};
    final static int paging_delay_array0[] = {3,2,2};
    final static int paging_cost_array1[] = {6,6,9};
    final static int paging_delay_array1[] = {2,2,3};

    //+1
    final static int paging_cost_array2[] = {12,6,6,12};
    final static int paging_cost_array3[] = {6,6,12,12};
    final static int paging_delay_array2[] = {3,2,2,3};
    final static int paging_delay_array3[] = {2,2,3,3};;

    //10~20km +2
    final static int paging_cost_array14[] = {15,12,6,6,12};
    final static int paging_cost_array15[] = {12,6,6,12,15};
    final static int paging_cost_array16[] = {6,6,12,12,15};
    final static int paging_delay_array14[] = {4,3,2,2,3};
    final static int paging_delay_array15[] = {3,2,2,3,4};
    final static int paging_delay_array16[] = {2,2,3,3,4};

    //30~60 +3
    final static int paging_cost_array4[] = {18,18,12,6,6,12};
    final static int paging_cost_array5[] = {18,12,6,6,12,18};
    final static int paging_cost_array6[] = {12,6,6,12,18,18};
    final static int paging_cost_array7[] = {6,6,12,12,18,18};
    final static int paging_delay_array4[] = {4,3,3,2,2,4};
    final static int paging_delay_array5[] = {4,3,2,2,3,4};
    final static int paging_delay_array6[] = {3,2,2,2,3,4};
    final static int paging_delay_array7[] = {2,2,3,3,4,4};

    //+4
    final static int paging_cost_array17[] = {21,18,18,12,6,6,12};
    final static int paging_cost_array18[] = {21,18,12,6,6,12,18};
    final static int paging_cost_array19[] = {18,12,6,6,12,18,21};
    final static int paging_cost_array20[] = {12,6,6,12,18,18,21};
    final static int paging_cost_array21[] = {6,6,12,12,18,18,21};
    final static int paging_delay_array17[] = {5,4,4,3,2,2,3};
    final static int paging_delay_array18[] = {5,4,3,2,2,3,4};
    final static int paging_delay_array19[] = {4,3,2,2,3,4,5};
    final static int paging_delay_array20[] = {3,2,2,3,4,4,5};
    final static int paging_delay_array21[] = {2,2,3,3,4,4,5};

    //75 +5
    final static int paging_cost_array8[] = {24,21,21,15,15,6,6,15};
    final static int paging_cost_array9[] = {24,21,21,12,6,6,12,21};
    final static int paging_cost_array10[] = {24,18,12,6,6,12,18,24};
    final static int paging_cost_array11[] = {18,12,6,6,12,18,24,24};
    final static int paging_cost_array12[] = {12,6,6,12,18,18,24,24};
    final static int paging_cost_array13[] = {9,9,9,15,15,21,21,24};
    final static int paging_delay_array8[] = {5,4,4,3,3,2,2,3};
    final static int paging_delay_array9[] = {5,4,4,3,2,2,3,4};
    final static int paging_delay_array10[] = {5,4,3,2,2,3,4,5};
    final static int paging_delay_array11[] = {4,3,2,2,3,4,5,5};
    final static int paging_delay_array12[] = {3,2,2,3,4,4,5,5};
    final static int paging_delay_array13[] = {2,2,2,3,3,4,4,5};

    //+6
    final static int paging_cost_array22[] = {27,21,21,21,12,12,6,6,27};
    final static int paging_cost_array23[] = {27,21,21,15,15,6,6,15,27};
    final static int paging_cost_array24[] = {27,21,15,15,6,6,15,21,27};
    final static int paging_cost_array25[] = {21,15,15,6,6,15,21,21,27};
    final static int paging_cost_array26[] = {15,15,6,6,15,21,21,27,27};
    final static int paging_cost_array27[] = {15,6,6,15,15,21,21,27,27};
    final static int paging_cost_array28[] = {9,9,9,15,15,21,21,27,27};
    final static int paging_delay_array22[] = {5,5,4,4,3,3,2,2,5};
    final static int paging_delay_array23[] = {5,4,4,3,3,2,2,3,5};
    final static int paging_delay_array24[] = {5,4,3,3,2,2,3,4,5};
    final static int paging_delay_array25[] = {4,3,3,2,2,3,4,4,5};
    final static int paging_delay_array26[] = {3,3,2,2,3,4,4,5,5};
    final static int paging_delay_array27[] = {3,2,2,3,3,4,4,5,5};
    final static int paging_delay_array28[] = {2,2,2,3,3,4,4,5,5};

    //+7
    final static int paging_cost_array29[] = {30,30,21,21,15,15,15,6,6,30};
    final static int paging_cost_array30[] = {30,24,24,24,15,15,6,6,15,30};
    final static int paging_cost_array31[] = {24,24,24,15,15,6,6,15,30,30};
    final static int paging_cost_array32[] = {24,24,15,15,6,6,15,24,30,30};
    final static int paging_cost_array33[] = {24,15,15,6,6,15,24,24,30,30};
    final static int paging_cost_array34[] = {15,15,6,6,15,24,24,24,30,30};
    final static int paging_cost_array35[] = {15,6,6,15,15,24,24,24,30,30};
    final static int paging_cost_array36[] = {9,9,9,18,18,18,24,24,30,30};

    final static int paging_delay_array29[] = {5,5,4,4,3,3,3,2,2,5};
    final static int paging_delay_array30[] = {5,4,4,4,3,3,2,2,3,5};
    final static int paging_delay_array31[] = {4,4,4,3,3,2,2,3,5,5};
    final static int paging_delay_array32[] = {4,4,3,3,2,2,3,4,5,5};
    final static int paging_delay_array33[] = {4,3,3,2,2,3,4,4,5,5};
    final static int paging_delay_array34[] = {3,3,2,2,3,4,4,4,5,5};
    final static int paging_delay_array35[] = {3,2,2,3,3,4,4,4,5,5};
    final static int paging_delay_array36[] = {2,2,2,3,3,3,4,4,5,5};

    //+8
    final static int paging_cost_array37[] = {33,33,24,24,24,15,15,15,6,6,33};
    final static int paging_cost_array38[] = {33,27,27,27,27,15,15,6,6,15,33};
    final static int paging_cost_array39[] = {33,24,24,24,15,15,6,6,15,33,33};
    final static int paging_cost_array40[] = {24,24,24,15,15,6,6,15,33,33,33};
    final static int paging_cost_array41[] = {24,24,15,15,6,6,15,24,33,33,33};
    final static int paging_cost_array42[] = {24,15,15,6,6,15,24,24,33,33,33};
    final static int paging_cost_array43[] = {15,15,6,6,15,24,24,24,33,33,33};
    final static int paging_cost_array44[] = {15,6,6,15,15,24,24,24,33,33,33};
    final static int paging_cost_array45[] = {9,9,9,18,18,18,24,24,33,33,33};


    final static int paging_delay_array37[] = {5,5,4,4,4,3,3,3,2,2,5};
    final static int paging_delay_array38[] = {5,4,4,4,4,3,3,2,2,3,5};
    final static int paging_delay_array39[] = {5,4,4,4,3,3,2,2,3,5,5};
    final static int paging_delay_array40[] = {4,4,4,3,3,2,2,3,5,5,5};
    final static int paging_delay_array41[] = {4,4,3,3,2,2,3,4,5,5,5};
    final static int paging_delay_array42[] = {4,3,3,2,2,3,4,4,5,5,5};
    final static int paging_delay_array43[] = {3,3,2,2,3,4,4,4,5,5,5};
    final static int paging_delay_array44[] = {3,2,2,3,3,4,4,4,5,5,5};
    final static int paging_delay_array45[] = {2,2,2,3,3,3,4,4,5,5,5};


//    final static int paging_cost_array10[] = {21,21,15,15,9,9,9,24};
//    final static int paging_cost_array11[] = {21,21,15,9,9,9,15,24};
//    final static int paging_cost_array12[] = {21,15,9,9,9,15,21,24};
//    final static int paging_cost_array13[] = {9,9,9,15,15,21,21,24};


//    final static String file = "/Users/oky/Documents/proposal2.txt";
final static String file = "/Users/oky/Documents/new/test.txt";


    public static void main(String[] args) {


        Simu simu = new Simu();

        simu.simu_a();
      // simu.writeFile();

    }
}

