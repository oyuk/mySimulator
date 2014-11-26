import java.util.ArrayList;


public class MAG {
    int x;
    int y;
    ArrayList<Integer> mn_id;//このMAGドメイン内にいるMN
    int cost;


    boolean mn_exist;//このMAGドメイン内にMNが存在するか

    MAG(int x, int y) {
        this.x = x;
        this.y = y;

        mn_id = new ArrayList<Integer>();
        mn_exist = false;
        cost = 0;

    }

    void reg_mn(int id,boolean send_pbu,boolean active) {

        mn_id.add(id);

        //send_PBU()を実行する必要がある場合
        //mnがpaを移動した時(mnの状態を問わない)
        //mnがMAGを移動した時（mnがactive)
        if (send_pbu) {
            send_PBU();
        } else if (active) {
            send_PBU2();
        }

        mn_exist = true;

    }

    void first_reg_mn(int id) {

        mn_id.add(id);
        mn_exist = true;

    }

    void del_mn(int id) {

        for (int i = 0; i < mn_id.size(); i++) {
            if (mn_id.get(i) == id) {
                mn_id.remove(i);
            }
        }

        if (mn_id.isEmpty()) mn_exist = false;
    }

    void send_PBU() {

        Main.location_registration_cost += Main.C_leg;
        System.out.println("登録コスト"+Main.C_leg);
        Main.total_cost += Main.C_leg;
        Main.PBU_count++;
    }

    void send_PBU2() {

        Main.location_registration_cost += Main.C_leg2;
        System.out.println("登録コスト"+Main.C_leg2);
        Main.total_cost += Main.C_leg2;
        Main.PBU2_count++;
    }


    void state() {

        System.out.print("MAG(" + x + "," + y + "):");

        for (int i = 0; i < mn_id.size(); i++) {
            System.out.print(mn_id.get(i) + ",");
        }

        System.out.println(":cost:" + cost);
    }

}
