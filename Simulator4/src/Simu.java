
import java.io.*;
import java.math.BigDecimal;
import java.util.Random;


public class Simu {


    Field field;
    int time = 0;
    Random ran;
    MN mn;
    LMA lma;
    MNlist mNlist;
    BigDecimal bi;


    public Simu() {
        // TODO 自動生成されたコンストラクター・ス

        field = Field.getInstance();
        field.initField();
        ran = new Random();
        mNlist = new MNlist();

        for (int i = 0; i < Main.nodeNum; i++) {

            int x = ran.nextInt(Main.fieldx);
            int y = ran.nextInt(Main.fieldy);


//			mn = new MN(x,y,i,Main.mn_mag_stay_time[ran.nextInt(Main.mn_mag_stay_time.length)]);
            mn = new MN(x, y, i, Main.mn_mag_stay_time[0],2);

            //mnの初期MAGへの登録
            field.initial_mn_reg(x, y, i);

            mNlist.add(mn);

        }

        lma = new LMA(mNlist);
        lma.allocate_interval_time();

    }


    void simu_a() {

        time = 0;
        System.out.println("Simulation time:" + Main.SimuTime + "\nField:" + Main.fieldx + "*" + Main.fieldy +
                "\nNode" + ":" + Main.nodeNum);

        System.out.println("\nsimulation start");

        for (int i = 0; i < Main.nodeNum; i++) mNlist.get(i).state();

        System.out.println();


        while (true) {
            time += Main.refresh_rate;

           // System.out.println(time);

            for (int i = 0; i < Main.nodeNum; i++) mNlist.get(i).update(time);

            lma.update(time);

            if (time >= Main.SimuTime) break;

        }

        System.out.println("simulation finish\n");

        System.out.println();
        field.print();
        System.out.println();
        for (int i = 0; i < Main.nodeNum; i++) {
            mNlist.get(i).state();
            mNlist.get(i).print_move_history();
        }
        System.out.println();
        lma.state();

        System.out.println("LMA:location registration cost:"+Main.location_registration_cost);
        //System.out.println("straight:"+Main.straight);
        System.out.println("total_cost:" + (Main.total_cost+lma.total_paging_cost));
        System.out.println("total_paging_delay:" + Main.paging_delay);
        System.out.println("average_paging_delay:" + ((double)Main.paging_delay/(double)lma.session_come_count));
        System.out.println("pbu_count:" + Main.PBU_count);
        System.out.println("pbu2_count:" + Main.PBU2_count);

    }


    void writeFile(){


        File f = new File(Main.file);
        bi = new BigDecimal((double)Main.paging_delay/(double)lma.session_come_count);

        String totalCost = String.valueOf(Main.total_cost+lma.total_paging_cost);
        String regCost = String.valueOf(Main.location_registration_cost);
        String pagingCost = String.valueOf(lma.total_paging_cost);
        String pagingCost_ave = String.valueOf(lma.total_paging_cost /lma.session_come_count);
        String pagingDelay_ave = String.valueOf(bi.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        String pa_m_c = String.valueOf(mNlist.get(0).pa.pa_move_count);

        try {

            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f,true)));

            pw.println(totalCost+" "+regCost+" "+pagingCost+" "+pagingCost_ave+" "+pagingDelay_ave+" "+pa_m_c);
            pw.close();

        }catch (IOException e){
            System.out.println(e);
        }
    }
}
