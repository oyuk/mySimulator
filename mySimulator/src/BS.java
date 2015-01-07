/**
 * Created by oky on 14/11/27.
 */
public class BS {
    private int x;
    private int y;
    int cost;


    boolean mn_exist;//このBSドメイン内にMNが存在するか

    BS(int x, int y) {
        this.x = x;
        this.y = y;
        mn_exist = false;
        cost = 0;

    }
    
    void reg_mn(boolean isLocationUpdate,boolean active) {

        //mnがpaを移動した時(mnの状態を問わない)
        //mnがBSを移動した時（mnがactive)
        if (isLocationUpdate || active) {
            locationUpdate();
        }

        mn_exist = true;
    }

    void first_reg_mn() {
        mn_exist = true;
    }

    void del_mn() {
         mn_exist = false;
    }

    void locationUpdate() {

        Main.location_registration_num++;

    }


    void state() {

//        System.out.print("BS(" + x + "," + y + "):");
//        for (int i = 0; i < mn_id.size(); i++) {
//            System.out.print(mn_id.get(i) + ",");
//        }
//
//        System.out.println(":cost:" + cost);
    }
}
